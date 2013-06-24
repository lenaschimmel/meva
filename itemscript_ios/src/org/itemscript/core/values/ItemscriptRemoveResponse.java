
package org.itemscript.core.values;

public class ItemscriptRemoveResponse implements RemoveResponse {
    private final JsonObject meta;

    public ItemscriptRemoveResponse(JsonObject meta) {
        this.meta = meta;
    }

    @Override
    public JsonObject meta() {
        return meta;
    }
}
