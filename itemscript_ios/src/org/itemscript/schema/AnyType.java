
package org.itemscript.schema;

import java.util.ArrayList;
import java.util.List;

import org.itemscript.core.Params;
import org.itemscript.core.exceptions.ItemscriptError;
import org.itemscript.core.values.JsonArray;
import org.itemscript.core.values.JsonValue;
import org.itemscript.core.values.JsonObject;
/**
 * Type class for the base type Any. All other types are extensions of this type.
 * 
 * @author Eileen Bai
 */
final class AnyType extends TypeBase {
	private static final String ARRAY_KEY = ".array";
	private static final String BOOLEAN_KEY = ".boolean";
	private static final String NUMBER_KEY = ".number";
	private static final String OBJECT_KEY = ".object";
	private static final String STRING_KEY = ".string";
	private static final String IN_ARRAY_KEY = ".inArray";
	private static final String NOT_IN_ARRAY_KEY = ".notInArray";
	private final boolean hasDef;
	JsonValue array;
	JsonValue bool;
	JsonValue number;
	JsonValue object;
	JsonValue string;
    private final List<JsonValue> inArray;
    private final List<JsonValue> notInArray;
	
    /**
     * Create a new AnyType. Only used to create the empty AnyType to extend other types with.
     * 
     * @param schema
     */
    AnyType(Schema schema) {
        super(schema);
        array = null;
        bool = null;
        number = null;
        object = null;
        string = null;
        inArray = null;
        notInArray = null;
        hasDef = false;
    }
    
    /**
     * Create a new AnyType. Sets all associated ".keys" that are specified.
     *
     * @param schema
     * @param extendsType
     * @param def
     */
    AnyType(Schema schema, Type extendsType, JsonObject def) {
        super(schema, extendsType, def);
		if (def != null) {
			hasDef = true;
	    	if (def.containsKey(ARRAY_KEY)) {
	    		array = def.getRequiredValue(ARRAY_KEY);
	    	} else {
	    		array = null;
	    	}
	    	if (def.containsKey(BOOLEAN_KEY)) {
	    		bool = def.getRequiredValue(BOOLEAN_KEY);
	    	} else {
	    		bool = null;
	    	}
	    	if (def.containsKey(NUMBER_KEY)) {
	    		number = def.getRequiredValue(NUMBER_KEY);
	    	} else {
	    		number = null;
	    	}
	    	if (def.containsKey(OBJECT_KEY)) {
	    		object = def.getRequiredValue(OBJECT_KEY);
	    	} else {
	    		object = null;
	    	}
	    	if (def.containsKey(STRING_KEY)) {
	    		string = def.getRequiredValue(STRING_KEY);
			} else {
				string = null;
	    	}
            if (def.containsKey(IN_ARRAY_KEY)) {
            	inArray = new ArrayList<JsonValue>();
            	JsonArray array = def.getRequiredArray(IN_ARRAY_KEY);
            	for (int i = 0; i < array.size(); ++i) {
        			inArray.add(array.getRequiredValue(i));
            	}
            } else {
            	inArray = null;
            }
            if (def.containsKey(NOT_IN_ARRAY_KEY)) {
            	notInArray = new ArrayList<JsonValue>();
            	JsonArray array = def.getRequiredArray(NOT_IN_ARRAY_KEY);
            	for (int i = 0; i < array.size(); ++i) {
        			notInArray.add(array.getRequiredValue(i));
            	}
            } else {
            	notInArray = null;
            }
		} else {
			array = null;
			bool = null;
			number = null;
			object = null;
			string = null;
			inArray = null;
			notInArray = null;
			hasDef = false;
		}
    }
  
    @Override
    public boolean isAny() {
        return true;
    }
    
    private Params pathValueParams(String path, JsonValue value) {
        return schema().pathParams(path)
                .p("value", value);
    }
    
    @Override
    public void validate(String path, JsonValue value) {
        super.validate(path, value);
        if (value == null) { throw ItemscriptError.internalError(this, "validate.value.was.null",
                schema().pathParams(path));
        }
    	if (hasDef) {
    		validateAny(path, value);
    	}      
    }
       
