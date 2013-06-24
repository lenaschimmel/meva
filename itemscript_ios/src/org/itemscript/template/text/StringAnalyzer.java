/*
 * Copyright � 2010, Data Base Architects, Inc. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice,
 *       this list of conditions and the following disclaimer in the documentation
 *       and/or other materials provided with the distribution.
 *     * Neither the names of Kalinda Software, DBA Software, Data Base Architects, Itemscript
 *       nor the names of its contributors may be used to endorse or promote products derived
 *       from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
 * SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * 
 * Author: Jacob Davies
 */

package org.itemscript.template.text;

import java.util.ArrayList;
import java.util.List;

import org.itemscript.core.HasSystem;
import org.itemscript.core.JsonSystem;
import org.itemscript.core.Params;
import org.itemscript.core.exceptions.ItemscriptError;
import org.itemscript.core.url.Url;
import org.itemscript.template.Analyzer;
import org.itemscript.template.Template;
import org.itemscript.template.expression.ConstantFunction;
import org.itemscript.template.expression.Expression;
import org.itemscript.template.expression.FieldFunction;
import org.itemscript.template.expression.Function;
import org.itemscript.template.expression.FunctionFoundry;
import org.itemscript.template.expression.LiteralFunction;
import org.itemscript.template.expression.LoadFunction;
import org.itemscript.template.expression.NumericLiteralFunction;
import org.itemscript.template.scanner.ExpressionToken;
import org.itemscript.template.scanner.Scanner;
import org.itemscript.template.scanner.Token;

/**
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 */
public class StringAnalyzer implements HasSystem {
    private final JsonSystem system;
    private final Scanner scanner;
    private final Analyzer analyzer;
    private final String string;

    public StringAnalyzer(JsonSystem system, Analyzer analyzer, String string) {
        this.system = system;
        this.analyzer = analyzer;
        this.string = string;
        this.scanner = new Scanner(string);
    }

    public TextTemplate analyze() {
        Token token = scanner.next();
        List<TextElement> elements = new ArrayList<TextElement>();
        while (token != null) {
            // Expect either text or an open tag.
            if (token.isTextToken()) {
                elements.add(new Text(system(), token.asTextToken()
                        .text()));
            } else if (token.isOpenTagToken()) {
                elements.add(analyzeTag());
            } else {
                throw unexpectedTokenError("template", token);
            }
            token = scanner.next();
        }
        return new TextTemplate(system(), elements);
    }

    private Segment analyzeDirective() {
        Token next = scanner.next();
        if (!next.isExpressionToken()) { throw ItemscriptError.internalError(this,
                "analyzeDirective.unexpected.token", next + ""); }
        ExpressionToken expressionToken = next.asExpressionToken();
        // Expect either ".if", ".foreach", or ".section".
        if (expressionToken.isIf()) {
            return analyzeIf(expressionToken);
        } else if (expressionToken.isForeach()) {
            return analyzeForeach(expressionToken);
        } else if (expressionToken.isSection()) {
            return analyzeSection(expressionToken);
        } else {
            throw unexpectedTokenError("directive", next);
        }
    }

    private Expression analyzeExpression() {
        List<Function> contents = new ArrayList<Function>();
        Token peek = scanner.peek();
        Token start = peek;
        while (peek != null) {
            // Expect expression token, a quoted string,, an end of args, a comma, or an end of tag.
            if (peek.isExpressionToken()) {
                // Add a new function.
                contents.add(analyzeExpressionToken());
            } else if (peek.isQuotedStringToken()) {
                // Consume & add a new literal.
                scanner.next();
                contents.add(new LiteralFunction(system(), peek.asQuotedStringToken()
                        .string()));
            } else if (peek.isCloseTagToken() || peek.isCloseParanToken() || peek.isCommaToken()) {
                return new Expression(system(), contents);
            } else {
                throw unexpectedTokenError("expression", start, peek);
            }
            peek = scanner.peek();
        }
        // If we didn't complete the tag, throw an error.
        throw unexpectedEndOfString("expression", start);
    }

