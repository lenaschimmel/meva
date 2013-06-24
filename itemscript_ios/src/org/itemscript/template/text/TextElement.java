
package org.itemscript.template.text;

import org.itemscript.template.Element;
import org.itemscript.template.expression.Expression;

public interface TextElement extends Element {
    public Expression asExpression();

    public boolean isExpression();
}