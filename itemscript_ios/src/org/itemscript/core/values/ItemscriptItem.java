/*
 * Copyright © 2010, Data Base Architects, Inc. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice,
 *       this list of conditions and the following disclaimer in the documentation
 *       and/or other materials provided with the distribution.
 *     * Neither the names of Kalinda Software, DBA Software, Data Base Architects,
 *       nor the names of its contributors may be used to endorse or promote products derived
 *       from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
 * SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * 
 * Author: Jacob Davies
 */

package org.itemscript.core.values;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.itemscript.core.ItemscriptSystem;
import org.itemscript.core.JsonSystem;
import org.itemscript.core.Params;
import org.itemscript.core.connectors.GetCallback;
import org.itemscript.core.connectors.PutCallback;
import org.itemscript.core.connectors.RemoveCallback;
import org.itemscript.core.events.Event;
import org.itemscript.core.events.EventType;
import org.itemscript.core.events.Handler;
import org.itemscript.core.events.HandlerReg;
import org.itemscript.core.exceptions.ItemscriptError;
import org.itemscript.core.url.Fragment;
import org.itemscript.core.url.Url;
import org.itemscript.core.util.JsonAccessHelper;

/**
 * The implementation class for {@link JsonItem}.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 */
public final class ItemscriptItem implements JsonItem {
    private final JsonSystem system;
    private final Url source;
    private JsonValue value;
    private final JsonObject meta;
    private Map<String, Handler> handlers;

    protected ItemscriptItem(JsonSystem system, Url source, JsonObject meta, JsonValue value) {
        this.system = system;
        this.value = value;
        this.source = source;
        this.meta = meta;
        if (value == null) { throw ItemscriptError.internalError(this, "constructor.value.was.null"); }
        ((ItemscriptValue) value).setItem(this);
    }

    protected ItemscriptItem(JsonSystem system, Url source, JsonValue value) {
        this(system, source, null, value);
    }

    @Override
    public HandlerReg addHandler(Handler handler) {
        if (handlers == null) {
            handlers = new HashMap<String, Handler>();
        }
        String id = system().util()
                .generateB64id();
        handlers.put(id, handler);
        return new HandlerReg(this, id);
    }

    @Override
    public PutResponse createArray(String url) {
        return put(url, system().createArray());
    }

    @Override
    public PutResponse createObject(String url) {
        return put(url, system().createObject());
    }

    @Override
    public void detachValue() {
        if (value() == null) { return; }
        ((ItemscriptValue) value).setItem(null);
        this.value = null;
        this.handlers = null;
    }

    /**
     * Detach the value from this JsonItem. Only called by {@link ItemscriptValue#detachFromItem}.
     */
    void detachFromValue() {
        this.value = null;
        this.handlers = null;
    }

    private void dispatchEvent(Event event) {
        // Must copy this first to prevent concurrent modification problems if an event handler adds a new event handler to this
        // item.
        Collection<Handler> values = handlers.values();
        Handler[] handlersValues = new Handler[values.size()];
        int i = 0;
        for (Handler handler : values.toArray(handlersValues)) {
            handlersValues[i] = handler;
            ++i;
        }
        for (Handler handler : handlersValues) {
            handler.handle(event);
        }
    }

    @Override
    public JsonValue get(String url) {
        return get(system().util()
                .createUrl(url));
    }

    @Override
    public void get(String url, GetCallback callback) {
        get(system().util()
                .createUrl(url), callback);
    }

    JsonValue get(Url url) {
        if (isFragmentOnly(url)) {
            if (url.fragmentString()
                    .length() == 0) { return value; }
            return ((ItemscriptContainer) value).getByPath(url.fragmentString());
        } else {
            return ((ItemscriptSystem) system).get(system().util()
                    .createRelativeUrl(source, url));
        }
    }

    void get(Url url, GetCallback callback) {
        if (isFragmentOnly(url)) {
            try {
                callback.onSuccess(get(url));
            } catch (ItemscriptError e) {
                callback.onError(e);
            }
        } else {
            ((ItemscriptSystem) system).get(system().util()
                    .createRelativeUrl(source, url), callback);
        }
    }

