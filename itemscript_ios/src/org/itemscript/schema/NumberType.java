
package org.itemscript.schema;

import java.util.ArrayList;
import java.util.List;

import org.itemscript.core.exceptions.ItemscriptError;
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;

import org.itemscript.core.Params;
import org.itemscript.core.values.JsonArray;
/**
 * Type class for the Number Type.
 * 
 * @author Eileen Bai
 */
final class NumberType extends TypeBase {
	private static final String EQUAL_TO_KEY = ".equalTo";
	private static final String GREATER_THAN_KEY = ".greaterThan";
	private static final String GREATER_THAN_OR_EQUAL_TO_KEY = ".greaterThanOrEqualTo";
	private static final String LESS_THAN_KEY = ".lessThan";
	private static final String LESS_THAN_OR_EQUAL_TO_KEY = ".lessThanOrEqualTo";
	private static final String EVEN_KEY = ".even";
	private static final String ODD_KEY = ".odd";
	private static final String IN_ARRAY_KEY = ".inArray";
	private static final String NOT_IN_ARRAY_KEY = ".notInArray";
	private boolean hasDef;
	private final double equalTo;
	private final double greaterThan;
	private final double greaterThanOrEqualTo;
	private final double lessThan;
	private final double lessThanOrEqualTo;
	private final boolean hasEqualTo;
	private final boolean hasGreaterThan;
	private final boolean hasGreaterThanOrEqualTo;
	private final boolean hasLessThan;
	private final boolean hasLessThanOrEqualTo;
    private final boolean even;
    private final boolean odd;
    private final boolean hasEven;
    private final boolean hasOdd;
    private final List<Double> inArray;
    private final List<Double> notInArray;


    
    /**
     * Create a new NumberType. Sets all associated ".keys" that are specified.
     * 
     * @param schema
     * @param extendsType
     * @param def
     */
    NumberType(Schema schema, Type extendsType, JsonObject def) {
        super(schema, extendsType, def);
        if (def != null) {
            hasDef = true;
            if (def.containsKey(EQUAL_TO_KEY)) {
            	hasEqualTo = true;
            	equalTo = def.getRequiredDouble(EQUAL_TO_KEY);
            } else {
            	hasEqualTo = false;
            	equalTo = -1;
            }
            if (def.containsKey(GREATER_THAN_KEY)) {
            	hasGreaterThan = true;
                greaterThan = def.getRequiredDouble(GREATER_THAN_KEY);
            } else {
            	hasGreaterThan = false;
            	greaterThan = -1;
            }
            if (def.containsKey(GREATER_THAN_OR_EQUAL_TO_KEY)) {
            	hasGreaterThanOrEqualTo = true;
            	greaterThanOrEqualTo = def.getRequiredDouble(GREATER_THAN_OR_EQUAL_TO_KEY);	
            } else {
            	hasGreaterThanOrEqualTo = false;
            	greaterThanOrEqualTo = -1;
            }
            if (def.containsKey(LESS_THAN_KEY)) {
            	hasLessThan = true;
            	lessThan = def.getRequiredDouble(LESS_THAN_KEY);
            } else {
            	hasLessThan = false;
            	lessThan = -1;
            }
            if (def.containsKey(LESS_THAN_OR_EQUAL_TO_KEY)) {
            	hasLessThanOrEqualTo = true;
            	lessThanOrEqualTo = def.getRequiredDouble(LESS_THAN_OR_EQUAL_TO_KEY);
            } else {
            	hasLessThanOrEqualTo = false;
            	lessThanOrEqualTo = -1;
            }
            if (def.containsKey(EVEN_KEY)) {
            	even = def.getRequiredBoolean(EVEN_KEY);
            	hasEven = true;
            } else {
            	even = false;
            	hasEven = false;
            }
            if (def.containsKey(ODD_KEY)) {
            	odd = def.getRequiredBoolean(ODD_KEY);
            	hasOdd = true;
            } else {
            	odd = false;
            	hasOdd = false;
            }
            if (def.containsKey(IN_ARRAY_KEY)) {
            	inArray = new ArrayList<Double>();
            	JsonArray array = def.getRequiredArray(IN_ARRAY_KEY);
            	for (int i = 0; i < array.size(); ++i) {
            		inArray.add(array.getRequiredDouble(i));
            	}
            } else {
            	inArray = null;
            }
            if (def.containsKey(NOT_IN_ARRAY_KEY)) {
            	notInArray = new ArrayList<Double>();
            	JsonArray array = def.getRequiredArray(NOT_IN_ARRAY_KEY);
            	for (int i = 0; i < array.size(); ++i) {
            		notInArray.add(array.getRequiredDouble(i));
            	}
            } else {
            	notInArray = null;
            }

        } else {
        	hasDef = false;
        	equalTo = -1;
        	greaterThan = -1;
        	greaterThanOrEqualTo = -1;
        	lessThan = -1;
        	lessThanOrEqualTo = -1;
        	hasEqualTo = false;
        	hasGreaterThan = false;
        	hasGreaterThanOrEqualTo = false;        	
        	hasLessThan = false;
        	hasLessThanOrEqualTo = false;
        	even = false;
        	hasEven = false;
        	odd = false;
        	hasOdd = false;
        	inArray = null;
        	notInArray = null;

        }
    }

