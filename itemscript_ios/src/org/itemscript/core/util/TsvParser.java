
package org.itemscript.core.util;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.exceptions.ItemscriptError;
import org.itemscript.core.values.JsonArray;
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;

/**
 * A simple TSV parser that can be used to parse pasted spreadsheet results. It does not handle quoting or escaped characters, and expects lines terminated
 * with "\n".
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 */
public class TsvParser {
    public static JsonArray parse(JsonSystem system, String string) {
        if (string == null) { return null; }
        JsonArray results = system.createArray();
        int size = string.length();
        if (size == 0) { return results; }
        int pos = 0;
        // This comparison is < because we don't want to include an empty last line if the file was not terminated with a CR.
        while (pos < size) {
            int nextCr = string.indexOf('\n', pos);
            int until;
            if (nextCr == -1) {
                until = size;
            } else {
                until = nextCr;
            }
            int actuallyUntil = until;
            // Check for lines terminated with \r\n and skip the \r if so.
            if ((until - pos) > 0) {
                if (string.charAt(until - 1) == '\r') {
                    actuallyUntil = until - 1;
                }
            }
            results.add(parseLine(system, string.substring(pos, actuallyUntil)));
            pos = until + 1;
        }
        return results;
    }

    public static JsonArray parseLine(JsonSystem system, String line) {
        if (line == null) { return null; }
        JsonArray results = system.createArray();
        int size = line.length();
        if (size == 0) { return results; }
        int pos = 0;
        // This comparison is <= because we want to look for an empty trailing field.
        while (pos <= size) {
            int nextTab = line.indexOf('\t', pos);
            int until;
            if (nextTab == -1) {
                until = size;
            } else {
                until = nextTab;
            }
            results.add(line.substring(pos, until));
            pos = until + 1;
        }
        return results;
    }

    public static JsonArray parseWithHeaderLine(JsonSystem system, String string) {
        JsonArray array = parse(system, string);
        if (array.size() < 1) { throw new ItemscriptError(
                "error.itemscript.TsvParser.parseWithHeaderLine.need.at.least.one.line", array.toJsonString()); }
        JsonArray withHeaders = system.createArray();
        JsonArray headers = array.getArray(0);
        int fieldCount = headers.size();
        for (int i = 1, s = array.size(); i < s; ++i) {
            JsonObject object = withHeaders.addObject();
            JsonArray line = array.getArray(i);
            for (int j = 0; j < fieldCount; ++j) {
                JsonValue value = line.get(j);
                String header = headers.getString(j);
                object.put(header, value.copy());
            }
        }
        return withHeaders;
    }
}