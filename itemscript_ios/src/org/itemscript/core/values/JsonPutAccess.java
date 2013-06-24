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

import java.util.Map;

import org.itemscript.core.JsonSystem;

/**
 * Provides access to contained {@link JsonValue} by URLs for the {@link JsonItem} or {@link JsonSystem} classes.
 * 
 * For compatibility with the Map and List interfaces, the simple put and remove methods on this class
 * are named putValue and removeValue. Implementing classes should add put and remove methods compatible
 * with Map and List as appropriate for their interface.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 *
 */
public interface JsonPutAccess {
    /**
     * Create a new JsonArray at the given URL.
     * 
     * @param url The URL to create at.
     * @return The PutResponse returned by the put operation.
     */
    public PutResponse createArray(String url);

    /**
     * Create a new JsonObject at the given URL.
     * 
     * @param url The URL to create at.
     * @return The PutResponse returned by the put operation.
     */
    public PutResponse createObject(String url);

    /**
     * Put a boolean value as a JsonBoolean.
     * 
     * @param url The URL to put the value under.
     * @param value The boolean value to store.
     * @return The PutResponse returned by the put operation.
     */
    public PutResponse put(String url, Boolean value);

    /**
     * Put a binary value. The value will be base64-encoded and stored in a JsonString.
     * <p>
     * Note: The JsonString object will point to the underlying byte[] that was supplied; it will
     * not copy it. The internal operations of the JsonSystem will not change it, but it will
     * be made directly available through the JsonString object's {@link JsonString#binaryValue}
     * method. So, if you need to make sure that the original is not changed, you must copy it before
     * supplying it to this method. 
     * 
     * @param url The URL to put the value under.
     * @param value The binary value to store.
     * @return The PutResponse returned by the put operation. 
     */
    public PutResponse put(String url, byte[] value);

    /**
     * Put a double value as a JsonNumber.
     * 
     * @param url The URL to put the value under.
     * @param value The double value to store.
     * @return The PutResponse returned by the put operation. 
     */
    public PutResponse put(String url, Double value);

    /**
     * Put a float value as a JsonNumber.
     * 
     * @param url The URL to put the value under.
     * @param value The float value to store.
     * @return The PutResponse returned by the put operation. 
     */
    public PutResponse put(String url, Float value);

    /**
     * Put an int value as a JsonNumber.
     * 
     * @param url The URL to put the value under.
     * @param value The int value to store.
     * @return The PutResponse returned by the put operation. 
     */
    public PutResponse put(String url, Integer value);

    /**
     * Put a long value as a JsonString.
     * 
     * @param url The URL to put the value under.
     * @param value The long value to store.
     * @return The PutResponse returned by the put operation. 
     */
    public PutResponse put(String url, Long value);

    /**
     * Put a string value as a JsonString.
     * 
     * @param url The URL to put the value under.
     * @param value The String value to store.
     * @return The PutResponse returned by the put operation. 
     */
    public PutResponse put(String url, String value);

    /**
     * Put a native object as a JsonNative.
     * 
     * @param url The URL to put the value under.
     * @param value The native object to store.
     * @return The PutResponse returned by the put operation. 
     */
    public PutResponse putNative(String url, Object value);

    /**
     * Put a JsonValue.
     * <p>
     * Note that the return from this method is the value that was returned from the put operation, which differs
     * from the {@link Map#put} method which returns any previous value that the map had under that url.<br/>
     * <p>
     * The <code>put</code> methods on JsonSystem and JsonItem are interchangeable
     * with this method.
     * 
     * @param url The URL to put the value under.
     * @param value The JsonValue to store.
     * @return The PutResponse returned by the put operation. 
     */
    public PutResponse putValue(String url, JsonValue value);

    /**
     * Remove a value.
     * <p>
     * This method is named "removeValue" to avoid conflicts with the List and Map "remove" methods.
     * A method named "remove" will be provided by any class implementing this interface, and that
     * method should be used instead of this one, for brevity.
     * 
     * @param url The URL indicating the value to remove.
     * @return The RemoveResponse returned by the remove operation.
     */
    public RemoveResponse removeValue(String url);
}