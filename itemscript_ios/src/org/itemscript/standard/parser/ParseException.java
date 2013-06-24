
package org.itemscript.standard.parser;

/**
 * ParseException explains why and where the error occurs in source JSON text.
 * 
 * @author FangYidong<fangyidong@yahoo.com.cn>
 *
 */
public final class ParseException extends Exception {
    private static final long serialVersionUID = -7880698968187728548L;
    public static final int ERROR_UNEXPECTED_CHAR = 0;
    public static final int ERROR_UNEXPECTED_TOKEN = 1;
    public static final int ERROR_UNEXPECTED_EXCEPTION = 2;
    private int errorType;
    private Object unexpectedObject;
    private final int line;
    private final int column;

    public ParseException(int errorType) {
        this(-1, -1, errorType, null);
    }

    public ParseException(int line, int column, int errorType, Object unexpectedObject) {
        this.line = line;
        this.column = column;
        this.errorType = errorType;
        this.unexpectedObject = unexpectedObject;
    }

    public ParseException(int errorType, Object unexpectedObject) {
        this(-1, -1, errorType, unexpectedObject);
    }

    public int getColumn() {
        return column;
    }

    public int getErrorType() {
        return errorType;
    }

    public int getLine() {
        return line;
    }

    @Override
    public String getMessage() {
        return toString();
    }

    /**
     * @see org.itemscript.standard.parser.Yytoken
     * 
     * @return One of the following base on the value of errorType:
     * 		   	ERROR_UNEXPECTED_CHAR		java.lang.Character
     * 			ERROR_UNEXPECTED_TOKEN		org.json.simple.parser.Yytoken
     * 			ERROR_UNEXPECTED_EXCEPTION	java.lang.Exception
     */
    public Object getUnexpectedObject() {
        return unexpectedObject;
    }

    public void setErrorType(int errorType) {
        this.errorType = errorType;
    }

    public void setUnexpectedObject(Object unexpectedObject) {
        this.unexpectedObject = unexpectedObject;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        switch (errorType) {
            case ERROR_UNEXPECTED_CHAR :
                sb.append("Unexpected character ")
                        .append(unexpectedObject)
                        .append(" at line ")
                        .append(line)
                        .append(" column ")
                        .append(column)
                        .append(".");
                break;
            case ERROR_UNEXPECTED_TOKEN :
                sb.append("Unexpected token ")
                        .append(unexpectedObject)
                        .append(" at line ")
                        .append(line)
                        .append(" column ")
                        .append(column)
                        .append(".");
                break;
            case ERROR_UNEXPECTED_EXCEPTION :
                sb.append("Unexpected exception at line ")
                        .append(line)
                        .append(" column ")
                        .append(column)
                        .append(": ")
                        .append(unexpectedObject);
                break;
            default :
                sb.append("Unknown error at line ")
                        .append(line)
                        .append(" column ")
                        .append(column)
                        .append(".");
                break;
        }
        return sb.toString();
    }
}
