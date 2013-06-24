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

import java.util.List;
import java.util.Map;

/**
 * A representation of the pagination specified in a URL query string.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 */
public final class Pagination {
    private final int startRow;
    private final int numRows;
    private final String orderBy;
    private final boolean ascending;

    /**
     * Create a new Pagination from a set of URL query parameters.
     * 
     * @param params The URL query parameters to examine.
     */
    public Pagination(Map<String, List<String>> params) {
        if (params.containsKey(Query.START_ROW_KEY)) {
            startRow = Integer.parseInt(params.get(Query.START_ROW_KEY)
                    .get(0));
        } else {
            startRow = 0;
        }
        if (params.containsKey(Query.NUM_ROWS_KEY)) {
            numRows = Integer.parseInt(params.get(Query.NUM_ROWS_KEY)
                    .get(0));
        } else {
            numRows = 10;
        }
        if (params.containsKey(Query.ORDER_BY_KEY)) {
            orderBy = params.get(Query.ORDER_BY_KEY)
                    .get(0);
        } else {
            orderBy = null;
        }
        if (params.containsKey(Query.ASCENDING_KEY)) {
            ascending = params.get(Query.ASCENDING_KEY)
                    .get(0)
                    .equalsIgnoreCase("true");
        } else {
            ascending = true;
        }
    }

    /**
     * Get whether this pagination is ascending or descending.
     * 
     * @return True if this pagination is ascending, false if it is descending.
     */
    public boolean ascending() {
        return ascending;
    }

    /**
     * Get the number of rows specified by this pagination.
     * 
     * @return The number of rows to return.
     */
    public int numRows() {
        return numRows;
    }

    /**
     * Get the column to order by.
     * 
     * @return The name of the column to order by.
     */
    public String orderBy() {
        return orderBy;
    }

    /**
     * Get the starting row specified by this pagination.
     * 
     * @return The starting row.
     */
    public int startRow() {
        return startRow;
    }
}