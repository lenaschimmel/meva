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

import java.util.Set;

import org.itemscript.core.exceptions.ItemscriptError;

/**
 * The parent interface for {@link JsonObject} and {@link JsonArray}.
 * 
 * Defines a set of common operations for accessing child values of these container types.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 *
 */
public interface JsonContainer extends JsonValue, JsonGetAccess {
    /**
     * Tests whether this container contains a value under the given key.
     * 
     * @param key The key to test.
     * @return True if this container has a value under the key, false otherwise.
     */
    public boolean containsKey(String key);

    /**
     * Create a new JsonArray in this container.
     * 
     * @param key The key to create under.
     * @return The new JsonArray.
     */
    public JsonArray createArray(String key);

    /**
     * Create a new JsonObject in this container.
     * 
     * @param key The key to create under.
     * @return The new JsonObject.
     */
    public JsonObject createObject(String key);

    /**
     * Get a specific value from this container using a path consisting of a series of URL-encoded keys separated by
     * slashes. If the path is empty, it is an error.
     * 
     * @param path The path to the value.
     * @return The value under that path, or null if the value cannot be found.
     * @throws ItemscriptError If a value that needed to be a container to interpret the path was a scalar value, or
     * if a string key needs to be applied to an array container, or if the path was null or empty.
     */
    public JsonValue getByPath(String path);

    /**
     * Get a JsonArray from this container, creating it if the value did not exist.
     * 
     * If the value already existed but
     * was not an array, an error will be thrown.
     * 
     * @param key The key to retrieve from or create under.
     * @return The existing or newly-created JsonArray.
     */
    public JsonArray getOrCreateArray(String key);

    /**
     * Get a JsonObject from this container, creating it if the value did not exist.
     * 
     * If the value already existed but was
     * not an object, an error will be thrown.
     * 
     * @param key The key to retrieve from or create under.
     * @return The existing or newly-created JsonObject.
     */
    public JsonObject getOrCreateObject(String key);

    /**
     * Get a JsonArray from this container. If the value does not exist, or is not a JsonArray, throws an exception.
     * 
     * @param key The key to get from.
     * @return The JsonArray under that key.
     */
    public JsonArray getRequiredArray(String key);

    /**
     * Get a binary value from this container as a byte[]. If the value does not exist, or is not a JsonString,
     * or is a JsonString but cannot be parsed as a base64 value, throws an exception.
     * 
     * @param key The key to get from.
     * @return The binary value of the JsonString under that key.
     */
    public byte[] getRequiredBinary(String key);

    /**
     * Get a Boolean from this container. If the value does not exist, or is not a JsonBoolean, throws an exception.
     * 
     * @param key The key to get from.
     * @return The Boolean value of the JsonBoolean under that key. 
     */
    public Boolean getRequiredBoolean(String key);

    /**
     * Get a Double from this container. If the value does not exist, or is not a JsonNumber, throws an exception.
     * 
     * @param key The key to get from.
     * @return The Double value of the JsonNumber under that key.
     */
    public Double getRequiredDouble(String key);

    /**
     * Get a Float from this container. If the value does not exist, or is not a JsonNumber, throws an exception.
     * 
     * @param key The key to get from.
     * @return The Float value of the JsonNumber under that key.
     */
    public Float getRequiredFloat(String key);

    /**
     * Get an Integer from this container. If the value does not exist, or is not a JsonNumber, throws an exception.
     * 
     * @param key The key to get from.
     * @return The Integer value of the JsonNumber under that key.
     */
    public Integer getRequiredInt(String key);

    /**
     * Get a Long from this container. If the value does not exist, or is not a JsonString, throws an exception.
     * If the value is a JsonString, but it cannot be converted to a Long value, throws an exception.
     * 
     * @param key The key to get from.
     * @return The Long value of the JsonString under that key.
     */
    public Long getRequiredLong(String key);

    /**
     * Get a JsonObject from this container. If the value does not exist, or is not a JsonObject, throws an exception.
     * 
     * @param key The key to get from.
     * @return The JsonObject value under that key.
     */
    public JsonObject getRequiredObject(String key);

    /**
     * Get a string from this container. If the value does not exist, or is not a JsonString, throws an exception.
     * 
     * @param key The key to get from.
     * @return The String value of the JsonString under that key.
     */
    public String getRequiredString(String key);

    /**
     * Get a value from this container. If the value does not exist, throws an exception.
     * 
     * @param key The key to get from.
     * @return The value under that key.
     */
    public JsonValue getRequiredValue(String key);

    /**
     * Get a value by key.
     * 
     * @param key The key to get from.
     * @return The value if it exists, null if it does not.
     */
    public JsonValue getValue(String key);

