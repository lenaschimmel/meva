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

import org.itemscript.core.HasSystem;
import org.itemscript.core.JsonSystem;
import org.itemscript.core.JsonUtil;
import org.itemscript.core.exceptions.ItemscriptError;
import org.itemscript.core.values.JsonObject;

/**
 * A scheme-parser for URLs with an unknown scheme.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 */
final class UnknownSchemeParser implements SchemeParser {
    public Url parse(JsonSystem system, String urlString, int endOfScheme) {
        String scheme = urlString.substring(0, endOfScheme);
        String remainder = urlString.substring(endOfScheme + 1, urlString.length());
        boolean foundFragment = false;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < urlString.length(); ++i) {
            char c = urlString.charAt(i);
            if (foundFragment) {
                sb.append(c);
            }
            if (c == '#') {
                foundFragment = true;
            }
        }
        String fragmentString = null;
        Fragment fragment = null;
        if (sb.length() > 0) {
            fragmentString = sb.toString();
            fragment = Fragment.decodeFragment(fragmentString);
        }
        return new Url(system, urlString, remainder, scheme, null, null, null, null, null, null, null, null, null,
                null, fragmentString, fragment);
    }
}

/**
 * A class for creating URLs from String. This is used by {@link JsonUtil}.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 *
 */
public class UrlFactory implements HasSystem {
    private static String addBasePath(Url baseUrl, Url relativeUrl, String finalUrl) {
        // If the relative URL didn't have a path, use the entire original path:
        finalUrl += baseUrl.pathString();
        // And if the relative URL had no query, use the entire original query if there was one:
        if (relativeUrl.queryString() == null || relativeUrl.queryString()
                .length() == 0) {
            if (baseUrl.queryString() != null && baseUrl.queryString()
                    .length() > 0) {
                finalUrl += "?" + baseUrl.queryString();
            }
        } else {
            // Otherwise add the relative URL's query:
            finalUrl += "?" + relativeUrl.queryString();
        }
        return finalUrl;
    }

    private static List<String> combinePaths(Url baseUrlObj, Url relativeUrlObj) {
        List<String> unreducedPath = new ArrayList<String>();
        if (relativeUrlObj.path()
                .get(0)
                .equals("/")) {
            unreducedPath.addAll(relativeUrlObj.path());
        } else {
            // Otherwise add the relative URL path to the base URL's directory:
            int lastBaseElementToAdd;
            // If it had a filename, stop before that.. unless the filename was ".."!
            if (baseUrlObj.filename() != null && baseUrlObj.filename()
                    .length() > 0) {
                if (baseUrlObj.filename()
                        .equals("..")) {
                    lastBaseElementToAdd = baseUrlObj.path()
                            .size() - 1;
                } else {
                    lastBaseElementToAdd = baseUrlObj.path()
                            .size() - 2;
                }
            } else {
                lastBaseElementToAdd = baseUrlObj.path()
                        .size() - 1;
            }
            for (int i = 0; i <= lastBaseElementToAdd; ++i) {
                unreducedPath.add(baseUrlObj.path()
                        .get(i));
            }
            unreducedPath.addAll(relativeUrlObj.path());
        }
        return unreducedPath;
    }

    private static String getSchemeHostnameAndPort(Url url) {
        if (url.scheme()
                .equals("mem")) {
            // Sort of a hack...
            return "mem:";
        } else {
            return url.scheme()
                    + "://"
                    + (url.hostname() != null ? url.hostname() + (url.port() != null ? ":" + url.port() : "") : "");
        }
    }

    private static boolean isSchemeChar(char c) {
        if (c == '+' || c == '.' || c == '-') { return true; }
        if (Character.isLetterOrDigit(c)) { return true; }
        return false;
    }

    private final JsonSystem system;
    private final static String UNKNOWN_SCHEME = "UNKNOWN SCHEME";
    private final static String NO_SCHEME = "NO SCHEME";
    public static final String SCHEME_PARSER_FACTORIES_PATH = "mem:/itemscript/schemeParserFactories";
    private final JsonObject schemeParserFactories;

