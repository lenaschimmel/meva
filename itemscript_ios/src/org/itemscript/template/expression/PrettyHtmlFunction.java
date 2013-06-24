
package org.itemscript.template.expression;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.util.StaticJsonUtil;
import org.itemscript.core.values.JsonValue;
import org.itemscript.template.TemplateExec;

public class PrettyHtmlFunction extends FunctionBase {
    public PrettyHtmlFunction(JsonSystem system) {
        super(system, null);
    }

    @Override
    public JsonValue execute(TemplateExec template, JsonValue context, JsonValue value) {
        if (value != null) {
            return system().createString(StaticJsonUtil.toHtmlJson(value));
        } else {
            return system().createString("null");
        }
    }
}