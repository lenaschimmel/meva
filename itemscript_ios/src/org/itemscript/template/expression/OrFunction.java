
package org.itemscript.template.expression;

import java.util.List;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.exceptions.ItemscriptError;
import org.itemscript.core.values.JsonValue;
import org.itemscript.template.TemplateExec;

public class OrFunction extends FunctionBase {
    public OrFunction(JsonSystem system, List<Expression> args) {
        super(system, args);
    }

    @Override
    public JsonValue execute(TemplateExec templateExec, JsonValue context, JsonValue value) {
        if (value != null && !value.isBoolean()) { throw ItemscriptError.internalError(this,
                "value.was.not.boolean", value + ""); }
        if (args().size() != 1) { throw ItemscriptError.internalError(this, "execute.only.one.arg.allowed", args()
                + ""); }
        JsonValue orValue = args().get(0)
                .interpret(templateExec, context);
        if (orValue != null && !orValue.isBoolean()) { throw ItemscriptError.internalError(this,
                "or.value.was.not.boolean", orValue + ""); }
        boolean valueBoolean;
        if (value == null) {
            valueBoolean = false;
        } else {
            valueBoolean = value.booleanValue();
        }
        // Short-circuit.
        if (valueBoolean) { return system().createBoolean(true); }
        boolean orValueBoolean;
        if (orValue == null) {
            orValueBoolean = false;
        } else {
            orValueBoolean = orValue.booleanValue();
        }
        return system().createBoolean(valueBoolean || orValueBoolean);
    }
}