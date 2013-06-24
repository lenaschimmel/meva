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
import org.itemscript.core.connectors.GetCallback;
import org.itemscript.core.connectors.PutCallback;
import org.itemscript.core.connectors.RemoveCallback;
import org.itemscript.core.events.Handler;
import org.itemscript.core.events.HandlerReg;

/**
 * A JsonItem is associated with a JsonValue loaded from a particular source.
 * 
 * It provides methods for accessing values nested inside its associated JsonValue by fragment identifier,
 * for accessing values relative to the URL that this item was originally loaded from, and for adding event handlers
 * that will be notified when values inside this item are modified or removed.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 *
 */
public interface JsonItem extends HasSystem, JsonGetAccess, JsonPutAccess {
    /**
     * Add an event handler to this item. The handler will be called any time the value of this item
     * changes or is removed. The returned HandlerReg can be used to remove the event handler at a later date.
     * 
     * @param handler The Handler to attach to this JsonItem.
     * @return The HandlerReg for this event handler.
     */
    public HandlerReg addHandler(Handler handler);

    /**
     * Get a value from (or relative to) this item by URL.
     * 
     * If the URL is a fragment only (starting with "#"), the fragment will be used to retrieve a value from
     * the root value of this item. A fragment of "#" will return the root value; a fragment containing a string
     * will be split on the dot (".") character, and each component will be URL-unencoded, then the components will
     * be used to navigate from the root value of this item to the desired child item.
     * 
     * If the URL is a relative URL (starting with a path or a query), it will be interpreted as relative to the source URL of this item
     * and then retrieved with {@link JsonSystem#get(String)}.
     * 
     * If the URL is an absolute URL, it will be retrieved with {@link JsonSystem#get(String)}.
     * 
     * @param url The URL to get.
     * @return The value returned by the get operation.
     */
    public JsonValue get(String url);

    /**
     * Get a value from (or relative to) this item by URL, asynchronously.
     * 
     * The URL will be interpreted the same way as in {@link #get(String)}.
     * 
     * The supplied callback will be called when the value is returned.
     * 
     * @param url The URL to get.
     * @param callback The callback that will be called when the get operation completes.
     */
    public void get(String url, GetCallback callback);

    /**
     * Retrieve optional metadata about this item.
     * 
     * @return A JsonObject containing metadata about this item, or null if no metadata is available.
     */
    public JsonObject meta();

    /**
     * Put a value in (or relative to) this item by URL.
     * 
     * The URL will be interpreted the same way as in {@link #get(String)}.
     * 
     * @param url The URL to put the value at.
     * @param value The value to put.
     * @return The PutResponse returned by the put operation.
     */
    public PutResponse put(String url, JsonValue value);

    /**
     * Put a value in (or relative to) this item by URL, asynchronously.
     * 
     * The URL will be interpreted the same way as in {@link #get(String)}.
     * 
     * The supplied callback will be called when the operation is complete.
     * 
     * @param url The URL to put the value at.
     * @param value The value to put.
     * @param callback The callback that will be called when the put operation completes.
     */
    public void put(String url, JsonValue value, PutCallback callback);

    /**
     * Remove a value from (or relative to) this item by URL.
     * 
     * The URL will be interpreted the same way as in {@link #get(String)}.
     * 
     * @param url The URL to remove a value from.
     * @return The RemoveResponse returned by the remove operation.
     */
    public RemoveResponse remove(String url);

    /**
     * Remove a value from (or relative to) this item by URL.
     * 
     * The URL will be interpreted the same way as in {@link #get(String)}.
     * 
     * The supplied callback will be called when the operation is complete.
     * 
     * @param url The URL to remove a value from.
     * @param callback The callback to call when the remove operation completes.
     */
    public void remove(String url, RemoveCallback callback);

    /**
     * Remove an event handler from this item. Use this to clean up event handlers when the object they point
     * to needs to be garbage-collected; otherwise, they will leak memory.
     * 
     * @param reg The original HandlerReg that was returned from addHandler.
     */
    public void removeHandler(HandlerReg reg);

    /**
     * Detach the value from this item, allowing it to be attached to a different item.
     */
    public void detachValue();

    /**
     * Get the source URL of this item.
     * 
     * @return The source URL of this item.
     */
    public String source();

    /**
     * Get the JsonValue of this item.
     * 
     * @return The JsonValue of this item.
     */
    public JsonValue value();
}