    @Override
    public JsonArray getArray(String key) {
        return JsonAccessHelper.asArray(get(key));
    }

    @Override
    public byte[] getBinary(String key) {
        return JsonAccessHelper.asBinary(get(key));
    }

    @Override
    public Boolean getBoolean(String key) {
        return JsonAccessHelper.asBoolean(get(key));
    }

    @Override
    public Double getDouble(String key) {
        return JsonAccessHelper.asDouble(get(key));
    }

    @Override
    public Float getFloat(String key) {
        return JsonAccessHelper.asFloat(get(key));
    }

    @Override
    public Integer getInt(String key) {
        return JsonAccessHelper.asInt(get(key));
    }

    @Override
    public Long getLong(String key) {
        return JsonAccessHelper.asLong(get(key));
    }

    @Override
    public Object getNative(String key) {
        return JsonAccessHelper.asNative(get(key));
    }

    private JsonValue getNext(Url url, JsonValue next, String key) {
        if (next.isContainer()) {
            next = next.asContainer()
                    .getValue(key);
        } else {
            throw new ItemscriptError("error.itemscript.JsonSystem.getNext.next.was.not.a.container",
                    new Params().p("next", next + "")
                            .p("fragment", url.fragmentString())
                            .p("key", key));
        }
        return next;
    }

    @Override
    public JsonObject getObject(String key) {
        return JsonAccessHelper.asObject(get(key));
    }

    @Override
    public String getString(String key) {
        return JsonAccessHelper.asString(get(key));
    }

    @Override
    public JsonValue getValue(String url) {
        return get(url);
    }

    boolean hasHandlers() {
        return handlers != null;
    }

    private boolean isFragmentOnly(Url url) {
        if (url == null) { return false; }
        return (url.fragment() != null) && (!url.hasScheme()) && (!url.hasPath()) && (!url.hasQuery());
    }

    @Override
    public JsonObject meta() {
        return meta;
    }

    /**
     * Notify this item that the value attached to it has had something put in it.
     * 
     * @param fragment The fragment of the value that has been put; "#" if we put a new value for this item.
     */
    public void notifyPut(String fragment) {
        if (handlers != null && handlers.size() > 0) {
            dispatchEvent(new Event(EventType.PUT, fragment, value()));
        }
    }

    /**
     * Notify this item that the value attached to it has had something removed from it.
     * 
     * @param fragment The fragment of the value that has been removed; "#" if we are removing this item.
     */
    public void notifyRemove(String fragment) {
        if (handlers != null && handlers.size() > 0) {
            dispatchEvent(new Event(EventType.REMOVE, fragment, value()));
        }
    }

    @Override
    public PutResponse put(String url, Boolean value) {
        return putValue(url, system().createBoolean(value));
    }

    @Override
    public PutResponse put(String url, byte[] value) {
        return putValue(url, system().createString(value));
    }

    @Override
    public PutResponse put(String url, Double value) {
        return putValue(url, system().createNumber(value));
    }

    @Override
    public PutResponse put(String url, Float value) {
        return putValue(url, system().createNumber(value));
    }

    @Override
    public PutResponse put(String url, Integer value) {
        return putValue(url, system().createNumber(value));
    }

    @Override
    public PutResponse put(String url, JsonValue value) {
        return putValue(system().util()
                .createUrl(url), value);
    }

    @Override
    public void put(String url, JsonValue value, PutCallback callback) {
        put(system().util()
                .createUrl(url), value, callback);
    }

    @Override
    public PutResponse put(String url, Long value) {
        return putValue(url, system().createString(value));
    }

    @Override
    public PutResponse put(String url, String value) {
        return putValue(url, system().createString(value));
    }

    private void put(Url url, JsonValue value, PutCallback callback) {
        if (isFragmentOnly(url)) {
            // If it's only a fragment, put the value inside the root value of this item and call
            // the callback immediately.
            try {
                callback.onSuccess(putValue(url, value));
            } catch (ItemscriptError e) {
                callback.onError(e);
            }
        } else {
            // Otherwise interpret it as relative to the source URL of this item.
            ((ItemscriptSystem) system).put(system().util()
                    .createRelativeUrl(source, url), value, callback);
        }
    }

