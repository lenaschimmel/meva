/*
 * $Id: JSONParser.java,v 1.1 2006/04/15 14:10:48 platform Exp $
 * Created on 2006-4-15
 */

package org.itemscript.standard.parser;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.values.JsonArray;
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonString;
import org.itemscript.core.values.JsonValue;

/**
 * Parser for JSON text. Please note that JSONParser is NOT thread-safe.
 * 
 * @author FangYidong<fangyidong@yahoo.com.cn>
 */
public final class JsonParser {
    public static final int S_INIT = 0;
    public static final int S_IN_FINISHED_VALUE = 1;//string,number,boolean,null,object,array
    public static final int S_IN_OBJECT = 2;
    public static final int S_IN_ARRAY = 3;
    public static final int S_PASSED_PAIR_KEY = 4;
    public static final int S_IN_PAIR_VALUE = 5;
    public static final int S_END = 6;
    public static final int S_IN_ERROR = -1;
    private LinkedList<Integer> handlerStatusStack;
    private Yylex lexer;
    private Yytoken token = null;
    private int status = S_INIT;
    private final JsonSystem system;

    public JsonParser(JsonSystem system) {
        this.system = system;
        lexer = new Yylex(system, (Reader) null);
    }

    private org.itemscript.core.values.JsonArray createArrayContainer() {
        return system.createArray();
    }

    private org.itemscript.core.values.JsonObject createObjectContainer() {
        return system.createObject();
    }

    /**
     * @return The position of the beginning of the current token.
     */
    public int getPosition() {
        return lexer.getPosition();
    }

    private void nextToken() throws ParseException, IOException {
        token = lexer.yylex();
        if (token == null) token = new Yytoken(Yytoken.TYPE_EOF, null);
    }

