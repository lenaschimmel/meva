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

package org.itemscript.core.events;

import org.itemscript.core.values.JsonItem;
import org.itemscript.core.values.JsonValue;

/**
 * An event triggered on a {@link JsonItem}.
 * 
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 *
 */
public class Event {
    private final EventType eventType;
    private final String fragment;
    private final JsonValue value;

    /**
     * Create a new Event.
     * 
     * @param eventType The EventType of this event.
     * @param fragment The URL fragment identifying the value that changed.
     * @param value The value of the item in which this event occurred.
     */
    public Event(EventType eventType, String fragment, JsonValue value) {
        this.eventType = eventType;
        this.fragment = fragment;
        this.value = value;
    }

    /**
     * Get the type of event that occurred.
     * 
     * @return The type of event.
     */
    public final EventType eventType() {
        return eventType;
    }

    /**
     * Get the URL fragment for the value that changed or was removed.
     * 
     * @return The URL fragment.
     */
    public final String fragment() {
        return fragment;
    }

    /**
     * Get the value of the item where the change occurred. If the event was the
     * removal of the entire item, this will be null.
     * 
     * @return The value of the item where the change occurred.
     */
    public final JsonValue value() {
        return value;
    }
}