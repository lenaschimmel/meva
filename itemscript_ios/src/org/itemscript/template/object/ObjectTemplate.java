
package org.itemscript.template.object;

import java.util.Map;

import org.itemscript.core.HasSystem;
import org.itemscript.core.JsonSystem;
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;
import org.itemscript.template.Element;
import org.itemscript.template.TemplateExec;

public class ObjectTemplate implements Element, HasSystem {
    private final Map<String, JsonValue> literalKeys;
    private final Map<String, Element> regularKeys;
    private final JsonSystem system;

    public ObjectTemplate(JsonSystem system, Map<String, JsonValue> literalKeys, Map<String, Element> regularKeys) {
        this.system = system;
        this.literalKeys = literalKeys;
        this.regularKeys = regularKeys;
    }

    public ObjectTemplate asObjectTemplate() {
        return this;
    }

    @Override
    public JsonValue interpret(TemplateExec templateExec, JsonValue context) {
        JsonObject out = system().createObject();
        for (String key : literalKeys.keySet()) {
            out.put(key, literalKeys.get(key)
                    .copy());
        }
        for (String key : regularKeys.keySet()) {
            out.put(key, regularKeys.get(key)
                    .interpret(templateExec, context)
                    .copy());
        }
        return out;
    }

    public boolean isObjectTemplate() {
        return true;
    }

    @Override
    public JsonSystem system() {
        return system;
    }

    @Override
    public String toString() {
        return "[ObjectTemplate literalKeys=" + literalKeys + " regularKeys=" + regularKeys + "]";
    }
}