    /**
     * Create a new UrlFactory for the associated JsonSystem.
     * 
     * @param system The associated JsonSystem.
     */
    public UrlFactory(JsonSystem system) {
        this.system = system;
        SchemeParserFactory httpLike = new SchemeParserFactory() {
            public SchemeParser create() {
                return new HttpLikeSchemeParser();
            }
        };
        schemeParserFactories = system().createObject();
        schemeParserFactories.putNative(UrlFactory.NO_SCHEME, httpLike);
        schemeParserFactories.putNative(Url.MEM_SCHEME, httpLike);
        schemeParserFactories.putNative(Url.HTTP_SCHEME, httpLike);
        schemeParserFactories.putNative(Url.HTTPS_SCHEME, httpLike);
        schemeParserFactories.putNative(Url.FILE_SCHEME, httpLike);
        SchemeParserFactory unknown = new SchemeParserFactory() {
            public SchemeParser create() {
                return new UnknownSchemeParser();
            }
        };
        schemeParserFactories.putNative(UrlFactory.UNKNOWN_SCHEME, unknown);
    }

    private String addRelativePath(Url baseUrlObj, Url relativeUrlObj, String finalUrl) {
        // If the relative URL's path was absolute, ignore the base URL's path:
        List<String> unreducedPath = UrlFactory.combinePaths(baseUrlObj, relativeUrlObj);
        // Now, throw out the leading "/" entry for now, and remove any components consisting only of "."
        List<String> reducedPath = reducePath(unreducedPath);
        // Bearing in mind that the path should start with a slash...
        if (reducedPath.size() > 0) {
            for (int i = 0; i < reducedPath.size(); ++i) {
                finalUrl += "/";
                finalUrl += reducedPath.get(i);
            }
        } else {
            finalUrl += "/";
        }
        // OK - now, if the relative URL ended with a "/" AND was not just the string "/" AND the final URL doesn't
        // already end with a slash, append one...
        if (relativeUrlObj.pathString()
                .length() > 1 && relativeUrlObj.pathString()
                .endsWith("/") && !finalUrl.endsWith("/")) {
            finalUrl += "/";
        }
        // Now if the relative URL had a query, add it:
        if (relativeUrlObj.queryString() != null && relativeUrlObj.queryString()
                .length() > 0) {
            finalUrl += "?" + relativeUrlObj.queryString();
        }
        return finalUrl;
    }

    /**
     * Create a new Url object from the given Url string.
     * 
     * @param url The string to parse.
     * @return A new Url object.
     * @throws ItemscriptError If the string cannot be parsed as a Url.
     */
    public Url create(String url) {
        if (url == null) { return null; }
        // Remove leading and trailing whitespace first...
        url = url.trim();
        int startOfScheme = 0;
        int endOfScheme = 0;
        for (int i = startOfScheme; i < url.length(); ++i) {
            char c = url.charAt(i);
            if (!UrlFactory.isSchemeChar(c)) {
                if (c == ':') {
                    endOfScheme = i;
                    break;
                } else {
                    break;
                }
            }
        }
        String scheme = null;
        if (endOfScheme > startOfScheme) {
            scheme = url.substring(startOfScheme, endOfScheme)
                    .toLowerCase();
        } else {
            scheme = UrlFactory.NO_SCHEME;
        }
        if (parserFactory(scheme) == null) {
            scheme = UrlFactory.UNKNOWN_SCHEME;
        }
        SchemeParser schemeParser = parserFactory(scheme).create();
        return schemeParser.parse(system, url, endOfScheme);
    }

    /**
     * Create a new relative Url from the given base URL and relative URL.
     * 
     * @param baseUrl The base URL.
     * @param relativeUrl The relative URL.
     * @return A new Url object.
     */
    public Url createRelative(String baseUrl, String relativeUrl) {
        return createRelative(create(baseUrl), create(relativeUrl));
    }

    /**
     * Create a new relative Url from the given base URL and relative URL.
     * 
     * @param baseUrl The base URL.
     * @param relativeUrl The relative URL.
     * @return A new Url object.
     */
    public Url createRelative(Url baseUrl, String relativeUrl) {
        return createRelative(baseUrl, create(relativeUrl));
    }

