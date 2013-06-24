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

package org.itemscript.core.foundries;

import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;

/**
 * A JsonFoundry is a class that facilitates the creation of instance objects.
 * 
 * It contains a map of {@link JsonFactory} objects. The right factory is found
 * for a {@link JsonObject}, and that factory is used to create a Java object. 
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 *
 * @param <T> The supertype of the types that this foundry will create.
 */
public interface JsonFoundry<T> {
    /**
     * Create a new object using the supplied JsonValue as a parameter.
     * If the supplied value is a JsonString, the value will be used as a name to look up the right
     * factory. If the supplied value is a JsonObject, a key in the JsonObject will be used to find the
     * right factory.
     * 
     * @param parameters The parameters for creating an object.
     * @return The newly created object.
     */
    public abstract T create(JsonValue parameters);

    /**
     * Create an instance of an object using the supplied name to find the
     * factory. No parameters will be supplied to the factory.
     * 
     * @param name The name used to find the right factory.
     * @return The newly created object.
     */
    public abstract T create(String name);

    /**
     * Create an instance of an object using the supplied name to locate the factory.
     * The given parameters will be supplied to the factory.
     * 
     * @param name The name used to find the right factory.
     * @param params The params specifying the object to create.
     * @return The newly created object.
     */
    public abstract T create(String name, JsonObject params);

    /**
     * Put a {@link FactoryName} into this foundry.
     * 
     * @param factoryName The factory to be put in this foundry.
     */
    public abstract void put(FactoryName<T> factoryName);

    /**
     * Put a {@link JsonFactory} into this foundry.
     * 
     * @param name The name of the factory.
     * @param factory The factory to be put in this foundry.
     */
    public abstract void put(String name, JsonFactory<T> factory);

    /**
     * Put a {@link JsonObject} as a factory.
     * 
     * @param name The name of the factory.
     * @param factoryObject The <code>JsonObject</code> describing the factory. 
     */
    public abstract void put(String name, JsonValue factoryObject);
}