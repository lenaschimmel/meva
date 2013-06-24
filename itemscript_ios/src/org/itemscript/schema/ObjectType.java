
package org.itemscript.schema;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.itemscript.core.Params;
import org.itemscript.core.exceptions.ItemscriptError;
import org.itemscript.core.values.JsonArray;
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;
/**
 * Type class for the Object Type. All ObjectTypes are represented by JsonObjects.
 * 
 * @author Eileen Bai
 */
class ObjectType extends TypeBase {

    public static final String KEY_KEY = ".key ";
    public static final String OPTIONAL_KEY = ".optional ";
    public static final String PATTERN_KEY = ".pattern ";
    public static final String WILDCARD_KEY = ".wildcard";
    public static final String IN_ARRAY_KEY = ".inArray";
    public static final String NOT_IN_ARRAY_KEY = ".notInArray";
    private final boolean hasDef;
    private boolean resolved;
    private Map<String, JsonValue> optionalKeys;
    private Map<String, JsonValue> requiredKeys;
    private Map<String, JsonValue> patterns;
    private Map<String, Type> resolvedOptionalKeys;
    private Map<String, Type> resolvedRequiredKeys;
    private List<JsonObject> inArray;
    private List<JsonObject> notInArray;
    private JsonValue wildcard;
    
    /**
     * Create a new ObjectType. Sets all associated ".keys" that are specified.
     * 
     * @param schema
     * @param extendsType
     * @param def
     */
    ObjectType(Schema schema, Type extendsType, JsonObject def) {
        super(schema, extendsType, def);
        if (def != null) {
            hasDef = true;
            resolved = false;
            this.requiredKeys = new HashMap<String, JsonValue>();
            Set<String> schemaKeys = new HashSet<String>();
            for (String key : def.keySet()) {
                if (key.length() == 0) { throw ItemscriptError.internalError(this,
                        "ObjectType.value.constructor.object.type.had.empty.key", def.toCompactJsonString()); }
                String[] split = key.split("\\s+", 2);
                String first = split[0];
                if (first.startsWith(".")) {
                    schemaKeys.add(key);
                } else {
                    requiredKeys.put(key, def.get(key)
                            .copy());
                }
            }
            this.optionalKeys = new HashMap<String, JsonValue>();
            this.patterns = new HashMap<String, JsonValue>();
            for (String key : schemaKeys) {
                if (key.startsWith(KEY_KEY)) {
                    String remainder = key.substring(KEY_KEY.length());
                    requiredKeys.put(remainder, def.get(key)
                            .copy());
                } else if (key.startsWith(OPTIONAL_KEY)) {
                    String remainder = key.substring(OPTIONAL_KEY.length());
                    optionalKeys.put(remainder, def.get(key)
                            .copy());
                } else if (key.startsWith(PATTERN_KEY)) {
                	String remainder = key.substring(PATTERN_KEY.length());
                	patterns.put(remainder, def.get(key)
                			.copy());
                } else if (key.startsWith(WILDCARD_KEY)) {
                	wildcard = def.getRequiredValue(WILDCARD_KEY);
                } else if (key.startsWith(IN_ARRAY_KEY)) {
                	inArray = new ArrayList<JsonObject>();
                	JsonArray array = def.getRequiredArray(IN_ARRAY_KEY);
                	for (int i = 0; i < array.size(); ++i) {
                		inArray.add(array.getRequiredObject(i));
                	}
                } else if (key.startsWith(NOT_IN_ARRAY_KEY)) {
                	notInArray = new ArrayList<JsonObject>();
                	JsonArray array = def.getRequiredArray(NOT_IN_ARRAY_KEY);
                	for (int i = 0; i < array.size(); ++i) {
                		notInArray.add(array.getRequiredObject(i));
                	}
                }
            }
        } else {
            hasDef = false;
            resolved = false;
            patterns = null;
            optionalKeys = null;
            requiredKeys = null;
            resolvedOptionalKeys = null;
            resolvedRequiredKeys = null;
            wildcard = null;
            inArray = null;
            notInArray = null;

        }
    }

    @Override
    public boolean isObject() {
        return true;
    }
    
    private void resolveTypes() {
        if (!resolved) {
            resolvedRequiredKeys = new HashMap<String, Type>();
            for (String key : requiredKeys.keySet()) {
                resolvedRequiredKeys.put(key, schema().resolve(requiredKeys.get(key)));
            }
            resolvedOptionalKeys = new HashMap<String, Type>();
            for (String key : optionalKeys.keySet()) {
                resolvedOptionalKeys.put(key, schema().resolve(optionalKeys.get(key)));
            }
            resolved = true;
        }
    }
    
    private Params pathValueParams(String path, JsonObject object) {
        return schema().pathParams(path)
                .p("value", object);
    }
    
