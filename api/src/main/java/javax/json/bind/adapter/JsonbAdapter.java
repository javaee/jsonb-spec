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
package javax.json.bind.adapter;

/**
 * <p>
 *     Allows to define custom mapping for given java type.
 *     The target type could be string or some mappable java type.
 * </p>
 *
 * @param <From>
 *     The type that JSON Binding does not know how to handle. An adapter is written
 *      to allow this type to be adapted to "To" type
 * @param <To>
 *     The type that JSON Binding knows how to handle out of the box
 *
 * <p>
 * Adapter runtime "From" and "To" generic types are inferred from subclassing information, be sure to provide it,
 * so it is not deleted by type erasure.
 * </p>
 *
 * <pre>
 * {@code
 *      //Generic information is provided by sublcassing.
 *      class BoxToCrateAdapter implements JsonbAdapter<Box<Integer>, Crate<String>> {...};
 *      jsonbConfig.withAdapters(new BoxToCrateAdapter());
 *
 *      //Generic information is provided by subclassing with anonymous class
 *      jsonbConfig.withAdapters(new JsonbAdapter<Box<Integer>,Crate<String>> {...};
 *
 *      //in following case..
 *      BoxToCrateAdapter<T> implements JsonbAdapter<Box<T>,Integer<T>> {...}
 *
 *      //Here, generic information is lost due to type erasure
 *      jsonbConfig.withAdapters(new BoxToCrateAdapter<Integer>());
 *
 *      //instead this will work (note anonymous class curly braces)
 *      jsonbConfig.withAdapters(new BoxToCrateAdapter<Integer>(){});
 * }
 * </pre>
 *
 */
public interface JsonbAdapter<From, To> {

    /**
     * Converts an object of type "To" to type "From".
     * @param obj object to convert
     *
     * @return Converted object representing pojo to be set in business model
     * @throws Exception
     *  if there is an error during the conversion.
     */
    From adaptTo(To obj) throws Exception;

    /**
     * Converts an object of type "From" to type "To".
     * @param obj object to convert
     *
     * @return Converted object representing pojo to be set in business model
     * @throws Exception
     *  if there is an error during the conversion.
     */
    To adaptFrom(From obj) throws Exception;

}