    private void validateAny(String path, JsonValue value) {
		boolean useSlash = path.length() > 0;
		boolean validValue = false;
		Type arrayType;
		Type boolType;
		Type numberType;
		Type objectType;
		Type stringType;
		
		if (string == null && number == null && bool == null && array == null && object == null) {
			validValue = true;
		}
		if (array != null) {
    		arrayType = schema().resolve(array);
    		if (arrayType != null) {
	    		if (value.isArray()) {
	    			arrayType.validate(path + (useSlash ? "/" : ""), value);
	    			validValue = true;
	    		}
    		} else {
    			throw ItemscriptError.internalError(this,
                        "validateAny.value.resolved.arrayType.was.null", pathValueParams(path, object));
    		}    		
		}
		if (bool != null) {
    		boolType = schema().resolve(bool);
    		if (boolType != null) {
    			if (value.isBoolean()) {
    				boolType.validate(path + (useSlash ? "/" : ""), value);
    				validValue = true;
    			}
    		} else {
    			throw ItemscriptError.internalError(this,
                        "validateAny.value.resolved.boolType.was.null", pathValueParams(path, object));
    		}
		}
		if (number != null) {
			numberType = schema().resolve(number);
			if (numberType != null) {
				if (value.isNumber()) {
					numberType.validate(path + (useSlash ? "/" : ""), value);
					validValue = true;
				}
			} else {
    			throw ItemscriptError.internalError(this,
                        "validateAny.value.resolved.numberType.was.null", pathValueParams(path, object));
    		}
		}
		if (object != null) {
    		objectType = schema().resolve(object);
    		if (objectType != null) {
	    		if (value.isObject()) {
	    			objectType.validate(path + (useSlash ? "/" : ""), value);
	    			validValue = true;
	    		}
    		} else {
    			throw ItemscriptError.internalError(this,
                        "validateAny.value.resolved.objectType.was.null", pathValueParams(path, object));
    		}
		}
		if (string != null) {
    		stringType = schema().resolve(string);
    		if (stringType != null) {
	    		if (value.isString()) {
	    			stringType.validate(path + (useSlash ? "/" : ""), value);
	    			validValue = true;
	    		}
    		} else {
    			throw ItemscriptError.internalError(this,
                        "validateAny.value.resolved.stringType.was.null", pathValueParams(path, object));
    		}
		}
		if (inArray != null) {
            boolean matched = false;
            validValue = true;
            for (int i = 0; i < inArray.size(); ++i) {
                JsonValue inArrayValue = inArray.get(i);
                if (value.isString() && inArrayValue.isString()) {
                	if (DecimalType.isDecimal(value.stringValue())
                			&& DecimalType.isDecimal(inArrayValue.stringValue())) {
                		if(DecimalType.decimalEquals(value.stringValue(), inArrayValue.stringValue())) {
                			matched = true;
                			break;
                		}
                	}
                }
                if (value == inArrayValue || value.equals(inArrayValue)) {
            		matched = true;
            		break;
            	}
            }
            if (!matched) { throw ItemscriptError.internalError(this,
                    "validateAny.value.did.not.match.a.valid.choice", pathValueParams(path, object)); }
        }
		if (notInArray != null) {
            boolean matched = false;
            validValue = true;
            for (int i = 0; i < notInArray.size(); ++i) {
                JsonValue notInArrayValue = notInArray.get(i);
                if (value.isString() && notInArrayValue.isString()) {
                	if (DecimalType.isDecimal(value.stringValue())
                			&& DecimalType.isDecimal(notInArrayValue.stringValue())) {
                		if(DecimalType.decimalEquals(value.stringValue(), notInArrayValue.stringValue())) {
                			matched = true;
                			break;
                		}
                	}
                }
                if (value == notInArrayValue || value.equals(notInArrayValue)) {
            		matched = true;
            		break;
            	}
            }
            if (matched) { throw ItemscriptError.internalError(this,
                    "validateAny.value.matched.an.invalid.choice", pathValueParams(path, object)); }
        }
		if (!validValue) {
			throw ItemscriptError.internalError(this,
        			"validateAny.value.was.not.of.specified.type", pathValueParams(path, value));
		}
    }
}