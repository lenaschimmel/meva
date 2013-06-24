
package org.itemscript.template.expression;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.exceptions.ItemscriptError;
import org.itemscript.core.values.JsonValue;
import org.itemscript.template.TemplateExec;

public class NotFunction extends FunctionBase {
    public NotFunction(JsonSystem system) {
        super(system, null);
    }

    @Override
    public JsonValue execute(TemplateExec templateExec, JsonValue context, JsonValue value) {
        if (value != null && !value.isBoolean()) { throw ItemscriptError.internalError(this,
                "value.was.not.boolean", value + ""); }
        return system().createBoolean(!value.booleanValue());
    }
}