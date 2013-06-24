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

package org.itemscript.template.text;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.values.JsonArray;
import org.itemscript.core.values.JsonValue;
import org.itemscript.template.Template;
import org.itemscript.template.TemplateExec;
import org.itemscript.template.expression.Expression;

/**
 * @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 */
class Foreach extends Segment {
    private final Expression valueExpression;
    private final TextTemplate contents;
    private final TextTemplate join;

    /**
     * @param system
     * @param valueExpression The expression that returns the value to iterate over.
     * @param contents The template for each entry.
     * @param join The template to join with; may be null. 
     */
    public Foreach(JsonSystem system, Expression valueExpression, TextTemplate contents, TextTemplate join) {
        super(system);
        this.valueExpression = valueExpression;
        this.contents = contents;
        this.join = join;
    }

    @Override
    public JsonValue interpret(TemplateExec templateExec, JsonValue context) {
        JsonValue innerContext = valueExpression.interpret(templateExec, context);
        StringBuffer sb = new StringBuffer();
        if (innerContext != null && innerContext.isArray()) {
            JsonArray innerContextArray = innerContext.asArray();
            // For each element in the array, interpret the contents of the foreach in the context of that element.
            for (int i = 0; i < innerContextArray.size(); ++i) {
                sb.append(Template.coerceToString(contents.interpret(templateExec, innerContextArray.get(i))));
                // If we have a join section, interpret that for each element but the last in the surrounding context.
                if (join != null && i < (innerContextArray.size() - 1)) {
                    sb.append(Template.coerceToString(join.interpret(templateExec, context)));
                }
            }
        } else {
            // Silently ignore it if the value was null or not an array.
        }
        return system().createString(sb.toString());
    }
}