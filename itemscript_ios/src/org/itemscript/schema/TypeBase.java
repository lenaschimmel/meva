
package org.itemscript.schema;

import org.itemscript.core.HasSystem;
import org.itemscript.core.JsonSystem;
import org.itemscript.core.exceptions.ItemscriptError;
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;

abstract class TypeBase implements Type, HasSystem {
    private final JsonSystem system;
    private final Schema schema;
    private final Type extendsType;
    private final boolean isBaseAnyType;
    private final String description;

    /**
     * This constructor is exclusively for use by the base (unrestricted) AnyType.
     * 
     * @param schema
     */
    TypeBase(Schema schema) {
        this.system = schema.system();
        this.schema = schema;
        this.extendsType = null;
        this.isBaseAnyType = true;
        this.description = null;
    }

    /**
     * This constructor should be called by all other subclasses.
     * 
     * @param schema
     * @param extendsType
     * @param def
     */
    TypeBase(Schema schema, Type extendsType, JsonObject def) {
        this.system = schema.system();
        this.schema = schema;
        this.extendsType = extendsType;
        if (extendsType == null) { throw ItemscriptError.internalError(this, "TypeBase.constructor.extendsType.was.null"); }
        this.isBaseAnyType = false;
        if (def != null) {
            this.description = def.getString(".description");
        } else {
            this.description = null;
        }
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public boolean isArray() {
        return false;
    }

    @Override
    public boolean isBinary() {
        return false;
    }

    @Override
    public boolean isBoolean() {
        return false;
    }

    @Override
    public boolean isInteger() {
        return false;
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public boolean isNumber() {
        return false;
    }

    @Override
    public boolean isObject() {
        return false;
    }

    @Override
    public boolean isString() {
        return false;
    }
    
    @Override
    public boolean isDecimal() {
    	return false;
    }
    
    @Override
    public boolean isLong() {
    	return false;
    }
    
    @Override
    public boolean isAny() {
    	return false;
    }

    @Override
    public Schema schema() {
        return schema;
    }

    @Override
    public JsonSystem system() {
        return system;
    }
    
    @Override
    public void validate(String path, JsonValue value) {
        if (!isBaseAnyType) {
            extendsType.validate(path, value);
        }
    }
}