    private Expression analyzeExpressionTag() {
        // Expect an expression followed by a tag close.
        Expression expression = analyzeExpression();
        Token endTag = scanner.next();
        if (endTag.isCloseTagToken()) { return expression; }
        throw unexpectedTokenError("expression", endTag);
    }

    private Function analyzeExpressionToken() {
        Token peek = scanner.peek();
        if (!peek.isExpressionToken()) { throw ItemscriptError.internalError(this,
                "analyzeExpressionToken.expected.expression.token", peek + ""); }
        ExpressionToken peekExpressionToken = peek.asExpressionToken();
        String content = peekExpressionToken.content();
        final char firstChar = content.charAt(0);
        if (firstChar == Template.LOAD_CHAR) {
            // If it starts with "@", the rest is a URL to load.
            scanner.next();
            return new LoadFunction(system(), content.substring(1));
        } else if (firstChar == Template.LITERAL_CHAR) {
            // If it starts with "&" the rest is a URL-encoded string literal.
            scanner.next();
            return new LiteralFunction(system(), Url.decode(content.substring(1)));
        } else if (firstChar == Template.CONSTANT_CHAR) {
            // If it starts with "*" it's a constant reference.
            scanner.next();
            return new ConstantFunction(system(), Url.decode(content.substring(1)));
        } else if (firstChar == Template.FIELD_CHAR) {
            // If it starts with ":" it's a field reference.
            scanner.next();
            return new FieldFunction(system(), content.substring(1));
        } else if (firstChar == Template.DIRECTIVE_CHAR) {
            // It's an error to have a "." at the beginning of any token in an expression except the first.
            throw ItemscriptError.internalError(this, "analyzeExpressionToken.unexpected.directive",
                    peekExpressionToken + "");
        } else if (Character.isLetter(firstChar)) {
            // If it starts with a letter, it's a function.
            return analyzeFunction();
        } else if (Character.isDigit(firstChar) || firstChar == '-') {
            // If it starts with a number, it's a numeric literal.
            return analyzeNumber();
        } else {
            System.err.println("text: " + system().createString(string));
            // Anything else is an error.
            throw ItemscriptError.internalError(this, "analyzeExpressionToken.unexpected.token", new Params().p(
                    "expressionToken", peekExpressionToken + "")
                    .p("text", system().createString(scanner.text()))
                    + "");
        }
    }

    private Foreach analyzeForeach(ExpressionToken start) {
        Token peek = scanner.peek();
        if (peek.isCloseTagToken()) { throw unexpectedTokenError("foreach", start, peek); }
        // Expect an expression tag.
        Expression valueExpression = analyzeExpressionTag();
        return analyzeForeachContents(valueExpression);
    }

    private Foreach analyzeForeachContents(Expression valueExpression) {
        // Expect text, tags, or directives; if a tag is encountered, check if it's {.join} or {.end}.
        List<TextElement> contents = new ArrayList<TextElement>();
        List<TextElement> join = null;
        List<TextElement> currentContents = contents;
        Token token = scanner.next();
        Token start = token;
        while (token != null) {
            if (token.isTextToken()) {
                currentContents.add(new Text(system(), token.asTextToken()
                        .text()));
            } else if (token.isOpenTagToken()) {
                // Take a look at the first token in it; if it's .join or .end act appropriately.
                Token peek = scanner.peek();
                if (peek.isExpressionToken()) {
                    ExpressionToken peekExpressionToken = peek.asExpressionToken();
                    if (peekExpressionToken.isJoin()) {
                        join = new ArrayList<TextElement>();
                        currentContents = join;
                        consumeShortDirective();
                    } else if (peekExpressionToken.isEnd()) {
                        consumeShortDirective();
                        return new Foreach(system(), valueExpression, new TextTemplate(system(), contents),
                                join != null ? new TextTemplate(system(), join) : null);
                    } else {
                        // If it's not .join or .end, analyze as any other tag.
                        currentContents.add(analyzeTag());
                    }
                } else {
                    throw unexpectedTokenError("foreach.contents", start, token);
                }
            } else {
                throw unexpectedTokenError("foreach.contents", start, token);
            }
            token = scanner.next();
        }
        throw unexpectedEndOfString("foreach.contents", start);
    }

