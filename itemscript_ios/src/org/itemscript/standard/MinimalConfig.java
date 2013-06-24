/*
 * Copyright � 2010, Data Base Architects, Inc. All rights reserved.
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

import java.util.Random;

import org.itemscript.core.ItemscriptSystem;
import org.itemscript.core.JsonSystem;
import org.itemscript.core.config.JsonConfig;
import org.itemscript.core.util.Base64;
import org.itemscript.core.values.JsonCreator;

/**
 * A minimal configuration for a native Java environment.
 * <p>
 * Includes only the <code>mem:</code> connector.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 */
public class MinimalConfig implements JsonConfig {
    /**
     * Create a new JsonSystem using a MinimalConfig.
     * 
     * @return A new JsonSystem.
     */
    public static JsonSystem createSystem() {
        return new ItemscriptSystem(new MinimalConfig());
    }

    private static void longToBytes(long value, byte[] dest, int start) {
        for (int i = 0; i < 8; ++i) {
            dest[start + i] = (byte) (value & 0xFF);
            value >>= 8;
        }
    }

    private final Random random = new Random();

    @Override
    public JsonCreator createJsonCreator(JsonSystem system) {
        return new StandardJsonCreator(system);
    }

    @Override
    public String generateB64id() {
        UUID uuid = UUID.randomUUID();
        byte[] bytes = new byte[16];
        longToBytes(uuid.getLeastSignificantBits(), bytes, 0);
        longToBytes(uuid.getMostSignificantBits(), bytes, 8);
        return new String(Base64.encodeForUrl(bytes));
    }

    @Override
    public String generateUuid() {
        return UUID.randomUUID()
                .toString();
    }

    @Override
    public int nextRandomInt() {
        return random.nextInt();
    }

    @Override
    public void seedSystem(JsonSystem system) {}
}