
package org.itemscript.template.scanner;

public class OpenArgToken extends Token {
    public static final OpenArgToken INSTANCE = new OpenArgToken();

    private OpenArgToken() {
        super(-1, -1);
    }

    @Override
    public boolean isOpenParanToken() {
        return true;
    }

    @Override
    public String toString() {
        return "[OpenArgToken]";
    }
}