    private Function analyzeFunction() {
        String functionName = scanner.next()
                .asExpressionToken()
                .content();
        Token peek = scanner.peek();
        List<Expression> args = null;
        if (peek.isOpenParanToken()) {
            scanner.next();
            args = analyzeFunctionArgs();
        }
        return FunctionFoundry.create(system, functionName, args);
    }

    private List<Expression> analyzeFunctionArgs() {
        List<Expression> args = new ArrayList<Expression>();
        Token peek = scanner.peek();
        Token start = peek;
        while (peek != null) {
            if (peek.isExpressionToken() || peek.isQuotedStringToken()) {
                // Add the entire following expression.
                args.add(analyzeExpression());
            } else if (peek.isCommaToken()) {
                // Eat the comma, continue.
                scanner.next();
            } else if (peek.isCloseParanToken()) {
                // Eat the closing paran, return.
                scanner.next();
                return args;
            } else {
                // Anything else is an error.
                throw unexpectedTokenError("function.args", start, peek);
            }
            peek = scanner.peek();
        }
        throw unexpectedEndOfString("function.args", start);
    }

    private If analyzeIf(ExpressionToken start) {
        Token peek = scanner.peek();
        if (peek.isCloseTagToken()) { throw unexpectedTokenError("if", start, peek); }
        // Expect an expression.
        Expression conditionalExpression = analyzeExpressionTag();
        return analyzeIfContents(conditionalExpression);
    }

    private If analyzeIfContents(Expression conditionalExpression) {
        // Expect text, tags, or directives; if a tag is encountered, check if it's {.else} or {.end}.
        List<TextElement> trueContents = new ArrayList<TextElement>();
        List<TextElement> elseContents = null;
        List<TextElement> currentContents = trueContents;
        Token token = scanner.next();
        Token start = token;
        while (token != null) {
            if (token.isTextToken()) {
                currentContents.add(new Text(system(), token.asTextToken()
                        .text()));
            } else if (token.isOpenTagToken()) {
                // Take a look at the first token in it; if it's .else or .end act appropriately.
                Token peek = scanner.peek();
                if (peek.isExpressionToken()) {
                    ExpressionToken peekExpressionToken = peek.asExpressionToken();
                    if (peekExpressionToken.isElse()) {
                        elseContents = new ArrayList<TextElement>();
                        currentContents = elseContents;
                        consumeShortDirective();
                    } else if (peekExpressionToken.isEnd()) {
                        consumeShortDirective();
                        return new If(system(), conditionalExpression, new TextTemplate(system(), trueContents),
                                elseContents != null ? new TextTemplate(system(), elseContents) : null);
                    } else {
                        // If it's not .else or .end, analyze as any other tag.
                        currentContents.add(analyzeTag());
                    }
                } else {
                    throw unexpectedTokenError("if.contents", start, token);
                }
            } else {
                throw unexpectedTokenError("if.contents", start, token);
            }
            token = scanner.next();
        }
        throw unexpectedEndOfString("if.contents", start);
    }

    private NumericLiteralFunction analyzeNumber() {
        Token next = scanner.next();
        String content = next.asExpressionToken()
                .content();
        double number = Double.parseDouble(content);
        return new NumericLiteralFunction(system(), number);
    }

    private Section analyzeSection(ExpressionToken start) {
        Token peek = scanner.peek();
        if (peek.isCloseTagToken()) { throw unexpectedTokenError("section", start, peek); }
        // Expect an expression and end of tag.
        Expression valueExpression = analyzeExpressionTag();
        return analyzeSectionContents(valueExpression);
    }

