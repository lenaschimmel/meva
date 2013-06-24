
package org.itemscript.template.expression;

import java.util.List;

import org.itemscript.core.HasSystem;
import org.itemscript.core.JsonSystem;

/**
 * A base implementation class for Function.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 */
public abstract class FunctionBase implements Function, HasSystem {
    private final JsonSystem system;
    private final List<Expression> args;

    public FunctionBase(JsonSystem system, List<Expression> args) {
        this.system = system;
        this.args = args;
    }

    public List<Expression> args() {
        return args;
    }

    @Override
    public JsonSystem system() {
        return system;
    }
}