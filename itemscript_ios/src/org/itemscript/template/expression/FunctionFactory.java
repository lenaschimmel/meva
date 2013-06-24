
package org.itemscript.template.expression;

import java.util.List;

import org.itemscript.core.JsonSystem;

/**
 * A FunctionFactory creates a new Function given a list of Expressions as arguments.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 */
public interface FunctionFactory {
    /**
     * Create a new Function with the given list of Expressions.
     * 
     * @param system The associated JsonSystem.
     * @param args The arguments for this function.
     * @return An appropriate Function.
     */
    public Function create(JsonSystem system, List<Expression> args);
}