    private Section analyzeSectionContents(Expression valueExpression) {
        // Expect text, tags, or directives; if a tag is encountered, check if it's {.or} or {.end}.
        List<TextElement> contents = new ArrayList<TextElement>();
        List<TextElement> orContents = null;
        List<TextElement> currentContents = contents;
        Token token = scanner.next();
        while (token != null) {
            if (token.isTextToken()) {
                currentContents.add(new Text(system(), token.asTextToken()
                        .text()));
            } else if (token.isOpenTagToken()) {
                // Take a look at the first token in it; if it's .or or .end act appropriately.
                Token peek = scanner.peek();
                if (peek.isExpressionToken()) {
                    ExpressionToken peekExpressionToken = peek.asExpressionToken();
                    if (peekExpressionToken.isOr()) {
                        orContents = new ArrayList<TextElement>();
                        currentContents = orContents;
                        consumeShortDirective();
                    } else if (peekExpressionToken.isEnd()) {
                        consumeShortDirective();
                        return new Section(system(), valueExpression, new TextTemplate(system(), contents),
                                orContents != null ? new TextTemplate(system(), orContents) : null);
                    } else {
                        // If it's not .or or .end, analyze as any other tag.
                        currentContents.add(analyzeTag());
                    }
                } else {
                    throw ItemscriptError.internalError(this, "analyzeSection.unexpected.token", token + "");
                }
            } else {
                // Anything else is an error.
                throw ItemscriptError.internalError(this, "analyzeSection.unexpected.token", token + "");
            }
            token = scanner.next();
        }
        throw ItemscriptError.internalError(this, "analyzeSection.unexpected.end.of.string");
    }

    private TextElement analyzeTag() {
        Token peek = scanner.peek();
        // Empty tags are an error.
        if (peek.isCloseTagToken()) { throw ItemscriptError.internalError(this, "analyzeTag.empty.tag", peek + ""); }
        // If the first token in a tag is a directive, go to analyzing a directive.
        if (peek.isExpressionToken()) {
            if (peek.asExpressionToken()
                    .content()
                    .startsWith(Template.DIRECTIVE_CHAR + "")) { return analyzeDirective(); }
            if (peek.asExpressionToken()
                    .content()
                    .startsWith(Template.COMMENT_CHAR + "")) {
                consumeComment();
                return new Text(system, "");
            }
        }
        // Otherwise it's just an expression.
        return analyzeExpressionTag();
    }

    private void consumeComment() {
        // Expect any old junk until we get to a close tag.
        Token token = scanner.next();
        Token start = token;
        while (token != null) {
            if (token.isCloseTagToken()) { return; }
            token = scanner.next();
        }
        throw unexpectedEndOfString("comment", start);
    }

    private void consumeShortDirective() {
        // Expect a short directive followed by a close tag; anything else is an error.
        Token next = scanner.next();
        if (next.isExpressionToken() && next.asExpressionToken()
                .isDirective()) {
            Token endTag = scanner.next();
            if (endTag.isCloseTagToken()) { return; }
            throw ItemscriptError.internalError(this, "consumeShortDirective.expected.end.of.tag", endTag + "");
        }
        throw ItemscriptError.internalError(this, "consumeShortDirective.unexpected.token", next + "");
    }

    public JsonSystem system() {
        return system;
    }

    private ItemscriptError unexpectedEndOfString(String area, Token startToken) {
        return ItemscriptError.internalError(this, "unexpectedEndOfString.in." + area, new Params().p(
                "startToken", scanner.errorText(startToken)));
    }

    private ItemscriptError unexpectedTokenError(String area, Token unexpectedToken) {
        return ItemscriptError.internalError(this, "unexpectedTokenError.in." + area, new Params().p(
                "unexpectedToken", scanner.errorText(unexpectedToken)));
    }

    private ItemscriptError unexpectedTokenError(String area, Token startToken, Token unexpectedToken) {
        return ItemscriptError.internalError(this, "unexpectedTokenError.in." + area, new Params().p("startToken",
                scanner.errorText(startToken))
                .p("unexpectedToken", scanner.errorText(unexpectedToken)));
    }
}