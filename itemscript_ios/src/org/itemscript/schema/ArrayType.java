package org.itemscript.schema;

import java.util.ArrayList;
import java.util.List;

import org.itemscript.core.Params;
import org.itemscript.core.exceptions.ItemscriptError;
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;
import org.itemscript.core.values.JsonArray;
/**
 * Type class for the Array Type. All ArrayTypes are represented by JsonArrays.
 * 
 * @author Eileen Bai
 */
final class ArrayType extends TypeBase {
	private static final String CONTAINS_KEY = ".contains";
	private static final String EXACT_SIZE_KEY = ".exactSize";
	private static final String MAX_SIZE_KEY = ".maxSize";
	private static final String MIN_SIZE_KEY = ".minSize";
	private static final String IN_ARRAY_KEY = ".inArray";
	private static final String NOT_IN_ARRAY_KEY = ".notInArray";
	private final boolean hasDef;
	private final JsonValue contains;
	private final Type containsType;
	private final int exactSize;
	private final int maxSize;
	private final int minSize;
    private final List<JsonArray> inArray;
    private final List<JsonArray> notInArray;
    
    /**
     * Create a new ArrayType. Sets all associated ".keys" that are specified.
     * 
     * @param schema
     * @param extendsType
     * @param def
     */
	ArrayType(Schema schema, Type extendsType, JsonObject def) {
		super(schema, extendsType, def);
		if (def != null) {
			hasDef = true;
			if (def.containsKey(CONTAINS_KEY)) {
				contains = def.getRequiredValue(CONTAINS_KEY);
				containsType = schema().resolve(contains);
			} else {
				contains = null;
				containsType = null;
			}
			if (def.containsKey(EXACT_SIZE_KEY)) {
				exactSize = def.getRequiredInt(EXACT_SIZE_KEY);
			} else {
				exactSize = -1;
			}
			if (def.containsKey(MAX_SIZE_KEY)) {
				maxSize = def.getRequiredInt(MAX_SIZE_KEY);
			} else {
				maxSize = -1;
			}
			if (def.containsKey(MIN_SIZE_KEY)) {
				minSize = def.getRequiredInt(MIN_SIZE_KEY);
			} else {
				minSize = -1;
			}
            if (def.containsKey(IN_ARRAY_KEY)) {
            	inArray = new ArrayList<JsonArray>();
            	JsonArray array = def.getRequiredArray(IN_ARRAY_KEY);
            	for (int i = 0; i < array.size(); ++i) {
            		inArray.add(array.getRequiredArray(i));
            	}
            } else {
            	inArray = null;
            }
            if (def.containsKey(NOT_IN_ARRAY_KEY)) {
            	notInArray = new ArrayList<JsonArray>();
            	JsonArray array = def.getRequiredArray(NOT_IN_ARRAY_KEY);
            	for (int i = 0; i < array.size(); ++i) {
            		notInArray.add(array.getRequiredArray(i));
            	}
            } else {
            	notInArray = null;
            }
		} else {
			hasDef = false;
			contains = null;
			containsType = null;
			exactSize = -1;
			maxSize = -1;
			minSize = -1;
			inArray = null;
			notInArray = null;
		}
	}

	@Override
	public boolean isArray() {
		return true;
	}

	private Params pathValueParams(String path, JsonArray array) {
		return schema().pathParams(path).p("value", array);
	}

	@Override
	public void validate(String path, JsonValue value) {
		super.validate(path, value);
		if (!value.isArray()) {
			throw ItemscriptError.internalError(this,
					"validate.value.was.not.array", schema().pathParams(path)
							.p("value", value.toCompactJsonString()));
		}
		if (hasDef) {
			validateArray(path, value.asArray());
		}
	}

	private void validateArray(String path, JsonArray array) {
		if (contains != null) {
			if (containsType != null) {
				boolean useSlash = path.length() > 0;
				for (int i = 0; i < array.size(); i++) {
					containsType.validate(path + (useSlash ? "/" : "") + i, array
							.get(i));
				}
			} else {
				throw ItemscriptError.internalError(this,
						"validateArray.value.resolved.containsType.was.null",
						pathValueParams(path, array));
			}
		}
		if (exactSize > 0) {
			if (array.size() != exactSize) {
				throw ItemscriptError.internalError(this,
						"validateArray.value.array.is.the.wrong.size",
						pathValueParams(path, array)
							.p("specified", exactSize)
							.p("input", array.size()));
			}
		}
		if (maxSize > 0) {
			if (array.size() > maxSize) {
				throw ItemscriptError.internalError(this,
						"validateArray.value.array.size.is.bigger.than.max",
						pathValueParams(path, array)
							.p("specified", maxSize)
							.p("input", array.size()));
			}
		}
		if (minSize > 0) {
			if (array.size() < minSize) {
				throw ItemscriptError.internalError(this,
						"validateArray.value.array.size.is.smaller.than.min",
						pathValueParams(path, array)
							.p("specified", minSize)
							.p("input", array.size()));
			}
		}
        if (inArray != null) {
            boolean matched = false;
            for (int i = 0; i < inArray.size(); ++i) {
                JsonArray inArrayJsonArray = inArray.get(i);
                if (array.equals(inArrayJsonArray)) {
                    matched = true;
                    break;
                }
            }
            if (!matched) { throw ItemscriptError.internalError(this,
                    "validateArray.value.did.not.match.a.valid.choice", pathValueParams(path, array)); }
        }
        if (notInArray != null) {
            boolean matched = false;
            for (int i = 0; i < notInArray.size(); ++i) {
                JsonArray notInArrayJsonArray = notInArray.get(i);
                if (array.equals(notInArrayJsonArray)) {
                    matched = true;
                    break;
                }
            }
            if (matched) { throw ItemscriptError.internalError(this,
                    "validateArray.value.matched.an.invalid.choice", pathValueParams(path, array)); }
        }
	}
}