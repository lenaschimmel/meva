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
 *     * Neither the names of Kalinda Software, DBA Software, Data Base Architects,
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
/*
 * Incorporates code from the W3 issued under this license:
 *
 * Created: 17 April 1997
 * Author: Bert Bos <bert@w3.org>
 *
 * URLUTF8Encoder: http://www.w3.org/International/URLUTF8Encoder.java
 *
 * Copyright © 1997 World Wide Web Consortium, (Massachusetts
 * Institute of Technology, European Research Consortium for
 * Informatics and Mathematics, Keio University). All Rights Reserved. 
 * This work is distributed under the W3C® Software License [1] in the
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.
 *
 * [1] http://www.w3.org/Consortium/Legal/2002/copyright-software-20021231
 */

package org.itemscript.core.url;

import org.itemscript.core.HasSystem;
import org.itemscript.core.JsonSystem;
import org.itemscript.core.exceptions.ItemscriptError;

/**
 * Represents a decoded URL in the Itemscript system.
 * <p>
 * This separate implementation to {@link java.net.URL} was necessary because the latter is not supported in the GWT
 * environment.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 */
public final class Url implements HasSystem {
    /**
     * URL-decode the supplied string.
     * 
     * @param s The String to decode.
     * @return The decoded String.
     */
    public static String decode(String s) {
        StringBuffer sbuf = new StringBuffer();
        int l = s.length();
        int ch = -1;
        int b, sumb = 0;
        for (int i = 0, more = -1; i < l; i++) {
            /* Get next byte b from URL segment s */
            switch (ch = s.charAt(i)) {
                case '%' :
                    if (i + 2 >= l) { throw new ItemscriptError(
                            "error.itemscript.Url.decode.encountered.percent.sign.without.trailing.characters", s); }
                    ch = s.charAt(++i);
                    int hb =
                            (Character.isDigit((char) ch) ? ch - '0' : 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
                    ch = s.charAt(++i);
                    int lb =
                            (Character.isDigit((char) ch) ? ch - '0' : 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
                    b = (hb << 4) | lb;
                    break;
                case '+' :
                    b = ' ';
                    break;
                default :
                    b = ch;
            }
            /* Decode byte b as UTF-8, sumb collects incomplete chars */
            if ((b & 0xc0) == 0x80) { // 10xxxxxx (continuation byte)
                sumb = (sumb << 6) | (b & 0x3f); // Add 6 bits to sumb
                if (--more == 0) sbuf.append((char) sumb); // Add char to sbuf
            } else if ((b & 0x80) == 0x00) { // 0xxxxxxx (yields 7 bits)
                sbuf.append((char) b); // Store in sbuf
            } else if ((b & 0xe0) == 0xc0) { // 110xxxxx (yields 5 bits)
                sumb = b & 0x1f;
                more = 1; // Expect 1 more byte
            } else if ((b & 0xf0) == 0xe0) { // 1110xxxx (yields 4 bits)
                sumb = b & 0x0f;
                more = 2; // Expect 2 more bytes
            } else if ((b & 0xf8) == 0xf0) { // 11110xxx (yields 3 bits)
                sumb = b & 0x07;
                more = 3; // Expect 3 more bytes
            } else if ((b & 0xfc) == 0xf8) { // 111110xx (yields 2 bits)
                sumb = b & 0x03;
                more = 4; // Expect 4 more bytes
            } else /*if ((b & 0xfe) == 0xfc)*/{ // 1111110x (yields 1 bit)
                sumb = b & 0x01;
                more = 5; // Expect 5 more bytes
            }
            /* We don't test if the UTF-8 encoding is well-formed */
        }
        return sbuf.toString();
    }

    /**
    * Encode a string to the "x-www-form-urlencoded" form, enhanced
    * with the UTF-8-in-URL proposal. This is what happens:
    *
    * <ul>
    * <li><p>The ASCII characters 'a' through 'z', 'A' through 'Z',
    *        and '0' through '9' remain the same.
    *
    * <li><p>The unreserved characters - _ . ! ~ * ' ( ) remain the same.
    *
    * <li><p>The space character ' ' is converted into a plus sign '+'.
    *
    * <li><p>All other ASCII characters are converted into the
    *        3-character string "%xy", where xy is
    *        the two-digit hexadecimal representation of the character
    *        code
    *
    * <li><p>All non-ASCII characters are encoded in two steps: first
    *        to a sequence of 2 or 3 bytes, using the UTF-8 algorithm;
    *        secondly each of these bytes is encoded as "%xx".
    * </ul>
    *
    * @param s The string to be encoded
    * @return The encoded string
    */
    public static String encode(String s) {
        if (s == null) { return null; }
        StringBuffer sbuf = new StringBuffer();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            int ch = s.charAt(i);
            if ('A' <= ch && ch <= 'Z') { // 'A'..'Z'
                sbuf.append((char) ch);
            } else if ('a' <= ch && ch <= 'z') { // 'a'..'z'
                sbuf.append((char) ch);
            } else if ('0' <= ch && ch <= '9') { // '0'..'9'
                sbuf.append((char) ch);
            } else if (ch == '-' || ch == '_' // unreserved
                    || ch == '.' || ch == '!' || ch == '~' || ch == '*' || ch == '\'' || ch == '(' || ch == ')') {
                sbuf.append((char) ch);
            } else if (ch <= 0x007f) { // other ASCII
                sbuf.append(hex[ch]);
            } else if (ch <= 0x07FF) { // non-ASCII <= 0x7FF
                sbuf.append(hex[0xc0 | (ch >> 6)]);
                sbuf.append(hex[0x80 | (ch & 0x3F)]);
            } else { // 0x7FF < ch <= 0xFFFF
                sbuf.append(hex[0xe0 | (ch >> 12)]);
                sbuf.append(hex[0x80 | ((ch >> 6) & 0x3F)]);
                sbuf.append(hex[0x80 | (ch & 0x3F)]);
            }
        }
        return sbuf.toString();
    }

    private final String remainder;
    private final String scheme;
    private final String authority;
    private final String userInformation;
    private final String hostname;
    private final String port;
    private final String pathString;
    private final Path path;
    private final String directory;
    private final String filename;
    private final String urlString;
    private final String queryString;
    private final Query query;
    private final String fragmentString;
    private final JsonSystem system;
    private final Fragment fragment;
    /**
     * The mem scheme.
     */
    public final static String MEM_SCHEME = "mem";
    /**
     * The http scheme.
     */
    public final static String HTTP_SCHEME = "http";
    /**
     * The https scheme.
     */
    public final static String HTTPS_SCHEME = "https";
    /**
     * The file scheme.
     */
    public final static String FILE_SCHEME = "file";
    private final static String[] hex =
            {"%00", "%01", "%02", "%03", "%04", "%05", "%06", "%07", "%08", "%09", "%0a", "%0b", "%0c", "%0d",
                    "%0e", "%0f", "%10", "%11", "%12", "%13", "%14", "%15", "%16", "%17", "%18", "%19", "%1a",
                    "%1b", "%1c", "%1d", "%1e", "%1f", "%20", "%21", "%22", "%23", "%24", "%25", "%26", "%27",
                    "%28", "%29", "%2a", "%2b", "%2c", "%2d", "%2e", "%2f", "%30", "%31", "%32", "%33", "%34",
                    "%35", "%36", "%37", "%38", "%39", "%3a", "%3b", "%3c", "%3d", "%3e", "%3f", "%40", "%41",
                    "%42", "%43", "%44", "%45", "%46", "%47", "%48", "%49", "%4a", "%4b", "%4c", "%4d", "%4e",
                    "%4f", "%50", "%51", "%52", "%53", "%54", "%55", "%56", "%57", "%58", "%59", "%5a", "%5b",
                    "%5c", "%5d", "%5e", "%5f", "%60", "%61", "%62", "%63", "%64", "%65", "%66", "%67", "%68",
                    "%69", "%6a", "%6b", "%6c", "%6d", "%6e", "%6f", "%70", "%71", "%72", "%73", "%74", "%75",
                    "%76", "%77", "%78", "%79", "%7a", "%7b", "%7c", "%7d", "%7e", "%7f", "%80", "%81", "%82",
                    "%83", "%84", "%85", "%86", "%87", "%88", "%89", "%8a", "%8b", "%8c", "%8d", "%8e", "%8f",
                    "%90", "%91", "%92", "%93", "%94", "%95", "%96", "%97", "%98", "%99", "%9a", "%9b", "%9c",
                    "%9d", "%9e", "%9f", "%a0", "%a1", "%a2", "%a3", "%a4", "%a5", "%a6", "%a7", "%a8", "%a9",
                    "%aa", "%ab", "%ac", "%ad", "%ae", "%af", "%b0", "%b1", "%b2", "%b3", "%b4", "%b5", "%b6",
                    "%b7", "%b8", "%b9", "%ba", "%bb", "%bc", "%bd", "%be", "%bf", "%c0", "%c1", "%c2", "%c3",
                    "%c4", "%c5", "%c6", "%c7", "%c8", "%c9", "%ca", "%cb", "%cc", "%cd", "%ce", "%cf", "%d0",
                    "%d1", "%d2", "%d3", "%d4", "%d5", "%d6", "%d7", "%d8", "%d9", "%da", "%db", "%dc", "%dd",
                    "%de", "%df", "%e0", "%e1", "%e2", "%e3", "%e4", "%e5", "%e6", "%e7", "%e8", "%e9", "%ea",
                    "%eb", "%ec", "%ed", "%ee", "%ef", "%f0", "%f1", "%f2", "%f3", "%f4", "%f5", "%f6", "%f7",
                    "%f8", "%f9", "%fa", "%fb", "%fc", "%fd", "%fe", "%ff"};

    /**
     * Url constructor.
     * 
     * @param system
     * @param urlString
     * @param remainder
     * @param scheme
     * @param authority
     * @param userInformation
     * @param hostname
     * @param port
     * @param pathString
     * @param path
     * @param directory
     * @param filename
     * @param queryString
     * @param query
     * @param fragmentString
     * @param fragment
     */
    public Url(JsonSystem system, String urlString, String remainder, String scheme, String authority,
            String userInformation, String hostname, String port, String pathString, Path path, String directory,
            String filename, String queryString, Query query, String fragmentString, Fragment fragment) {
        this.system = system;
        this.urlString = urlString;
        this.remainder = remainder;
        this.scheme = scheme;
        this.authority = authority;
        this.userInformation = userInformation;
        this.hostname = hostname;
        this.port = port;
        this.pathString = pathString;
        this.path = path;
        this.directory = directory;
        this.filename = filename;
        this.queryString = queryString;
        this.query = query;
        this.fragmentString = fragmentString;
        this.fragment = fragment;
    }

    /**
     * Get the authority portion of the URL.
     * 
     * @return The authority portion of the URL.
     */
    public String authority() {
        return authority;
    }

    /**
     * Get the directory portion of the path from this URL.
     * 
     * @return The directory portion of the path, or null if there was no path.
     */
    public String directory() {
        return directory;
    }

    /**
     * Get the filename from the path, if any. A path that ends in "/" does not have a filename.
     * 
     * @return The filename, or null if there was no filename.
     */
    public String filename() {
        return filename;
    }

    /**
     * Get the fragment components for this URL. This divides the fragment by / characters, then un-URL-encodes each component
     * 
     * @return The Fragment object.
     */
    public Fragment fragment() {
        return fragment;
    }

    /**
     * Get the fragment, that portion of the URL after the # character. It will not be URL-decoded.
     * 
     * @return The fragment string.
     */
    public String fragmentString() {
        return fragmentString;
    }

    /**
     * Test whether this URL has a filename or not.
     * 
     * @return True if this URL has a filename, false otherwise.
     */
    public boolean hasFilename() {
        return filename != null;
    }

    /**
     * Test whether this URL has a fragment or not.
     * 
     * @return True if this URL had a fragment, false otherwise.
     */
    public boolean hasFragment() {
        return fragmentString != null;
    }

    /**
     * Test whether this URL has a path or not.
     * 
     * @return True if this URL has a path, false otherwise.
     */
    public boolean hasPath() {
        return pathString != null;
    }

    /**
     * Test whether this URL has a query or not.
     * 
     * @return True if this URL has a query, false otherwise.
     */
    public boolean hasQuery() {
        return queryString != null;
    }

    /**
     * Test whether this URL has a scheme or not.
     * 
     * @return True if this URL has a scheme, false otherwise.
     */
    public boolean hasScheme() {
        return scheme != null;
    }

    /**
     * Get the hostname.
     * 
     * @return The hostname, or null if there was no hostname.
     */
    public String hostname() {
        return hostname;
    }

    /**
     * Get the hostname and the port, if any.
     * 
     * @return The hostname and port, or null if there was no hostname and port.
     */
    public String hostnameAndPort() {
        if (hostname != null && port != null) {
            return hostname + ":" + port;
        } else {
            return hostname;
        }
    }

    /**
     * Get a list of the components in the path.
     * If the URL was absolute, the first component will be "/".
     * If the URL was relative but started with a /, the first component will be "/".
     * Otherwise the first component will be the first file/directory name in the path.
     * Each component will be URL-unencoded.
     * The last component may be a filename or a directory name; this method does not distinguish between the two. If you wish to know
     * if there was a trailing filename, call filename().
     * 
     * @return The Path object.
     */
    public Path path() {
        return path;
    }

    /**
     * Get the path portion of this URL.
     * 
     * @return The path, or null if there was no path.
     */
    public String pathString() {
        return pathString;
    }

    /**
     * Get the port, if any.
     * 
     * @return The port, or null if there was no port.
     */
    public String port() {
        return port;
    }

    /**
     * Get the components of the query. Each key corresponds to a list of values. Empty keys or values may be contained.
     * 
     * @return The Query object.
     */
    public Query query() {
        return query;
    }

    /**
     * Get the query section of the URL, that is, the section between the ? and either the end of the URL or a # character.
     * This is not URL-decoded.
     * 
     * @return The query string, or null if there was no query string.
     */
    public String queryString() {
        return queryString;
    }

    /**
     * Get the remainder of the URL string after the scheme.
     * 
     * @return The remainder of the URL string after the scheme.
     */
    public String remainder() {
        return remainder;
    }

    /**
     * Get the scheme for this URL.
     * 
     * @return The scheme for this URL.
     */
    public String scheme() {
        return scheme;
    }

    @Override
    public JsonSystem system() {
        return system;
    }

    /**
     * Returns the URL string. This may not be exactly as the string passed to create().
     */
    public String toString() {
        return urlString;
    }

    /**
     * Get the user information from the URL.
     * 
     * @return The user information.
     */
    public String userInformation() {
        return userInformation;
    }

    /**
     * Get a new Url object based on this one, but with the fragment (if any) removed.
     * 
     * @return The new Url object.
     */
    public Url withoutFragment() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < urlString.length(); ++i) {
            char c = urlString.charAt(i);
            if (c == '#') {
                break;
            }
            sb.append(c);
        }
        return system().util()
                .createUrl(sb.toString());
    }

    /**
     * Get a new Url object based on this one, but with the query and fragment (if any) removed.
     * 
     * @return The new Url object.
     */
    public Url withoutQueryOrFragment() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < urlString.length(); ++i) {
            char c = urlString.charAt(i);
            if (c == '?' || c == '#') {
                break;
            }
            sb.append(c);
        }
        return system().util()
                .createUrl(sb.toString());
    }
}