    /**
     * Parse JSON text into java object from the input source.
     * 	
     * @param in
     * @return Instance of the following:
     *  org.json.simple.JSONObject,
     * 	org.json.simple.JSONArray,
     * 	java.lang.String,
     * 	java.lang.Number,
     * 	java.lang.Boolean,
     * 	null
     * 
     * @throws IOException
     * @throws ParseException
     */
    public JsonValue parse(Reader in) throws IOException, ParseException {
        reset(in);
        LinkedList<Integer> statusStack = new LinkedList<Integer>();
        LinkedList<JsonValue> valueStack = new LinkedList<JsonValue>();
        try {
            do {
                nextToken();
                switch (status) {
                    case S_INIT :
                        switch (token.type) {
                            case Yytoken.TYPE_VALUE :
                                status = S_IN_FINISHED_VALUE;
                                statusStack.addFirst(new Integer(status));
                                valueStack.addFirst(token.value);
                                break;
                            case Yytoken.TYPE_LEFT_BRACE :
                                status = S_IN_OBJECT;
                                statusStack.addFirst(new Integer(status));
                                valueStack.addFirst(createObjectContainer());
                                break;
                            case Yytoken.TYPE_LEFT_SQUARE :
                                status = S_IN_ARRAY;
                                statusStack.addFirst(new Integer(status));
                                valueStack.addFirst(createArrayContainer());
                                break;
                            default :
                                status = S_IN_ERROR;
                        }//inner switch
                        break;
                    case S_IN_FINISHED_VALUE :
                        if (token.type == Yytoken.TYPE_EOF)
                            return valueStack.removeFirst();
                        else
                            throw new ParseException(lexer.getLine(), lexer.getColumn(),
                                    ParseException.ERROR_UNEXPECTED_TOKEN, token);
                    case S_IN_OBJECT :
                        switch (token.type) {
                            case Yytoken.TYPE_COMMA :
                                break;
                            case Yytoken.TYPE_VALUE :
                                if (token.value instanceof JsonString) {
                                    JsonString key = (JsonString) token.value;
                                    valueStack.addFirst(key);
                                    status = S_PASSED_PAIR_KEY;
                                    statusStack.addFirst(new Integer(status));
                                } else {
                                    status = S_IN_ERROR;
                                }
                                break;
                            case Yytoken.TYPE_RIGHT_BRACE :
                                if (valueStack.size() > 1) {
                                    statusStack.removeFirst();
                                    valueStack.removeFirst();
                                    status = peekStatus(statusStack);
                                } else {
                                    status = S_IN_FINISHED_VALUE;
                                }
                                break;
                            default :
                                status = S_IN_ERROR;
                                break;
                        }//inner switch
                        break;
                    case S_PASSED_PAIR_KEY :
                        switch (token.type) {
                            case Yytoken.TYPE_COLON :
                                break;
                            case Yytoken.TYPE_VALUE :
                                statusStack.removeFirst();
                                JsonString key = (JsonString) valueStack.removeFirst();
                                JsonObject parent = (JsonObject) valueStack.getFirst();
                                parent.put(key.stringValue(), token.value);
                                status = peekStatus(statusStack);
                                break;
                            case Yytoken.TYPE_LEFT_SQUARE :
                                statusStack.removeFirst();
                                key = (JsonString) valueStack.removeFirst();
                                parent = (JsonObject) valueStack.getFirst();
                                JsonArray newArray = createArrayContainer();
                                parent.put(key.stringValue(), newArray);
                                status = S_IN_ARRAY;
                                statusStack.addFirst(new Integer(status));
                                valueStack.addFirst(newArray);
                                break;
                            case Yytoken.TYPE_LEFT_BRACE :
                                statusStack.removeFirst();
                                key = (JsonString) valueStack.removeFirst();
                                parent = (JsonObject) valueStack.getFirst();
                                JsonObject newObject = createObjectContainer();
                                parent.put(key.stringValue(), newObject);
                                status = S_IN_OBJECT;
                                statusStack.addFirst(new Integer(status));
                                valueStack.addFirst(newObject);
                                break;
                            default :
                                status = S_IN_ERROR;
                        }
                        break;
                    case S_IN_ARRAY :
                        switch (token.type) {
                            case Yytoken.TYPE_COMMA :
                                break;
                            case Yytoken.TYPE_VALUE :
                                JsonArray val = (JsonArray) valueStack.getFirst();
                                val.add(token.value);
                                break;
                            case Yytoken.TYPE_RIGHT_SQUARE :
                                if (valueStack.size() > 1) {
                                    statusStack.removeFirst();
                                    valueStack.removeFirst();
                                    status = peekStatus(statusStack);
                                } else {
                                    status = S_IN_FINISHED_VALUE;
                                }
                                break;
                            case Yytoken.TYPE_LEFT_BRACE :
                                val = (JsonArray) valueStack.getFirst();
                                JsonObject newObject = createObjectContainer();
                                val.add(newObject);
                                status = S_IN_OBJECT;
                                statusStack.addFirst(new Integer(status));
                                valueStack.addFirst(newObject);
                                break;
                            case Yytoken.TYPE_LEFT_SQUARE :
                                val = (JsonArray) valueStack.getFirst();
                                JsonArray newArray = createArrayContainer();
                                val.add(newArray);
                                status = S_IN_ARRAY;
                                statusStack.addFirst(new Integer(status));
                                valueStack.addFirst(newArray);
                                break;
                            default :
                                status = S_IN_ERROR;
                        }//inner switch
                        break;
                    case S_IN_ERROR :
                        throw new ParseException(lexer.getLine(), lexer.getColumn(),
                                ParseException.ERROR_UNEXPECTED_TOKEN, token);
                }//switch
                if (status == S_IN_ERROR) { throw new ParseException(lexer.getLine(), lexer.getColumn(),
                        ParseException.ERROR_UNEXPECTED_TOKEN, token); }
            } while (token.type != Yytoken.TYPE_EOF);
        } catch (IOException ie) {
            throw ie;
        }
        throw new ParseException(lexer.getLine(), lexer.getColumn(), ParseException.ERROR_UNEXPECTED_TOKEN, token);
    }

    public void parse(Reader in, ContentHandler contentHandler) throws IOException, ParseException {
        parse(in, contentHandler, false);
    }

