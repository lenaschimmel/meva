
package org.itemscript.template;

import java.util.List;
import java.util.Map;

import org.itemscript.core.values.JsonValue;

/**
 * Holds the result of a template interpretation, including the value and the result from the Accumulator.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 */
public class TemplateResult {
    private final JsonValue value;
    private final Map<String, List<Object>> accumulated;

    /**
     * Create a new TemplateResult.
     * 
     * @param value The value returned by the template.
     * @param accumulated The accumulated values.
     */
    public TemplateResult(JsonValue value, Map<String, List<Object>> accumulated) {
        this.value = value;
        this.accumulated = accumulated;
    }

    /**
     * Get the accumulated values from this template result.
     * 
     * @param type The type of value to get.
     * @return A list of values of that type.
     */
    public List<Object> accumulated(String type) {
        return accumulated.get(type);
    }

    /**
     * Get the value returned by the template.
     * 
     * @return The value returned by the template.
     */
    public JsonValue value() {
        return value;
    }
}