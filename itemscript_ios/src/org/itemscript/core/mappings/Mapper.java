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

package org.itemscript.core.mappings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A utility class that allows you to use a {@link Mapping} to turn lists into lists, lists into maps, and maps into maps.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 *
 */
public final class Mapper {
    /**
     * Map a List of values to a new List.
     * 
     * @param <FROM> The type of values in the original List.
     * @param <TO> The type of values in the new List.
     * @param source The source List to map from.
     * @param mapping The mapping to use.
     * @return A new List of type TO.
     */
    public static <FROM, TO> List<TO> listToList(List<FROM> source, Mapping<FROM, TO> mapping) {
        ArrayList<TO> list = new ArrayList<TO>();
        for (int i = 0; i < source.size(); ++i) {
            TO value = mapping.map(source.get(i));
            if (value != null) {
                list.add(value);
            }
        }
        return list;
    }

    /**
     * Map a List of values to a new Map in which the keys will be the original values from the List,
     * and the values will be the result of mapping those values.
     * 
     * @param <TO> The type of values in the new Map.
     * @param <KEY> The type of values in the original List.
     * @param source The source List to map from.
     * @param mapping The mapping to use.
     * @return A new Map with keys of type KEY and values of type TO.
     */
    public static <TO, KEY> Map<KEY, TO> listToMap(List<KEY> source, Mapping<KEY, TO> mapping) {
        HashMap<KEY, TO> map = new HashMap<KEY, TO>();
        for (int i = 0; i < source.size(); ++i) {
            KEY key = source.get(i);
            TO value = mapping.map(key);
            if (value != null) {
                map.put(key, value);
            }
        }
        return map;
    }

    /**
     * Map a Map of values to a new Map. Each value in the original Map will be mapped, and the result
     * placed under the same key in the new Map. 
     * 
     * @param <FROM> The type of values in the original Map.
     * @param <TO> The type of values in the new Map.
     * @param <KEY> The type of keys in both Maps.
     * @param source The source Map to map from.
     * @param mapping The mapping to use.
     * @return A new Map with keys of type KEY and values of type TO.
     */
    public static <FROM, TO, KEY> Map<KEY, TO> mapToMap(Map<KEY, FROM> source, Mapping<FROM, TO> mapping) {
        HashMap<KEY, TO> map = new HashMap<KEY, TO>();
        for (KEY key : source.keySet()) {
            TO value = mapping.map(source.get(key));
            if (value != null) {
                map.put(key, value);
            }
        }
        return map;
    }
}