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

/**
 * A JsonCreator is used to create {@link JsonValue}s, and to convert from native Java types to JsonValues.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 *
 */
public interface JsonCreator {
    /**
     * Create a new empty JsonArray.
     * 
     * @return A new JsonArray.
     */
    public JsonArray createArray();

    /**
     * Create a new JsonBoolean.
     * 
     * @param value The boolean value of the JsonBoolean.
     * @return A new JsonBoolean.
     */
    public JsonBoolean createBoolean(Boolean value);

    /**
     * Create a new JsonItem with the given source URL, metadata, and value.
     * 
     * @param sourceUrl The source URL for this item.
     * @param meta The metadata for this item.
     * @param value The value of this item.
     * @return A new JsonItem.
     */
    public JsonItem createItem(String sourceUrl, JsonObject meta, JsonValue value);

    /**
     * Create a new JsonItem with the given source URL and value.
     * 
     * @param sourceUrl The source URL for this item.
     * @param value The value of this item.
     * @return A new JsonItem.
     */
    public JsonItem createItem(String sourceUrl, JsonValue value);

    /**
     * Create a new JsonNative.
     * 
     * @param nativeValue The native object value of the new JsonNative.
     * @return A new JsonNative.
     */
    public JsonNative createNative(Object nativeValue);

    /**
     * Create a new JsonNull.
     * 
     * @return A new JsonNull.
     */
    public JsonNull createNull();

    /**
     * Create a new JsonNumber with the given double value.
     * 
     * @param value The double value of the JsonNumber.
     * @return A new JsonNumber.
     */
    public JsonNumber createNumber(Double value);

    /**
     * Create a new JsonNumber with the given float value.
     * 
     * @param value The float value of the JsonNumber.
     * @return A new JsonNumber.
     */
    public JsonNumber createNumber(Float value);

    /**
     * Create a new JsonNumber with the given int value.
     * 
     * @param value The int value of the JsonNumber.
     * @return A new JsonNumber.
     */
    public JsonNumber createNumber(Integer value);

    /**
     * Create a new empty JsonObject.
     * 
     * @return A new JsonObject.
     */
    public JsonObject createObject();

    /**
     * Create a new JsonString with the given binary value.
     * <p>
     * Note: The JsonString object will point to the underlying byte[] that was supplied; it will
     * not copy it. The internal operations of the JsonSystem will not change it, but it will
     * be made directly available through the JsonString object's {@link JsonString#binaryValue}
     * method. So, if you need to make sure that the original is not changed, you must copy it before
     * supplying it to this method. 
     * 
     * @param value The binary value of the JsonString.
     * @return A new JsonString.
     */
    public JsonString createString(byte[] value);

    /**
     * Create a new JsonString with the given long value.
     * 
     * @param value The long value of the JsonString.
     * @return A new JsonString.
     */
    public JsonString createString(Long value);

    /**
     * Create a new JsonString with the given string value.
     * 
     * @param value The String value of the JsonString.
     * @return A new JsonString.
     */
    public JsonString createString(String value);

    /**
     * Parse the given string as JSON.
     * 
     * Throws an exception if the JSON cannot be parsed.
     * 
     * @param json The String to parse.
     * @return The JsonValue parsed from the string.
     */
    public JsonValue parse(String json);

    /**
     * Parse the given {@link java.io.Reader} as JSON. Note that this takes an Object argument for compatibility with GWT.
     * 
     * @param reader The Reader to parse.
     * @return The JsonValue parsed from the Reader.
     */
    public JsonValue parseReader(Object reader);
}