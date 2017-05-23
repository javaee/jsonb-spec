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
package javax.json.bind.adapter;

/**
 * <p>Allows to define custom mapping for given java type. The target type could be string or some
 * mappable java type.</p>
 *
 * <p>On serialization "Original" type is converted into "Adapted" type. After that "Adapted" type is serialized to
 * JSON the standard way.</p>
 *
 * <p>On deserialization it works the reverse way: JSON data are deserialized into "Adapted" type which is converted
 * to "Original" type after that.</p>
 *
 * <p>Adapters are registered using {@link javax.json.bind.JsonbConfig#withAdapters(JsonbAdapter[])} method
 * or using {@link javax.json.bind.annotation.JsonbTypeAdapter} annotation on class field.</p>
 *
 * @param <Original> The type that JSONB doesn't know how to handle
 * @param <Adapted> The type that JSONB knows how to handle out of the box
 *
 * <p>Adapter runtime "Original" and "Adapted" generic types are inferred from subclassing information,
 * which is mandatory for adapter to work.</p>
 *
 * <p>Sample 1:</p>
 * <pre>
 * {@code
 *      // Generic information is provided by subclassing.
 *      class BoxToCrateAdapter implements JsonbAdapter<Box<Integer>, Crate<String>> {...};
 *      jsonbConfig.withAdapters(new BoxToCrateAdapter());
 *
 *      // Generic information is provided by subclassing with anonymous class
 *      jsonbConfig.withAdapters(new JsonbAdapter<Box<Integer>, Crate<String>> {...});
 * }
 * </pre>
 *
 * <p>Sample 2:</p>
 * <pre>
 * {@code
 *      BoxToCrateAdapter<T> implements JsonbAdapter<Box<T>, Integer> {...};
 *
 *      // Bad way: Generic type information is lost due to type erasure
 *      jsonbConfig.withAdapters(new BoxToCrateAdapter<Integer>());
 *
 *      // Proper way: Anonymous class holds generic type information
 *      jsonbConfig.withAdapters(new BoxToCrateAdapter<Integer>(){});
 * }
 * </pre>
 *
 * @see javax.json.bind.JsonbConfig
 * @see javax.json.bind.annotation.JsonbTypeAdapter
 * @since JSON Binding 1.0
 */
public interface JsonbAdapter<Original, Adapted> {

    /**
     * This method is used on serialization only. It contains a conversion logic from type Original to type Adapted.
     * After conversion Adapted type will be mapped to JSON the standard way.
     *
     * @param obj
     *      Object to convert.
     * @return Converted object which will be serialized to JSON.
     * @throws Exception if there is an error during the conversion.
     */
    Adapted adaptToJson(Original obj) throws Exception;

    /**
     * This method is used on deserialization only. It contains a conversion logic from type Adapted to type Original.
     *
     * @param obj
     *      Object to convert.
     * @return Converted object representing pojo to be set into object graph.
     * @throws Exception if there is an error during the conversion.
     */
    Original adaptFromJson(Adapted obj) throws Exception;
}
