/*
 * Copyright � 2010, Data Base Architects, Inc. All rights reserved.
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

final class ItemscriptNumber extends ItemscriptScalar implements JsonNumber {
    private final double value;

    protected ItemscriptNumber(JsonSystem system, Double value) {
        super(system);
        this.value = value;
    }

    public ItemscriptNumber(JsonSystem system, Float value) {
        this(system, (double) value.floatValue());
    }

    public ItemscriptNumber(JsonSystem system, Integer value) {
        this(system, (double) value.intValue());
    }

    protected ItemscriptNumber(JsonSystem system, JsonContainer parent) {
        this(system, 0);
    }

    public ItemscriptNumber(JsonSystem system, Long value) {
        this(system, (double) value.longValue());
    }

    @Override
    public JsonNumber asNumber() {
        return this;
    }

    @Override
    public JsonNumber copy() {
        return system().createNumber(value);
    }

    @Override
    public Double doubleValue() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof JsonNumber) { return value == ((JsonNumber) other).doubleValue(); }
        return false;
    }

    @Override
    public Float floatValue() {
        return (float) value;
    }

    @Override
    public Integer intValue() {
        return (int) value;
    }

    @Override
    public boolean isNumber() {
        return true;
    }

    @Override
    public String toJsonString() {
        String s = value + "";
        if (s.indexOf('.') > 0 && s.indexOf('e') < 0 && s.indexOf('E') < 0) {
            while (s.endsWith("0")) {
                s = s.substring(0, s.length() - 1);
            }
            if (s.endsWith(".")) {
                s = s.substring(0, s.length() - 1);
            }
        }
        return s;
    }
}