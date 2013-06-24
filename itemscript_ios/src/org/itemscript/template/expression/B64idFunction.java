
package org.itemscript.template.expression;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.values.JsonString;
import org.itemscript.core.values.JsonValue;
import org.itemscript.template.TemplateExec;

public class B64idFunction extends FunctionBase {
    public B64idFunction(JsonSystem system) {
        super(system, null);
    }

    @Override
    public JsonString execute(TemplateExec context, JsonValue contextVal, JsonValue value) {
        return system().createString(system().util()
                .generateB64id());
    }
}