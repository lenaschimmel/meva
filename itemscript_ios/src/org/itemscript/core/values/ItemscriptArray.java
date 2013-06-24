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
import org.itemscript.core.exceptions.ItemscriptError;
import org.itemscript.core.util.JsonAccessHelper;

/**
 * The implementation class for JsonArray.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 */
final class ItemscriptArray extends ItemscriptContainer implements JsonArray {
    private final ArrayList<JsonValue> values = new ArrayList<JsonValue>();

    /**
     * Create a new ItemscriptArray.
     * 
     * @param system The associated JsonSystem.
     */
    protected ItemscriptArray(JsonSystem system) {
        super(system);
    }

    /**
     * Create a new ItemscriptArray with the given initial list of values.
     * 
     * @param system The associated JsonSystem.
     * @param values The values to initalize the list with.
     */
    protected ItemscriptArray(JsonSystem system, List<JsonValue> values) {
        super(system);
        values.addAll(values);
    }

    @Override
    public JsonArray a(Boolean value) {
        add(value);
        return this;
    }

    @Override
    public JsonArray a(byte[] value) {
        add(value);
        return this;
    }

    @Override
    public JsonArray a(Double value) {
        add(value);
        return this;
    }

    @Override
    public JsonArray a(Float value) {
        add(value);
        return this;
    }

    @Override
    public JsonArray a(Integer value) {
        add(value);
        return this;
    }

    @Override
    public JsonArray a(JsonValue value) {
        add(value);
        return this;
    }

    @Override
    public JsonArray a(Long value) {
        add(value);
        return this;
    }

    @Override
    public JsonArray a(String value) {
        add(value);
        return this;
    }

    @Override
    public void add(Boolean value) {
        add(system().createBoolean(value));
    }

    @Override
    public void add(byte[] value) {
        add(system().createString(value));
    }

    @Override
    public void add(Double value) {
        add(system().createNumber(value));
    }

    @Override
    public void add(Float value) {
        add(system().createNumber(value));
    }

    @Override
    public void add(int index, JsonValue value) {
        prepareValueForPut(index + "", value);
        values.add(index, value);
        renumberEntriesFrom(index);
    }

    @Override
    public void add(Integer value) {
        add(system().createNumber(value));
    }

    @Override
    public boolean add(JsonValue value) {
        if (value == null) {
            value = system().createNull();
        }
        if (value.system() != system()) { throw ItemscriptError.internalError(this, "add.system.mismatch"); }
        int index = values.size();
        boolean ret = values.add(value);
        prepareValueForPut(index + "", value);
        if (item() != null) {
            ((ItemscriptItem) item()).notifyPut(value.fragment());
        }
        return ret;
    }

    @Override
    public void add(Long value) {
        add(system().createString(value));
    }

    @Override
    public void add(String value) {
        add(system().createString(value));
    }

    @Override
    public boolean addAll(Collection<? extends JsonValue> c) {
        for (JsonValue value : c) {
            add(value);
        }
        return c.size() > 0;
    }

    @Override
    public boolean addAll(int index, Collection<? extends JsonValue> c) {
        throw new UnsupportedOperationException("error.itemscript.ItemscriptArray.addAll.index.not.supported");
    }

    @Override
    public JsonArray addArray() {
        JsonArray array = system().createArray();
        add(array);
        return array;
    }

    @Override
    public JsonObject addObject() {
        JsonObject object = system().createObject();
        add(object);
        return object;
    }

    @Override
    public JsonArray asArray() {
        return this;
    }

    /**
     * Parse the given string as an index, throw an error if it can't be parsed.
     * 
     * @param key The string to parse.
     * @return The int value of the string.
     */
    private int checkIndex(String key) {
        try {
            return Integer.parseInt(key);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("error.itemscript.ItemscriptArray.checkIndex.non.integer.key", e);
        }
    }

    @Override
    public void clear() {
        values.clear();
    }

