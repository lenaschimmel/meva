/*
 * Copyright © 2010, Data Base Architects, Inc. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice,
 *       this list of conditions and the following disclaimer in the documentation
 *       and/or other materials provided with the distribution.
 *     * Neither the names of Kalinda Software, DBA Software, Data Base Architects, Itemscript
 *       nor the names of its contributors may be used to endorse or promote products derived
 *       from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
 * SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * 
 * Author: Jacob Davies
 */

package org.itemscript.core.connectors;

import java.util.Arrays;
import java.util.Comparator;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.exceptions.ItemscriptError;
import org.itemscript.core.url.Pagination;
import org.itemscript.core.url.Path;
import org.itemscript.core.url.Query;
import org.itemscript.core.url.Url;
import org.itemscript.core.values.ItemscriptItem;
import org.itemscript.core.values.ItemscriptPutResponse;
import org.itemscript.core.values.ItemscriptRemoveResponse;
import org.itemscript.core.values.JsonArray;
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;
import org.itemscript.core.values.PutResponse;
import org.itemscript.core.values.RemoveResponse;

/**
 * Implements the in-memory item store.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 *
 */
public final class MemConnector extends ConnectorBase
        implements
            SyncGetConnector,
            SyncPutConnector,
            SyncBrowseConnector,
            SyncDumpConnector,
            SyncLoadConnector,
            SyncPostConnector {
    /**
     * Used in {@link #sortByField}.
     */
    private class KeyAndField {
        final String key;
        final String field;

        private KeyAndField(String key, String field) {
            this.key = key;
            this.field = field;
        }

        @Override
        public String toString() {
            return "[KeyAndField key=" + key + " field=" + field + "]";
        }
    }

    /**
     * Used in {@link #sortByKey}.
     */
    private static final Comparator<String> reverseStringComparator = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return o2.compareTo(o1);
        }
    };
    /**
     * Used in {@link #sortByField}.
     */
    private static final Comparator<KeyAndField> fieldComparator = new Comparator<KeyAndField>() {
        @Override
        public int compare(KeyAndField o1, KeyAndField o2) {
            return o1.field.compareTo(o2.field);
        }
    };
    /**
     * Used in {@link #sortByField}.
     */
    private static final Comparator<KeyAndField> reverseFieldComparator = new Comparator<KeyAndField>() {
        @Override
        public int compare(KeyAndField o1, KeyAndField o2) {
            return o2.field.compareTo(o1.field);
        }
    };

    /**
     * Create the MemConnector for an {@link JsonSystem}.
     * 
     * This initializes the system and returns the JsonObject from the location
     * "mem:/itemscript/connectors" that is used to store the {@link Connector} objects for
     * the system.
     * 
     * @param system The associated JsonSystem.
     * @return The JsonObject from the location "mem:/itemscript/connectors".
     */
    public static JsonObject create(JsonSystem system) {
        MemConnector connector = new MemConnector(system);
        connector.root.put("itemscript", new ItemNode(system.createItem("mem:/itemscript", system.createNull())));
        JsonObject connectorsObject = system.createObject();
        connector.root.get("itemscript")
                .put("connectors", new ItemNode(system.createItem("mem:/itemscript/connectors", connectorsObject)));
        connectorsObject.putNative("mem", connector);
        return connectorsObject;
    }

    /**
     * Notify the given ItemNode and all of its sub-nodes that they have been removed.
     * 
     * @param removedNode
     */
    private static void notifyAllOfRemove(ItemNode removedNode) {
        for (String key : removedNode.keySet()) {
            ItemNode next = removedNode.get(key);
            notifyAllOfRemove(next);
        }
        ((ItemscriptItem) removedNode.item()).notifyRemove("#");
    }

    private final ItemNode root;

    /**
     * Create a new MemConnector.
     * 
     * @param system
     */
    private MemConnector(JsonSystem system) {
        super(system);
        root = new ItemNode(system().createItem(JsonSystem.ROOT_URL, system.createNull()));
    }

    @Override
    public JsonObject countItems(Url url) {
        ItemNode node = findNode(url);
        if (node == null) { return null; }
        JsonObject count = system().createObject();
        count.put("count", node.size());
        system().createItem(url + "", count);
        return count;
    }

    @Override
    public JsonObject dump(Url url) {
        ItemNode node = findNode(url);
        if (node == null) { return null; }
        return dumpNode(node);
    }

    /**
     * Dump the given node and sub-nodes recursively.
     * 
     * @param node
     * @return
     */
    private JsonObject dumpNode(ItemNode node) {
        JsonObject dump = system().createObject();
        dump.put("value", node.item()
                .value()
                .copy());
        JsonObject subItems = system().createObject();
        dump.put("subItems", subItems);
        for (String key : node.keySet()) {
            subItems.put(key, dumpNode(node.get(key)));
        }
        return dump;
    }

    /**
     * Find an ItemNode from the root for the given URL.
     * 
     * @param url
     * @return
     */
    private ItemNode findNode(Url url) {
        ItemNode node = root;
        // We start at 1 because the first element of any path is always "/".
        for (int i = 1; i < url.path()
                .size(); ++i) {
            String key = url.path()
                    .get(i);
            node = node.get(key);
            if (node == null) { return null; }
        }
        return node;
    }

    @Override
    public JsonValue get(Url url) {
        ItemNode node = findNode(url);
        if (node == null) { return null; }
        return node.item()
                .value();
    }

    @Override
    public JsonArray getKeys(Url url) {
        ItemNode node = findNode(url);
        if (node == null) { return null; }
        JsonArray keys = system().createArray();
        for (String key : node.keySet()) {
            keys.add(key);
        }
        system().createItem(url + "", keys);
        return keys;
    }

    @Override
    public void load(Url url, JsonObject value) {
        if (value.size() == 0) { return; }
        Url pathedUrl = url.withoutQueryOrFragment();
        put(pathedUrl, value.get("value")
                .copy());
        JsonObject subItems = value.getObject("subItems");
        if (subItems == null) { return; }
        for (String key : subItems.keySet()) {
            String subUrl = pathedUrl + "/" + Url.encode(key);
            load(system().util()
                    .createUrl(subUrl), subItems.getObject(key));
        }
    }

    @Override
    public JsonArray pagedItems(Url url) {
        ItemNode node = findNode(url);
        if (node == null) { return null; }
        String[] keyArray = sortedKeys(node, url.query()
                .pagination());
        Pagination pagination = url.query()
                .pagination();
        int beginIndex = 0;
        int endIndex = beginIndex + SyncBrowseConnector.DEFAULT_NUM_ROWS - 1;
        if (pagination.startRow() != -1) {
            beginIndex = pagination.startRow();
        }
        if (pagination.numRows() != -1) {
            endIndex = beginIndex + pagination.numRows() - 1;
        }
        if (endIndex >= keyArray.length) {
            endIndex = keyArray.length - 1;
        }
        JsonArray pagedItems = system().createArray();
        for (int i = beginIndex; i <= endIndex; ++i) {
            String key = keyArray[i];
            pagedItems.add(system().createObject()
                    .p("key", key)
                    .p("value", node.get(key)
                            .item()
                            .value()
                            .copy()));
        }
        system().createItem(url + "", pagedItems);
        return pagedItems;
    }

    @Override
    public JsonArray pagedKeys(Url url) {
        ItemNode node = findNode(url);
        if (node == null) { return null; }
        String[] keyArray = sortedKeys(node, url.query()
                .pagination());
        Pagination pagination = url.query()
                .pagination();
        int beginIndex = 0;
        int endIndex = beginIndex + SyncBrowseConnector.DEFAULT_NUM_ROWS - 1;
        if (pagination.startRow() != -1) {
            beginIndex = pagination.startRow();
        }
        if (pagination.numRows() != -1) {
            endIndex = beginIndex + pagination.numRows() - 1;
        }
        if (endIndex >= keyArray.length) {
            endIndex = keyArray.length - 1;
        }
        JsonArray pagedKeys = system().createArray();
        for (int i = beginIndex; i <= endIndex; ++i) {
            pagedKeys.add(keyArray[i]);
        }
        system().createItem(url + "", pagedKeys);
        return pagedKeys;
    }

    @Override
    public PutResponse post(Url url, JsonValue value) {
        Query query = url.query();
        if (query.isUuidQuery()) {
            String uuid = system().util()
                    .generateUuid();
            Url generatedUrl = system().util()
                    .createRelativeUrl(JsonSystem.ROOT_URL, url.withoutQueryOrFragment() + "/" + uuid);
            return put(generatedUrl, value);
        } else if (query.isB64idQuery()) {
            String b64id = system().util()
                    .generateB64id();
            Url generatedUrl = system().util()
                    .createRelativeUrl(JsonSystem.ROOT_URL, url.withoutQueryOrFragment() + "/" + b64id);
            return put(generatedUrl, value);
        } else {
            throw ItemscriptError.internalError(this, "post.unknown.query.type");
        }
    }

    @Override
    public PutResponse put(Url url, JsonValue value) {
        ItemNode node = root;
        Path path = url.path();
        if (path.size() <= 1) { throw ItemscriptError.internalError(this, "put.cannot.put.to.root.node"); }
        String nodeUrl = "mem:";
        // Starting at path index 1 to skip the initial "/" element...
        for (int i = 1; i < (path.size()); ++i) {
            String key = path.get(i);
            nodeUrl += "/" + Url.encode(key);
            ItemNode next = node.get(key);
            if (next == null) {
                JsonObject createObject = system().createObject();
                next = new ItemNode(system().createItem(nodeUrl, createObject));
                node.put(key, next);
            }
            node = next;
        }
        String fragmentString = url.fragmentString();
        if (fragmentString == null) {
            fragmentString = "";
        }
        node.item()
                .put("#" + fragmentString, value);
        return new ItemscriptPutResponse(url + "", null, value);
    }

    @Override
    public RemoveResponse remove(Url url) {
        ItemNode node = root;
        Path path = url.path();
        // Path must have at least one component as well as the root component...
        if (path.size() <= 1) { throw ItemscriptError.internalError(this, "remove.path.not.long.enough",
                url.pathString()); }
        for (int i = 1; i < (path.size() - 1); ++i) {
            String key = path.get(i);
            node = node.get(key);
            if (node == null) { return new ItemscriptRemoveResponse(null); }
        }
        if (url.hasFragment()) {
            node = node.get(path.lastKey());
            if (node != null) {
                node.item()
                        .remove("#" + url.fragmentString());
            }
        } else {
            String lastKey = path.lastKey();
            ItemNode lastNode = node.get(lastKey);
            node.remove(lastKey);
            if (lastNode != null) {
                notifyAllOfRemove(lastNode);
            }
        }
        return new ItemscriptRemoveResponse(null);
    }

    /**
     * Sort the given key array for the given node by the pagination criteria.
     * 
     * @param node
     * @param pagination
     * @param keyArray
     */
    private void sortByField(ItemNode node, Pagination pagination, String[] keyArray) {
        String orderBy = pagination.orderBy();
        KeyAndField[] keysAndFields = new KeyAndField[keyArray.length];
        for (int i = 0; i < keyArray.length; ++i) {
            String key = keyArray[i];
            JsonObject object = node.get(key)
                    .item()
                    .value()
                    .asObject();
            String field;
            if (object == null) {
                field = "";
            } else {
                JsonValue fieldValue = object.getByPath(orderBy);
                if (fieldValue == null) {
                    field = "";
                } else if (fieldValue.isString()) {
                    field = fieldValue.stringValue();
                } else if (fieldValue.isNumber()) {
                    field = fieldValue.toJsonString();
                } else if (fieldValue.isBoolean()) {
                    field = fieldValue.booleanValue() + "";
                } else {
                    field = "";
                }
            }
            keysAndFields[i] = new KeyAndField(key, field);
        }
        if (pagination.ascending()) {
            Arrays.sort(keysAndFields, fieldComparator);
        } else {
            Arrays.sort(keysAndFields, reverseFieldComparator);
        }
        for (int i = 0; i < keysAndFields.length; ++i) {
            keyArray[i] = keysAndFields[i].key;
        }
    }

    /**
     * Sort the given key array by key.
     * 
     * @param pagination
     * @param keyArray
     */
    private void sortByKey(Pagination pagination, String[] keyArray) {
        if (pagination.ascending()) {
            Arrays.sort(keyArray);
        } else {
            Arrays.sort(keyArray, reverseStringComparator);
        }
    }

    /**
     * Get a sorted list of keys for the given node by the given pagination criteria.
     * 
     * @param node
     * @param pagination
     * @return
     */
    private String[] sortedKeys(ItemNode node, Pagination pagination) {
        if (node.keySet()
                .size() == 0) { return new String[0]; }
        String[] keyArray = new String[1];
        keyArray = node.keySet()
                .toArray(keyArray);
        if (pagination.orderBy() == null) {
            sortByKey(pagination, keyArray);
        } else {
            sortByField(node, pagination, keyArray);
        }
        return keyArray;
    }
}