    /**
     * Create a new relative Url from the given base URL and relative URL.
     * 
     * @param baseUrl The base URL.
     * @param relativeUrl The relative URL.
     * @return A new Url object.
     */
    public Url createRelative(Url baseUrl, Url relativeUrl) {
        String baseUrlString = baseUrl.toString();
        String relativeUrlString = relativeUrl.toString();
        // per RFC1808/18
        if (baseUrlString == null) {
            baseUrlString = "";
        }
        baseUrlString = baseUrlString.trim();
        if (relativeUrlString == null) {
            relativeUrlString = "";
        }
        relativeUrlString = relativeUrlString.trim();
        // If the base URL is empty, we return the relative URL as the whole URL.
        if (baseUrlString.length() == 0) { return create(relativeUrlString); }
        // If the relative URL is empty, we return the base URL as the whole URL.
        if (relativeUrlString.length() == 0) { return create(baseUrlString); }
        // If the putatively relative URL had a scheme, it wasn't actually relative, so return it as the whole URL.
        if (relativeUrl.scheme() != null) { return relativeUrl; }
        // If the base URL didn't have a scheme, uh, it wasn't a very good base URL now was it?
        if (baseUrl.scheme() == null) { throw ItemscriptError.internalError(baseUrl,
                "createRelative.baseUrl.did.not.have.a.scheme", baseUrlString); }
        // OK, so the final URL will have the scheme/hostname/port of the base URL, if it had any.
        String finalUrl = UrlFactory.getSchemeHostnameAndPort(baseUrl);
        // If the relative URL had a path, add that path to the final URL.
        if (relativeUrl.pathString() != null && relativeUrl.pathString()
                .length() > 0) {
            finalUrl = addRelativePath(baseUrl, relativeUrl, finalUrl);
        } else {
            finalUrl = UrlFactory.addBasePath(baseUrl, relativeUrl, finalUrl);
        }
        // Finally, if the relative URL had a fragment section, add it (the base fragment is always ignored unless the relative URL was empty, which is handled
        // above):
        if (relativeUrl.fragmentString() != null && relativeUrl.fragmentString()
                .length() > 0) {
            finalUrl += "#" + relativeUrl.fragmentString();
        }
        return create(finalUrl);
    }

    private SchemeParserFactory parserFactory(String name) {
        return (SchemeParserFactory) schemeParserFactories.getNative(name);
    }

    private List<String> reducePath(List<String> unreducedPath) {
        // Otherwise we need to remove the "." and ".." entries. First, the leading "/" and any "." entries:
        List<String> reducedPath = new ArrayList<String>();
        for (int i = 1; i < unreducedPath.size(); ++i) {
            String component = unreducedPath.get(i);
            if (!component.equals(".")) {
                reducedPath.add(component);
            }
        }
        // Now, as long as any exist, remove any combinations of a component followed by .., iteratively.
        LOOKFORDOTDOTS : while (true) {
            int indexOfFirstDotDot = -1;
            COMPONENTS : for (int i = 0; i < reducedPath.size(); ++i) {
                String component = reducedPath.get(i);
                if (component.equals("..")) {
                    indexOfFirstDotDot = i;
                    break COMPONENTS;
                }
            }
            // If we didn't find any, move on.
            if (indexOfFirstDotDot == -1) {
                break LOOKFORDOTDOTS;
            }
            // Otherwise create a new reduced path with the component before the first ".." and the ".." itself removed.
            if (indexOfFirstDotDot == 0) { throw ItemscriptError.internalError(this,
                    "first.component.was.dot.dot", unreducedPath + ""); }
            List<String> newReducedPath = new ArrayList<String>();
            // OK, now add all the components up to the one before the ".."
            for (int i = 0; i < (indexOfFirstDotDot - 1); ++i) {
                newReducedPath.add(reducedPath.get(i));
            }
            // Now add all the components after the ".." and repeat...
            for (int i = indexOfFirstDotDot + 1; i < reducedPath.size(); ++i) {
                newReducedPath.add(reducedPath.get(i));
            }
            reducedPath = newReducedPath;
        }
        return reducedPath;
    }

    /**
     * Get the JsonObject where SchemeParserFactories are stored.
     * This is a method used during bootstrapping to get hold of the scheme parsers - since the
     * scheme parsers are required in order to decode a URL and find a value, we have to give a
     * direct reference that the system can itself store in a location during bootstrapping.
     * 
     * @return The JsonObject where SchemeParserFactories are stored.
     */
    public JsonObject schemeParserFactories() {
        return schemeParserFactories;
    }

    @Override
    public JsonSystem system() {
        return system;
    }
}