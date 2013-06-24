
package org.itemscript.template.expression;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.exceptions.ItemscriptError;
import org.itemscript.core.values.JsonValue;
import org.itemscript.template.TemplateExec;

public class EmptyFunction extends FunctionBase {
    public EmptyFunction(JsonSystem system) {
        super(system, null);
    }

    @Override
    public JsonValue execute(TemplateExec templateExec, JsonValue context, JsonValue value) {
        if (value == null) {
            return system().createBoolean(true);
        } else if (value.isContainer()) {
            return system().createBoolean(value.asContainer()
                    .size() == 0);
        } else if (value.isString()) {
            return system().createBoolean(value.stringValue()
                    .length() == 0);
        } else if (value.isNumber()) {
            return system().createBoolean(false);
        } else if (value.isBoolean()) {
            return system().createBoolean(false);
        } else if (value.isNative()) {
            return system().createBoolean(false);
        } else if (value.isNull()) {
            return system().createBoolean(true);
        } else {
            // Should never happen.
            throw ItemscriptError.internalError(this, "execute.value.was.unknown.type", value + "");
        }
    }
}