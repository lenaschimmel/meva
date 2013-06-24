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

import org.itemscript.core.JsonSystem;
import org.itemscript.core.exceptions.ItemscriptError;

/**
 * A scheme parser for HTTP-like URLs.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 */
public final class HttpLikeSchemeParser implements SchemeParser {
    private String urlString;
    private String remainder;
    private int length;
    private boolean hasScheme;
    private String scheme;
    private String authority;
    private String userInformation;
    private String hostname;
    private boolean hasPort;
    private String port;
    private String pathString;
    private boolean hasQuery;
    private boolean hasFragment;
    private String directory;
    private String filename;
    private Path path = new Path();;
    private String queryString;
    private Query query = new Query();
    private String fragmentString;
    private Fragment fragment = new Fragment();

    private Url _parse(JsonSystem system, int endOfScheme) {
        if (endOfScheme == 0) {
            hasScheme = true;
        } else {
            hasScheme = false;
        }
        int startOfPath;
        if (hasScheme) {
            remainder = null;
            scheme = null;
            hostname = null;
            port = null;
            startOfPath = 0;
        } else {
            scheme = substring(0, endOfScheme).toLowerCase();
            remainder = urlString.substring(endOfScheme + 1);
            int endOfAuthority = findAuthority(endOfScheme);
            startOfPath = endOfAuthority;
        }
        int endOfPath = findPath(startOfPath);
        int endOfPathOrQuery = endOfPath;
        if (hasQuery) {
            endOfPathOrQuery = findQuery(endOfPath);
        }
        if (hasFragment) {
            int endOfFragment = findFragment(endOfPathOrQuery);
        }
        return new Url(system, urlString, remainder, scheme, authority, userInformation, hostname, port,
                pathString, path, directory, filename, queryString, query, fragmentString, fragment);
    }

    private char charAt(int index) {
        // At the end of the string or past it, return a 0 char.
        if (index >= urlString.length()) { return 0; }
        return urlString.charAt(index);
    }

    private void decodePath() {
        // Look for a leading '/' indicating a rooted path.
        int startOfFirstNonRootComponent = 0;
        if (pathString.length() > 0 && pathString.charAt(0) == '/') {
            path.add("/");
            startOfFirstNonRootComponent = 1;
        }
        int startOfComponent = startOfFirstNonRootComponent;
        for (int i = startOfFirstNonRootComponent; i <= pathString.length(); ++i) {
            char c;
            if (i == pathString.length()) {
                c = 0;
            } else {
                c = pathString.charAt(i);
            }
            if (c == '/' || c == 0) {
                if (i > startOfComponent) {
                    String component = pathString.substring(startOfComponent, i);
                    path.add(Url.decode(component));
                }
                startOfComponent = i + 1;
            }
        }
        // Look for a trailing '/' indicating no filename. If not found, set the directory & filename.
        if (pathString.length() > 0 && pathString.charAt(pathString.length() - 1) != '/') {
            filename = path.get(path.size() - 1);
            directory = pathString.substring(0, pathString.length() - filename.length());
        } else {
            directory = pathString;
            filename = null;
        }
    }

    private void decodeQuery() {
        boolean inKey = true;
        String key = "";
        int startOfKey = 0;
        int startOfValue = 0;
        for (int i = 0; i <= queryString.length(); ++i) {
            char c;
            if (i == queryString.length()) {
                c = 0;
            } else {
                c = queryString.charAt(i);
            }
            if (inKey) {
                if (c == '=') {
                    key = Url.decode(queryString.substring(startOfKey, i));
                    inKey = false;
                    startOfValue = i + 1;
                } else if (c == '&' || c == 0) {
                    // A key with no value....
                    key = queryString.substring(startOfKey, i);
                    pushValue(key, "");
                    inKey = true;
                    startOfKey = i + 1;
                }
            } else {
                if (c == '&' || c == 0) {
                    String value = Url.decode(queryString.substring(startOfValue, i));
                    pushValue(key, value);
                    inKey = true;
                    startOfKey = i + 1;
                }
            }
        }
    }

