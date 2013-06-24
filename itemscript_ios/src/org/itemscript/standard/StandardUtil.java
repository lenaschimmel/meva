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

package org.itemscript.standard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.values.JsonString;
import org.itemscript.core.values.JsonValue;

/**
 * Various utility methods for the standard Java environment.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 */
public final class StandardUtil {
    /**
     * Read the given InputStream as binary into a JsonString.
     * 
     * @param system The associated JsonSystem.
     * @param stream The InputStream to read.
     * @return A new JsonString.
     * @throws IOException
     */
    public static JsonString readBinary(JsonSystem system, InputStream stream) throws IOException {
        byte[] contents = Util.readStreamToByteArray(stream);
        stream.close();
        JsonString value = system.createString(contents);
        return value;
    }

    /**
     * Read the given Reader as JSON into a new JsonValue.
     * 
     * @param system The associated JsonSystem.
     * @param reader The Reader to read from.
     * @return A new JsonValue.
     * @throws IOException
     */
    public static JsonValue readJson(JsonSystem system, Reader reader) throws IOException {
        JsonValue value = system.parseReader(reader);
        reader.close();
        return value;
    }

    /**
     * Read the given BufferedReader as text into a JsonString. Line endings will be regularized to "\n".
     * 
     * @param system The associated JsonSystem.
     * @param reader The BufferedReader to read.
     * @return A new JsonString.
     * @throws IOException
     */
    public static JsonString readText(JsonSystem system, BufferedReader reader) throws IOException {
        StringBuffer sb = new StringBuffer();
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            sb.append(line + "\n");
        }
        reader.close();
        return system.createString(sb.toString());
    }
}