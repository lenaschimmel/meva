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

package org.itemscript.core.connectors;

import org.itemscript.core.url.Url;
import org.itemscript.core.values.JsonArray;
import org.itemscript.core.values.JsonObject;

/**
 * The interface to be implemented for a {@link Connector} that allows synchronous browse operations.
 * 
 * This is a convenience for synchronous connectors that implement some of the standard set of queries.
 * 
 * If the connector implements this interface, the corresponding method from this interface will be called instead
 * of the simple {@link SyncGetConnector#get} method.
 * 
 * For an example implementation, see {@link MemConnector}.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 *
 */
public interface SyncBrowseConnector extends Connector {
    /**
     * The default page size for {@link #pagedKeys} and {@link #pagedItems} queries, if not specified.
     */
    public final static int DEFAULT_NUM_ROWS = 10;

    /**
     * Called when the query contains the "countItems" key.
     * 
     * It must return a JsonObject with a "count" field with an integer value corresponding to
     * the number of sub-items of the item given by the URL path, like this:
     * 
     * <pre>
     * {
     *     "count" : 1000000
     * }
     * </pre>
     *
     * 
     * Connectors that cannot return a count of sub-items should return a JsonObject exactly like this:
     * 
     * <pre>
     * {
     *     "countType":"unknown",
     *     "count" : -1
     * }
     * </pre>
     * 
     * Connectors that cannot return an accurate count of sub-items but can return an approximate number
     * should return a JsonObject looking like this:
     * 
     * <pre>
     * {
     *     "countType":"approximate",
     *     "count":1000000
     * }
     * </pre>
     * 
     * where 1000000 should be a JsonNumber with the approximate number of sub-items. 
     * 
     * @param url The URL to query.
     * @return A JsonNumber with the count of sub-items, or a JsonObject as above.
     */
    public JsonObject countItems(Url url);

    /**
     * Called when the query contains the "keys" key.
     * 
     * It must return a JsonArray containing the keys for the sub-items of the
     * item given by the URL path as JsonStrings.
     * 
     * Connectors that cannot return a full list of keys should return a single page of keys, as if
     * {@link #pagedKeys} was called without specifying a start row or number of rows to return.
     * 
     * @param url The URL to query.
     * @return A JsonArray with the keys of sub-items as JsonStrings.
     */
    public JsonArray getKeys(Url url);

    /**
     * Called when the query contains the "pagedItems" key.
     * 
     * It must return a JsonArray in which each element is a JsonObject with two entries: under "key"
     * will be the key of the sub-item; under "value" is the actual JsonValue of the sub-item.
     * 
     * It may also return a JsonObject under "meta" containing metadata about the results.
     * 
     * For instance:
     * 
     * <pre>
     * [
     *     {
     *         "key" : "one",
     *         "value" : {
     *             "actual":"value"
     *         }
     *     },
     *     {
     *         "key" : "two",
     *         "value" : {
     *             "another":"value"
     *         }
     *     }
     * ]
     * </pre>
     * 
     * Sorting and paging keys in the query will be used to determine the ordering and number of results,
     * as with the {@link #pagedKeys} method.
     * 
     * The sorting of the results should be alphabetically by key, or failing that, any other deterministic method of sorting.
     * 
     * @param url The URL to query.
     * @return A JsonArray with contents as specified above.
     */
    public JsonArray pagedItems(Url url);

    /**
     * Called when the query contains the "pagedKeys" key.
     * 
     * It must return a JsonArray in which each entry is a JsonString containing the key of a sub-item of
     * the item pointed to by the URL.
     * 
     * The following keys, if present, control the paging and ordering behavior of the query:
     * 
     * <ul>
     * <li>"startRow" - The row to begin the page of keys, where 0 would be the first row.</li>
     * <li>"numRows" - The number of rows to return in the page of keys.</li>
     * <li>"orderBy" - The path to the field in the value of the sub-items to order by. This must have a scalar value, not a
     * container value. The ordering of sub-items whose values are not objects or which are objects without the given field or where the field
     * is a container value is undefined. The path must consist of a key or a series of URL-encoded keys separated by slashes.</li>
     * <li>"ascending" - With a value of "true" or "false", whether to sort results in ascending or descending order.</li>
     * </ul>
     * 
     * If "orderBy" is not present, results should be sorted by key name, or failing that, by any other deterministic method appropriate
     * to the system.
     * 
     * @param url The URL to query.
     * @return A JsonArray containing JsonStrings giving the keys of sub-items.
     */
    public JsonArray pagedKeys(Url url);
}