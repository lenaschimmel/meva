
package org.itemscript.template.expression;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.values.JsonString;
import org.itemscript.core.values.JsonValue;
import org.itemscript.template.TemplateExec;

public class ConstantFunction extends FunctionBase {
    private final JsonString constantValue;

    public ConstantFunction(JsonSystem system, String name) {
        super(system, null);
        constantValue = system().createString(system().constant(name));
    }

    @Override
    public JsonValue execute(TemplateExec template, JsonValue context, JsonValue value) {
        return constantValue;
    }
}