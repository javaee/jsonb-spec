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

import java.util.HashMap;
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
 one of the build methods in {@link Jsonb} factory class.
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

    /**
     * Protected default constructor, can be created
     */
    protected JsonbContext() { }

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

    /**
     * Builder design pattern class for creating JsonbContext objects.
     */
    public static class Builder {

        /**
         * Default JsonbContext Builder constructor.
         */
        public Builder() { };

        private Class<?>[] recognizedClasses;
        private Map<String, Object> configuration = new HashMap<>();

        /**
        * List of classes that the new context object needs to recognize.
        *
        * The implementation will not only recognize the provided classes, but it will
        * also recognize any classes that are directly or indirectly statically referenced
        * Subclasses of referenced classes, nor {@link javax.json.bind.annotation.JsonbTransient} annotated classes
        * are recognized.
        *
        * Can be empty, in which case a default {@link JsonbContext} which only
        * recognizes default spec-defined classes will be returned.
        *
        * @param classes list of classes to be recognized
        *
        * @return 'this' instance, for fluent support
        */
       public Builder setClasses(final Class<?> ... classes) {
           this.recognizedClasses = classes;
           return this;
       }

        /**
         * Set the particular property for the implementation of
         * <tt>JsonbContext</tt>. The method can only be used to set one of
         * the standard JSON Binding properties defined in this class or a provider specific
         * property. Attempting to set an undefined property will result in
         * a JsonbConfigurationException being thrown.
         *
         * @param name the name of the property to be set. This value can either
         *              be specified using one of the constant fields or a user
         *              supplied string.
         * @param value the value of the property to be set
         *
         * @return 'this' instance, for fluent support
         */
        public Builder setProperty(final String name, final Object value) {
            this.configuration.put(name, value);
            return this;
        }

        /**
         * Returns a new instance of JsonbContext based on the parameters passed to
         * this builder.
         *
         * @return JsonbContext
         *      A new instance of JsonbContext class. Always a non-null valid object.
         *
         * @throws javax.json.bind.JsonbException
         *      If an error was encountered while creating the JsonbContext instance,
         *      such as (but not limited to) no JSON Binding provider is found, classes
         *      provide conflicting annotations.
         *
         * @throws IllegalArgumentException
         *      If there's an error processing the set parameters, such as the non-null
         *      parameter is assigned null value, unrecognized property is set.
         */
        public JsonbContext build() {
            return Jsonb.createContext(recognizedClasses);
        }

    }

}
