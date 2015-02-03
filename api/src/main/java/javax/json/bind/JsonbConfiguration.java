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

/**
 * <p>
 * <a name="supportedProps"></a>
 * <b>Supported Properties</b><br>
 * <blockquote>
 * <p>
 * All JSON Binding providers are required to support the following set of properties.
 * Some providers may support additional properties.
 * <dl>
 *   <dt><tt>jsonb.formatted.output</tt> - java.lang.Boolean
 *   <dd>Controls whether or not the JsonbMarshaller will format
 *       the resulting JSON data with line breaks and indentation. A
 *       true value for this property indicates human readable indented
 *       data, while a false value indicates unformatted data.
 *       The JsonbMarshaller will default to false (unformatted) if this
 *       property is not specified.
 * </dl>
 * <dl>
 *   <dt><tt>jsonb.marshaller.encoding</tt> - java.lang.String
 *   <dd>Encoding f
 *       The JsonbMarshaller will default to false (unformatted) if this
 *       property is not specified.
 * </dl>
 * <dl>
 *   <dt><tt>jsonb.marshaller.encoding</tt> - java.lang.String
 *   <dd>Encoding f
 *       The JsonbMarshaller will default to false (unformatted) if this
 *       property is not specified.
 * </dl>
 * </blockquote>
 *
 * @see JsonbConfigurationException
 * @author Martin Grebac
 * @since JSON Binding 1.0
 */
public abstract class JsonbConfiguration {

    /**
     * The name of the property used to specify whether or not the marshaled
     * JSON data is formatted with linefeeds and indentation.
     */
    public static final String JSON_BIND_FORMATTED_OUTPUT = "json.bind.formatted.output";

    /**
     * Set the particular configuration property to a new value. The method can
     * only be used to set one of the standard JSON Binding properties defined in
     * this class or a provider specific property. Attempting to set an undefined
     * property will result in a JsonbConfigurationException being thrown.
     * See <a href="#supportedProps"> Supported Properties</a>.
     *
     * @param name the name of the property to be set. This value can either
     * be specified using one of the constant fields or a user supplied
     * string.
     * @param value the value of the property to be set
     *
     * @return 'this' instance, for fluent support
     */
    public abstract Jsonb setProperty(final String name, final Object value);

    /**
     * Return value of particular configuration property. The method can
     * only be used to retrieve one of the standard JSON Binding properties defined
     * in this class or a provider specific property. Attempting to get an undefined
     * property will result in a JsonbConfigurationException being thrown.
     * See <a href="#supportedProps"> Supported Properties</a>.
     *
     * @param name the name of the property to retrieve
     *
     * @return the value of the requested property
     *
     * @throws JsonbConfigurationException
     *      when there is an error retrieving the given property or value
     *      property name
     * @throws IllegalArgumentException if the name parameter is null.
     */
    public abstract Object getProperty(String name) throws JsonbConfigurationException;

}
