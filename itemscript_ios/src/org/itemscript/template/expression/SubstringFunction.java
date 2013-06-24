
package org.itemscript.template.expression;

import java.util.List;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.exceptions.ItemscriptError;
import org.itemscript.core.values.JsonValue;
import org.itemscript.template.TemplateExec;

public class SubstringFunction extends FunctionBase {
    public SubstringFunction(JsonSystem system, List<Expression> args) {
        super(system, args);
    }

    @Override
    public JsonValue execute(TemplateExec templateExec, JsonValue context, JsonValue value) {
        if (value == null || !value.isString()) { throw ItemscriptError.internalError(this,
                "execute.value.was.not.a.string", value + ""); }
        String stringValue = value.stringValue();
        if (args().size() == 0) { throw ItemscriptError.internalError(this, "execute.no.args.supplied"); }
        if (args().size() > 2) { throw ItemscriptError.internalError(this, "execute.too.many.args.suppled", args()
                + ""); }
        JsonValue beginValue = args().get(0)
                .interpret(templateExec, context);
        if (beginValue == null || !beginValue.isNumber()) { throw ItemscriptError.internalError(this,
                "execute.first.argument.value.was.not.a.number", beginValue + ""); }
        if (args().size() == 1) {
            return system().createString(stringValue.substring(beginValue.intValue()));
        } else {
            JsonValue endValue = args().get(1)
                    .interpret(templateExec, context);
            if (endValue == null || !endValue.isNumber()) { throw ItemscriptError.internalError(this,
                    "execute.second.argument.value.was.not.a.number", beginValue + ""); }
            return system().createString(stringValue.substring(beginValue.intValue(), endValue.intValue()));
        }
    }
}