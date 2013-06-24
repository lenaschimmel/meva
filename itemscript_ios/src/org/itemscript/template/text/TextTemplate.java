
package org.itemscript.template.text;

import java.util.List;

import org.itemscript.core.HasSystem;
import org.itemscript.core.JsonSystem;
import org.itemscript.core.values.JsonValue;
import org.itemscript.template.Element;
import org.itemscript.template.Template;
import org.itemscript.template.TemplateExec;

public class TextTemplate implements Element, HasSystem {
    private final TextElement oneElement;
    private final List<TextElement> contents;
    private final JsonSystem system;

    public TextTemplate(JsonSystem system, List<TextElement> contents) {
        this.system = system;
        if (contents.size() == 1) {
            this.contents = null;
            this.oneElement = contents.get(0);
        } else {
            this.contents = contents;
            this.oneElement = null;
        }
    }

    @Override
    public JsonValue interpret(TemplateExec templateExec, JsonValue context) {
        // special-case when contents is only one thing; just return the single value returned by the single contents.
        if (oneElement != null) {
            return oneElement.interpret(templateExec, context);
        } else {
            // otherwise return a string that is the result of concatenating all the sections.
            StringBuffer sb = new StringBuffer();
            for (int i = 0, s = contents.size(); i < s; ++i) {
                sb.append(Template.coerceToString(contents.get(i)
                        .interpret(templateExec, context)));
            }
            return system().createString(sb.toString());
        }
    }

    public JsonSystem system() {
        return system;
    }

    @Override
    public String toString() {
        return "[TextTemplate oneElement=" + oneElement + " contents=" + contents + "]";
    }
}