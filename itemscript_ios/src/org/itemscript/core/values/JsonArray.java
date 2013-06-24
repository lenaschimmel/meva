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

import java.util.List;

import org.itemscript.core.exceptions.ItemscriptError;

/**
 * Represents a JSON array value.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 *
 */
public interface JsonArray extends JsonContainer, List<JsonValue> {
    /**
     * Add a boolean value to this array, returning this JsonArray.
     * 
     * This method can be used for inline initialization of an array.
     * 
     * @param value The boolean value to be added.
     * @return This JsonArray object.
     */
    public JsonArray a(Boolean value);

    /**
     * Add a binary value to this array, returning this JsonArray.
     * 
     * This method can be used for inline initialization of an array.
     * <p>
     * Note: The JsonString object will point to the underlying byte[] that was supplied; it will
     * not copy it. The internal operations of the JsonSystem will not change it, but it will
     * be made directly available through the JsonString object's {@link JsonString#binaryValue}
     * method. So, if you need to make sure that the original is not changed, you must copy it before
     * supplying it to this method. 
     * 
     * @param value The binary value to be added.
     * @return This JsonArray object.
     */
    public JsonArray a(byte[] value);

    /**
     * Add a double value to this array, returning this JsonArray.
     * 
     * This method can be used for inline initialization of an array.
     * 
     * @param value The double value to be added.
     * @return This JsonArray object.
     */
    public JsonArray a(Double value);

    /**
     * Add a float value to this array, returning this JsonArray.
     * 
     * This method can be used for inline initialization of an array.
     * 
     * @param value The float value to be added.
     * @return This JsonArray object.
     */
    public JsonArray a(Float value);

    /**
     * Add an int value to this array, returning this JsonArray.
     * 
     * This method can be used for inline initialization of an array.
     * 
     * @param value The int value to be added.
     * @return This JsonArray object.
     */
    public JsonArray a(Integer value);

    /**
     * Add a JsonValue to this array, returning this JsonArray.
     * 
     * This method can be used for inline initialization of an array.
     * 
     * @param value The value to be added.
     * @return This JsonArray object.
     */
    public JsonArray a(JsonValue value);

    /**
     * Add a long value to this array, returning this JsonArray.
     * This method can be used for inline initialization of an array.
     * Note that because JSON numbers cannot safely contain a long value, longs are stored in JSON string values.
     * 
     * @param value The long value to be added.
     * @return This JsonArray object.
     */
    public JsonArray a(Long value);

    /**
     * Add a string value to this array, returning this JsonArray.
     * 
     * This method can be used for inline initialization of an array.
     * 
     * @param value The String value to be added.
     * @return This JsonArray object.
     */
    public JsonArray a(String value);

    /**
     * Add a boolean value to this array.
     * 
     * @param value The boolean value to add.
     */
    public void add(Boolean value);

    /**
     * Add a binary value to this array.
     * <p>
     * Note: The JsonString object will point to the underlying byte[] that was supplied; it will
     * not copy it. The internal operations of the JsonSystem will not change it, but it will
     * be made directly available through the JsonString object's {@link JsonString#binaryValue}
     * method. So, if you need to make sure that the original is not changed, you must copy it before
     * supplying it to this method. 
     * 
     * @param value The binary value to add.
     */
    public void add(byte[] value);

    /**
     * Add a double value to this array.
     * 
     * @param value The double value to add.
     */
    public void add(Double value);

    /**
     * Add a float value to this array.
     * 
     * @param value The float value to add.
     */
    public void add(Float value);

    /**
     * Add an integer value to this array.
     * 
     * @param value The int value to add.
     */
    public void add(Integer value);

    /**
     * Add a long value to this array. Note that because JSON numbers cannot safely contain a long value, longs are stored in JSON string values.
     * 
     * @param value The long value to add.
     */
    public void add(Long value);

    /**
     * Add a string value to this array.
     * 
     * @param value The String value to add.
     */
    public void add(String value);

