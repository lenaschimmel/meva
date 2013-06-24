
package org.itemscript.template.expression;

import org.itemscript.core.values.JsonValue;
import org.itemscript.template.TemplateExec;

/**
 * A Function is a component of an Expression, taking input and returning a value.
 * Functions should be immutable, stateless, and side-effect-free; that is, having only "final" fields
 * and no internal state that can change, and not making any changes in external state.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 */
public interface Function {
    /**
     * Execute this Function in the given context with the given input value.
     * 
     * @param template The template execution environment.
     * @param context The context.
     * @param value The input value, which may be null.
     * @return An output value, which may be null.
     */
    public abstract JsonValue execute(TemplateExec template, JsonValue context, JsonValue value);
}