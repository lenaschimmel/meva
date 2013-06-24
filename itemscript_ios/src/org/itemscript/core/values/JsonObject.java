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

/**
 * Represents a JSON object value.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 *
 */
public interface JsonObject extends JsonContainer, Map<String, JsonValue> {
    /**
     * Put a String value in this container under the given key, returning this container.
     * 
     * @param key The key to put the value under.
     * @param value The String value to store.
     * @return This JsonObject.
     */
    public JsonObject p(String key, Boolean value);

    /**
     * Put a double value in this container under the given key, returning this container.
     * 
     * @param key The key to put the value under.
     * @param value The double value to store.
     * @return This JsonObject.
     */
    public JsonObject p(String key, Double value);

    /**
     * Put a float value in this container under the given key, returning this container.
     * 
     * @param key The key to put the value under.
     * @param value The String value to store.
     * @return This JsonObject.
     */
    public JsonObject p(String key, Float value);

    /**
     * Put a integer value in this container under the given key, returning this container.
     * 
     * @param key The key to put the value under.
     * @param value The int value to store.
     * @return This JsonObject.
     */
    public JsonObject p(String key, Integer value);

    /**
     * Put a JsonValue in this container under the given key, returning this container.
     * 
     * @param key The key to put the value under.
     * @param value The value to store.
     * @return This JsonObject.
     */
    public JsonObject p(String key, JsonValue value);

    /**
     * Put a long in this container under the given key, returning this container.
     * Note that because JSON numbers cannot safely contain a long value, longs are stored in JSON string values.
     * 
     * @param key The key to put the value under.
     * @param value The long value to store.
     * @return This JsonObject.
     */
    public JsonObject p(String key, Long value);

    /**
     * Put a String value in this container under the given key, returning this container.
     * 
     * @param key The key to put the value under.
     * @param value The String value to store.
     * @return This JsonObject.
     */
    public JsonObject p(String key, String value);
}