    private int findAuthority(int endOfScheme) {
        // First check that after the scheme comes a double slash "//" - if not, there's no authority.
        if (charAt(endOfScheme + 1) != '/' || charAt(endOfScheme + 2) != '/') {
            authority = null;
            userInformation = null;
            hostname = null;
            port = null;
            return endOfScheme + 1;
        }
        int startOfAuthority = endOfScheme + 3;
        int endOfAuthority = 0;
        boolean hasUserInformation = false;
        boolean hasPort = false;
        // Look for a /, ?, or # to end the authority.
        for (int i = startOfAuthority; i <= length; ++i) {
            char c = charAt(i);
            if (c == '@') {
                hasUserInformation = true;
            } else if (c == 0) {
                endOfAuthority = i;
                break;
            } else if (c == '/') {
                endOfAuthority = i;
                break;
            }
        }
        // The authority can be empty even if the url had a scheme e.g. "file:///foo/bar/"
        if (startOfAuthority == endOfAuthority) {
            authority = null;
        } else {
            authority = substring(startOfAuthority, endOfAuthority).toLowerCase();
        }
        if (authority != null) {
            // Now break it up and look for a user information section, host, and port.
            String hostnameAndPort = "";
            if (hasUserInformation) {
                for (int i = 0; i < authority.length(); ++i) {
                    char c = authority.charAt(i);
                    if (c == '@') {
                        userInformation = authority.substring(0, i);
                        hostnameAndPort = authority.substring(i + 1);
                        break;
                    }
                }
            } else {
                userInformation = null;
                hostnameAndPort = authority;
            }
            // Now look for a hostname and maybe a port:
            for (int i = 0; i <= hostnameAndPort.length(); ++i) {
                char c;
                if (i == hostnameAndPort.length()) {
                    c = 0;
                } else {
                    c = hostnameAndPort.charAt(i);
                }
                if (c == ':') {
                    hostname = hostnameAndPort.substring(0, i);
                    port = hostnameAndPort.substring(i + 1);
                    break;
                } else if (c == 0) {
                    hostname = hostnameAndPort;
                    port = null;
                }
            }
        } else {
            userInformation = null;
            hostname = null;
            port = null;
        }
        return endOfAuthority;
    }

    private int findFragment(int endOfPathOrQuery) {
        int startOfFragment = endOfPathOrQuery + 1;
        int endOfFragment = 0;
        // For fragments they always run to the end of the string, but we're gonna go by character by character just for the hell of it...
        for (int i = startOfFragment; i <= length; ++i) {
            char c = charAt(i);
            if (c == 0) {
                endOfFragment = i;
                break;
            }
        }
        fragmentString = substring(startOfFragment, endOfFragment);
        fragment = Fragment.decodeFragment(fragmentString);
        return endOfFragment;
    }

    private int findPath(int startOfPath) {
        int endOfPath = 0;
        for (int i = startOfPath; i <= length; ++i) {
            char c = charAt(i);
            // The path runs until the end of the string or a ? or # character.
            if (c == 0) {
                endOfPath = i;
                hasQuery = false;
                hasFragment = false;
                break;
            } else if (c == '?') {
                endOfPath = i;
                hasQuery = true;
                break;
            } else if (c == '#') {
                endOfPath = i;
                hasQuery = false;
                hasFragment = true;
                break;
            }
        }
        // An empty path with a scheme is equivalent to "/" - e.g. a URL like "http://well.com"
        // An empty path with no scheme is equivalent to null, though - e.g. "?foo=bar" or "#someanchor"
        if (startOfPath == endOfPath) {
            if (hasScheme) {
                pathString = null;
            } else {
                pathString = "/";
            }
        } else {
            pathString = substring(startOfPath, endOfPath);
        }
        // if there was a scheme, paths must start with "/"
        if (!hasScheme) {
            if (pathString.charAt(0) != '/') { throw new ItemscriptError(
                    "error.itemscript.Url.findPath.url.with.scheme.path.did.not.start.with.slash", pathString); }
        }
        if (pathString != null) {
            decodePath();
        }
        return endOfPath;
    }

    private int findQuery(int endOfPath) {
        int startOfQuery = endOfPath + 1;
        int endOfQuery = 0;
        for (int i = startOfQuery; i <= length; ++i) {
            char c = charAt(i);
            if (c == 0) {
                endOfQuery = i;
                hasFragment = false;
                break;
            } else if (c == '#') {
                endOfQuery = i;
                hasFragment = true;
                break;
            }
        }
        queryString = substring(startOfQuery, endOfQuery);
        decodeQuery();
        return endOfQuery;
    }

    public Url parse(JsonSystem system, String urlString, int endOfScheme) {
        this.urlString = urlString;
        this.length = urlString.length();
        return _parse(system, endOfScheme);
    }

    public void pushValue(String key, String value) {
        List<String> list = query.get(key);
        if (list == null) {
            list = new ArrayList<String>();
            query.put(key, list);
        }
        list.add(value);
    }

    private String substring(int begin, int end) {
        return urlString.substring(begin, end);
    }
}
