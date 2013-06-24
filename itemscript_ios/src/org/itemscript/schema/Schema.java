
package org.itemscript.schema;

import org.itemscript.core.HasSystem;
import org.itemscript.core.JsonSystem;
import org.itemscript.core.Params;
import org.itemscript.core.exceptions.ItemscriptError;
import org.itemscript.core.url.Url;
import org.itemscript.core.values.JsonArray;
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;

/**
 * Provides access to the Schema functions of Itemscript. 
 * 
 * @author Jacob Davies <br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 *
 */
public class Schema implements HasSystem {
    private static final String ASTERISK = "*";
    public static final String TYPES_URL = "mem:/itemscript/schema/types";
    public static final String SCHEMA_URL = "mem:/itemscript/schema/Schema";
    private final JsonSystem system;
    public final JsonObject types;

    /**
     * Create a new Schema, and store it in the associated JsonSystem.
     * 
     * @param system
     */
    public Schema(JsonSystem system) {
        this.system = system;
        system.putNative(SCHEMA_URL, this);
        JsonObject typesObject = system.getObject(TYPES_URL);
        boolean initialized = true;
        if (typesObject == null) {
            typesObject = system.createObject(TYPES_URL)
                    .value()
                    .asObject();
            // FIXME - this check for initialized should be a bit more sophisticated.
            initialized = false;
        }
        this.types = typesObject;
        if (!initialized) {
            initTypes(types);
        }
    }

    String addKey(String path, String key) {
        if (path.length() == 0) {
            return Url.encode(key);
        } else {
            return path + "/" + Url.encode(key);
        }
    }

    private Type createAnyType(Type extendsType, JsonObject def) {
        return new AnyType(this, extendsType, def);
    }

    private Type createArrayType(Type extendsType, JsonObject def) {
        return new ArrayType(this, extendsType, def);
    }

    private Type createBinaryType(Type extendsType, JsonObject def) {
        return new BinaryType(this, extendsType, def);
    }

    private Type createBooleanType(Type extendsType, JsonObject def) {
        return new BooleanType(this, extendsType, def);
    }

    private Type createDecimalType(Type extendsType, JsonObject def) {
        return new DecimalType(this, extendsType, def);
    }

    private Type createFromArray(JsonArray array) {
        // An empty array means a generic array.
        if (array.size() == 0) { return get("array"); }
        return createFromObject(system().createObject()
                .p(".extends", "array")
                .p(".contains", array));
    }

    private Type createFromObject(JsonObject def) {
        // An empty object means a generic object.
        if (def.size() == 0) { return get("object"); }
        if (def.hasString(".extends")) {
            // Get the actual type.
            Type extendsType = get(def.getString(".extends"));
            if (extendsType != null) {
                if (extendsType.isAny()) {
                    return createAnyType(extendsType, def);
                } else if (extendsType.isObject()) {
                    return createObjectType(extendsType, def);
                } else if (extendsType.isArray()) {
                    return createArrayType(extendsType, def);
                } else if (extendsType.isString()) {
                    return createStringType(extendsType, def);
                } else if (extendsType.isBinary()) {
                    return createBinaryType(extendsType, def);
                } else if (extendsType.isNumber()) {
                    return createNumberType(extendsType, def);
                } else if (extendsType.isInteger()) {
                    return createIntegerType(extendsType, def);
                } else if (extendsType.isBoolean()) {
                    return createBooleanType(extendsType, def);
                } else if (extendsType.isNull()) {
                    return createNullType(extendsType, def);
                } else if (extendsType.isDecimal()) {
                    return createDecimalType(extendsType, def);
                } else if (extendsType.isLong()) {
                    return createLongType(extendsType, def);
                } else {
                    // This of course should never happen.
                    throw ItemscriptError.internalError(this, "createFromObject.extendsType.unknown.type",
                            extendsType + "");
                }
            } else {
                throw ItemscriptError.internalError(this, "createFromObject.extendsType.was.null", extendsType
                        + "");
            }
        } else {
            // Default to object type if no EXTENDS key.
            return createObjectType(get("object"), def);
        }
    }

    private Type createIntegerType(Type extendsType, JsonObject def) {
        return new IntegerType(this, extendsType, def);
    }

    private Type createLongType(Type extendsType, JsonObject def) {
        return new LongType(this, extendsType, def);
    }

