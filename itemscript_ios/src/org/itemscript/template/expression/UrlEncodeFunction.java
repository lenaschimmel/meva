
package org.itemscript.template.expression;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.url.Url;
import org.itemscript.core.values.JsonString;
import org.itemscript.core.values.JsonValue;
import org.itemscript.template.Template;
import org.itemscript.template.TemplateExec;

public class UrlEncodeFunction extends FunctionBase {
    public UrlEncodeFunction(JsonSystem system) {
        super(system, null);
    }

    @Override
    public JsonString execute(TemplateExec context, JsonValue contextVal, JsonValue value) {
        return system().createString(Url.encode(Template.coerceToString(value)));
    }
}