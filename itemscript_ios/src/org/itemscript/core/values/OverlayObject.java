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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.exceptions.ItemscriptError;
import org.itemscript.core.util.JsonAccessHelper;

/**
 * A pseudo-JsonObject that wraps a list of other JsonObjects. When a value is requested from this
 * OverlayObject, it is looked for in each of the objects inside this one in turn; the first object that
 * has a value under that key causes that value to be returned. If none of the objects contains a value
 * under that key, {@link #getValue(String)} returns null.
 * <p>
 * All of the JsonObject methods that change the state of the object are not supported; objects of this class
 * also cannot be placed in JsonContainers. Some methods that do not change state are not supported either,
 * although they could be at some point if they were needed. 
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 */
public class OverlayObject extends WeakContainer implements JsonObject, ToJsonStringWithIndent {
    private final List<JsonObject> objects;

    /**
     * Create a new OverlayObject from the given list of JsonObjects.
     * 
     * @param system The associated JsonSystem.
     * @param objects The list of objects to search.
     */
    public OverlayObject(JsonSystem system, List<JsonObject> objects) {
        super(system);
        this.objects = objects;
        for (int i = 0; i < objects.size(); ++i) {
            JsonObject object = objects.get(i);
            if (object == this) { throw ItemscriptError.internalError(this,
                    "constructor.objects.list.contained.this.object", objects + ""); }
        }
    }

    @Override
    public JsonObject asObject() {
        return this;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsKey(Object key) {
        if (!(key instanceof String)) { return false; }
        return getValue((String) key) != null;
    }

    @Override
    public boolean containsKey(String key) {
        return getValue(key) != null;
    }

    @Override
    public boolean containsValue(Object arg0) {
        // This could be implemented, but has not been necessary so far.
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonObject copy() {
        return JsonAccessHelper.copyObject(system(), this);
    }

    @Override
    public Set<java.util.Map.Entry<String, JsonValue>> entrySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonValue get(Object key) {
        if (!(key instanceof String)) { return null; }
        return getValue((String) key);
    }

    @Override
    public JsonValue getValue(String key) {
        for (int i = 0; i < objects.size(); ++i) {
            JsonValue value = objects.get(i)
                    .get(key);
            if (value != null) { return value; }
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        // This is expensive...
        return keySet().size() == 0;
    }

    @Override
    public boolean isObject() {
        return true;
    }

    @Override
    public Set<String> keySet() {
        Set<String> keySet = new HashSet<String>();
        for (int i = 0; i < objects.size(); ++i) {
            keySet.addAll(objects.get(i)
                    .keySet());
        }
        return keySet;
    }

    @Override
    public JsonObject p(String key, Boolean value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonObject p(String key, Double value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonObject p(String key, Float value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonObject p(String key, Integer value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonObject p(String key, JsonValue value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonObject p(String key, Long value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonObject p(String key, String value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonValue put(String arg0, JsonValue arg1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends String, ? extends JsonValue> arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonValue remove(Object arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        // This is quite expensive...
        return keySet().size();
    }

    @Override
    public String toCompactJsonString() {
        return JsonAccessHelper.objectToCompactJsonString(this);
    }

    @Override
    public String toJsonString() {
        return toJsonString(0) + "\n";
    }

    @Override
    public String toJsonString(int indent) {
        return JsonAccessHelper.objectToJsonString(this, indent);
    }

    @Override
    public String toString() {
        return toJsonString();
    }

    @Override
    public Collection<JsonValue> values() {
        throw new UnsupportedOperationException();
    }
}