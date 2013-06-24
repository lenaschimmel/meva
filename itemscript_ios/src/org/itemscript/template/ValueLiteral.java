
package org.itemscript.template;

import org.itemscript.core.values.JsonValue;

/**
 * A container for a literal JsonValue that may be contained in a template.
 *
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 */
public class ValueLiteral implements Element {
    private final JsonValue value;

    /**
     * Create a new ValueLiteral with the given value. The value will be copied and stored.
     * 
     * @param value The value for this ValueLiteral.
     */
    public ValueLiteral(JsonValue value) {
        this.value = value.copy();
    }

    @Override
    public JsonValue interpret(TemplateExec templateExec, JsonValue context) {
        return value;
    }
}