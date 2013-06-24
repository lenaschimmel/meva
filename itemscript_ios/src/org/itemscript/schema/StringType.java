
package org.itemscript.schema;

import java.util.ArrayList;
import java.util.List;

import org.itemscript.core.Params;
import org.itemscript.core.exceptions.ItemscriptError;
import org.itemscript.core.values.JsonArray;
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;
/**
 * Type class for the String Type.
 * 
 * @author Eileen Bai
 */
class StringType extends TypeBase {
	private static final String EQUALS_KEY = ".equals";
	private static final String IS_LENGTH_KEY = ".isLength";
	private static final String MAX_LENGTH_KEY = ".maxLength";
	private static final String MIN_LENGTH_KEY = ".minLength";
	private static final String REG_EX_PATTERN_KEY = ".regExPattern";
	private static final String IN_ARRAY_KEY = ".inArray";
	private static final String NOT_IN_ARRAY_KEY = ".notInArray";
	private static final String PATTERN_KEY = ".pattern";
	private boolean hasDef;
    private final int isLength;
    private final int minLength;
    private final int maxLength;
    private final String equals;
    private final String regExPattern;
    private final List<String> inArray;
    private final List<String> notInArray;
    private final List<String> pattern;

    /**
     * Create a new StringType. Sets all associated ".keys" that are specified.
     * 
     * @param schema
     * @param extendsType
     * @param def
     */
    public StringType(Schema schema, Type extendsType, JsonObject def) {
        super(schema, extendsType, def);
        if (def != null) {
            hasDef = true;
            pattern = new ArrayList<String>();
            
            if (def.containsKey(EQUALS_KEY)) {
            	equals = def.getRequiredString(EQUALS_KEY);
            } else{
            	equals = null;
            }
            if (def.containsKey(IS_LENGTH_KEY)) {
            	isLength = def.getRequiredInt(IS_LENGTH_KEY);
            } else{
            	isLength = -1;
            }
            if (def.containsKey(MAX_LENGTH_KEY)) {
                maxLength = def.getRequiredInt(MAX_LENGTH_KEY);
            } else {
                maxLength = -1;
            }
            if (def.containsKey(MIN_LENGTH_KEY)) {
                minLength = def.getRequiredInt(MIN_LENGTH_KEY);
            } else {
                minLength = -1;
            }
            if (def.containsKey(REG_EX_PATTERN_KEY)) {
            	regExPattern = def.getRequiredString(REG_EX_PATTERN_KEY);
            } else{
            	regExPattern = null;
            }
            if (def.containsKey(IN_ARRAY_KEY)) {
            	inArray = new ArrayList<String>();
            	JsonArray array = def.getRequiredArray(IN_ARRAY_KEY);
            	for (int i = 0; i < array.size(); ++i) {
            		inArray.add(array.getRequiredString(i));
            	}
            } else {
            	inArray = null;
            }
            if (def.containsKey(NOT_IN_ARRAY_KEY)) {
            	notInArray = new ArrayList<String>();
            	JsonArray array = def.getRequiredArray(NOT_IN_ARRAY_KEY);
            	for (int i = 0; i < array.size(); ++i) {
            		notInArray.add(array.getRequiredString(i));
            	}
            } else {
            	notInArray = null;
            }
            if (def.containsKey(PATTERN_KEY)) {
            	if (def.hasArray(PATTERN_KEY)) {
	                JsonArray array = def.getRequiredArray(PATTERN_KEY);
	                for (int i = 0; i < array.size(); ++i) {
	                	pattern.add(array.getRequiredString(i));
	                }
            	} else if (def.hasString(PATTERN_KEY)) {
            		pattern.add(def.getRequiredString(PATTERN_KEY));
            	} else {
            		throw ItemscriptError.internalError(this,
                            "validateString.value.pattern.key.was.not.array.or.string");
            	}
            }
        } else {
            hasDef = false;
            equals = null;
            isLength = -1;
            maxLength = -1;
            minLength = -1;
            regExPattern = null;
            inArray = null;
            notInArray = null;
            pattern = null;
        }
    }

    @Override
    public boolean isString() {
        return true;
    }

    private Params pathValueParams(String path, String string) {
        return schema().pathParams(path)
                .p("value", string);
    }

    @Override
    public void validate(String path, JsonValue value) {
        super.validate(path, value);
        if (!value.isString()) { throw ItemscriptError.internalError(this, "validate.value.was.not.string",
                schema().pathParams(path)
                        .p("value", value.toCompactJsonString())); }
        if (hasDef) {
            validateString(path, value.stringValue());
        }
    }

    private void validateString(String path, String string) {
        if (equals != null) {
        	if (!string.equals(equals)) { throw ItemscriptError.internalError(this,
        			"validateString.value.does.not.equal.equals", pathValueParams(path, string)); }
        }
        if (isLength > 0) {
        	if (string.length() != isLength) { throw ItemscriptError.internalError(this,
        			"validateString.value.does.not.equal.is.length", pathValueParams(path, string)
        				.p("specified", isLength)
        				.p("input", string.length())); }
        }
        if (maxLength > 0) {
            if (string.length() > maxLength) { throw ItemscriptError.internalError(this,
                    "validateString.value.longer.than.max.length", pathValueParams(path, string)
                    	.p("specified", maxLength)
                    	.p("input", string.length())); }
        }
        if (minLength > 0) {
            if (string.length() < minLength) { throw ItemscriptError.internalError(this,
                    "validateString.value.shorter.than.min.length", pathValueParams(path, string)
                    	.p("specified", minLength)
                    	.p("input", string.length())); }
        }
        if (regExPattern != null) {
        	if (!string.matches(regExPattern)) { throw ItemscriptError.internalError(this,
        			"validateString.value.does.not.match.reg.ex.pattern", pathValueParams(path, string)); }
        }
        if (inArray != null) {
            boolean matched = false;
            for (int i = 0; i < inArray.size(); ++i) {
                String inArrayString = inArray.get(i);
                if (string.equals(inArrayString)) {
                    matched = true;
                }
            }
            if (!matched) { throw ItemscriptError.internalError(this,
                    "validateString.value.did.not.match.a.valid.choice", pathValueParams(path, string)); }
        }
        if (notInArray != null) {
            boolean matched = false;
            for (int i = 0; i < notInArray.size(); ++i) {
                String notInArrayString = notInArray.get(i);
                if (string.equals(notInArrayString)) {
                    matched = true;
                }
            }
            if (matched) { throw ItemscriptError.internalError(this,
                    "validateString.value.matched.an.invalid.choice", pathValueParams(path, string)); }
        }
        if (pattern.size() > 0) {
            boolean matched = false;
            for (int i = 0; i < pattern.size(); ++i) {
                String patternString = pattern.get(i);
                if (schema().match(patternString, string)) {
                    matched = true;
                }
            }
            if (!matched) { throw ItemscriptError.internalError(this,
                    "validateString.value.did.not.match.any.pattern", pathValueParams(path, string)); }
        }
    }
}