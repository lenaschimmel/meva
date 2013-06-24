
package org.itemscript.template.expression;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.exceptions.ItemscriptError;
import org.itemscript.core.util.TsvParser;
import org.itemscript.core.values.JsonValue;
import org.itemscript.template.TemplateExec;

public class ParseTsvFunction extends FunctionBase {
    public ParseTsvFunction(JsonSystem system) {
        super(system, null);
    }

    @Override
    public JsonValue execute(TemplateExec template, JsonValue context, JsonValue value) {
        if (value == null || !value.isString()) { throw ItemscriptError.internalError(this,
                "execute.value.was.not.a.JsonString", value + ""); }
        return TsvParser.parse(system(), value.stringValue());
    }
}