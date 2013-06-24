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

import org.itemscript.core.exceptions.ItemscriptError;
import org.itemscript.core.url.Url;
import org.itemscript.core.url.UrlFactory;

/**
 * An interface defining various utility methods that can't be static but aren't really part of
 * the core {@link JsonSystem} interface.
 * <p>
 * Note: methods on this interface should be consider somewhat more subject to change than other
 * parts of the API.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 */
public interface JsonUtil {
    /**
     * Create a new relative Url from the given base URL and relative URL.
     * 
     * @param baseUrl The base URL.
     * @param relativeUrl The relative URL.
     * @return A new Url object.
     */
    public Url createRelativeUrl(String baseUrl, String relativeUrl);

    /**
     * Create a new relative Url from the given base URL and relative URL.
     * 
     * @param baseUrl The base URL.
     * @param relativeUrl The relative URL.
     * @return A new Url object.
     */
    public Url createRelativeUrl(Url baseUrl, String relativeUrl);

    /**
     * Create a new relative Url from the given base URL and relative URL.
     * 
     * @param baseUrl The base URL.
     * @param relativeUrl The relative URL.
     * @return A new Url object.
     */
    public Url createRelativeUrl(Url baseUrl, Url relativeUrl);

    /**
     * Create a new Url object from the given Url string.
     * 
     * @param url The string to parse.
     * @return A new Url object.
     * @throws ItemscriptError If the string cannot be parsed as a Url.
     */
    public Url createUrl(String url);

    /**
     * Generate a random base-64 unique-ID string. This is a 22-character representation of a 128-bit
     * random unique-ID encoded as base64 without padding and containing only URL-safe characters.
     * <p>
     * Note: although this is a 128-bit random unique-ID, it is not technically a UUID because it may
     * not have the flag bits set that say what type of ID it is.
     * 
     * @return The new b64id.
     */
    public String generateB64id();

    /**
     * Generate a random UUID string.
     * 
     * @return The new UUID.
     */
    public String generateUuid();

    /**
     * Generate a new random int.
     * 
     * @return A new random int.
     */
    public int nextRandomInt();

    /**
     * Get this system's associated {@link UrlFactory}.
     * 
     * @return The associated UrlFactory.
     */
    public UrlFactory urlFactory();
}