    @Override
    public boolean contains(Object o) {
        return values.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return values.containsAll(c);
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
    public JsonArray copy() {
        return JsonAccessHelper.copyArray(system(), this);
    }

    @Override
    public JsonArray createArray(int index) {
        return createArray(index + "");
    }

    @Override
    public JsonObject createObject(int index) {
        return createObject(index + "");
    }

    private void enlargeValues(int index) {
        while (index >= size()) {
            add(system().createNull());
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof JsonArray) {
            JsonArray otherArray = (JsonArray) other;
            if (otherArray.size() == size()) {
                for (int i = 0; i < size(); ++i) {
                    if (!get(i).equals(otherArray.get(i))) { return false; }
                }
                return true;
            }
        }
        return false;
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
    public final JsonArray getOrCreateArray(int index) {
        if (index < size()) {
            JsonArray array = getArray(index);
            if (array == null) { throw ItemscriptError.internalError(this,
                    "getOrCreateArray.value.existed.but.was.not.an.array", JsonAccessHelper.keyValueParams(this,
                            index + "", get(index))); }
            return array;
        } else {
            return createArray(index);
        }
    }

    @Override
    public final JsonObject getOrCreateObject(int index) {
        if (index < size()) {
            JsonObject object = getObject(index);
            if (object == null) { throw ItemscriptError.internalError(this,
                    "getOrCreateObject.value.existed.but.was.not.an.object", JsonAccessHelper.keyValueParams(this,
                            index + "", get(index))); }
            return object;
        } else {
            return createObject(index);
        }
    }

    @Override
    public final JsonArray getRequiredArray(int index) {
        return JsonAccessHelper.getRequiredArray(this, index, get(index));
    }

    @Override
    public final byte[] getRequiredBinary(int index) {
        return JsonAccessHelper.getRequiredBinary(this, index, get(index));
    }

    @Override
    public final Boolean getRequiredBoolean(int index) {
        return JsonAccessHelper.getRequiredBoolean(this, index, get(index));
    }

    @Override
    public final Double getRequiredDouble(int index) {
        return JsonAccessHelper.getRequiredDouble(this, index, get(index));
    }

    @Override
    public final Float getRequiredFloat(int index) {
        return JsonAccessHelper.getRequiredFloat(this, index, get(index));
    }

    @Override
    public final Integer getRequiredInt(int index) {
        return JsonAccessHelper.getRequiredInt(this, index, get(index));
    }

    @Override
    public final Long getRequiredLong(int index) {
        return JsonAccessHelper.getRequiredLong(this, index, get(index));
    }

    @Override
    public final JsonObject getRequiredObject(int index) {
        return JsonAccessHelper.getRequiredObject(this, index, get(index));
    }

    @Override
    public final String getRequiredString(int index) {
        return JsonAccessHelper.getRequiredString(this, index, get(index));
    }

    @Override
    public final JsonValue getRequiredValue(int index) {
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
    public int hashCode() {
        return values.hashCode();
    }

    @Override
    public int indexOf(Object o) {
        return values.indexOf(o);
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
        for (int i = 0; i < size(); ++i) {
            keys.add(i + "");
        }
        return keys;
    }

    @Override
    public int lastIndexOf(Object o) {
        return values.lastIndexOf(o);
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
    public void putValue(String key, JsonValue value) {
        set(Integer.valueOf(key), value);
    }

    @Override
    public JsonValue remove(int index) {
        String fragment = null;
        if (item() != null) {
            JsonValue value = get(index);
            if (value != null) {
                fragment = value.fragment();
            }
        }
        JsonValue ret = values.remove(index);
        updateRemovedValue(ret);
        if (item() != null && fragment != null) {
            ((ItemscriptItem) item()).notifyRemove(fragment);
        }
        renumberEntriesFrom(index);
        return ret;
    }

    /**
     * Note: remove(Object o) is not supported on ItemscriptArrays.
     */
    @Override
    public boolean remove(Object o) {
        // FIXME this should be supported. Remember to call notifyDelete on the value and notifyUpdate on this value,
        // and re-index after the value is removed.
        throw new UnsupportedOperationException("error.itemscript.ItemscriptArray.remove.Object.not.supported");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("error.itemscript.ItemscriptArray.removeAll.not.supported");
    }

    @Override
    public void removeValue(String key) {
        remove(checkIndex(key));
    }

    private void renumberEntriesFrom(int index) {
        for (int i = index; i < size(); ++i) {
            ItemscriptValue value = (ItemscriptValue) get(i);
            value.setKey(null);
            value.setKey(i + "");
        }
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("error.itemscript.ItemscriptArray.retainAll.not.supported");
    }

    @Override
    public void set(int index, Boolean value) {
        set(index, system().createBoolean(value));
    }

    @Override
    public void set(int index, byte[] value) {
        set(index, system().createString(value));
    }

    @Override
    public void set(int index, Double value) {
        set(index, system().createNumber(value));
    }

    @Override
    public void set(int index, Float value) {
        set(index, system().createNumber(value));
    }

    @Override
    public void set(int index, Integer value) {
        set(index, system().createNumber(value));
    }

    @Override
    public JsonValue set(int index, JsonValue value) {
        if (value == null) {
            value = system().createNull();
        }
        if (value.system() != system()) { throw ItemscriptError.internalError(this, "set.system.mismatch", index
                + ""); }
        enlargeValues(index);
        prepareValueForPut(index + "", value);
        JsonValue previous = values.set(index, value);
        updateRemovedValue(previous);
        if (item() != null) {
            ((ItemscriptItem) item()).notifyPut(value.fragment());
        }
        return previous;
    }

    @Override
    public void set(int index, Long value) {
        set(index, system().createString(value));
    }

    @Override
    public void set(int index, String value) {
        set(index, system().createString(value));
    }

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public List<JsonValue> subList(int fromIndex, int toIndex) {
        // We cannot support this because GWT's ArrayList does not support it.
        throw new UnsupportedOperationException("error.itemscript.ItemscriptArray.subList.not.supported");
    }

    @Override
    public Object[] toArray() {
        return values.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return values.toArray(a);
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
}