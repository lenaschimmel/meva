
package org.itemscript.core.values;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.util.JsonAccessHelper;

public abstract class WeakContainer implements JsonContainer {
    private final JsonSystem system;

    protected WeakContainer(JsonSystem system) {
        this.system = system;
    }

    @Override
    public JsonArray asArray() {
        return null;
    }

    @Override
    public final JsonBoolean asBoolean() {
        return null;
    }

    @Override
    public final JsonContainer asContainer() {
        return this;
    }

    @Override
    public final JsonNative asNative() {
        return null;
    }

    @Override
    public final JsonNull asNull() {
        return null;
    }

    @Override
    public final JsonNumber asNumber() {
        return null;
    }

    @Override
    public JsonObject asObject() {
        return null;
    }

    @Override
    public final JsonString asString() {
        return null;
    }

    @Override
    public final byte[] binaryValue() {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Boolean booleanValue() {
        throw new UnsupportedOperationException();
    }

    @Override
    public final JsonArray createArray(String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final JsonObject createObject(String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Double doubleValue() {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Float floatValue() {
        throw new UnsupportedOperationException();
    }

    @Override
    public final String fragment() {
        return null;
    }

    @Override
    public final JsonArray getArray(String key) {
        return JsonAccessHelper.asArray(getValue(key));
    }

    @Override
    public final byte[] getBinary(String key) {
        return JsonAccessHelper.asBinary(getValue(key));
    }

    @Override
    public final Boolean getBoolean(String key) {
        return JsonAccessHelper.asBoolean(getValue(key));
    }

    @Override
    public final JsonValue getByPath(String path) {
        return JsonAccessHelper.getByPath(this, path);
    }

    @Override
    public final Double getDouble(String key) {
        return JsonAccessHelper.asDouble(getValue(key));
    }

    @Override
    public final Float getFloat(String key) {
        return JsonAccessHelper.asFloat(getValue(key));
    }

    @Override
    public final Integer getInt(String key) {
        return JsonAccessHelper.asInt(getValue(key));
    }

    @Override
    public final Long getLong(String key) {
        return JsonAccessHelper.asLong(getValue(key));
    }

    @Override
    public final Object getNative(String key) {
        return JsonAccessHelper.asNative(getValue(key));
    }

    @Override
    public final JsonObject getObject(String key) {
        return JsonAccessHelper.asObject(getValue(key));
    }

    @Override
    public final JsonArray getOrCreateArray(String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final JsonObject getOrCreateObject(String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final JsonArray getRequiredArray(String key) {
        return JsonAccessHelper.getRequiredArray(this, key, getValue(key));
    }

    @Override
    public final byte[] getRequiredBinary(String key) {
        return JsonAccessHelper.getRequiredBinary(this, key, getValue(key));
    }

    @Override
    public final Boolean getRequiredBoolean(String key) {
        return JsonAccessHelper.getRequiredBoolean(this, key, getValue(key));
    }

    @Override
    public final Double getRequiredDouble(String key) {
        return JsonAccessHelper.getRequiredDouble(this, key, getValue(key));
    }

    @Override
    public final Float getRequiredFloat(String key) {
        return JsonAccessHelper.getRequiredFloat(this, key, getValue(key));
    }

    @Override
    public final Integer getRequiredInt(String key) {
        return JsonAccessHelper.getRequiredInt(this, key, getValue(key));
    }

    @Override
    public final Long getRequiredLong(String key) {
        return JsonAccessHelper.getRequiredLong(this, key, getValue(key));
    }

    @Override
    public final JsonObject getRequiredObject(String key) {
        return JsonAccessHelper.getRequiredObject(this, key, getValue(key));
    }

    @Override
    public final String getRequiredString(String key) {
        return JsonAccessHelper.getRequiredString(this, key, getValue(key));
    }

    @Override
    public final JsonValue getRequiredValue(String key) {
        return JsonAccessHelper.getRequiredValue(this, key, getValue(key));
    }

    @Override
    public final String getString(String key) {
        return JsonAccessHelper.asString(getValue(key));
    }

    @Override
    public final boolean hasArray(String key) {
        JsonValue value = getValue(key);
        if (value == null) { return false; }
        return value.isArray();
    }

    @Override
    public final boolean hasBoolean(String key) {
        JsonValue value = getValue(key);
        if (value == null) { return false; }
        return value.isBoolean();
    }

    @Override
    public final boolean hasNumber(String key) {
        JsonValue value = getValue(key);
        if (value == null) { return false; }
        return value.isNumber();
    }

    @Override
    public final boolean hasObject(String key) {
        JsonValue value = getValue(key);
        if (value == null) { return false; }
        return value.isObject();
    }

    @Override
    public final boolean hasString(String key) {
        JsonValue value = getValue(key);
        if (value == null) { return false; }
        return value.isString();
    }

    @Override
    public final Integer intValue() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isArray() {
        return false;
    }

    @Override
    public final boolean isBoolean() {
        return false;
    }

    @Override
    public final boolean isContainer() {
        return true;
    }

    @Override
    public final boolean isNative() {
        return false;
    }

    @Override
    public final boolean isNull() {
        return false;
    }

    @Override
    public final boolean isNumber() {
        return false;
    }

    @Override
    public boolean isObject() {
        return false;
    }

    @Override
    public final boolean isString() {
        return false;
    }

    @Override
    public final JsonItem item() {
        return null;
    }

    @Override
    public final String key() {
        return null;
    }

    @Override
    public final Long longValue() {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Object nativeValue() {
        throw new UnsupportedOperationException();
    }

    @Override
    public final JsonContainer parent() {
        return null;
    }

    @Override
    public final JsonBoolean put(String key, Boolean value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final JsonString put(String key, byte[] value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final JsonNumber put(String key, Double value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final JsonNumber put(String key, Float value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final JsonNumber put(String key, Integer value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final JsonString put(String key, Long value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final JsonString put(String key, String value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void putByPath(String path, JsonValue value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void removeByPath(String path) {
        throw new UnsupportedOperationException();
    }
    @Override
    public final JsonNative putNative(String key, Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void putValue(String key, JsonValue value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void removeValue(String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final String stringValue() {
        throw new UnsupportedOperationException();
    }

    @Override
    public final JsonSystem system() {
        return system;
    }
}