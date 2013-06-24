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

import org.itemscript.core.JsonSystem;

/**
 * Provides access to contained {@link JsonValue} by string locators, which may be keys in the
 * case of {@link JsonContainer} classes, or URLs in the case of {@link JsonItem} or {@link JsonSystem} classes.
 * 
 * For compatibility with the Map and List interfaces, the simple get method on this class
 * is named getValue. Implementing classes should add get methods as appropriate for their interface.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 *
 */
public interface JsonGetAccess {
    /**
     * Get a JsonArray value. If the element does not exist or is not an JsonArray, returns null.
     * 
     * @param key The key used to find the value.
     * @return The JsonArray, or null if it did not exist or was not a JsonArray.
     */
    public JsonArray getArray(String key);

    /**
     * Get a binary value. If the element does not exist or is not a JsonString, returns null.
     * If the value is a string but cannot be base64-decoded, throws an exception.
     * 
     * @param key The key used to find the value.
     * @return The binary value, or null if it it did not exist or was not a JsonString.
     */
    public byte[] getBinary(String key);

    /**
     * Get a boolean value. If the element does not exist or is not a JsonBoolean, returns null.
     * 
     * @param key The key used to find the value.
     * @return The Boolean value, or null if it did not exist or was not a JsonBoolean.
     */
    public Boolean getBoolean(String key);

    /**
     * Get a double value. If the element does not exist or is not a JsonNumber, returns null.
     * 
     * @param key The key used to find the value.
     * @return The Double value, or null if it did not exist or was not a JsonNumber.
     */
    public Double getDouble(String key);

    /**
     * Get a float value. If the element does not exist or is not a JsonNumber, returns null.
     * 
     * @param key The key used to find the value.
     * @return The Float value, or null if it did not exist or was not a JsonNumber.
     */
    public Float getFloat(String key);

    /**
     * Get an int value. If the element does not exist or is not a JsonNumber, returns null.
     * 
     * @param key The key used to find the value.
     * @return The Integer value, or null if it did not exist or was not a JsonNumber.
     */
    public Integer getInt(String key);

    /**
     * Get a long value. If the element does not exist or is not a JsonString, returns null.
     * 
     * If the value is a string but cannot be parsed as a long, throws an exception.
     * 
     * @param key The key used to find the value.
     * @return The Long value, or null if it did not exist or was not a JsonString.
     */
    public Long getLong(String key);

    /**
     * Get a native value. If the element does not exist or is not a JsonNative, returns null.
     * 
     * @param key The key used to find the value.
     * @return The native object, or null if it did not exist or was not a JsonNative.
     */
    public Object getNative(String key);

    /**
     * Get a JsonObject value. If the element does not exist or is not a JsonObject, returns null.
     * 
     * @param key The key used to find the value.
     * @return The JsonObject value, or null if the value did not exist or was not a JsonObject.
     */
    public JsonObject getObject(String key);

    /**
     * Get a string value. If the value does not exist or is not a JsonString, returns null.
     * 
     * @param key The key used to find the value.
     * @return The String value, or null if the value did not exist or was not a JsonString.
     */
    public String getString(String key);

    /**
     * Get a JsonValue. If the value does not exist, returns null.
     * <p>
     * This method is named "getValue" to avoid conflicts with the List and Map "get" methods.
     * A method named "get" will be provided by any class implementing this interface, and that
     * method should be used instead of this one, for brevity.
     * 
     * @param key The key used to find the value.
     * @return The value, or null if the value did not exist.
     */
    public JsonValue getValue(String key);
}