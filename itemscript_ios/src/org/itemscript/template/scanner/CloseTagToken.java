
package org.itemscript.template.scanner;

public final class CloseTagToken extends Token {
    public static final CloseTagToken INSTANCE = new CloseTagToken();

    private CloseTagToken() {
        super(-1, -1);
    }

    @Override
    public boolean isCloseTagToken() {
        return true;
    }

    @Override
    public String toString() {
        return "[CloseTagToken]";
    }
}