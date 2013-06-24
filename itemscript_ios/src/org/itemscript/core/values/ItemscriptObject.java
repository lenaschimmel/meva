/*
 * Copyright � 2010, Data Base Architects, Inc. All rights reserved.
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
import java.util.Set;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.exceptions.ItemscriptError;
import org.itemscript.core.util.JsonAccessHelper;

final class ItemscriptObject extends ItemscriptContainer implements JsonObject {
    private final HashMap<String, JsonValue> values = new HashMap<String, JsonValue>();

    public ItemscriptObject(JsonSystem system) {
        super(system);
    }

    public ItemscriptObject(JsonSystem system, Map<String, JsonValue> value) {
        super(system);
        putAll(value);
    }

    @Override
    public JsonObject asObject() {
        return this;
    }

    @Override
    public void clear() {
        for (String key : values.keySet()) {
            remove(key);
        }
    }

    @Override
    public boolean containsKey(Object key) {
        return values.containsKey(key);
    }

    @Override
    public boolean containsKey(String key) {
        return values.containsKey(key);
    }

    @Override
    public boolean containsValue(Object key) {
        return values.containsValue(key);
    }

    @Override
    public JsonObject copy() {
        return JsonAccessHelper.copyObject(system(), this);
    }

    @Override
    public Set<java.util.Map.Entry<String, JsonValue>> entrySet() {
        return values.entrySet();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof JsonObject) {
            JsonObject otherObject = (JsonObject) other;
            if (otherObject.size() == size()) {
                for (String key : keySet()) {
                    if (!get(key).equals(otherObject.get(key))) { return false; }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public JsonValue get(Object key) {
        return values.get(key);
    }

    @Override
    public JsonValue getValue(String key) {
        return get(key);
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public boolean isObject() {
        return true;
    }

    @Override
    public Set<String> keySet() {
        return values.keySet();
    }

    @Override
    public JsonObject p(String key, Boolean value) {
        put(key, value);
        return this;
    }

    @Override
    public JsonObject p(String key, Double value) {
        put(key, value);
        return this;
    }

    @Override
    public JsonObject p(String key, Float value) {
        put(key, value);
        return this;
    }

    @Override
    public JsonObject p(String key, Integer value) {
        put(key, value);
        return this;
    }

    @Override
    public JsonObject p(String key, JsonValue value) {
        put(key, value);
        return this;
    }

    @Override
    public JsonObject p(String key, Long value) {
        put(key, value);
        return this;
    }

    @Override
    public JsonObject p(String key, String value) {
        put(key, value);
        return this;
    }

    @Override
    public JsonValue put(String key, JsonValue value) {
        if (value == null) {
            value = system().createNull();
        }
        if (value.system() != system()) { throw ItemscriptError.internalError(this, "put.system.mismatch", key); }
        prepareValueForPut(key, value);
        JsonValue previous = values.put(key, value);
        updateRemovedValue(previous);
        if (item() != null) {
            if (((ItemscriptItem) item()).hasHandlers()) {
                ((ItemscriptItem) item()).notifyPut(value.fragment());
            }
        }
        return previous;
    }

    @Override
    public void putAll(Map<? extends String, ? extends JsonValue> other) {
        for (String key : other.keySet()) {
            JsonValue value = other.get(key);
            put(key, value);
        }
    }

    @Override
    public void putValue(String key, JsonValue value) {
        put(key, value);
    }

    @Override
    public JsonValue remove(Object key) {
        String fragment = null;
        if (item() != null) {
            JsonValue value = get(key);
            if (value != null) {
                fragment = value.fragment();
            }
        }
        JsonValue ret = values.remove(key);
        updateRemovedValue(ret);
        if (item() != null) {
            if (((ItemscriptItem) item()).hasHandlers()) {
                ((ItemscriptItem) item()).notifyRemove(fragment);
            }
        }
        return ret;
    }

    @Override
    public void removeValue(String key) {
        remove(key);
    }

    @Override
    public int size() {
        return values.size();
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
    public Collection<JsonValue> values() {
        return values.values();
    }
}