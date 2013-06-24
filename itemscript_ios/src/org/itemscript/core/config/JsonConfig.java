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

package org.itemscript.core.config;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.values.JsonCreator;

/**
 * The interface that must be implemented to configure an {@link JsonSystem}.
 * The methods on this class are either initialization methods called once, after the JsonSystem
 * is created, or utility methods whose implementation may be platform dependent.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 *
 */
public interface JsonConfig {
    /**
     * Create a new {@link JsonCreator}. Will be called once by the JsonSystem after it is created.
     * 
     * @param system The associated JsonSystem.
     * @return A new JsonFactory.
     */
    public JsonCreator createJsonCreator(JsonSystem system);

    /**
     * Generate a new random b64id.
     * 
     * @return A new random b64id.
     */
    public String generateB64id();

    /**
     * Generate a new random UUID.
     * 
     * @return A new random UUID.
     */
    public String generateUuid();

    /**
     * Get a new random int.
     * 
     * @return A new random int.
     */
    public int nextRandomInt();

    /**
     * Seed a newly created {@link JsonSystem}. Will be called once, after the JsonSystem is set up.
     * 
     * @param system The associated JsonSystem.
     */
    public void seedSystem(JsonSystem system);
}