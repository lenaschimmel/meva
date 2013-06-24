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

package org.itemscript.core;

import org.itemscript.core.config.JsonConfig;
import org.itemscript.core.url.Url;
import org.itemscript.core.url.UrlFactory;

/**
 * The implementation class for JsonUtil.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 */
class ItemscriptUtil implements JsonUtil, HasSystem {
    private final JsonConfig config;
    private final JsonSystem system;
    private final UrlFactory urlFactory;

    /**
     * Create a new ItemscriptUtil with the associated JsonSystem and JsonConfig.
     * 
     * @param system The associated JsonSystem.
     * @param config The associated JsonConfig.
     */
    ItemscriptUtil(JsonSystem system, JsonConfig config) {
        this.system = system;
        this.config = config;
        this.urlFactory = new UrlFactory(system);
    }

    @Override
    public Url createRelativeUrl(String baseUrl, String relativeUrl) {
        return urlFactory.createRelative(baseUrl, relativeUrl);
    }

    @Override
    public Url createRelativeUrl(Url baseUrl, String relativeUrl) {
        return urlFactory.createRelative(baseUrl, relativeUrl);
    }

    @Override
    public Url createRelativeUrl(Url baseUrl, Url relativeUrl) {
        return urlFactory.createRelative(baseUrl, relativeUrl);
    }

    @Override
    public Url createUrl(String url) {
        return urlFactory.create(url);
    }

    @Override
    public String generateB64id() {
        return config.generateB64id();
    }

    @Override
    public String generateUuid() {
        return config.generateUuid();
    }

    @Override
    public int nextRandomInt() {
        return config.nextRandomInt();
    }

    @Override
    public JsonSystem system() {
        return system;
    }

    @Override
    public UrlFactory urlFactory() {
        return urlFactory;
    }
}