
package org.itemscript.template;

import java.util.HashMap;
import java.util.Map;

import org.itemscript.core.HasSystem;
import org.itemscript.core.JsonSystem;
import org.itemscript.core.values.JsonValue;

/**
 * The execution context for a template, providing access to the JsonSystem, Accumulator, and potentially other
 * template-specific facilities.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 */
public class TemplateExec implements HasSystem {
    private final JsonSystem system;
    private Accumulator accumulator;
    private Map<String, JsonValue> cache;

    /**
     * Create a new TemplateContext.
     * 
     * @param system The associated JsonSystem.
     */
    public TemplateExec(JsonSystem system) {
        this.system = system;
    }

    /**
     * Get the accumulator for this template invocation.
     *  
     * @return The accumulator.
     */
    public Accumulator accumulator() {
        if (accumulator == null) {
            accumulator = new Accumulator(system());
        }
        return accumulator;
    }

    /**
     * Get a value by URL; this will cache all values retrieved during a given template execution.
     * 
     * @param url The URL to get.
     * @return The value at that URL.
     */
    public JsonValue get(String url) {
        if (cache == null) {
            cache = new HashMap<String, JsonValue>();
        }
        JsonValue cached = cache.get(url);
        if (cached == null) {
            cached = system().get(url);
            cache.put(url, cached);
        }
        return cached;
    }

    @Override
    public JsonSystem system() {
        return system;
    }
}