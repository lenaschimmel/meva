
package org.itemscript.template.object;

import java.util.HashMap;
import java.util.Map;

import org.itemscript.core.HasSystem;
import org.itemscript.core.JsonSystem;
import org.itemscript.core.exceptions.ItemscriptError;
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;
import org.itemscript.template.Analyzer;
import org.itemscript.template.Element;
import org.itemscript.template.Template;

public class ObjectAnalyzer implements HasSystem {
    private final JsonSystem system;
    private final Analyzer analyzer;
    private final JsonObject object;

    public ObjectAnalyzer(JsonSystem system, Analyzer analyzer, JsonObject object) {
        this.system = system;
        this.analyzer = analyzer;
        this.object = object;
    }

    public Element analyze() {
        if (object == null) { throw ItemscriptError.internalError(this, "analyze.object.was.null"); }
        // FIXME - Check for special key indicating a function here...
        Map<String, JsonValue> literalKeys = new HashMap<String, JsonValue>();
        Map<String, Element> regularKeys = new HashMap<String, Element>();
        for (String key : object.keySet()) {
            // Empty keys are an error.
            if (key.length() == 0) { throw ItemscriptError.internalError(this, "analyze.empty.key", object + ""); }
            char firstChar = key.charAt(0);
            if (firstChar == Template.LITERAL_CHAR) {
                if (key.length() == 1) { throw ItemscriptError.internalError(this, "analyze.empty.literal.key",
                        object + ""); }
                String literalKey = key.substring(1);
                literalKeys.put(literalKey, object.get(key)
                        .copy());
            } else if (Character.isLetter(firstChar)) {
                regularKeys.put(key, analyzer.analyze(object.get(key)));
            } else {
                throw ItemscriptError.internalError(this, "analyze.unknown.key.type", key);
            }
        }
        return new ObjectTemplate(system(), literalKeys, regularKeys);
    }

    @Override
    public JsonSystem system() {
        return system;
    }
}