    /**
     * Stream processing of JSON text.
     * 
     * @see ContentHandler
     * 
     * @param in
     * @param contentHandler
     * @param isResume - Indicates if it continues previous parsing operation.
     *                   If set to true, resume parsing the old stream, and parameter 'in' will be ignored. 
     *                   If this method is called for the first time in this instance, isResume will be ignored.
     * 
     * @throws IOException
     * @throws ParseException
     */
    public void parse(Reader in, ContentHandler contentHandler, boolean isResume) throws IOException,
            ParseException {
        if (!isResume) {
            reset(in);
            handlerStatusStack = new LinkedList<Integer>();
        } else {
            if (handlerStatusStack == null) {
                isResume = false;
                reset(in);
                handlerStatusStack = new LinkedList<Integer>();
            }
        }
        LinkedList<Integer> statusStack = handlerStatusStack;
        try {
            do {
                switch (status) {
                    case S_INIT :
                        contentHandler.startJSON();
                        nextToken();
                        switch (token.type) {
                            case Yytoken.TYPE_VALUE :
                                status = S_IN_FINISHED_VALUE;
                                statusStack.addFirst(new Integer(status));
                                if (!contentHandler.primitive(token.value)) return;
                                break;
                            case Yytoken.TYPE_LEFT_BRACE :
                                status = S_IN_OBJECT;
                                statusStack.addFirst(new Integer(status));
                                if (!contentHandler.startObject()) return;
                                break;
                            case Yytoken.TYPE_LEFT_SQUARE :
                                status = S_IN_ARRAY;
                                statusStack.addFirst(new Integer(status));
                                if (!contentHandler.startArray()) return;
                                break;
                            default :
                                status = S_IN_ERROR;
                        }//inner switch
                        break;
                    case S_IN_FINISHED_VALUE :
                        nextToken();
                        if (token.type == Yytoken.TYPE_EOF) {
                            contentHandler.endJSON();
                            status = S_END;
                            return;
                        } else {
                            status = S_IN_ERROR;
                            throw new ParseException(lexer.getLine(), lexer.getColumn(),
                                    ParseException.ERROR_UNEXPECTED_TOKEN, token);
                        }
                    case S_IN_OBJECT :
                        nextToken();
                        switch (token.type) {
                            case Yytoken.TYPE_COMMA :
                                break;
                            case Yytoken.TYPE_VALUE :
                                if (token.value instanceof JsonString) {
                                    JsonString key = (JsonString) token.value;
                                    status = S_PASSED_PAIR_KEY;
                                    statusStack.addFirst(new Integer(status));
                                    if (!contentHandler.startObjectEntry(key.stringValue())) return;
                                } else {
                                    status = S_IN_ERROR;
                                }
                                break;
                            case Yytoken.TYPE_RIGHT_BRACE :
                                if (statusStack.size() > 1) {
                                    statusStack.removeFirst();
                                    status = peekStatus(statusStack);
                                } else {
                                    status = S_IN_FINISHED_VALUE;
                                }
                                if (!contentHandler.endObject()) return;
                                break;
                            default :
                                status = S_IN_ERROR;
                                break;
                        }//inner switch
                        break;
                    case S_PASSED_PAIR_KEY :
                        nextToken();
                        switch (token.type) {
                            case Yytoken.TYPE_COLON :
                                break;
                            case Yytoken.TYPE_VALUE :
                                statusStack.removeFirst();
                                status = peekStatus(statusStack);
                                if (!contentHandler.primitive(token.value)) return;
                                if (!contentHandler.endObjectEntry()) return;
                                break;
                            case Yytoken.TYPE_LEFT_SQUARE :
                                statusStack.removeFirst();
                                statusStack.addFirst(new Integer(S_IN_PAIR_VALUE));
                                status = S_IN_ARRAY;
                                statusStack.addFirst(new Integer(status));
                                if (!contentHandler.startArray()) return;
                                break;
                            case Yytoken.TYPE_LEFT_BRACE :
                                statusStack.removeFirst();
                                statusStack.addFirst(new Integer(S_IN_PAIR_VALUE));
                                status = S_IN_OBJECT;
                                statusStack.addFirst(new Integer(status));
                                if (!contentHandler.startObject()) return;
                                break;
                            default :
                                status = S_IN_ERROR;
                        }
                        break;
                    case S_IN_PAIR_VALUE :
                        /*
                         * S_IN_PAIR_VALUE is just a marker to indicate the end of an object entry, it doesn't proccess any token,
                         * therefore delay consuming token until next round.
                         */
                        statusStack.removeFirst();
                        status = peekStatus(statusStack);
                        if (!contentHandler.endObjectEntry()) return;
                        break;
                    case S_IN_ARRAY :
                        nextToken();
                        switch (token.type) {
                            case Yytoken.TYPE_COMMA :
                                break;
                            case Yytoken.TYPE_VALUE :
                                if (!contentHandler.primitive(token.value)) return;
                                break;
                            case Yytoken.TYPE_RIGHT_SQUARE :
                                if (statusStack.size() > 1) {
                                    statusStack.removeFirst();
                                    status = peekStatus(statusStack);
                                } else {
                                    status = S_IN_FINISHED_VALUE;
                                }
                                if (!contentHandler.endArray()) return;
                                break;
                            case Yytoken.TYPE_LEFT_BRACE :
                                status = S_IN_OBJECT;
                                statusStack.addFirst(new Integer(status));
                                if (!contentHandler.startObject()) return;
                                break;
                            case Yytoken.TYPE_LEFT_SQUARE :
                                status = S_IN_ARRAY;
                                statusStack.addFirst(new Integer(status));
                                if (!contentHandler.startArray()) return;
                                break;
                            default :
                                status = S_IN_ERROR;
                        }//inner switch
                        break;
                    case S_END :
                        return;
                    case S_IN_ERROR :
                        throw new ParseException(lexer.getLine(), lexer.getColumn(),
                                ParseException.ERROR_UNEXPECTED_TOKEN, token);
                }//switch
                if (status == S_IN_ERROR) { throw new ParseException(lexer.getLine(), lexer.getColumn(),
                        ParseException.ERROR_UNEXPECTED_TOKEN, token); }
            } while (token.type != Yytoken.TYPE_EOF);
        } catch (IOException ie) {
            status = S_IN_ERROR;
            throw ie;
        } catch (ParseException pe) {
            status = S_IN_ERROR;
            throw pe;
        } catch (RuntimeException re) {
            status = S_IN_ERROR;
            throw re;
        } catch (Error e) {
            status = S_IN_ERROR;
            throw e;
        }
        status = S_IN_ERROR;
        throw new ParseException(lexer.getLine(), lexer.getColumn(), ParseException.ERROR_UNEXPECTED_TOKEN, token);
    }

