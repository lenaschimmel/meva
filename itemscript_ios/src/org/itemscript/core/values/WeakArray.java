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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.util.JsonAccessHelper;

/**
 * A WeakArray implements a JsonArray that does not change the state of values contained within it,
 * and whose contents cannot be changed through any of the JsonArray methods. It can be useful when you
 * need something that looks like a JsonArray, but cannot be changed, and whose values may be inside other
 * containers. It should be used with caution, since it does not fully substitute for JsonArray and because
 * the values contained within it may change without notice.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 */
public class WeakArray extends WeakContainer implements JsonArray, ToJsonStringWithIndent {
    private final List<JsonValue> values = new ArrayList<JsonValue>();

    public WeakArray(JsonSystem system) {
        super(system);
    }

    @Override
    public JsonArray a(Boolean value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonArray a(byte[] value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonArray a(Double value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonArray a(Float value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonArray a(Integer value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonArray a(JsonValue value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonArray a(Long value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonArray a(String value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(Boolean value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(byte[] value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(Double value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(Float value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int arg0, JsonValue arg1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(Integer value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(JsonValue arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(Long value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(String value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends JsonValue> arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int arg0, Collection<? extends JsonValue> arg1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonArray addArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonObject addObject() {
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonArray asArray() {
        return this;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object object) {
        return values.contains(object);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return values.containsAll(collection);
    }

    @Override
    public boolean containsKey(String key) {
        int index;
        try {
            index = Integer.parseInt(key);
        } catch (NumberFormatException e) {
            return false;
        }
        return index < size();
    }

    @Override
    public JsonValue copy() {
        return JsonAccessHelper.copyArray(system(), this);
    }

    @Override
    public JsonArray createArray(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonObject createObject(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonValue get(int index) {
        return values.get(index);
    }

    @Override
    public JsonArray getArray(int index) {
        return JsonAccessHelper.asArray(get(index));
    }

    @Override
    public byte[] getBinary(int index) {
        return JsonAccessHelper.asBinary(get(index));
    }

    @Override
    public Boolean getBoolean(int index) {
        return JsonAccessHelper.asBoolean(get(index));
    }

    @Override
    public Double getDouble(int index) {
        return JsonAccessHelper.asDouble(get(index));
    }

    @Override
    public Float getFloat(int index) {
        return JsonAccessHelper.asFloat(get(index));
    }

    @Override
    public Integer getInt(int index) {
        return JsonAccessHelper.asInt(get(index));
    }

    @Override
    public Long getLong(int index) {
        return JsonAccessHelper.asLong(get(index));
    }

    @Override
    public JsonObject getObject(int index) {
        return JsonAccessHelper.asObject(get(index));
    }

    @Override
    public JsonArray getOrCreateArray(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonObject getOrCreateObject(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonArray getRequiredArray(int index) {
        return JsonAccessHelper.getRequiredArray(this, index + "", get(index));
    }

    @Override
    public byte[] getRequiredBinary(int index) {
        return JsonAccessHelper.getRequiredBinary(this, index + "", get(index));
    }

    @Override
    public Boolean getRequiredBoolean(int index) {
        return JsonAccessHelper.getRequiredBoolean(this, index + "", get(index));
    }

    @Override
    public Double getRequiredDouble(int index) {
        return JsonAccessHelper.getRequiredDouble(this, index + "", get(index));
    }

    @Override
    public Float getRequiredFloat(int index) {
        return JsonAccessHelper.getRequiredFloat(this, index + "", get(index));
    }

    @Override
    public Integer getRequiredInt(int index) {
        return JsonAccessHelper.getRequiredInt(this, index + "", get(index));
    }

    @Override
    public Long getRequiredLong(int index) {
        return JsonAccessHelper.getRequiredLong(this, index + "", get(index));
    }

    @Override
    public JsonObject getRequiredObject(int index) {
        return JsonAccessHelper.getRequiredObject(this, index + "", get(index));
    }

    @Override
    public String getRequiredString(int index) {
        return JsonAccessHelper.getRequiredString(this, index + "", get(index));
    }

    @Override
    public JsonValue getRequiredValue(int index) {
        return JsonAccessHelper.getRequiredValue(this, index + "", get(index));
    }

    @Override
    public String getString(int index) {
        return JsonAccessHelper.asString(get(index));
    }

    @Override
    public JsonValue getValue(String key) {
        try {
            int index = Integer.valueOf(key);
            return get(index);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public int indexOf(Object object) {
        return values.indexOf(object);
    }

    @Override
    public boolean isArray() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public Iterator<JsonValue> iterator() {
        return values.iterator();
    }

    @Override
    public Set<String> keySet() {
        HashSet<String> keys = new HashSet<String>();
        for (int i = 0, s = size(); i < s; ++i) {
            keys.add(i + "");
        }
        return keys;
    }

    @Override
    public int lastIndexOf(Object object) {
        return values.lastIndexOf(object);
    }

    @Override
    public ListIterator<JsonValue> listIterator() {
        return values.listIterator();
    }

    @Override
    public ListIterator<JsonValue> listIterator(int index) {
        return values.listIterator(index);
    }

    @Override
    public JsonValue remove(int arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void set(int index, Boolean value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void set(int index, byte[] value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void set(int index, Double value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void set(int index, Float value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void set(int index, Integer value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonValue set(int arg0, JsonValue arg1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void set(int index, Long value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void set(int index, String value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public List<JsonValue> subList(int arg0, int arg1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        return values.toArray();
    }

    @Override
    public <T> T[] toArray(T[] arg0) {
        return values.toArray(arg0);
    }

    @Override
    public String toCompactJsonString() {
        return JsonAccessHelper.arrayToCompactJsonString(this);
    }

    @Override
    public String toJsonString() {
        return toJsonString(0) + "\n";
    }

    @Override
    public String toJsonString(int indent) {
        return JsonAccessHelper.arrayToJsonString(this, indent);
    }

    @Override
    public String toString() {
        return toJsonString();
    }

    /**
     * Add the given value, without changing its state.
     * 
     * @param value The value to add.
     */
    public void weakAdd(JsonValue value) {
        values.add(value);
    }
}