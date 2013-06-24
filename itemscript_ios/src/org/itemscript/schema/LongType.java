package org.itemscript.schema;

import java.util.ArrayList;
import java.util.List;

import org.itemscript.core.Params;
import org.itemscript.core.exceptions.ItemscriptError;
import org.itemscript.core.values.JsonArray;
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;
/**
 * Type class for the Long Type. All LongTypes are represented by strings.
 * 
 * @author Eileen Bai
 */
class LongType extends TypeBase {
	private static final String EQUAL_TO_KEY = ".equalTo";
	private static final String GREATER_THAN_KEY = ".greaterThan";
	private static final String GREATER_THAN_OR_EQUAL_TO_KEY = ".greaterThanOrEqualTo";
	private static final String LESS_THAN_KEY = ".lessThan";
	private static final String LESS_THAN_OR_EQUAL_TO_KEY = ".lessThanOrEqualTo";
	private static final String EVEN_KEY = ".even";
	private static final String ODD_KEY = ".odd";
	private static final String IN_ARRAY_KEY = ".inArray";
	private static final String NOT_IN_ARRAY_KEY = ".notInArray";
	private final boolean hasDef;
	private final boolean even;
	private final boolean hasEven;
	private final boolean odd;
	private final boolean hasOdd;
	private final String equalTo;
	private final String greaterThan;
	private final String greaterThanOrEqualTo;
	private final String lessThan;
	private final String lessThanOrEqualTo;
	private final List<String> inArray;
	private final List<String> notInArray;
	private final long equalToValue;
	private final long greaterThanValue;
	private final long greaterThanOrEqualToValue;
	private final long lessThanValue;
	private final long lessThanOrEqualToValue;

	/**
	 * Create a new LongType. Sets all associated ".keys" that are specified.
	 * 
	 * @param schema
	 * @param extendsType
	 * @param def
	 */
	public LongType(Schema schema, Type extendsType, JsonObject def) {
		super(schema, extendsType, def);
		if (def != null) {
			hasDef = true;
			if (def.containsKey(EQUAL_TO_KEY)) {
				equalTo = def.getRequiredString(EQUAL_TO_KEY);
				try {
					equalToValue = Long.parseLong(equalTo);
				} catch (NumberFormatException e) {
					throw ItemscriptError.internalError(this,
							"validateLong.value.equalTo.could.not.be.parsed.into.long",
										def.toCompactJsonString());
				}
			} else {
				equalTo = null;
				equalToValue = -1;
			}
			if (def.containsKey(GREATER_THAN_KEY)) {
				greaterThan = def.getRequiredString(GREATER_THAN_KEY);
				try {
					greaterThanValue = Long.parseLong(greaterThan);
				} catch (NumberFormatException e) {
					throw ItemscriptError.internalError(this,
							"validateLong.value.greaterThan.could.not.be.parsed.into.long",
									def.toCompactJsonString());
				}
			} else {
				greaterThan = null;
				greaterThanValue = -1;
			}
			if (def.containsKey(GREATER_THAN_OR_EQUAL_TO_KEY)) {
				greaterThanOrEqualTo = def.getRequiredString(GREATER_THAN_OR_EQUAL_TO_KEY);
				try {
					greaterThanOrEqualToValue = Long
							.parseLong(greaterThanOrEqualTo);
				} catch (NumberFormatException e) {
					throw ItemscriptError.internalError(this,
							"validateLong.value.greaterThanOrEqualTo.could.not.be.parsed.into.long",
									def.toCompactJsonString());
				}
			} else {
				greaterThanOrEqualTo = null;
				greaterThanOrEqualToValue = -1;
			}
			if (def.containsKey(LESS_THAN_KEY)) {
				lessThan = def.getRequiredString(LESS_THAN_KEY);
				try {
					lessThanValue = Long.parseLong(lessThan);
				} catch (NumberFormatException e) {
					throw ItemscriptError.internalError(this,
							"validateLong.value.lessThan.could.not.be.parsed.into.long",
								def.toCompactJsonString());
				}
			} else {
				lessThan = null;
				lessThanValue = -1;
			}
			if (def.containsKey(LESS_THAN_OR_EQUAL_TO_KEY)) {
				lessThanOrEqualTo = def.getRequiredString(LESS_THAN_OR_EQUAL_TO_KEY);
				try {
					lessThanOrEqualToValue = Long.parseLong(lessThanOrEqualTo);
				} catch (NumberFormatException e) {
					throw ItemscriptError.internalError(this,
							"validateLong.value.lessThanOrEqualTo.could.not.be.parsed.into.long",
								def.toCompactJsonString());
				}
			} else {
				lessThanOrEqualTo = null;
				lessThanOrEqualToValue = -1;
			}
			if (def.containsKey(EVEN_KEY)) {
				hasEven = true;
				even = def.getRequiredBoolean(EVEN_KEY);
			} else {
				hasEven = false;
				even = false;
			}
			if (def.containsKey(ODD_KEY)) {
				hasOdd = true;
				odd = def.getRequiredBoolean(ODD_KEY);
			} else {
				hasOdd = false;
				odd = false;
			}
			if (def.containsKey(IN_ARRAY_KEY)) {
				inArray = new ArrayList<String>();
				JsonArray array = def.getRequiredArray(IN_ARRAY_KEY);
				for (int i = 0; i < array.size(); ++i) {
					String inArrayString = array.getRequiredString(i);
					try {
						Long.parseLong(inArrayString);
					} catch (NumberFormatException e) {
						throw ItemscriptError.internalError(this,
								"validateLong.value.inArray.value.could.not.be.parsed.into.long",
								def.toCompactJsonString());
					}
					inArray.add(array.getRequiredString(i));
				}
			} else {
				inArray = null;
			}
			if (def.containsKey(NOT_IN_ARRAY_KEY)) {
				notInArray = new ArrayList<String>();
				JsonArray array = def.getRequiredArray(NOT_IN_ARRAY_KEY);
				for (int i = 0; i < array.size(); ++i) {
					String inArrayString = array.getRequiredString(i);
					try {
						Long.parseLong(inArrayString);
					} catch (NumberFormatException e) {
						throw ItemscriptError.internalError(this,
								"validateLong.value.notInArray.value.could.not.be.parsed.into.long",
								def.toCompactJsonString());
					}
					notInArray.add(array.getRequiredString(i));
				}
			} else {
				notInArray = null;
			}
		} else {
			hasDef = false;
			equalTo = null;
			equalToValue = -1;
			greaterThan = null;
			greaterThanValue = -1;
			greaterThanOrEqualTo = null;
			greaterThanOrEqualToValue = -1;
			lessThan = null;
			lessThanValue = -1;
			lessThanOrEqualTo = null;
			lessThanOrEqualToValue = -1;
			even = false;
			odd = false;
			hasEven = false;
			hasOdd = false;
			inArray = null;
			notInArray = null;
		}
	}

