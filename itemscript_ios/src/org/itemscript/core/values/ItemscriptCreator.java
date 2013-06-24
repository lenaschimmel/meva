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
import org.itemscript.core.url.Url;

/**
 * The implementation class for a {@link JsonCreator}. Subclass this to create a new JsonCreator type.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 */
public abstract class ItemscriptCreator implements JsonCreator, HasSystem {
    public static String quotedString(String value) {
        if (value == null || value.length() == 0) { return "\"\""; }
        char b;
        char c = 0;
        int i;
        int len = value.length();
        StringBuffer sb = new StringBuffer(len + 4);
        String t;
        sb.append('"');
        for (i = 0; i < len; i += 1) {
            b = c;
            c = value.charAt(i);
            switch (c) {
                case '\\' :
                case '"' :
                    sb.append('\\');
                    sb.append(c);
                    break;
                case '/' :
                    if (b == '<') {
                        sb.append('\\');
                    }
                    sb.append(c);
                    break;
                case '\b' :
                    sb.append("\\b");
                    break;
                case '\t' :
                    sb.append("\\t");
                    break;
                case '\n' :
                    sb.append("\\n");
                    break;
                case '\f' :
                    sb.append("\\f");
                    break;
                case '\r' :
                    sb.append("\\r");
                    break;
                default :
                    if (c < ' ' || (c >= '\u0080' && c < '\u00a0') || (c >= '\u2000' && c < '\u2100')) {
                        t = "000" + Integer.toHexString(c);
                        sb.append("\\u" + t.substring(t.length() - 4));
                    } else {
                        sb.append(c);
                    }
            }
        }
        sb.append('"');
        return sb.toString();
    }

    private final JsonSystem system;

    /**
     * Call this constructor from sub-classes.
     * 
     * @param system The associated JsonSystem.
     */
    public ItemscriptCreator(JsonSystem system) {
        this.system = system;
    }

    @Override
    public final JsonArray createArray() {
        return new ItemscriptArray(system);
    }

    @Override
    public final JsonBoolean createBoolean(Boolean value) {
        return new ItemscriptBoolean(system, value);
    }

    @Override
    public final JsonItem createItem(String sourceUrl, JsonObject meta, JsonValue value) {
        return createItem(system().util()
                .createUrl(sourceUrl), meta, value);
    }

    @Override
    public final JsonItem createItem(String sourceUrl, JsonValue value) {
        return createItem(sourceUrl, null, value);
    }

    /**
     * Create a new JsonItem with the given source URL, metadata, and value.
     * 
     * @param sourceUrl The source URL for this item.
     * @param meta The metadata for this item.
     * @param value The value of this item.
     * @return A new JsonItem.
     */
    public final JsonItem createItem(Url sourceUrl, JsonObject meta, JsonValue value) {
        return new ItemscriptItem(system, sourceUrl, meta, value);
    }

    @Override
    public final JsonNative createNative(Object nativeObject) {
        ItemscriptNative value = new ItemscriptNative(system, nativeObject);
        return value;
    }

    @Override
    public final JsonNull createNull() {
        return new ItemscriptNull(system);
    }

    @Override
    public final JsonNumber createNumber(Double value) {
        return new ItemscriptNumber(system, value);
    }

    @Override
    public final JsonNumber createNumber(Float value) {
        return createNumber((double) value);
    }

    @Override
    public final JsonNumber createNumber(Integer value) {
        return createNumber((double) value);
    }

    @Override
    public final JsonObject createObject() {
        return new ItemscriptObject(system);
    }

    @Override
    public final JsonString createString(byte[] value) {
        return new ItemscriptString(system, value);
    }

    @Override
    public final JsonString createString(Long value) {
        return createString("" + value);
    }

    @Override
    public final JsonString createString(String value) {
        return new ItemscriptString(system, value);
    }

    @Override
    public JsonSystem system() {
        return system;
    }
}