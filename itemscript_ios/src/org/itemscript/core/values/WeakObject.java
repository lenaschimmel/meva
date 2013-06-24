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
import java.util.Set;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.util.JsonAccessHelper;

/**
 * A WeakObject implements a JsonObject that does not change the state of the values contained within it,
 * and whose contents cannot be changed through any of the standard JsonObject <code>put</code> operations. It can be useful when you need
 * a JsonObject whose contents are JsonValues found elsewhere, but do not want to copy all those values; it should be used with
 * caution, and only when the recipient will not be modifying the object, since it is not a full substitute for JsonObject and
 * since the values contained within it may be changed externally to it.
 *  
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 */
public class WeakObject extends WeakContainer implements JsonObject, ToJsonStringWithIndent {
    private final Map<String, JsonValue> values = new HashMap<String, JsonValue>();

    /**
     * Create a new WeakObject.
     * 
     * @param system The associated JsonSystem.
     */
    public WeakObject(JsonSystem system) {
        super(system);
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
        return values.containsKey(key);
    }

    @Override
    public boolean containsKey(String key) {
        return values.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return values.containsValue(value);
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
    public JsonValue get(Object key) {
        return values.get(key);
    }

    @Override
    public JsonValue getValue(String key) {
        return values.get(key);
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
    public String toString() {
        return toJsonString();
    }

    @Override
    public Collection<JsonValue> values() {
        throw new UnsupportedOperationException();
    }

    /**
     * Add the given value under the given key, without changing its state.
     * 
     * @param key The key to put under.
     * @param value The value to put.
     */
    public void weakPut(String key, JsonValue value) {
        values.put(key, value);
    }
}