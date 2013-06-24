
package org.itemscript.template.scanner;

import org.itemscript.template.Template;

/**
 * An element of an expression, usually the contents of a tag.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 */
public class ExpressionToken extends Token {
    private final String content;

    public ExpressionToken(int begin, int end, String content) {
        super(begin, end);
        this.content = content;
    }

    @Override
    public ExpressionToken asExpressionToken() {
        return this;
    }

    public String content() {
        return content;
    }

    public boolean isDirective() {
        return content.startsWith(Template.DIRECTIVE_CHAR + "");
    }

    public boolean isElse() {
        return content.equals(".else");
    }

    public boolean isEnd() {
        return content.equals(".end");
    }

    @Override
    public boolean isExpressionToken() {
        return true;
    }

    public boolean isForeach() {
        return content.equals(".foreach");
    }

    public boolean isIf() {
        return content.equals(".if");
    }

    public boolean isJoin() {
        return content.equals(".join");
    }

    public boolean isOr() {
        return content.equals(".or");
    }

    public boolean isSection() {
        return content.equals(".section");
    }

    @Override
    public String toString() {
        return "[ExpressionToken content='" + content + "']";
    }
}