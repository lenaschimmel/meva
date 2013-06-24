
package org.itemscript.template.text;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.values.JsonString;
import org.itemscript.core.values.JsonValue;
import org.itemscript.template.TemplateExec;

public class Text extends Segment {
    private final JsonString text;

    public Text(JsonSystem system, String text) {
        super(system);
        this.text = system.createString(text);
    }

    @Override
    public JsonValue interpret(TemplateExec templateExec, JsonValue context) {
        return text;
    }

    @Override
    public String toString() {
        return "[Text text=" + text + "]";
    }
}