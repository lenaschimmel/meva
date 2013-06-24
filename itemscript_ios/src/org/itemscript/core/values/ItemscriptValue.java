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
import org.itemscript.core.exceptions.ItemscriptError;
import org.itemscript.core.url.Url;

abstract class ItemscriptValue implements JsonValue {
    private final JsonSystem system;
    private String key = null;
    private JsonContainer parent;
    private JsonItem item;

    protected ItemscriptValue(JsonSystem system) {
        this.system = system;
    }

    @Override
    public JsonArray asArray() {
        return null;
    }

    @Override
    public JsonBoolean asBoolean() {
        return null;
    }

    @Override
    public JsonContainer asContainer() {
        return null;
    }

    @Override
    public JsonNative asNative() {
        return null;
    }

    @Override
    public JsonNull asNull() {
        return null;
    }

    @Override
    public JsonNumber asNumber() {
        return null;
    }

    @Override
    public JsonObject asObject() {
        return null;
    }

    @Override
    public JsonString asString() {
        return null;
    }

    @Override
    public byte[] binaryValue() {
        throw new UnsupportedOperationException("binaryValue() called on a value that was not a JsonString");
    }

    @Override
    public Boolean booleanValue() {
        throw new UnsupportedOperationException("booleanValue() called on a value that was not a JsonBoolean");
    }

    @Override
    public Double doubleValue() {
        throw new UnsupportedOperationException("doubleValue() called on a value that was not a JsonNumber");
    }

    @Override
    public Float floatValue() {
        throw new UnsupportedOperationException("floatValue() called on a value that was not a JsonNumber");
    }

    @Override
    public final String fragment() {
        if (parent() == null) {
            return "#";
        } else {
            if (parent().parent() == null) {
                return "#" + Url.encode(key());
            } else {
                return parent().fragment() + "." + Url.encode(key());
            }
        }
    }

    @Override
    public Integer intValue() {
        throw ItemscriptError.internalError(this, "intValue.called.on.a.value.that.was.not.a.number",
                toCompactJsonString());
    }

    @Override
    public boolean isArray() {
        return false;
    }

    @Override
    public boolean isBoolean() {
        return false;
    }

    @Override
    public boolean isContainer() {
        return false;
    }

    @Override
    public boolean isNative() {
        return false;
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public boolean isNumber() {
        return false;
    }

    @Override
    public boolean isObject() {
        return false;
    }

    @Override
    public boolean isString() {
        return false;
    }

    @Override
    public final JsonItem item() {
        if (parent() == null) {
            return item;
        } else {
            return parent().item();
        }
    }

    @Override
    public final String key() {
        return key;
    }

    @Override
    public Long longValue() {
        throw new UnsupportedOperationException("longValue() called on a value that was not a JsonString");
    }

    @Override
    public Object nativeValue() {
        throw new UnsupportedOperationException("nativeObject() called on a value that was not a JsonNative");
    }

    @Override
    public final JsonContainer parent() {
        return parent;
    }

    protected final void setItem(JsonItem newItem) {
        // If this item has a parent we need to set item on the parent, not on this...
        if (parent() != null) {
            ((ItemscriptValue) parent()).setItem(newItem);
        } else {
            // It's okay to set item to null if it's not null, but not to any other value.
            if (newItem != null && item() != null) {
                // Should not occur, but just in case...
                throw ItemscriptError.internalError(this, "setItem.item.was.already.set");
            }
            this.item = newItem;
        }
    }

    protected final void setKey(String newKey) {
        // It's okay to set key to null if it's not null, but not to any other value.
        if (newKey != null && key() != null) {
            // Should not occur, but just in case...
            throw ItemscriptError.internalError(this, "setKey.key.was.already.set");
        }
        this.key = newKey;
    }

    protected final void setParent(JsonContainer newParent) {
        // It's not okay to change the parent of this value to a value that is a sub-value of it
        // (i.e. a cycle), only applies if this is a container of course.
        if (isContainer()) {
            JsonContainer parentToCheck = newParent;
            while (parentToCheck != null) {
                if (parentToCheck == this) { throw ItemscriptError.internalError(this,
                        "setParent.cycle.detected.in.new.parent"); }
                parentToCheck = parentToCheck.parent();
            }
        }
        // It's not okay to change the parent of a value to a value from another item.
        if (item() != null && newParent != null && item() != newParent.item()) { throw ItemscriptError.internalError(
                this, "setParent.new.parent.was.in.another.item"); }
        // It's okay to set parent to null if it's not null, but not to any other value.
        if (newParent != null && parent() != null) {
            // Should not occur, but just in case...
            throw ItemscriptError.internalError(this, "setParent.parent.was.already.set");
        }
        this.parent = newParent;
    }

    @Override
    public String stringValue() {
        throw new UnsupportedOperationException("stringValue() called on a value that was not a JsonString");
    }

    @Override
    public final JsonSystem system() {
        return system;
    }

    @Override
    public final String toString() {
        return toJsonString();
    }
}