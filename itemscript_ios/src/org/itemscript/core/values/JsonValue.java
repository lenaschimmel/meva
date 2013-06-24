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

import org.itemscript.core.HasSystem;
import org.itemscript.core.JsonSystem;

/**
 * Represents a JSON value.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 *
 */
public interface JsonValue extends HasSystem {
    /**
     * Return this value as a JsonArray if it is a JsonArray.
     * 
     * If it is not a JsonArray, returns null.
     * 
     * @return This value as a JsonArray, or null if it is not a JsonArray.
     */
    public JsonArray asArray();

    /**
     * Return this value as a JsonBoolean if it is a JsonBoolean.
     * 
     * If it is not a JsonBoolean, returns null.
     * 
     * @return This value as a JsonBoolean, or null if it is not a JsonBoolean.
     */
    public JsonBoolean asBoolean();

    /**
     * If this value is a JsonContainer, returns it as a JsonContainer.
     * 
     * If it is not a JsonContainer, returns null.
     * 
     * @return This value as a JsonContainer, or null if it is not a JsonContainer.
     */
    public JsonContainer asContainer();

    /**
     * If this value is a JsonNative, returns it as a JsonNative.
     * 
     * If it is not a JsonNative, returns null.
     * 
     * @return This value as a JsonNative, or null if it is not a JsonNative.
     */
    public JsonNative asNative();

    /**
     * If this value is a JsonNull, returns it as a JsonNull.
     * 
     * If it is not a JsonNull, returns null. (Note that a JsonNull object is not the
     * same as a Java null.)
     * 
     * @return This value as a JsonNull, or null if it is not a JsonNull.
     */
    public JsonNull asNull();

    /**
     * If this value is a JsonNumber, returns it as a JsonNumber.
     * 
     * If it is not a JsonNumber, returns null.
     * 
     * @return This value as a JsonNumber, or null if it is not a JsonNumber.
     */
    public JsonNumber asNumber();

    /**
     * If this value is a JsonObject, returns it as a JsonObject.
     * 
     * If it is not a JsonObject, returns null.
     * 
     * @return This value as a JsonObject, or null if it is not a JsonObject.
     */
    public JsonObject asObject();

    /**
     * If this value is a JsonString, returns it as a JsonString.
     * 
     * If it is not a JsonString, returns null.
     * 
     * @return This value as a JsonString, or null if it is not a JsonString.
     */
    public JsonString asString();

    /**
     * If this value is a JsonString, parse it as a base-64-encoded string and return the binary value.
     * <br>
     * If this value is not a JsonString, or cannot be parsed as a base-64-encoded string, an exception will be thrown.
     * <br>
     * Do not change the array returned from this operation. If you need to change this array, make a copy of it first.
     * 
     * @return The binary value.
     */
    public byte[] binaryValue();

    /**
     * If this value is a JsonBoolean, return its boolean value.
     * 
     * If it is not a JsonBoolean an exception will be thrown.
     * 
     * @return The boolean value.
     */
    public Boolean booleanValue();

    /**
     * Make a deep copy of this value, including all of its sub-values.
     * 
     * References to native objects, and to the associated JsonItem, will not be copied.
     * 
     * @return A deep copy of this value.
     */
    public JsonValue copy();

    /**
     * If this value is a JsonNumber, return its double value.
     * 
     * If this value is not a JsonNumber an exception will be thrown.
     * 
     * @return The double value.
     */
    public Double doubleValue();

    /**
     * If this value is a JsonNumber, return its float value.
     * 
     * If this value is not a JsonNumber an exception will be thrown.
     * 
     * @return The float value.
     */
    public Float floatValue();

    /**
     * Return the fragment identifier of this value.
     * 
     * If this value has no parent container, returns "#".
     * If this value has a parent, but that parent has no parent, returns "#key" where "key" is the value returned by key(), URL-encoded.
     * If this value has a parent, and that parent has a parent, returns "#parent/key", where "#parent" is the fragment identifier of the
     * parent of this value (which may itself be composed of several keys), and "key" is the value returned by key(), URL-encoded.
     *  
     * @return The fragment identifier of this value.
     */
    public String fragment();

    /**
     * If this value is a JsonNumber, return its int value.
     * 
     * If this value is not a JsonNumber, an exception will be thrown.
     * 
     * @return The int value.
     */
    public Integer intValue();

    /**
     * Test whether this value is a JsonArray.
     * 
     * @return True if this value is a JsonArray, false otherwise.
     */
    public boolean isArray();

    /**
     * Test whether this value is a JsonBoolean.
     * 
     * @return True if this value is a JsonBoolean, false otherwise.
     */
    public boolean isBoolean();

    /**
     * Test whether this value is a JsonContainer.
     * 
     * @return True if this value is a JsonContainer, false otherwise.
     */
    public boolean isContainer();

    /**
     * Test whether this value is a JsonNative.
     * 
     * @return True if this value is a JsonNative, false otherwise.
     */
    public boolean isNative();

    /**
     * Test whether this value is a JsonNull.
     * 
     * @return True if this value is a JsonNull, false otherwise.
     */
    public boolean isNull();

    /**
     * Test whether this value is a JsonNumber.
     * 
     * @return True if this value is a JsonNumber, false otherwise.
     */
    public boolean isNumber();

    /**
     * Test whether this value is a JsonObject.
     * 
     * @return True if this value is a JsonObject, false otherwise.
     */
    public boolean isObject();

    /**
     * Test whether this value is a JsonString.
     *  
     * @return True if this value is a JsonString, false otherwise.
     */
    public boolean isString();

    /**
     * Return the JsonItem associated with this value, if any.
     * 
     * @return The associated JsonItem, or null if no item is associated with this value.
     */
    public JsonItem item();

    /**
     * Get the key that this element is stored under in its parent. If it doesn't have a parent, returns null.
     * 
     * @return The key, or null if this value has no parent.
     */
    public String key();

    /**
     * If this value is a JsonString, attempt to parse it as a long value, and return the result.
     * 
     * If it is not a JsonString, or cannot be parsed as a long value, an exception will be thrown.
     * 
     * @return The long value.
     */
    public Long longValue();

    /**
     * If this value is a JsonNative, return its native object value.
     * 
     * If it is not a JsonNative an exception will be thrown.
     * 
     * @return The native value.
     */
    public Object nativeValue();

    /**
     * Retrieve the parent container of this value, if any.
     * 
     * @return The parent container, or null if this value has no parent container.
     */
    public JsonContainer parent();

    /**
     * If this value is a JsonString, returns its String value.
     * 
     * If it is not a JsonString, throws an exception.
     * 
     * @return The string value.
     */
    public String stringValue();

    /**
     * Returns the JsonSystem associated with this value.
     */
    public JsonSystem system();

    /**
     * Convert this value to a compact JSON string, that is, with all non-meaningful whitespace removed.
     * 
     * @return The compact JSON representation of this value.
     */
    public String toCompactJsonString();

    /**
     * Convert this value to a JSON string, with line breaks and indentation.
     * 
     * @return The JSON representation of this value.
     */
    public String toJsonString();
}