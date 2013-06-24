
package examples.org.itemscript;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.values.JsonValue;
import org.itemscript.core.values.PutResponse;
import org.itemscript.standard.StandardConfig;

public class PutExample {
    public static void main(String[] args) {
        JsonSystem system = StandardConfig.createSystem();
        JsonValue value = system.get("http://127.0.0.1:8888/cat.json");
        System.err.println(value);
        PutResponse put = system.put("http://127.0.0.1:8888/ReflectJson", value);
        System.err.println(put.meta());
        JsonValue response = put.value();
        System.err.println(response);
    }
}