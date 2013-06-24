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
 *     * Neither the names of Kalinda Software, DBA Software, Data Base Architects, Itemscript
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

package org.itemscript.core.exceptions;

import java.util.Map;

import org.itemscript.core.Params;

/**
 * The base class for all exceptions thrown by the Itemscript system.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 *
 */
public class ItemscriptError extends RuntimeException {
    /**
     * The prefix for all internal Itemscript errors.
     */
    public static final String ITEMSCRIPT_ERROR_KEY_PREFIX = "error.itemscript";

    private static String getClassName(Object source) {
        if (source == null) { return "(null)"; }
        String fullName = source.getClass() + "";
        return fullName.substring(fullName.lastIndexOf('.') + 1);
    }

    private static String getErrorString(String key, String singleParam, Map<String, String> params,
            Throwable cause) {
        StringBuffer errorString = new StringBuffer();
        errorString.append("[" + key);
        if (params != null) {
            if (singleParam != null) {
                errorString.append(" " + singleParam);
            } else {
                for (String paramKey : params.keySet()) {
                    errorString.append(" " + paramKey + "=" + params.get(paramKey));
                }
            }
        }
        errorString.append("]");
        if (cause != null) {
            errorString.append("\nCaused by: " + cause.getClass() + "\n with message: " + cause.getMessage());
        }
        return errorString.toString();
    }

    /**
     * Create a new exception with the given source and message key suffix.
     * 
     * @param source An instance of the object where this exception originated.
     * @param keySuffix The key suffix to add to the default Itemscript error prefix.
     * @return A new ItemscriptError.
     */
    public static ItemscriptError internalError(Object source, String keySuffix) {
        return new ItemscriptError(prefix(source) + keySuffix);
    }

    /**
     * Create a new exception with the given source, message key suffix, and parameters.
     * 
     * @param source An instance of the object where this exception originated.
     * @param keySuffix The key suffix to add to the default Itemscript error prefix.
     * @param params The parameters that go with this error.
     * @return A new ItemscriptError.
     */
    public static ItemscriptError internalError(Object source, String keySuffix, Map<String, String> params) {
        return new ItemscriptError(prefix(source) + keySuffix, params);
    }

    /**
     * Create a new exception with the given source, message key suffix, and single string parameter.
     * 
     * @param source An instance of the object where this exception originated.
     * @param keySuffix The key suffix to add to the default Itemscript error prefix.
     * @param param The single parameter that goes with this error.
     * @return A new ItemscriptError.
     */
    public static ItemscriptError internalError(Object source, String keySuffix, final String param) {
        return new ItemscriptError(prefix(source) + keySuffix, param);
    }

    /**
     * Create a new exception with the given source, message key suffix, and cause.
     * 
     * @param source An instance of the object where this exception originated.
     * @param keySuffix The key suffix to add to the default Itemscript error prefix.
     * @param cause The exception that caused this error.
     * @return A new ItemscriptError.
     */
    public static ItemscriptError internalError(Object source, String keySuffix, Throwable cause) {
        return new ItemscriptError(prefix(source) + keySuffix, cause);
    }

    private static String prefix(Object source) {
        return ITEMSCRIPT_ERROR_KEY_PREFIX + "." + getClassName(source) + ".";
    }

    private final String key;
    private final Map<String, String> params;
    private final Throwable cause;
    private final String singleParam;

    /**
     * Create a new exception with the given message key.
     * 
     * @param key The message key for this exception.
     */
    public ItemscriptError(String key) {
        this(key, new Params(), null);
    }

    /**
     * Create a new exception with the given message key and parameters.
     * 
     * @param key The message key for this exception.
     * @param params The parameters for this exception.
     */
    public ItemscriptError(String key, Map<String, String> params) {
        this(key, params, null);
    }

    private ItemscriptError(String key, Map<String, String> params, Throwable cause) {
        super(getErrorString(key, null, params, cause));
        this.key = key;
        this.params = params;
        this.cause = cause;
        this.singleParam = null;
    }

    /**
     * Create a new exception with the given message key and single string parameter.
     * 
     * @param key The message key for this exception.
     * @param param The single string parameter for this exception.
     */
    public ItemscriptError(String key, final String param) {
        this(key, new Params().p("param", param), null);
    }

    /**
     * Create a new exception with the given message key and cause.
     * 
     * @param key The message key for this exception.
     * @param cause The underlying cause of this exception.
     */
    public ItemscriptError(String key, Throwable cause) {
        super(getErrorString(key, null, null, cause));
        this.key = key;
        this.params = null;
        this.cause = cause;
        this.singleParam = null;
    }

    /**
     * Get the message key for this exception.
     * 
     * @return The message key.
     */
    public String key() {
        return key;
    }

    /**
     * Get the params for this exception.
     * 
     * @return A Map&lt;String,String&gt; of params, or null if there was no params map.
     */
    public Map<String, String> params() {
        return params;
    }

    /**
     * Get the single param for this exception.
     * 
     * @return The single param, or null if there was no single param.
     */
    public String singleParam() {
        return singleParam;
    }
}