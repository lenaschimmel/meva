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

package org.itemscript.standard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.connectors.ConnectorBase;
import org.itemscript.core.connectors.SyncBrowseConnector;
import org.itemscript.core.connectors.SyncGetConnector;
import org.itemscript.core.exceptions.ItemscriptError;
import org.itemscript.core.url.Url;
import org.itemscript.core.values.JsonArray;
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;

/**
 * Base Connector class for file connectors.
 * <p>
 * Note: At present support for file connectors is fairly minimal; loading of file resources on
 * other servers is not supported, nor are pagedItems or pagedKeys methods from SyncBrowseConnector,
 * nor is SyncPutConnector.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 */
public final class ReaderConnector extends ConnectorBase implements SyncGetConnector, SyncBrowseConnector {
    public ReaderConnector(JsonSystem system) {
        super(system);
    }

    @Override
    public JsonObject countItems(Url url) {
        return countObject(getKeys(url).asArray()
                .size());
    }

    public JsonValue getFromReader(String urlString, Reader reader) {
    	   try {
			return system().createItem(urlString,
			           StandardUtil.readText(system(), new BufferedReader(reader)))
			           .value();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
    }

    private ItemscriptError ioException(IOException e) {
        return ItemscriptError.internalError(this, "get.IOException", e);
    }

    public JsonArray pagedItems(Url url) {
        // FIXME
        return null;
    }

    public JsonArray pagedKeys(Url url) {
        // FIXME
        return null;
    }

	@Override
	public JsonArray getKeys(Url url) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonValue get(Url url) {
		// TODO Auto-generated method stub
		return null;
	}
}