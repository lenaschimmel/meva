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
import org.itemscript.core.util.Base64;

final class ItemscriptString extends ItemscriptScalar implements JsonString {
    // These fields are effectively final, but are lazily cross-initialized, so they can't actually be final.
    // One of them must be set when the object is created; the other *may* be set when an attempt is made to
    // access a binary value (if the original value was a string) or a string value (if the original value was binary).
    private String stringValue;
    private byte[] binaryValue;

    protected ItemscriptString(JsonSystem system, byte[] value) {
        super(system);
        this.stringValue = null;
        this.binaryValue = value;
    }

    protected ItemscriptString(JsonSystem system, String value) {
        super(system);
        this.stringValue = value;
        this.binaryValue = null;
    }

    @Override
    public JsonString asString() {
        return this;
    }

    @Override
    public byte[] binaryValue() {
        if (binaryValue == null) {
            binaryValue = Base64.decode(stringValue);
        }
        return binaryValue;
    }

    @Override
    public JsonValue copy() {
        if (stringValue == null && binaryValue != null) { return system().createString(binaryValue); }
        return system().createString(stringValue);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof JsonString) { return stringValue().equals(((JsonString) other).stringValue()); }
        return false;
    }

    @Override
    public boolean isString() {
        return true;
    }

    @Override
    public Long longValue() {
        try {
            return Long.parseLong(stringValue);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String stringValue() {
        if (stringValue == null) {
            if (binaryValue != null) {
                stringValue = new String(Base64.encode(binaryValue));
            }
        }
        return stringValue;
    }

    @Override
    public String toJsonString() {
        return ItemscriptCreator.quotedString(stringValue());
    }
}