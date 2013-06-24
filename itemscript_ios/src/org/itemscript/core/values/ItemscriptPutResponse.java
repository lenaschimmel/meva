
package org.itemscript.core.values;

public class ItemscriptPutResponse implements PutResponse {
    private final JsonObject meta;
    private final String url;
    private final JsonValue value;

    public ItemscriptPutResponse(String url, JsonObject meta) {
        this(url, meta, null);
    }

    public ItemscriptPutResponse(String url, JsonObject meta, JsonValue value) {
        this.url = url;
        this.meta = meta;
        this.value = value;
    }

    @Override
    public JsonObject meta() {
        return meta;
    }

    @Override
    public String url() {
        return url;
    }

    @Override
    public JsonValue value() {
        return value;
    }
}
