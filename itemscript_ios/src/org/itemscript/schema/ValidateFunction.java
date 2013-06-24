package org.itemscript.schema;

import java.util.List;
import org.itemscript.template.expression.FunctionFoundry;

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

/**
 * Represents the general "validate" function.
 * This Function validates that a given value is valid according to the specified Type.
 * After validation, either a successObject or an errorObject is returned. If validation results in an
 * error, the "message" key provides the human-readable error message.
 * 
 * @author Eileen Bai
 */
public class ValidateFunction extends FunctionBase {
	
	/**
	 * Adds the "validate" function into the FunctionFoundry.
	 * Therefore ensuring that this function is only intialized when it is needed.
	 */
	public static void init() {
		FunctionFoundry.put("validate", new FunctionFactory() {
			@Override
			public Function create(JsonSystem system, List<Expression> args) {
				return new ValidateFunction(system, args);
			}
		});
	}

	Schema schema;
	
	/**
	 * Constructs a new ValidateFunction.
	 * 
	 * @param system
	 * @param args - the arguments provided to "validate."
	 */
	public ValidateFunction(JsonSystem system, List<Expression> args) {
		super(system, args);
		schema = new Schema(system);
	}

	@Override
	public JsonValue execute(TemplateExec templateExec, JsonValue context, JsonValue value) {;
		if (value == null) {
			return Template.setErrorMessage(onError(ItemscriptError.internalError(this, "execute.validate.value.was.null",
						value + "")));
		}
		else if (!(value.isString() || value.isNumber() || value.isBoolean()
				|| value.isArray() || value.isObject() || value.isNull())) {
			throw ItemscriptError.internalError(this,
					"execute.validate.value.was.not.of.valid.format", value + "");
		}
		else if (args().size() != 1) {
			throw ItemscriptError.internalError(this,
					"execute.validate.only.one.arg.allowed");
		}
		else {
			JsonValue typeValue = args().get(0).interpret(templateExec, context);
			Type type;
			try {
				type = schema.resolve(typeValue);
			} catch (ItemscriptError e) {
				return Template.setErrorMessage(onError(e));
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