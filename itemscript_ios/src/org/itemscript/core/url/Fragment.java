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

package org.itemscript.core.url;

import java.util.ArrayList;
import java.util.List;

/**
 * A representation of the fragment portion of a URL.<br/>
 * <br/>
 * No changes should be made to values in this List except by the scheme parser that produced it. The behavior is undefined
 * if changes are made.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 */
public final class Fragment extends ArrayList<String> implements List<String> {
    /**
     * Decode the given fragment string.
     * 
     * @param fragmentString The fragment string to decode.
     * @return The decoded Fragment.
     */
    public static Fragment decodeFragment(String fragmentString) {
        Fragment fragment = new Fragment();
        int startOfComponent = 0;
        for (int i = 0; i <= fragmentString.length(); ++i) {
            char c;
            if (i == fragmentString.length()) {
                c = 0;
            } else {
                c = fragmentString.charAt(i);
            }
            if (c == '/' || c == 0) {
                if (i > startOfComponent) {
                    String component = fragmentString.substring(startOfComponent, i);
                    fragment.add(Url.decode(component));
                    startOfComponent = i + 1;
                }
            }
        }
        return fragment;
    }

    /**
     * Create a new fragment.
     */
    public Fragment() {
        super();
    }

    /**
     * Get the last key from this fragment.
     * 
     * @return The last key.
     */
    public String lastKey() {
        if (size() > 0) { return get(size() - 1); }
        return null;
    }
}