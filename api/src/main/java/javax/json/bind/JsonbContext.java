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
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
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

package javax.json.bind;

import java.util.Map;

/**
 * <p>
 * The <tt>JsonbContext</tt> class provides the client's entry point to the
 * JSON Binding API. It provides an abstraction for managing the Json/Java binding
 * information necessary to implement the JAXB binding framework operations:
 * unmarshal (input JSON, output Java objects) and marshal (input Java objects,
 * output JSON data).
 *
 * <p>A client application normally obtains new instances of this class using
 * one of the createContext methods in {@link Jsonb} factory class.
 *
 * <p>All the methods in this class are safe for use by multiple concurrent
 * threads.
 *
 * @see Jsonb
 * @see JsonbMarshaller
 * @see JsonbUnmarshaller
 * @see java.util.ServiceLoader
 * @author Martin Grebac
 * @since JSON Binding 1.0
 */
public abstract class JsonbContext {

    private JsonbContext() { }

    /**
     * Creates a {@link JsonbMarshaller} object that can be used to convert java content
     * tree into JSON data.
     *
     * @throws JsonbException if an error was encountered while creating the marshaler instance
     * @return JsonbMarshaller instance
     */
    public abstract JsonbMarshaller createMarshaller();

    /**
     * Creates a {@link JsonbMarshaller} object that can be used to convert java content
     * tree into JSON data. Configured with standard or provider specific properties.
     *
     * @param configuration Standard or provider specific properties.
     *        Can be null - means an empty map.
     *
     * @throws JsonbException if an error was encountered while creating the marshaler instance
     * @throws JsonbConfigurationException if an error was encountered while configuring the
     *         marshaler instance with configuration properties
     *
     * @return JsonbMarshaller instance
     */
    public abstract JsonbMarshaller createMarshaller(Map<String, ?> configuration);

    /**
     * Creates a {@link JsonbUnmarshaller} object that can be used to convert JSON
     * data into a java content tree.
     *
     * @throws JsonbException if an error was encountered while creating the marshaler instance
     * @throws JsonbConfigurationException if an error was encountered while configuring the
     *         marshaler instance with configuration properties
     *
     * @return JsonbUnmarshaller instance
     */
    public abstract JsonbUnmarshaller createUnmarshaller();

    /**
     * Creates a {@link JsonbUnmarshaller} object that can be used to convert JSON
     * data into a java content tree.
     *
     * @param configuration Standard or provider specific properties.
     *        Can be null - means an empty map.
     *
     * @throws JsonbException if an error was encountered while creating the marshaler instance
     * @throws JsonbConfigurationException if an error was encountered while configuring the
     *         marshaler instance with configuration properties
     *
     * @return JsonbUnmarshaller instance
     */
    public abstract JsonbUnmarshaller createUnmarshaller(Map<String, ?> configuration);

}
