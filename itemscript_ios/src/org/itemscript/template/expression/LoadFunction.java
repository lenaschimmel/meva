
package org.itemscript.template.expression;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.values.JsonValue;
import org.itemscript.template.TemplateExec;

public class LoadFunction extends FunctionBase {
    private final String url;

    public LoadFunction(JsonSystem system, String url) {
        super(system, null);
        this.url = url;
    }

    @Override
    public JsonValue execute(TemplateExec template, JsonValue context, JsonValue value) {
        return template.get(url);
    }
}