
package org.itemscript.template.scanner;

public class OpenTagToken extends Token {
    public static final OpenTagToken INSTANCE = new OpenTagToken();

    private OpenTagToken() {
        super(-1, -1);
    }

    @Override
    public boolean isOpenTagToken() {
        return true;
    }

    @Override
    public String toString() {
        return "[OpenTagToken]";
    }
}