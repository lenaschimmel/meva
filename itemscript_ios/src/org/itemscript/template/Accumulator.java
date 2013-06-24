
package org.itemscript.template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.itemscript.core.HasSystem;
import org.itemscript.core.JsonSystem;

/**
 * An Accumulator is a write-only object used to accumulate extra information that should be tied to particular values in a template.
 * It's intended to corral side-effects of template interpretation without needing an ad-hoc mechanism for each case.
 * However, the interface is at present experimental and should not be depended on as unchanging.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 */
public class Accumulator implements HasSystem {
    private final Map<String, List<Object>> lists;
    private final JsonSystem system;

    /**
     * Create a new Accumulator.
     * 
     * @param system The associated JsonSystem.
     */
    public Accumulator(JsonSystem system) {
        this.system = system;
        this.lists = new HashMap<String, List<Object>>();
    }

    private List<Object> getOrCreateLists(String key) {
        List<Object> list = lists.get(key);
        if (list == null) {
            list = new ArrayList<Object>();
            lists.put(key, list);
        }
        return list;
    }

    /**
     * Get the map of values; only used by Template.
     * 
     * @return
     */
    public Map<String, List<Object>> lists() {
        return lists;
    }

    /**
     * Put a new value, returning the ID assigned to it.
     * 
     * @param key The key to put under.
     * @param value The value to put.
     * @return The ID that was assigned to this value.
     */
    public String put(String key, Object value) {
        String id = system().util()
                .generateB64id();
        List<Object> list = getOrCreateLists(key);
        int index = list.size();
        list.add(value);
        return key + "/" + index;
    }

    @Override
    public JsonSystem system() {
        return system;
    }
}