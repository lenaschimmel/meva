package org.itemscript.template.scanner;

import org.itemscript.core.Params;
import org.itemscript.core.exceptions.ItemscriptError;
import org.itemscript.core.util.GeneralUtil;
import org.itemscript.template.Template;

public class Scanner {
	private final String string;
	private static final int OUTSIDE_TAG = 0;
	private static final int INSIDE_TAG = 1;
	private int state;
	private int pos;
	private int length;
	private Token peeked;

	public Scanner(String string) {
		if (string == null) {
			throw ItemscriptError.internalError(this,
					"constructor.string.was.null");
		}
		this.string = string;
		state = OUTSIDE_TAG;
		pos = 0;
		length = string.length();
	}

	public String errorText(Token token) {
		if (token.beginIndex() == -1) {
			return token + "";
		}
		return "beginIndex=" + token.beginIndex() + " endIndex="
				+ token.endIndex() + " text="
				+ string.substring(token.beginIndex(), token.endIndex());
	}

	/**
	 * Get the next Token from this string; returns null at the end of the
	 * string.
	 * 
	 * @return The next Token.
	 */
	public Token next() {
		// If we had peeked at the next token, return that instead, and clear
		// the peeked.
		if (peeked != null) {
			Token next = peeked;
			peeked = null;
			return next;
		} else {
			return reallyNext();
		}
	}

	/**
	 * Look at the next token, but leave it there for when next() is called.
	 * 
	 * @return
	 */
	public Token peek() {
		peeked = next();
		return peeked;
	}

	private Token reallyNext() {
		// If at the end of the string, return null.
		if (pos >= length) {
			return null;
		}
		// If we're outside a tag, look for an opening brace.
		if (state == OUTSIDE_TAG) {
			int openBrace = string.indexOf(Template.OPEN_TAG_CHAR, pos);
			// If we don't find an open brace, the remainder of the string is
			// one text section.
			if (openBrace == -1) {
				TextToken token = new TextToken(pos, string.length() - 1,
						string.substring(pos));
				pos = string.length();
				return token;
			} else
			// If we find an open brace at the current position, return an open
			// tag token.
			if (pos == openBrace) {
				++pos;
				state = INSIDE_TAG;
				return OpenTagToken.INSTANCE;
			} else {
				// If we find an open brace after some text, return that text
				// token, set pos to at that open brace.
				TextToken token = new TextToken(pos, openBrace - 1, string
						.substring(pos, openBrace));
				pos = openBrace;
				return token;
			}
		} else if (state == INSIDE_TAG) {
			skipWhitespace();
			if (pos >= length) {
				throw ItemscriptError.internalError(this,
						"unclosed.tag.at.end.of.string");
			}
			// Look for one of: an expression token, an opening paran, a comma,
			// a closing paran, or a closing brace, or an opening quote.
			char c = string.charAt(pos);
			if (c == Template.CLOSE_TAG_CHAR) {
				++pos;
				state = OUTSIDE_TAG;
				return CloseTagToken.INSTANCE;
			} else if (c == Template.OPEN_ARG_CHAR) {
				++pos;
				return OpenArgToken.INSTANCE;
			} else if (c == Template.CLOSE_ARG_CHAR) {
				++pos;
				return CloseArgToken.INSTANCE;
			} else if (c == Template.COMMA_CHAR) {
				++pos;
				return CommaToken.INSTANCE;
			} else if (c == Template.OPEN_ARG_CHAR) {
				throw ItemscriptError.internalError(this,
						"next.unexpected.open.brace", pos + "");
			} else if (c == Template.QUOTE_CHAR) {
				String quotedString = "";
				int stringStart = pos;
				++pos;
				while (true) {
					// Look for the next quote character.
					int nextQuote = string.indexOf(Template.QUOTE_CHAR, pos);
					// If it doesn't terminate, or terminates at end-of-string,
					// that's an error.
					if (nextQuote == -1) {
						throw ItemscriptError.internalError(this,
								"next.unterminated.quoted.string", string
										.substring(stringStart));
					}
					if (nextQuote == length) {
						throw ItemscriptError.internalError(this,
								"next.quoted.string.ends.at.end.of.input",
								string.substring(stringStart));
					}
					// We know there will be a character after the next quote,
					// even if it's a tag close at the end of the string.
					char charAfter = string.charAt(nextQuote + 1);
					if (charAfter == Template.QUOTE_CHAR) {
						// If the character after the next quote is also a
						// quote, it's an escaped quote; add the string between
						// here and the
						// quote, and the quote itself, and keep going.
						quotedString += string.substring(pos, nextQuote) + "'";
						// Set position to after the 2 quotes.
						pos = nextQuote + 2;
					} else {
						// If it's anything else, that's the end of the string,
						// so add it and return it.
						quotedString += string.substring(pos, nextQuote);
						pos = nextQuote + 1;
						return new QuotedStringToken(stringStart, nextQuote,
								quotedString);
					}
				}
			} else {
				int start = pos;
				// Anything else, just look for the next whitespace, open or
				// close paran, quote, comma, or close tag.
				while (!GeneralUtil.isWhitespace(c)
						&& c != Template.CLOSE_TAG_CHAR
						&& c != Template.COMMA_CHAR
						&& c != Template.OPEN_ARG_CHAR
						&& c != Template.CLOSE_ARG_CHAR
						&& c != Template.CLOSE_ARG_CHAR
						&& c != Template.QUOTE_CHAR) {
					++pos;
					if (pos == length) {
						throw ItemscriptError.internalError(this,
								"next.unexpected.end.of.string", new Params()
										.p("tag", string.substring(start)));
					}
					c = string.charAt(pos);
				}
				// pos is now set to either the non-expression-token character
				// or at end of string.
				return new ExpressionToken(start, pos - 1, string.substring(
						start, pos));
			}
		} else {
			throw new ItemscriptError("Should never get here");
		}
	}

	public String text() {
		return string;
	}

	private void skipWhitespace() {
		// Skip any leading whitespace.
		char c = string.charAt(pos);
		while (pos < (length) && GeneralUtil.isWhitespace(c)) {
			++pos;
			c = string.charAt(pos);
		}
	}
}