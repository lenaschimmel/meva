
package org.itemscript.schema;

import java.util.List;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.Params;
import org.itemscript.core.exceptions.ItemscriptError;
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;
import org.itemscript.template.Template;
import org.itemscript.template.TemplateExec;
import org.itemscript.template.expression.Expression;
import org.itemscript.template.expression.Function;
import org.itemscript.template.expression.FunctionBase;
import org.itemscript.template.expression.FunctionFactory;
import org.itemscript.template.expression.FunctionFoundry;

/**
 * Represents a more specific validate Function used to parse user input that is provided in a String
 * into a Boolean. Only used to validate values against the Boolean Type.
 * 
 * @author Eileen Bai
 */
public class ValidateAsBooleanFunction extends FunctionBase {
	
	/**
	 * Adds the "validateAsBoolean" function into the FunctionFoundry.
	 * Therefore ensuring that this function is only intialized when it is needed.
	 */
	public static void init() {
		FunctionFoundry.put("validateAsBoolean", new FunctionFactory() {
			@Override
			public Function create(JsonSystem system, List<Expression> args) {
				return new ValidateAsBooleanFunction(system, args);
			}
		});
	}
	
	Schema schema;
	
	/**
	 * Constructs a new ValidateAsBooleanFunction.
	 * 
	 * @param system
	 * @param args - the arguments provided to "validateAsBoolean."
	 */
    public ValidateAsBooleanFunction(JsonSystem system, List<Expression> args) {
        super(system, args);
        schema = new Schema(system);
    }

    @Override
    public JsonValue execute(TemplateExec templateExec, JsonValue context, JsonValue value) {
		if (value == null) {
			return Template.setErrorMessage(onError(ItemscriptError.internalError(this,
					"execute.validateAsBoolean.value.was.null",
						value + "")));
		}
		else if (!(value.isString() || value.isBoolean())) {
			throw ItemscriptError.internalError(this,
					"execute.validateAsBoolean.value.was.not.a.string.or.boolean", value + "");
		}
		else if (args().size() != 1) {
			throw ItemscriptError.internalError(this,
					"execute.validateAsBoolean.only.one.arg.allowed");
		}
		else {
			JsonValue typeValue = args().get(0).interpret(templateExec, context);
			Type type;
			try {
				type = schema.resolve(typeValue);
			} catch (ItemscriptError e) {
				return Template.setErrorMessage(onError(e));
			}
			if (!type.isBoolean()) {
				throw ItemscriptError.internalError(this,
						"execute.validateAsBoolean.type.was.not.boolean", value+"");
			}
			if (value.isString()) {
				if (value.equals(system().createString("true"))) {
					value = system().createBoolean(true);
				} else if (value.equals(system().createString("false"))) {
					value = system().createBoolean(false);
				} else {
					return Template.setErrorMessage(onError(
							ItemscriptError.internalError(this,
								"execute.validateAsBoolean.value.asBoolean.was.not.true.or.false",
									value+""))
									.p("value", value+""));
				}
			}
			try {
				schema.validate(type, value);
				return onSuccess();
			} catch (ItemscriptError e) {
				return Template.setErrorMessage(onError(e));
			}
		}
    }
		
	private JsonObject onError(Throwable e) {
		JsonObject errorObject = system().createObject();
		errorObject.put("valid", false);
		Params params = (Params) ((ItemscriptError) e).params();
		errorObject.put("specified", params.get("specified"));
		errorObject.put("input", params.get("input"));
		errorObject.put("value", params.get("value"));
		errorObject.put("key", params.get("key"));
		if (e instanceof ItemscriptError) {
			errorObject.put("errorMessage", ((ItemscriptError) e).key());
		}
		return errorObject;
	}

	private JsonObject onSuccess() {
		JsonObject successObject = system().createObject();
		successObject.put("valid", true);
		successObject.put("message", "validated successfully");
		return successObject;
	}
}