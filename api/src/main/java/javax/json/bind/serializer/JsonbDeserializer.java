/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2016, 2017 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
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

import javax.json.stream.JsonParser;
import java.lang.reflect.Type;

/**
 * <p>Interface representing a custom deserializer for a given type. It provides a low-level API for java object
 * deserialization from JSON stream using {@link JsonParser}. Unlike {@link javax.json.bind.adapter.JsonbAdapter},
 * which acts more as converter from one java type to another, deserializer provides more fine grained control over
 * deserialization process.</p>
 *
 * <p>{@link DeserializationContext} acts as JSONB runtime, able to deserialize any java object provided.</p>
 *
 * <p>Sample of custom Deserializer:</p>
 * <pre>
 *     class Box {
 *         public BoxInner boxInnerObject;
 *         public String name;
 *     }
 *
 *     BoxDeserializer implements JsonbDeserializer&lt;Box&gt; {
 *         public Box deserialize(JsonParser parser, DeserializationContext ctx, Type rtType) {
 *             Box = new Box();
 *
 *             while (parser.hasNext()) {
 *                 Event event = parser.next();
 *
 *                 if (event == JsonParser.Event.KEY_NAME &amp;&amp; parser.getString().equals("boxInnerObject") {
 *                     // Deserialize inner object
 *                     box.boxInnerObject = ctx.deserialize(BoxInner.class, jsonParser);
 *
 *                 } else if (event == JsonParser.Event.KEY_NAME &amp;&amp; parser.getString().equals("name") {
 *                     // Deserialize name property
 *                     parser.next(); // move to VALUE
 *                     box.name = parser.getString();
 *                 }
 *             }
 *
 *             return box;
 *         }
 *     }
 * </pre>
 *
 * <p>Deserializers are registered using {@link javax.json.bind.JsonbConfig#withDeserializers(JsonbDeserializer[])}
 * method or using {@link javax.json.bind.annotation.JsonbTypeDeserializer} annotation on type.</p>
 *
 * @param <T> Type to bind deserializer for.
 * @see javax.json.bind.JsonbConfig
 * @see javax.json.bind.annotation.JsonbTypeDeserializer
 * @see JsonbSerializer
 * @see javax.json.bind.adapter.JsonbAdapter
 * @since JSON Binding 1.0
 */
public interface JsonbDeserializer<T> {

    /**
     * Deserialize JSON stream into object.
     *
     * @param parser
     *      Json parser.
     * @param ctx
     *      Deserialization context.
     * @param rtType
     *      Type of returned object.
     * @return Deserialized instance.
     */
    T deserialize(JsonParser parser, DeserializationContext ctx, Type rtType);
}
