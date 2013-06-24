
package org.itemscript.template.scanner;

public class CommaToken extends Token {
    public static final CommaToken INSTANCE = new CommaToken();

    private CommaToken() {
        super(-1, -1);
    }

    @Override
    public boolean isCommaToken() {
        return true;
    }

    @Override
    public String toString() {
        return "[CommaToken]";
    }
}