    @Override
    public PutResponse putNative(String url, Object value) {
        return put(url, system().createNative(value));
    }

    @Override
    public PutResponse putValue(String url, JsonValue value) {
        return putValue(system().util()
                .createUrl(url), value);
    }

    private PutResponse putValue(Url url, JsonValue value) {
        // First, determine if the URL was just a fragment, or whether it was something to be interpreted as
        // relative to the source of this item.
        if (isFragmentOnly(url)) {
            if (url.fragmentString()
                    .length() > 0) {
                // Find (or create) the last container...
                if (!value().isContainer()) { throw ItemscriptError.internalError(this,
                        "putValue.value.was.not.a.container", "#" + url.fragmentString()); }
                JsonContainer container = value().asContainer();
                container.putByPath(url.fragmentString(), value);
            } else {
                // If the fragment was of zero length, replace the root value of this item with the supplied value.
                JsonValue prevValue = value();
                ((ItemscriptValue) prevValue).setItem(null);
                ((ItemscriptValue) value).setItem(this);
                this.value = value;
                notifyPut("#");
            }
            return new ItemscriptPutResponse(system().util()
                    .createRelativeUrl(source(), url + "") + "", null, value);
        } else {
            // If the URL was anything other than a fragment, interpret it as being relative to the source of this
            // item and put the value there.
            return ((ItemscriptSystem) system).put(system().util()
                    .createRelativeUrl(source, url), value);
        }
    }

    @Override
    public RemoveResponse remove(String url) {
        return remove(system().util()
                .createUrl(url));
    }

    @Override
    public void remove(String url, RemoveCallback callback) {
        remove(system().util()
                .createUrl(url), callback);
    }

    private RemoveResponse remove(Url url) {
        if (isFragmentOnly(url)) {
            // If it's a fragment only, and has at least one key, remove the corresponding value from this item.
            Fragment fragment = url.fragment();
            if (fragment.size() > 0) {
                JsonContainer container = value().asContainer();
                // Find the last container...
                for (int i = 0; i < (fragment.size() - 1); ++i) {
                    String key = fragment.get(i);
                    JsonContainer next = getNext(url, container, key).asContainer();
                    // If the next container is null, just return.
                    if (next == null) { return new ItemscriptRemoveResponse(null); }
                    container = next;
                }
                container.removeValue(fragment.lastKey());
            } else {
                // If it was a fragment only but with no keys, remove this item.
                system.remove(source());
            }
        } else {
            // Otherwise treat the URL as relative to the source of this item and call system.remove().
            ((ItemscriptSystem) system).remove(system().util()
                    .createRelativeUrl(source, url));
        }
        return new ItemscriptRemoveResponse(null);
    }

    private void remove(Url url, RemoveCallback callback) {
        // If it's a fragment only, and has at least one key, remove that value from this item.
        if (isFragmentOnly(url)) {
            if (url.fragment()
                    .size() > 0) {
                try {
                    callback.onSuccess(remove(url));
                } catch (ItemscriptError e) {
                    callback.onError(e);
                }
                return;
            }
        }
        // Otherwise treat the URL as relative to the source of this item and call system.remove().
        ((ItemscriptSystem) system).remove(system().util()
                .createRelativeUrl(source, url), callback);
    }

    @Override
    public void removeHandler(HandlerReg reg) {
        if (handlers != null) {
            handlers.remove(reg.id());
        }
    }

    @Override
    public RemoveResponse removeValue(String url) {
        remove(url);
        return new ItemscriptRemoveResponse(null);
    }

    @Override
    public String source() {
        return source + "";
    }

    @Override
    public JsonSystem system() {
        return system;
    }

    @Override
    public String toString() {
        return "[ItemscriptItem sourceUrl=" + source + " value=" + value.toCompactJsonString() + "]";
    }

    @Override
    public JsonValue value() {
        return value;
    }
}