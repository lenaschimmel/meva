
package org.itemscript.template.expression;

import java.util.List;

import org.itemscript.core.HasSystem;
import org.itemscript.core.JsonSystem;
import org.itemscript.core.values.JsonValue;
import org.itemscript.template.TemplateExec;
import org.itemscript.template.text.TextElement;

public class Expression implements HasSystem, TextElement {
    private final List<Function> contents;
    private final JsonSystem system;

    public Expression(JsonSystem system, List<Function> contents) {
        this.system = system;
        this.contents = contents;
    }

    @Override
    public Expression asExpression() {
        return this;
    }

    @Override
    public JsonValue interpret(TemplateExec templateExec, JsonValue context) {
        // "output" to be fed to the first function in an expression is always a JsonNull.
        JsonValue output = system().createNull();
        for (int i = 0; i < contents.size(); ++i) {
            // Set the input to the output of the previous function.
            JsonValue input = output;
            // Execute this function.
            output = contents.get(i)
                    .execute(templateExec, context, input);
        }
        return output;
    }

    @Override
    public boolean isExpression() {
        return true;
    }

    public JsonSystem system() {
        return system;
    }

    @Override
    public String toString() {
        return "[Expression contents=" + contents + "]";
    }
}