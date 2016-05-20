/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2016 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package javax.json.bind.serializer;

import javax.json.stream.JsonGenerator;

/**
 * Serialize java object into JSON stream. Either JSONP {@link JsonGenerator} can be used directly
 * as lowest possible level access or {@link SerializationContext} which acts as JSONB runtime, able to serialize
 * any java object provided.
 *
 * <pre>
 * class BoxSerializer implements JsonbSerializer&lt;Box&gt; {
 *
 *      public void serialize(T obj, JsonGenerator generator, SerializationContext ctx) {
 *          generator.write("customBoxProperty", "property value"); //custom box key with value
 *          generator.writeStartObject("customObjectInsideBox");    //start new JSON object inside box
 *          generator.write("customProperty", "Custom value");      //write value inside
 *          generator.writeEndObject();
 *
 *          //runs JSONB serialization as for root object
 *          ctx.serialize("boxInternals", obj.getSomeInternals(), generator);      //serialize box inner object
 *          ctx.serialize("currentDate", new Date(), generator);                   //and arbitrary one
 *      }
 * }
 * </pre>
 *
 * In addition to {@link javax.json.bind.adapter.JsonbAdapter}, which acts more as converters from one java type
 * to another, (de)serializer api provides more fine grained control over (de)serialization process.
 *
 * @param <T> Type to bind serializer for.
 *
 * @since JSON Binding 1.0
 *
 */
public interface JsonbSerializer<T> {

    /**
     * Serialize object into JSON stream.
     *
     * @param obj
     *      Object to serialize
     * @param generator
     *      JSON generator to use
     * @param ctx
     *      JSONB mapper context
     */
    void serialize(T obj, JsonGenerator generator, SerializationContext ctx);
}