    /**
     * Add a new JsonArray to the end of this JsonArray.
     * 
     * @return A new JsonArray.
     */
    public JsonArray addArray();

    /**
     * Add a new JsonObject to the end of this JsonArray.
     * 
     * @return A new JsonObject.
     */
    public JsonObject addObject();

    /**
     * Create a new JsonArray at the given index, overwriting any existing value.
     * 
     * @param index The index to create at.
     * @return The new JsonArray.
     */
    public JsonArray createArray(int index);

    /**
     * Create a new JsonObject at the given index, overwriting any existing value.
     * 
     * @param index The index to create at.
     * @return The new JsonObject.
     */
    public JsonObject createObject(int index);

    /**
     * Get a JsonArray from this array.
     * 
     * @param index The index of the value.
     * @return The JsonArray at that index, or null if there was no value there or it was not a JsonArray.
     */
    public JsonArray getArray(int index);

    /**
     * Get a binary value from this array as a byte[].
     * 
     * @param index The index of the value.
     * @return The byte[] value of the JsonString at that index, or null if there was no value there or it was not a JsonString.
     * @throws ItemscriptError If there is a JsonString at that index, but it cannot be parsed as a base-64-encoded binary value. 
     */
    public byte[] getBinary(int index);

    /**
     * Get a boolean value from this array.
     * 
     * @param index The index of the value.
     * @return The boolean value of the JsonBoolean at that index, or null if there was no value there or it was not a JsonBoolean.
     */
    public Boolean getBoolean(int index);

    /**
     * Get a double value from this array.
     * 
     * @param index The index of the value.
     * @return The double value of the JsonNumber at that index, or null if there was no value there or it was not a JsonNumber.
     */
    public Double getDouble(int index);

    /**
     * Get a float value from this array.
     * 
     * @param index The index of the value.
     * @return The float value of the JsonNumber at that index, or null if there was no value there or it was not a JsonNumber.
     */
    public Float getFloat(int index);

    /**
     * Get an integer value from this array.
     * 
     * @param index The index of the value.
     * @return The int value of the JsonNumber at that index, or null if there was no value there or it was not a JsonNumber.
     */
    public Integer getInt(int index);

    /**
     * Get a long value from this array. Note that because JSON numbers cannot safely contain a long value, longs are stored in JSON string values.
     * 
     * If the value does not exist or is not a string, returns null. If the value is
     * a string but it cannot be parsed as a long, throws an exception.
     * 
     * @param index The index of the value.
     * @return The long value of the JsonString at that index, or null if there was no value there or it was not a JsonString.
     */
    public Long getLong(int index);

    /**
     * Get a JsonObject from this array.
     * 
     * @param index The index of the value.
     * @return The JsonObject value at that index, or null if there was no value there or it was not a JsonObject.
     */
    public JsonObject getObject(int index);

    /**
     * Get a JsonArray from this JsonArray, creating it if the value did not exist.
     * 
     * If the value already existed but
     * was not an array, an error will be thrown.
     * 
     * @param index The index of the value.
     * @return The existing or newly-created JsonArray.
     */
    public JsonArray getOrCreateArray(int index);

    /**
     * Get a JsonObject from this container, creating it if the value did not exist.
     * 
     * If the value already existed but was
     * not an object, an error will be thrown.
     * 
     * @param index The index of the value.
     * @return The existing or newly-created JsonObject.
     */
    public JsonObject getOrCreateObject(int index);

    /**
     * Get a JsonArray from this JsonArray. If the value does not exist, or is not a JsonArray, throws an exception.
     * 
     * @param index The index of the value.
     * @return The JsonArray under that index.
     */
    public JsonArray getRequiredArray(int index);

    /**
     * Get a binary value from this JsonArray as a byte[]. If the value does not exist, or is not a JsonString,
     * or is a JsonString but cannot be parsed as a base64 value, throws an exception.
     * 
     * @param index The index of the value.
     * @return The binary value of the JsonString under that index.
     */
    public byte[] getRequiredBinary(int index);

