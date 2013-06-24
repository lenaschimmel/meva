
package org.itemscript.template.expression;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.util.GeneralUtil;
import org.itemscript.core.values.JsonString;
import org.itemscript.core.values.JsonValue;
import org.itemscript.template.Template;
import org.itemscript.template.TemplateExec;

public class HtmlEscapeFunction extends FunctionBase {
    public HtmlEscapeFunction(JsonSystem system) {
        super(system, null);
    }

    @Override
    public JsonString execute(TemplateExec template, JsonValue context, JsonValue value) {
        return system().createString(GeneralUtil.htmlEncode(Template.coerceToString(value)));
    }
}