    public JsonValue parse(String s) throws ParseException {
        StringReader in = new StringReader(s);
        try {
            return parse(in);
        } catch (IOException ie) {
            /*
             * Actually it will never happen.
             */
            throw new ParseException(-1, -1, ParseException.ERROR_UNEXPECTED_EXCEPTION, ie);
        }
    }

    public void parse(String s, ContentHandler contentHandler) throws ParseException {
        parse(s, contentHandler, false);
    }

    public void parse(String s, ContentHandler contentHandler, boolean isResume) throws ParseException {
        StringReader in = new StringReader(s);
        try {
            parse(in, contentHandler, isResume);
        } catch (IOException ie) {
            /*
             * Actually it will never happen.
             */
            throw new ParseException(-1, -1, ParseException.ERROR_UNEXPECTED_EXCEPTION, ie);
        }
    }

    private int peekStatus(LinkedList<Integer> statusStack) {
        if (statusStack.size() == 0) return -1;
        Integer status = statusStack.getFirst();
        return status.intValue();
    }

    /**
     *  Reset the parser to the initial state without resetting the underlying reader.
     *
     */
    public void reset() {
        token = null;
        status = S_INIT;
        handlerStatusStack = null;
    }

    /**
     * Reset the parser to the initial state with a new character reader.
     * 
     * @param in - The new character reader.
     * @throws IOException
     * @throws ParseException
     */
    public void reset(Reader in) {
        lexer.yyreset(in);
        reset();
    }
}