    /**
     * Test if the value with the given key exists and is a JsonAarray.
     * 
     * @param key The key to test.
     * @return True if the value exists and is a JsonArray, false if not.
     */
    public boolean hasArray(String key);

    /**
     * Test if the specified value exists and is a JsonBoolean.
     * 
     * @param key The key to test.
     * @return True if the value exists and is a JsonBoolean, false if not.
     */
    public boolean hasBoolean(String key);

    /**
     * Test if the specified value exists and is a JsonNumber.
     * 
     * @param key The key to test.
     * @return True if the value exists and is a JsonNumber, false if not.
     */
    public boolean hasNumber(String key);

    /**
     * Test if the specified value exists and is a JsonObject.
     * 
     * @param key The key to test.
     * @return True if the value exists and is a JsonObject, false if not.
     */
    public boolean hasObject(String key);

    /**
     * Test if the specified value exists and is a JsonString.
     * 
     * @param key The key to test.
     * @return True if the value exists and is a JsonString, false if not.
     */
    public boolean hasString(String key);

    /**
     * Get the keys of this container as a set.
     * 
     * @return The keys of this container.
     */
    public Set<String> keySet();

    /**
     * Put a boolean value as a JsonBoolean.
     * 
     * @param key The key to put the value under.
     * @param value The boolean value to store.
     * @return A new JsonBoolean.
     */
    public JsonBoolean put(String key, Boolean value);

    /**
     * Put a binary value. The value will be base64-encoded and stored in a JsonString.
     * <p>
     * Note: The JsonString object will point to the underlying byte[] that was supplied; it will
     * not copy it. The internal operations of the JsonSystem will not change it, but it will
     * be made directly available through the JsonString object's {@link JsonString#binaryValue}
     * method. So, if you need to make sure that the original is not changed, you must copy it before
     * supplying it to this method. 
     * 
     * @param key The key to put the value under.
     * @param value The binary value to store.
     * @return A new JsonString.
     */
    public JsonString put(String key, byte[] value);

    /**
     * Put a double value as a JsonNumber.
     * 
     * @param key The key to put the value under.
     * @param value The double value to store.
     * @return A new JsonNumber
     */
    public JsonNumber put(String key, Double value);

    /**
     * Put a float value as a JsonNumber.
     * 
     * @param key The key to put the value under.
     * @param value The float value to store.
     * @return A new JsonNumber.
     */
    public JsonNumber put(String key, Float value);

    /**
     * Put an int value as a JsonNumber.
     * 
     * @param key The key to put the value under.
     * @param value The int value to store.
     * @return A new JsonNumber
     */
    public JsonNumber put(String key, Integer value);

    /**
     * Put a long value as a JsonString.
     * 
     * @param key The key to put the value under.
     * @param value The long value to store.
     * @return A new JsonString.
     */
    public JsonString put(String key, Long value);

    /**
     * Put a string value as a JsonString.
     * 
     * @param key The key to put the value under.
     * @param value The String value to store.
     * @return A new JsonString.
     */
    public JsonString put(String key, String value);

    /**
     * Put a value in this container or one of its sub-elements using a path consisting of a series of URL-encoded keys
     * separated by slashes. If the path is empty, it is an error.
     * 
     * @param path The path to the new value.
     * @param value The value to put.
     * @throws ItemscriptError If a value that needed to be a container to interpret the path was a scalar value, or if
     * a string key needs to be applied to an array container, or if the path was null or empty.
     */
    public void putByPath(String path, JsonValue value);

    /**
     * Put a native object as a JsonNative.
     * 
     * @param key The key to put the value under.
     * @param value The native object to store.
     * @return A new JsonNative.
     */
    public JsonNative putNative(String key, Object value);

    /**
     * Put a JsonValue. Note that the JsonValue to be stored must not currently be inside another container; nor can it be the
     * parent of this container (so cycles are not allowed). Nor can it be the value of another {@link JsonItem}.
     * 
     * @param key The key to put the value under.
     * @param value The JsonValue to store.
     */
    public void putValue(String key, JsonValue value);

    /**
     * Remove a value from this container by key.
     * 
     * @param key The key to remove.
     */
    public void removeValue(String key);

    /**
     * Remove a value from this container by path.
     * 
     * @param path The path to the value to remove.
     * @throws ItemscriptError If a value that needed to be a container to interpret the path was a scalar value, or if
     * a string key needs to be applied to an array container, or if the path was null or empty.
     */
    public void removeByPath(String path);

    /**
     * Get the number of entries in this container.
     * 
     * @return The number of entries in this container.
     */
    public int size();
}