    @Override
    public boolean isNumber() {
        return true;
    }
    
    private Params pathValueParams(String path, Double num) {
        return schema().pathParams(path)
                .p("value", num);
    }

    @Override
    public void validate(String path, JsonValue value) {
        super.validate(path, value);
        if (!value.isNumber()) { throw ItemscriptError.internalError(this, "validate.value.was.not.number",
                schema().pathParams(path)
                        .p("value", value.toCompactJsonString())); }
        if (hasDef) {
            validateNumber(path, value.doubleValue());
        }
    }
    
    private void validateNumber(String path, Double num) {
        if (hasEqualTo) {
        	if (num != equalTo) { throw ItemscriptError.internalError(this,
                    "validateNumber.value.is.not.equal.to.equal.to", pathValueParams(path, num)); }
        }
        if (hasGreaterThan) {
            if (num <= greaterThan) { throw ItemscriptError.internalError(this,
                    "validateNumber.value.is.less.than.or.equal.to.min", pathValueParams(path, num)
                    	.p("specified", greaterThan)
                    	.p("input", num.toString())); }
        }
        if (hasGreaterThanOrEqualTo) {
        	if (num < greaterThanOrEqualTo) { throw ItemscriptError.internalError(this,
                    "validateNumber.value.is.less.than.min", pathValueParams(path, num)
                		.p("specified", greaterThanOrEqualTo)
                		.p("input", num.toString())); }
        }
        if (hasLessThan) {
        	if (num >= lessThan) { throw ItemscriptError.internalError(this,
                    "validateNumber.value.is.greater.than.or.equal.to.max", pathValueParams(path, num)
            			.p("specified", lessThan)
            			.p("input", num.toString())); }
        }
        if (hasLessThanOrEqualTo) {
        	if (num > lessThanOrEqualTo) { throw ItemscriptError.internalError(this,
                    "validateNumber.value.is.greater.than.max", pathValueParams(path, num)
                    	.p("specified", lessThanOrEqualTo)
            			.p("input", num.toString())); }
        }
        if (hasEven) {
        	if (num.doubleValue() != Math.round(num.doubleValue())) { throw ItemscriptError.internalError(
                    this, "validateNumber.value.is.not.an.integer", pathValueParams(path, num)); }
        	if (even) {
        		if ((num % 2) != 0) { throw ItemscriptError.internalError(this,
                        "validateNumber.value.is.not.even", pathValueParams(path, num)); }
        	} else {
        		if ((num % 2) == 0) { throw ItemscriptError.internalError(this,
                        "validateNumber.value.is.not.odd", pathValueParams(path, num)); }
        	}
        }
        if (hasOdd) {
        	if (num.doubleValue() != Math.round(num.doubleValue())) { throw ItemscriptError.internalError(
                    this, "validateNumber.value.is.not.an.integer", pathValueParams(path, num)); }
        	if (odd) {
        		if ((num % 2) == 0) { throw ItemscriptError.internalError(this,
                        "validateNumber.value.is.not.odd", pathValueParams(path, num)); }
        	} else {
        		if ((num % 2) != 0) { throw ItemscriptError.internalError(this,
                        "validateNumber.value.is.not.even", pathValueParams(path, num)); }
        	}
        }
        if (inArray != null) {
            boolean matched = false;
            for (int i = 0; i < inArray.size(); ++i) {
                double inArrayDouble = inArray.get(i);
                if (num == inArrayDouble) {
                    matched = true;
                    break;
                }
            }
            if (!matched) { throw ItemscriptError.internalError(this,
                    "validateNumber.value.did.not.match.a.valid.choice", pathValueParams(path, num)); }
        }
        if (notInArray != null) {
            boolean matched = false;
            for (int i = 0; i < notInArray.size(); ++i) {
                double notInArrayDouble = notInArray.get(i);
                if (num == notInArrayDouble) {
                    matched = true;
                    break;
                }
            }
            if (matched) { throw ItemscriptError.internalError(this,
                    "validateNumber.value.matched.an.invalid.choice", pathValueParams(path, num)); }
        }
    }
}