    /**
     * Get a Boolean from this JsonArray. If the value does not exist, or is not a JsonBoolean, throws an exception.
     * 
     * @param index The index of the value.
     * @return The Boolean value of the JsonBoolean under that index. 
     */
    public Boolean getRequiredBoolean(int index);

    /**
     * Get a Double from this JsonArray. If the value does not exist, or is not a JsonNumber, throws an exception.
     * 
     * @param index The index of the value.
     * @return The Double value of the JsonNumber under that index.
     */
    public Double getRequiredDouble(int index);

    /**
     * Get a Float from this JsonArray. If the value does not exist, or is not a JsonNumber, throws an exception.
     * 
     * @param index The index of the value.
     * @return The Float value of the JsonNumber under that index.
     */
    public Float getRequiredFloat(int index);

    /**
     * Get an Integer from this JsonArray. If the value does not exist, or is not a JsonNumber, throws an exception.
     * 
     * @param index The index of the value.
     * @return The Integer value of the JsonNumber under that index.
     */
    public Integer getRequiredInt(int index);

    /**
     * Get a Long from this JsonArray. If the value does not exist, or is not a JsonString, throws an exception.
     * If the value is a JsonString, but it cannot be converted to a Long value, throws an exception.
     * 
     * @param index The index of the value.
     * @return The Long value of the JsonString under that index.
     */
    public Long getRequiredLong(int index);

    /**
     * Get a JsonObject from this JsonArray. If the value does not exist, or is not a JsonObject, throws an exception.
     * 
     * @param index The index of the value.
     * @return The JsonObject value under that index.
     */
    public JsonObject getRequiredObject(int index);

    /**
     * Get a string from this JsonArray. If the value does not exist, or is not a JsonString, throws an exception.
     * 
     * @param index The index of the value..
     * @return The String value of the JsonString under that index.
     */
    public String getRequiredString(int index);

    /**
     * Get a value from this JsonArray. If the value does not exist, throws an exception.
     * 
     * @param index The index of the value.
     * @return The value under that index.
     */
    public JsonValue getRequiredValue(int index);

    /**
     * Get a string value from this array.
     * 
     * @param index The index of the value.
     * @return The string value of the JsonString at that index, or null if there was no value there or it was not a JsonString.
     */
    public String getString(int index);

    /**
     * Set a boolean value at the given index.
     * 
     * @param index The index to set at.
     * @param value The boolean value to set.
     */
    public void set(int index, Boolean value);

    /**
     * Set a binary value at the given index.
     * <p>
     * Note: The JsonString object will point to the underlying byte[] that was supplied; it will
     * not copy it. The internal operations of the JsonSystem will not change it, but it will
     * be made directly available through the JsonString object's {@link JsonString#binaryValue}
     * method. So, if you need to make sure that the original is not changed, you must copy it before
     * supplying it to this method. 
     * 
     * @param index The index to set at.
     * @param value The binary value to set.
     */
    public void set(int index, byte[] value);

    /**
     * Set a double value at the given index.
     * 
     * @param index The index to set at.
     * @param value The double value to set.
     */
    public void set(int index, Double value);

    /**
     * Set a float value at the given index.
     * 
     * @param index The index to set at.
     * @param value The float value to set.
     */
    public void set(int index, Float value);

    /**
     * Set an integer value at the given index.
     * 
     * @param index The index to set at.
     * @param value The int value to set.
     */
    public void set(int index, Integer value);

    /**
     * Set a long value at the given index.
     * Note that because JSON numbers cannot safely contain a long value, longs are stored in JSON string values.
     * 
     * @param index The index to set at.
     * @param value The long value to set.
     */
    public void set(int index, Long value);

    /**
     * Set a string value at the given index.
     * 
     * @param index The index to set at.
     * @param value The String value to set.
    */
    public void set(int index, String value);
}