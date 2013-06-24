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
import org.itemscript.core.values.JsonObject;

/**
 * The interface to be implemented for a {@link Connector} which is capable of being dumped.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 */
public interface SyncDumpConnector extends Connector {
    /**
     * Dump the value at the given URL to a <code>JsonObject</code>.
     * <p>
     * The returned object will contain two keys. Under the key "value" will be the <code>JsonValue</code>
     * associated with the item that was dumped. Under the key "subItems" will be a <code>JsonObject</code>.
     * In that object, each key will the key of a sub-item of the dumped item, and each corresponding value
     * will be the dumped value of that sub-item.
     * <p>
     * For instance:
     * <pre>
     * system.put("/foo", 1);
     * system.put("/foo/a", 2);
     * system.put("/foo/a/x", true);
     * system.put("/foo/b", 3);
     * System.out.println(system.get("/foo?dump"));
     * </pre>
     * prints:
     * <pre>
     * {
     *     "subItems" : {
     *         "b" : {
     *              "subItems" : {},
     *              "value" : 3
     *         },
     *         "a" : {
     *             "subItems" : {
     *                 "x" : {
     *                     "subItems" : {},
     *                     "value" : true
     *                 }
     *             },
     *             "value" : 2
     *         }
     *     },
     *     "value" : 1
     * }
     * </pre>
     * 
     * @see SyncLoadConnector#load
     * @param url The URL to dump.
     * @return The <code>JsonObject</code> corresponding to the dumped contents of that value.
     */
    public JsonObject dump(Url url);
}