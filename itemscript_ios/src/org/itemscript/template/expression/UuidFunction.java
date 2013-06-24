
package org.itemscript.template.expression;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.values.JsonValue;
import org.itemscript.template.TemplateExec;

public class UuidFunction extends FunctionBase {
    public UuidFunction(JsonSystem system) {
        super(system, null);
    }

    @Override
    public JsonValue execute(TemplateExec template, JsonValue context, JsonValue value) {
        return system().createString(system().util()
                .generateUuid());
    }
}