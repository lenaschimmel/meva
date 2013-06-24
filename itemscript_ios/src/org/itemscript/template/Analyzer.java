
package org.itemscript.template;

import org.itemscript.core.HasSystem;
import org.itemscript.core.JsonSystem;
import org.itemscript.core.exceptions.ItemscriptError;
import org.itemscript.core.values.JsonValue;
import org.itemscript.template.object.ObjectAnalyzer;
import org.itemscript.template.text.StringAnalyzer;

/**
 * An Analyzer takes a JsonValue and returns a template element corresponding to it.
 */
public class Analyzer implements HasSystem {
    private final JsonSystem system;

    /**
     * Create a new Analyzer.
     * 
     * @param system The associated JsonSystem.
     */
    public Analyzer(JsonSystem system) {
        this.system = system;
    }

    /**
     * Analyze the given value and return a template element corresponding to it.
     * 
     * @param value The value to analyze.
     * @return A template element.
     */
    public Element analyze(JsonValue value) {
        if (value == null) { return null; }
        if (value.isString()) {
            return new StringAnalyzer(system(), this, value.stringValue()).analyze();
        } else if (value.isObject()) {
            return new ObjectAnalyzer(system(), this, value.asObject()).analyze();
        } else if (value.isBoolean() || value.isNumber() || value.isNull()) {
            return new ValueLiteral(value);
        } else {
            throw ItemscriptError.internalError(this, "analyze.unknown.value.type", value.toCompactJsonString());
        }
    }

    @Override
    public JsonSystem system() {
        return system;
    }
}