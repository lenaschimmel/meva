
package org.itemscript.template.scanner;

public class CloseArgToken extends Token {
    public static CloseArgToken INSTANCE = new CloseArgToken();

    private CloseArgToken() {
        super(-1, -1);
    }

    @Override
    public boolean isCloseParanToken() {
        return true;
    }

    @Override
    public String toString() {
        return "[CloseArgToken]";
    }
}