    @Override
    public void validate(String path, JsonValue value) {
        super.validate(path, value);
        if (!value.isObject()) { throw ItemscriptError.internalError(this,
        		"validate.value.was.not.object",
                schema().pathParams(path)
                .p("value", value.toCompactJsonString())); }
        if (hasDef) {
            resolveTypes();
            validateObject(path, value.asObject());
        }
    }

    private void validateObject(String path, JsonObject object) {  
    	ArrayList<JsonValue> checkWildcardList = new ArrayList<JsonValue>();
    	
    	//this first for loop is to make sure none of the instance keys are empty strings.
    	for (String instanceKey : object.keySet()) {
    		if (instanceKey.isEmpty()) {
    			throw ItemscriptError.internalError(this,
	            		"validateObject.value.instance.key.was.empty",
	            		pathValueParams(path, object));
    		}
    	}
    	if (!patterns.isEmpty()) {
    		for (String patternKey : patterns.keySet()) {
    			for (String key : object.keySet()) {
    				JsonValue value = object.get(key);
    				if (schema().match(patternKey, key)) {
    					Type resolvedPatternValue = schema().resolve(patterns.get(patternKey));
    					if (resolvedPatternValue != null) {
    						schema().validate(schema().addKey(path, key), resolvedPatternValue, value);
    					} else {
    						throw ItemscriptError.internalError(this,
    			            		"validateObject.value.resolvedPatternValue.was.null",
    			            		pathValueParams(path, object));
    					}
    				}
    			}
    		}
    	}
    	if (wildcard != null) {
    		for (String instanceKey : object.keySet()) {
	    		if (!requiredKeys.containsKey(instanceKey)) {
	    			if (!optionalKeys.containsKey(instanceKey)) {
	    				checkWildcardList.add(object.get(instanceKey));
	    			}
	    		}
    		}
	    	if (!validWildcardTypes(checkWildcardList, path, object)) {
	    		throw ItemscriptError.internalError(this,
	            		"validateObject.value.extra.instance.keys.did.not.all.match.wildcard.type",
	            		pathValueParams(path, object));
	    	}
    	}
        for (String key : resolvedRequiredKeys.keySet()) {
            // Required and must conform to the type.
            JsonValue value = object.get(key);
            if (value == null) { throw ItemscriptError.internalError(this,
            		"validateObject.value.missing.value.for.key",
            			pathValueParams(path, object)
            				.p("key", key));
            }
            schema().validate(schema().addKey(path, key), resolvedRequiredKeys.get(key), value);
        }
        for (String key : resolvedOptionalKeys.keySet()) {
            JsonValue value = object.get(key);
            // Optional, but if present, must conform to the type.
            if (value != null) {
                schema().validate(resolvedOptionalKeys.get(key), value);
            }   
        }
        if (inArray != null) {
        	boolean matched = false;
            for (int i = 0; i < inArray.size(); ++i) {
                JsonObject inArrayObject = inArray.get(i);
                if (object.equals(inArrayObject)) {
                    matched = true;
                    break;
                }
            }
            if (!matched) { throw ItemscriptError.internalError(this,
                    "validateObject.value.did.not.match.a.valid.choice", pathValueParams(path, object)); }
        }
        if (notInArray != null) {
	    	boolean matched = false;
	        for (int i = 0; i < notInArray.size(); ++i) {
	            JsonObject notInArrayObject = notInArray.get(i);
	            if (object.equals(notInArrayObject)) {
	                matched = true;
	                break;
	            }
	        }
	        if (matched) { throw ItemscriptError.internalError(this,
	                "validateObject.value.matched.an.invalid.choice", pathValueParams(path, object)); }
	    }
    }
    
    /**
     * Validates all of the values in the list using the wildcard's Type validator.
     * @param wildcardList
     * @param path
     * @return true if all are valid, false if not
     */
    private boolean validWildcardTypes(ArrayList<JsonValue> wildcardList, String path, JsonObject obj) {
		boolean useSlash = path.length() > 0;
    	
    	if (!wildcardList.isEmpty()) {
    		Type wildcardType = schema().resolve(wildcard);
    		if (wildcardType != null) {
	    		for (int i = 0; i < wildcardList.size(); i++) {
		    		JsonValue listValue = wildcardList.get(i);
		    		try {
		    			wildcardType.validate(path + (useSlash ? "/" : ""), listValue);
		    		} catch (ItemscriptError e) {
		    			return false;
		    		}
	    		}
    		} else {
    			throw ItemscriptError.internalError(this,
	            		"validateObject.resolved.wildcardType.was.null",
	            		pathValueParams(path, obj));
    		}
    	}
	    return true;
    }
}