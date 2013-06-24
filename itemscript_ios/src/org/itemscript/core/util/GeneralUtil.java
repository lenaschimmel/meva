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

package org.itemscript.core.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Various utility methods. These are not intended for use outside the Itemscript library and the interface to this class is
 * not guaranteed to remain stable. If you need to use a method from this class, it is safest to copy it to your own utility class.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 */
public final class GeneralUtil {
    /**
     * HTML-encode a string. This simple method only replaces the five characters &, <, >, ", and '.
     * 
     * @param input the String to convert
     * @return a new String with HTML encoded characters
     */
    public static String htmlEncode(String input) {
        String output = input.replaceAll("&", "&amp;");
        output = output.replaceAll("<", "&lt;");
        output = output.replaceAll(">", "&gt;");
        output = output.replaceAll("\"", "&quot;");
        output = output.replaceAll("'", "&#039;");
        return output;
    }

    /**
     * Returns true if the supplied int is even, false if it is odd.
     * 
     * @param i The number to test.
     * @return True if the number was even, false if it was odd.
     */
    public static boolean isEven(int i) {
        return (i % 2) == 0;
    }

    /**
     * Join a list of strings with the given joining string.
     * 
     * @param strings The strings to join.
     * @param join The string to join them with, or null if no string is to be used.
     * @return The string consisting of the strings joined together.
     */
    public static String join(List<String> strings, String join) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < strings.size(); ++i) {
            String entry = strings.get(i);
            buffer.append(entry);
            if (join != null) {
                if (i < (strings.size() - 1)) {
                    buffer.append(join);
                }
            }
        }
        return buffer.toString();
    }

    /**
     * Take a Map with String keys and return a String array containing the keys from the original map.
     *  
     * @param map The Map with String keys.
     * @return A new String array.
     */
    public static String[] keyList(Map<String, ?> map) {
        if (map == null) { return null; }
        String[] keyList = new String[map.size()];
        int i = 0;
        for (String key : map.keySet()) {
            keyList[i] = key;
            ++i;
        }
        return keyList;
    }

    /**
     * Take a String array and return a Map with an entry for each string in the original array,
     * whose value will be an Object.
     *  
     * @param array The String array.
     * @return A new Map.
     */
    public static Map<String, Object> stringArrayToMap(String[] array) {
        if (array == null) { return null; }
        Map<String, Object> map = new HashMap<String, Object>();
        Object o = new Object();
        for (int i = 0; i < array.length; ++i) {
            map.put(array[i], o);
        }
        return map;
    }

    public static boolean isWhitespace(char c) {
        // A poor substitute for Character.isWhitespace(char c) which is not implemented in GWT.
        switch (c) {
            case ' ' :
            case '\n' :
            case '\t' :
            case '\r' :
                return true;
            default :
                return false;
        }
    }
}