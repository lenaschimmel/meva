
package org.itemscript.template.scanner;

public class QuotedStringToken extends Token {
    private final String string;

    public QuotedStringToken(int begin, int end, String string) {
        super(begin, end);
        this.string = string;
    }

    @Override
    public QuotedStringToken asQuotedStringToken() {
        return this;
    }

    @Override
    public boolean isQuotedStringToken() {
        return true;
    }

    public String string() {
        return string;
    }

    @Override
    public String toString() {
        return "[QuotedStringToken string='" + string + "']";
    }
}