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

import java.util.ArrayList;
import java.util.List;

import org.itemscript.core.HasSystem;
import org.itemscript.core.JsonSystem;
import org.itemscript.core.Params;
import org.itemscript.core.exceptions.ItemscriptError;
import org.itemscript.core.values.JsonArray;
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;
import org.itemscript.core.values.OverlayObject;

/**
 * The implementation class for {@link JsonFoundry}. You can either instantiate a typed instance
 * of this class, or subclass it. 
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 * 
 * @param <T> The supertype that this foundry will create.
 */
public class ItemscriptFoundry<T> implements HasSystem, JsonFoundry<T> {
    private JsonSystem system;
    private final String location;
    private final JsonObject factories;
    private final String nameKey;

    /**
     * Subclasses must call this constructor.
     * 
     * @param system The associated JsonSystem.
     * @param location The mem:/ location that factories in this foundry will be stored under.
     * @param nameKey The key in the objects supplied to {@link #create(JsonValue)} that contains the name of the factory.
     */
    public ItemscriptFoundry(JsonSystem system, String location, String nameKey) {
        this.system = system;
        this.location = location;
        this.nameKey = nameKey;
        factories = system.createObject();
        system.put(location, factories);
    }

    private void checkName(String name) {
        if (name == null || name.length() == 0) { throw ItemscriptError.internalError(this,
                "checkName.empty.name.in.put"); }
        if (factories.containsKey(name)) { throw ItemscriptError.internalError(this,
                "checkName.name.already.in.use", name); }
    }

    private T create(JsonObject paramsObject) {
        return create(getName(paramsObject), paramsObject);
    }

    @Override
    public final T create(JsonValue params) {
        if (params.isString()) { return create(params.stringValue()); }
        if (params.isContainer()) {
            JsonObject paramsObject;
            if (params.isObject()) {
                paramsObject = params.asObject();
            } else {
                paramsObject = createFromArray(params.asArray());
            }
            return create(paramsObject);
        }
        throw ItemscriptError.internalError(this, "create.params.must.be.JsonString.JsonArray.or.JsonObject",
                params.toCompactJsonString());
    }

    @Override
    public final T create(String name) {
        return create(name, system().createObject());
    }

    @Override
    public final T create(String name, JsonObject params) {
        return createFromFactories(factories, name, params);
    }

    /**
     * Override this method if your foundry knows how to create an object from a JsonArray.
     * 
     * @param array The array supplied to {@link #create(JsonValue)}
     * @return A new JsonObject that can be passed to {@link #create(JsonObject)}.
     */
    public JsonObject createFromArray(JsonArray array) {
        return null;
    }

    public final T createFromFactories(JsonObject factories, JsonObject params) {
        return createFromFactories(factories, getName(params), params);
    }

    @SuppressWarnings("unchecked")
    public final T createFromFactories(JsonObject factories, String name, JsonObject params) {
        if (params == null) { throw new ItemscriptError(
                "error.itemscript.ItemscriptFoundry.create.params.was.null"); }
        if (name == null) {
            name = findMissingName(params);
            if (name == null) { throw ItemscriptError.internalError(this,
                    "create.no.name.supplied.and.no.name.could.be.found", params.toCompactJsonString()); }
            return create(name, params);
        }
        JsonValue factoryValue = factories.get(name);
        if (factoryValue != null) {
            if (factoryValue.isNative()) {
                Object nativeValue = factoryValue.nativeValue();
                JsonFactory<T> factory = (JsonFactory<T>) nativeValue;
                return factory.create(params);
            } else if (factoryValue.isArray()) {
                return create(createFromArray(factoryValue.asArray()));
            } else if (factoryValue.isObject()) {
                JsonObject factoryObject = factoryValue.asObject();
                String underlyingName = getName(factoryObject);
                List<JsonObject> objects = new ArrayList<JsonObject>();
                objects.add(factoryObject);
                objects.add(params);
                return create(underlyingName, new OverlayObject(system(), objects));
            }
        }
        throw ItemscriptError.internalError(this, "create.factory.not.found", new Params().p("name", name));
    }

    public String findMissingName(JsonObject params) {
        return null;
    }

    private String getName(JsonObject p) {
        String name = p.getString(nameKey);
        if (name == null) {
            name = findMissingName(p);
        }
        return name;
    }

    @Override
    public final void put(final FactoryName<T> factoryName) {
        put(factoryName.getName(), factoryName);
    }

    @Override
    public final void put(String name, final JsonFactory<T> factory) {
        checkName(name);
        factories.putNative(name, factory);
    }

    @Override
    public void put(String name, JsonValue params) {
        checkName(name);
        factories.put(name, params);
    }

    @Override
    public JsonSystem system() {
        return system;
    }

    @Override
    public final String toString() {
        return "[Foundry location=" + location + "]";
    }
}