    private Type createNullType(Type extendsType, JsonObject def) {
        return new NullType(this, extendsType, def);
    }

    private Type createNumberType(Type extendsType, JsonObject def) {
        return new NumberType(this, extendsType, def);
    }

    private ObjectType createObjectType(Type extendsType, JsonObject def) {
        return new ObjectType(this, extendsType, def);
    }

    private Type createStringType(Type extendsType, JsonObject def) {
        return new StringType(this, extendsType, def);
    }

    /**
     * Returns the Type associated with the string.
     * 
     * @param type
     * @return Type associated with the string.
     */
    public Type get(String type) {
        return (Type) types.getNative(type);
    }

    private void initTypes(JsonObject typesObject) {
        AnyType anyType = new AnyType(this);
        typesObject.putNative("any", new AnyType(this, anyType, null));
        typesObject.putNative("string", new StringType(this, anyType, null));
        typesObject.putNative("number", new NumberType(this, anyType, null));
        typesObject.putNative("boolean", new BooleanType(this, anyType, null));
        typesObject.putNative("null", new NullType(this, anyType, null));
        typesObject.putNative("integer", new IntegerType(this, anyType, null));
        typesObject.putNative("binary", new BinaryType(this, anyType, null));
        typesObject.putNative("array", new ArrayType(this, anyType, null));
        typesObject.putNative("object", new ObjectType(this, anyType, null));
        typesObject.putNative("decimal", new DecimalType(this, anyType, null));
        typesObject.putNative("long", new LongType(this, anyType, null));
    }

    /**
     * Test whether or not the specified string matches the given pattern.
     * 
     * @param pattern
     * @param string
     * @return True if matched, false otherwise.
     */
    public boolean match(String pattern, String string) {
        if (pattern.endsWith(ASTERISK)) {
            return string.startsWith(pattern.substring(0, pattern.length() - 1));
        } else if (pattern.startsWith(ASTERISK)) {
            return string.endsWith(pattern.substring(1));
        } else {
            throw ItemscriptError.internalError(this, "match.pattern.did.not.start.or.end.with.asterisk", pattern);
        }
    }

    Params pathParams(String path) {
        return new Params().p("path", path);
    }

    /**
     * Returns the Type associated with the JsonValue.
     * 
     * @param def
     * @return The Type associated with the JsonValue.
     * @throws ItemscriptError if JsonValue was not object, string, or array.
     */
    public Type resolve(JsonValue def) {
        if (def.isString()) {
            return get(def.stringValue());
        } else if (def.isObject()) {
            return createFromObject(def.asObject());
        } else if (def.isArray()) {
            return createFromArray(def.asArray());
        } else {
            throw ItemscriptError.internalError(this, "create.typeDef.was.not.object.string.or.array",
                    def.toCompactJsonString());
        }
    }

    @Override
    public JsonSystem system() {
        return system;
    }

    /**
     * Adds all of the types and their corresponding keys contained within the object into
     * the schema.
     * 
     * @param def
     */
    public void addAllTypes(JsonObject def) {
    	for (String key : def.keySet()) {
			addType(key, def.get(key));
    	}
    }

    /**
     * Adds one type with its corresponding key into the schema.
     * 
     * @param key
     * @param val
     */
    public void addType(String key, JsonValue val) {
        Type valType = resolve(val);
        types.putNative(key, valType);
    }

    /**
     * Validates that the specified JsonValue is valid according to its type.
     * 
     * @param type
     * @param value
     * @throws ItemscriptError if the type was not found.
     */
    public void validate(String type, JsonValue value) {
        Type typeObj = get(type);
        if (typeObj == null) { throw ItemscriptError.internalError(this, "validate.type.not.found", type); }
        validate(typeObj, value);
    }

    /**
     * Validates that the specified JsonValue is valid according to its type.
     * 
     * @param path
     * @param type
     * @param value
     * @throws ItemscriptError if the type is null.
     */
    public void validate(String path, Type type, JsonValue value) {
        if (type == null) { throw ItemscriptError.internalError(this, "validate.type.was.null", new Params().p(
                "path", path)
                .p("value", value.toCompactJsonString())); }
        type.validate(path, value);
    }

    /**
     * Validates that the specified JsonValue is valid according to its type.
     * 
     * @param type
     * @param value
     */
    public void validate(Type type, JsonValue value) {
        validate("", type, value);
    }
}