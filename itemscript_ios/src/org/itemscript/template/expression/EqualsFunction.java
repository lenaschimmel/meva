
package org.itemscript.template.expression;

import java.util.List;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.exceptions.ItemscriptError;
import org.itemscript.core.values.JsonValue;
import org.itemscript.template.TemplateExec;

public class EqualsFunction extends FunctionBase {
    public EqualsFunction(JsonSystem system, List<Expression> args) {
        super(system, args);
    }

    @Override
    public JsonValue execute(TemplateExec templateExec, JsonValue context, JsonValue value) {
        if (args().size() != 1) { throw ItemscriptError.internalError(this, "execute.only.one.arg.allowed", args()
                + ""); }
        JsonValue compareValue = args().get(0)
                .interpret(templateExec, context);
        boolean result;
        if (value == null) {
            result = compareValue == null;
        } else {
            result = value.equals(compareValue);
        }
        return system().createBoolean(result);
    }
}