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

package org.itemscript.template;

import org.itemscript.core.HasSystem;
import org.itemscript.core.JsonSystem;
import org.itemscript.core.exceptions.ItemscriptError;
import org.itemscript.core.util.StaticJsonUtil;
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonString;
import org.itemscript.core.values.JsonValue;

/**
 * A compiled template that can produce either text or a JSON value.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 */
public class Template implements HasSystem, Element {
    public static final String TEMPLATE_URL = "mem:/itemscript/template";
    public static final String TEMPLATE_CACHE_URL = TEMPLATE_URL + "/cache";
    public static final char COMMA_CHAR = ',';
    public static final char OPEN_TAG_CHAR = '{';
    public static final char CLOSE_TAG_CHAR = '}';
    public static final char OPEN_ARG_CHAR = '(';
    public static final char CLOSE_ARG_CHAR = ')';
    public static final char QUOTE_CHAR = '\'';
    public static final char COMMENT_CHAR = '#';
    public static final char LOAD_CHAR = '@';
    public static final char FIELD_CHAR = ':';
    public static final char LITERAL_CHAR = '&';
    public static final char FUNCTION_CHAR = '!';
    public static final char DIRECTIVE_CHAR = '.';

    public static String coerceToString(JsonValue value) {
        if (value == null || value.isNull()) {
            return "";
        } else if (value.isString()) {
            return value.stringValue();
        } else if (value.isNumber()) {
            return value.toJsonString();
        } else if (value.isBoolean()) {
            return value.toJsonString();
        } else {
            throw new ItemscriptError(
                    "error.itemscript.Template.coerceToString.value.could.not.be.converted.to.a.string",
                    value.toCompactJsonString());
        }
    }

    /**
     * Create a new Template from the given JsonValue. If the value is a JsonString the string value will be used
     * as a text template. If the value is a JsonArray whose values are all JsonStrings, each will be treated as a line
     * in the template. If the value is a JsonObject, it will be treated as an object template. If it is a JsonNumber,
     * JsonBoolean, or JsonNull, it will be treated as a literal value that the resulting template will return.
     * 
     * @param system The associated JsonSystem.
     * @param value The template value.
     * @return The new Template
     */
    public static Template create(JsonSystem system, JsonValue value) {
        if (value.isArray()) {
            value = StaticJsonUtil.joinArrayOfStrings(value.asArray());
        }
        if (value.isString()) {
            JsonObject cache = getCache(system);
            String text = value.stringValue();
            if (cache.containsKey(text)) {
                return (Template) cache.getNative(text);
            } else {
                Template template = new Template(system, value);
                cache.putNative(text, template);
                return template;
            }
        } else {
            return new Template(system, value);
        }
    }

    /**
     * Create a new text Template from the given string.
     * 
     * @param system The associated JsonSystem.
     * @param text The template text.
     * @return The new Template.
     */
    public static Template create(JsonSystem system, String text) {
        JsonObject cache = getCache(system);
        if (cache.containsKey(text)) {
            return (Template) cache.getNative(text);
        } else {
            Template template = new Template(system, system.createString(text));
            cache.putNative(text, template);
            return template;
        }
    }

    private static JsonObject getCache(JsonSystem system) {
        JsonObject cache = system.getObject(Template.TEMPLATE_CACHE_URL);
        if (cache == null) {
            cache = system.createObject(Template.TEMPLATE_CACHE_URL)
                    .value()
                    .asObject();
        }
        return cache;
    }

    private final JsonSystem system;
    private final Element element;
    private final String text;
    private final JsonString textValue;
    public static final char CONSTANT_CHAR = '*';

    private Template(JsonSystem system, JsonValue value) {
        this.system = system;
        // If the value is a single string with no { characters in it, we know it's a static string.
        if (value.isString() && value.stringValue()
                .indexOf(Template.OPEN_TAG_CHAR) == -1) {
            this.element = null;
            this.text = value.stringValue();
            this.textValue = system().createString(text);
        } else {
            this.element = new Analyzer(system).analyze(value);
            this.text = null;
            this.textValue = null;
        }
    }

    @Override
    public JsonValue interpret(TemplateExec templateExec, JsonValue context) {
        if (element != null) {
            return element.interpret(templateExec, context);
        } else {
            return textValue;
        }
    }

    /**
     * Interpret this template using the given context, returning the result as a TemplateResult. Use this when the
     * value of a template may not be a string or when access is required to the side-effects of the template execution.
     * <p>
     * If all you need is a string result, use {@link #interpretToString(JsonValue)}. If all you need is the JsonValue
     * result, use {@link #interpretToValue(JsonValue)}.
     * 
     * @param context The context to execute the template in.
     * @return The result of executing the template.
     */
    public TemplateResult interpretToResult(JsonValue context) {
        TemplateExec templateExec = new TemplateExec(system());
        JsonValue value = interpret(templateExec, context);
        return new TemplateResult(value, templateExec.accumulator()
                .lists());
    }

