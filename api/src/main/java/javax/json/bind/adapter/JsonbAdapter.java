/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2015 Oracle and/or its affiliates. All rights reserved.
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

import java.lang.reflect.Type;

/**
 * <p>
 *     Allows to define custom mapping for given java type.
 *     The target type could be string or some mappable java type.
 * </p>
 *
 * BoundType -
 *      The type that JSON Binding does not know how to handle. An adapter is written
 *      to allow this type to be used as an in-memory representation through
 *      the <tt>ValueType</tt>.
 *
 * ValueType -
 *      The type that JSON Binding knows how to handle out of the box.
 *
 * <p>
 * Exactly two default methods must be overridden.
 * If BoundType is generic type, it is recommended to override getRuntimeBoundType method.
 * Otherwise getBoundType method should be overridden.
 *
 * If ValueType is generic type, it is recommended to override getRuntimeValueType method.
 * Otherwise getValueType method should be overridden.
 * </p>
 *
 */
public interface JsonbAdapter {

    /**
     * Converts an object to type T.
     * @param obj object to convert
     * @param <ValueType> type we know how to serialize
     * @return object we need how to serialize
     * @throws Exception
     *  if there is an error during the conversion.
     */
    default <ValueType> ValueType adaptTo(Object obj) throws Exception {
        throw new UnsupportedOperationException();
    }

    /**
     * Converts an object to type T.
     * @param obj object to convert
     * @param <BoundType> type we don't know how to deserialize
     * @return object we don't know how to deserialize
     * @throws Exception
     *  if there is an error during the conversion.
     */
    default <BoundType> BoundType adaptFrom(Object obj) throws Exception {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns Value Type.
     *
     * @return Non-generic Value Type
     */
    default Class<?> getValueType() {
        return null;
    }

    /**
     * Returns Bound Type.
     *
     * @return Non-generic Bound Type
     */
    default Class<?> getBoundType() {
        return null;
    }

    /**
     * Returns runtime Value Type.
     *
     * @return runtime Value Type
     */
    default Type getRuntimeValueType() {
        return null;
    }

    /**
     * Returns runtime Bound Type.
     *
     * @return runtime Bound Type
     */
    default Type getRuntimeBoundType() {
        return null;
    }

    /**
     * Returns false if null values should be handled by JSON Binding.
     *
     * @return false if null values should be handled by JSON Binding
     */
    default boolean handlesNullValue() {
        return false;
    }
}