	@Override
	public boolean isLong() {
		return true;
	}

	private Params pathValueParams(String path, Long longVal) {
		return schema().pathParams(path).p("value", longVal);
	}

	@Override
	public void validate(String path, JsonValue value) {
		super.validate(path, value);
		Long longVal;
		if (!value.isString()) {
			throw ItemscriptError.internalError(this,
					"validate.value.was.not.string", schema().pathParams(path)
							.p("value", value.toCompactJsonString()));
		}
		try {
			longVal = Long.parseLong(value.stringValue());
		} catch (NumberFormatException e) {
			throw ItemscriptError.internalError(this,
					"validate.value.could.not.be.parsed.into.long", schema().pathParams(path)
							.p("value", value.toCompactJsonString()));
		}
		if (hasDef) {
			validateLong(path, longVal);
		}
	}

	private void validateLong(String path, Long longVal) {
		
		if (equalTo != null) {
			if (longVal != equalToValue) {
				throw ItemscriptError.internalError(this,
						"validateLong.value.is.not.equal.to.equal.to",
						pathValueParams(path, longVal)
							.p("specified", equalToValue)
							.p("input", longVal.toString()));
			}
		}
		if (greaterThan != null) {
			if (longVal <= greaterThanValue) {
				throw ItemscriptError.internalError(this,
						"validateLong.value.is.less.than.or.equal.to.min",
						pathValueParams(path, longVal)
							.p("specified", greaterThanValue)
							.p("input", longVal.toString()));
			}
		}
		if (greaterThanOrEqualTo != null) {
			if (longVal < greaterThanOrEqualToValue) {
				throw ItemscriptError.internalError(this,
						"validateLong.value.is.less.than.min",
						pathValueParams(path, longVal)
							.p("specified", greaterThanOrEqualToValue)
							.p("input", longVal.toString()));
			}
		}
		if (lessThan != null) {
			if (longVal >= lessThanValue) {
				throw ItemscriptError.internalError(this,
						"validateLong.value.is.greater.than.or.equal.to.max",
						pathValueParams(path, longVal)
							.p("specified", lessThanValue)
							.p("input", longVal.toString()));
			}
		}
		if (lessThanOrEqualTo != null) {
			if (longVal > lessThanOrEqualToValue) {
				throw ItemscriptError.internalError(this,
						"validateLong.value.is.greater.than.max",
						pathValueParams(path, longVal)
							.p("specified", lessThanOrEqualToValue)
							.p("input", longVal.toString()));
			}
		}
		if (hasEven) {
			if (even) {
				if ((longVal % 2) != 0) { throw ItemscriptError.internalError(this,
                        "validateLong.value.is.not.even", pathValueParams(path, longVal)); }
        	} else {
        		if ((longVal % 2) == 0) { throw ItemscriptError.internalError(this,
                        "validateLong.value.is.not.odd", pathValueParams(path, longVal)); }
        	}
        }
        if (hasOdd) {
        	if (odd) {
        		if ((longVal % 2) == 0) { throw ItemscriptError.internalError(this,
                        "validateLong.value.is.not.odd", pathValueParams(path, longVal)); }
        	} else {
        		if ((longVal % 2) != 0) { throw ItemscriptError.internalError(this,
                        "validateLong.value.is.not.even", pathValueParams(path, longVal)); }
        	}
        }
		if (inArray != null) {
			boolean matched = false;
			for (int i = 0; i < inArray.size(); i++) {
				long inArrayValue = Long.parseLong(inArray.get(i));
				if (longVal == inArrayValue) {
					matched = true;
					break;
				}
			}
			if (!matched) {
				throw ItemscriptError.internalError(this,
						"validateLong.value.did.not.match.a.valid.choice",
						pathValueParams(path, longVal));
			}
		}
		if (notInArray != null) {
			boolean matched = false;
			for (int i = 0; i < notInArray.size(); i++) {
				long inArrayValue = Long.parseLong(notInArray.get(i));
				if (longVal == inArrayValue) {
					matched = true;
					break;
				}
			}
			if (matched) {
				throw ItemscriptError.internalError(this,
						"validateLong.value.matched.an.invalid.choice",
						pathValueParams(path, longVal));
			}
		}

	}
}