    /**
     * Interpret this template using the given context, returning the result as a string.
     * 
     * @param context The context to interpret the template in.
     * @return The result of interpreting the template, as a string.
     */
    public String interpretToString(JsonValue context) {
        if (element != null) {
            return coerceToString(interpretToValue(context));
        } else {
            return text;
        }
    }

    /**
     * Interpret this template using the given context, returning the result as a JsonValue.
     * 
     * @param context The context to interpret the template in.
     * @return The result of interpreting the template.
     */
    public JsonValue interpretToValue(JsonValue context) {
        if (element != null) {
            return interpretToResult(context).value();
        } else {
            return textValue;
        }
    }

    @Override
    public JsonSystem system() {
        return system;
    }

    @Override
    public String toString() {
        return "[Template element=" + element + "]";
    }
    
    /**
     * Sets the user-friendly error message retrieved from the returned errorObject after it's been
     * validated. Only deals with error messages seen by the user at runtime.
     * 
     * @param returnedObject
     * @return the error message if the validate failed, a null string otherwise
     */
    public static JsonObject setErrorMessage(JsonObject returnedObject) {
    	if (returnedObject.get("valid").booleanValue() == false) {
    		String value = returnedObject.get("value").stringValue();
			String input = returnedObject.get("input").stringValue();
			String specified = returnedObject.get("specified").stringValue();
        	String key = returnedObject.get("key").stringValue();
    		String errorMessage = returnedObject.get("errorMessage").stringValue();    	
    		int begin = errorMessage.indexOf("value");
    		if (begin >= 0) {
	    		String error = errorMessage.substring(begin);
	    		
	    		//General Type errors
	    		if (error.equals("value.was.null")) {
	    			returnedObject.p("message", "Value cannot be null.");
	    		}
	    		if (error.equals("value.was.not.null")) {
	    			returnedObject.p("message", "Value '" + value + "' was not null.");
	    		}
	    		if (error.equals("value.was.not.object")) {
	    			returnedObject.p("message", "Value '" + value + "' was not an object.");
	    		}
	    		if (error.equals("value.was.not.string")) {
	    			returnedObject.p("message", "Value '" + value + "' was not a string.");
	    		}
	    		if (error.equals("value.was.not.array")) {
	    			returnedObject.p("message", "Value '" + value + "' was not an array.");
	    		}
	    		if (error.equals("value.was.not.boolean")) {
	        		returnedObject.p("message", "Value '" + value + "' was not a boolean.");
	    		}
	    		if (error.equals("value.was.not.number")) {
	    			returnedObject.p("message", "Value '" + value + "' was not a number.");
	    		}
	    		if (error.equals("value.was.not.proper.decimal")) {
	    			returnedObject.p("message", "Value '" + value + "' was not a decimal.");
	    		}
	    		if (error.equals("value.could.not.be.parsed.into.long")) {
	    			returnedObject.p("message", "Value '" + value + "' was not a long.");
	    		}
	    		if (error.equals("value.had.fractional.digits")) {
	    			returnedObject.p("message", "Value '" + value + "' was not an integer.");
	    		}
	    		
	    		//parseFunction errors
	    		if (error.equals("value.asNumber.could.not.be.parsed.into.double")) {
	    			returnedObject.p("message", "Your value '" + value + "' does not represent a number.");
	    		}
	    		if (error.equals("value.asBoolean.was.not.true.or.false")) {
	    			returnedObject.p("message", "Your value '" + value + "' does not represent a boolean.");
	    		}
	    		
	    		//AnyType errors
	    		if (error.equals("value.was.not.of.specified.type")) {
	    			returnedObject.p("message", "Your value '" + value +
	    				"' was not one of the types specified.");
	    		}
	    			    		
	    		//ArrayType errors
	    		if (error.equals("value.array.is.the.wrong.size")) {
	    			returnedObject.p("message", "Your array has the wrong number of items." +
    					" Your size is " + input + "." +
    					" The correct size is " + specified + ".");
	    		}
	    		if (error.equals("value.array.size.is.bigger.than.max")) {
	    			returnedObject.p("message", "Your array has too many items." +
	    				" Your size is " + input + "." +
	    				" The maximum size is " + specified + ".");
	    		}
	    		if (error.equals("value.array.size.is.smaller.than.min")) {
	    			returnedObject.p("message", "Your array does not have enough items." +
	    				" Your size is " + input + "." +
	    				" The minimum size is " + specified + ".");
	    		}
	    		
	    		//BinaryType errors
	    		if (error.equals("value.could.not.be.parse.as.base.64")) {
	    			returnedObject.p("message", "Your value '" + value +
	    				"' could not be parsed as base64.");
	    		}
	    		if (error.equals("value.illegal.character.in.base.64.encoded.data")) {
	    			returnedObject.p("message", "Your value '" + value +
	    				"' contains an illegal character that cannot be parsed into base64.");
	    		}
	    		if (error.equals("value.has.too.many.bytes")) {
	    			returnedObject.p("message", "Your value has more than the max number of bytes allowed." +
	    				" Your byte size is " + input + "." +
	    				" The maximum byte size is " + specified + ".");
	    		}
	    		
	    		//BooleanType errors
	    		if (error.equals("value.does.not.equal.required.boolean.value")) {
	    			 returnedObject.p("message", "Your value does not match the required boolean value.");
	    		}
	    		
	    		//DecimalType errors
	    		if (error.equals("value.could.not.be.parsed.into.double")) {
	    			returnedObject.p("message", "Your value '" + value + "' could not be parsed into a Java Double.");
	    		}
	    		if (error.equals("value.has.wrong.number.of.fraction.digits")) {
	    			returnedObject.p("message", "Your value '" + value + "' has " + input + " fractional digits." +
	    				" The maximum number of fractional digits is " + specified + ".");
	    		}
	    		
	    		//ObjectType errors
	    		if (error.equals("value.extra.instance.keys.did.not.all.match.wildcard.type")) {
	    			returnedObject.p("message", "Some of your key-values failed to match the wildcard type.");
	    		}
	    		if (error.equals("value.missing.value.for.key")) {
	    			returnedObject.p("message", "Missing value for key: " + key + ".");
	    		}
	    		if (error.equals("value.instance.key.was.empty")) {
	    			returnedObject.p("message", "Cannot have an empty key in your instance object.");
	    		}
	    		
	    		//StringType errors
	    		if (error.equals("value.does.not.equal.equals")) {
	    			returnedObject.p("message", "Your value " + value + " does not equal the specified value.");
	    		}
	    		if (error.equals("value.does.not.equal.is.length")) {
	    			returnedObject.p("message", "Your value is the wrong length." +
	    				" Your value is " + input + " characters long." +
	    				" The correct length is " + specified + " characters.");
	    		}
	    		if (error.equals("value.longer.than.max.length")) {
	    			returnedObject.p("message", "Your value is too long." +
	    				" Your value is " + input + " characters long." +
	    				" The max length is " + specified + " characters.");
	    		}
	    		if (error.equals("value.shorter.than.min.length")) {
	    			returnedObject.p("message", "Your value is too short." +
	    				" Your value is " + input + " characters long." +
	    				" The minimum length is " + specified + " characters.");
	    		}
	    		if (error.equals("value.does.not.match.reg.ex.pattern")) {
	    			returnedObject.p("message", "Your value '" + value + "' did not match the regular expression pattern.");
	    		}
	    		if (error.equals("value.did.not.match.any.pattern")) {
	    			returnedObject.p("message", "Your value '" + value + "' did not match any of the patterns that were specified.");
	    		}
	    		
	    		//Numericality errors (applies to decimal, long, number, integer)
	    		if (error.equals("value.is.not.equal.to.equal.to")) {
	    			returnedObject.p("message", "Your number '" + value + "' is not equal to the specified value.");
	    		}
	    		if (error.equals("value.is.less.than.or.equal.to.min")) {
	    			returnedObject.p("message", "Your number must be greater than the minimum value provided." +
	    				" Your number is '" + input + "'." +
	    				" The minimum value is '" + specified + "'.");
	    		}
	    		if (error.equals("value.is.less.than.min")) {
	    			returnedObject.p("message", "Your number must be greater than or equal to the minimum value provided." +
	    				" Your number is '" + input + "'." +
	    				" The minimum value is '" + specified + "'.");
	    		}
	    		if (error.equals("value.is.greater.than.or.equal.to.max")) {
	    			returnedObject.p("message", "Your number must be less than the maximum value provided." +
	    				" Your number is '" + input + "'." +
	    				" The maximum value is '" + specified + "'.");
	    		}
	    		if (error.equals("value.is.greater.than.max")) {
	    			returnedObject.p("message", "Your number must be less than or equal to the maximum value provided." +
	    				" Your number is '" + input + "'." +
	    				" The maximum value is '" + specified + "'.");
	    		}
	    		if (error.equals("value.cannot.test.parity.value.is.not.an.integer")) {
	    			returnedObject.p("message", "Your number '" + value + "' cannot be tested for even/odd because it is not an integer.");
	    		}
	    		if (error.equals("value.is.not.even")) {
	    			returnedObject.p("message", "Your number '" + value + "' is not even.") ;
	    		}
	    		if (error.equals("value.is.not.odd")) {
	    			returnedObject.p("message", "Your number '" + value + "' is not odd.");
	    		}
	    		
	    		//Inclusion + Exclusion errors
	    		if (error.equals("value.did.not.match.a.valid.choice")) {
	    			returnedObject.p("message", "Your value '" + value + "' is not allowed.");
	    		}
	    		if (error.equals("value.matched.an.invalid.choice")) {
	    			returnedObject.p("message", "Your value '" + value + "' is not allowed.");
	    		}
    		} else {
    			throw new ItemscriptError(
                        "error.itemscript.Template.error.message.parsed.wrong", errorMessage);
    		}
    	}
    	return returnedObject;
    }
}