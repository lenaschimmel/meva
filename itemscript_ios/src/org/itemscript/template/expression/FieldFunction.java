
package org.itemscript.template.expression;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.values.JsonValue;
import org.itemscript.template.TemplateExec;

public class FieldFunction extends FunctionBase {
    private final String path;

    public FieldFunction(JsonSystem system, String path) {
        super(system, null);
        this.path = path;
    }

    @Override
    public JsonValue execute(TemplateExec template, JsonValue context, JsonValue value) {
        if (path.length() == 0) {
            return context;
        } else {
            if (context != null && context.isContainer()) {
                return context.asContainer()
                        .getByPath(path);
            } else {
                return null;
            }
        }
    }
}