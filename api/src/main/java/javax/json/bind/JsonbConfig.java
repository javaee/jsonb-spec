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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * <a name="supportedProps"></a>
 * <b>Supported Properties</b><br>
 * <blockquote>
 * <p>
 * All JSON Binding providers are required to support the following set of properties.
 * Some providers may support additional properties.
 * <dl>
 *   <dt><tt>jsonb.to.json.formatted</tt> - java.lang.Boolean
 *   <dd>Controls whether or not the {@link javax.json.bind.Jsonb Jsonb} {@code toJson()}
 *       methods will format the resulting JSON data with line breaks and indentation. A
 *       true value for this property indicates human readable indented
 *       data, while a false value indicates unformatted data.
 *       Default value is false (unformatted) if this property is not specified.
 * </dl>
 * <dl>
 *   <dt><tt>jsonb.to.json.encoding</tt> - java.lang.String
 *   <dd>The {@link javax.json.bind.Jsonb Jsonb} marshalling {@code toJson()} methods
 *       will default to this property for encoding of output JSON data. Default
 *       value is 'UTF-8' if this property is not specified.
 * </dl>
 * <dl>
 *   <dt><tt>jsonb.from.json.encoding</tt> - java.lang.String
 *   <dd>The {@link javax.json.bind.Jsonb Jsonb} unmarshalling {@code fromJson()}
 *       methods will default to this property encoding of input JSON data if the
 *       encoding cannot be detected.
 * </dl>
 * </blockquote>
 *
 * @see JsonbConfigException
 * @author Martin Grebac, Przemyslaw Bielicki, Eugen Cepoi, Hendrik Saly
 * @since JSON Binding 1.0
 */
public class JsonbConfig {

    private final Map<String, Object> configuration = new HashMap<>();

    /**
     * Property used to specify whether or not the marshaled
     * JSON data is formatted with linefeeds and indentation.
     */
    public static final String JSONB_TO_JSON_FORMATTING = "jsonb.to.json.formatting";

    /**
     * The Jsonb marshalling {@code toJson()} methods will default to this property
     * for encoding of output JSON data. Default value is 'UTF-8'.
     */
    public static final String JSONB_TO_JSON_ENCODING = "jsonb.to.json.encoding";

    /**
     * The Jsonb unmarshalling {@code fromJson()} methods will default to this
     * property encoding of input JSON data if the encoding cannot be detected
     * automatically.
     */
    public static final String JSONB_FROM_JSON_ENCODING = "jsonb.from.json.encoding";

    /**
     * Set the particular configuration property to a new value. The method can
     * only be used to set one of the standard JSON Binding properties defined in
     * this class or a provider specific property. Attempting to set an undefined
     * property will result in a JsonbConfigException being thrown.
     * See <a href="#supportedProps"> Supported Properties</a>.
     *
     * @param name
     *      The name of the property to be set. This value can either
     *      be specified using one of the constant fields or a user supplied
     *      string.
     * @param value
     *      The value of the property to be set
     *
     * @return This JsonbConfig instance.
     */
    public final JsonbConfig setProperty(final String name, final Object value) throws JsonbConfigException {
        configuration.put(name, value);
        return this;
    }

    /**
     * Return value of particular configuration property. The method can
     * only be used to retrieve one of the standard JSON Binding properties defined
     * in this class or a provider specific property. Attempting to get an undefined
     * property will result in a JsonbConfigException being thrown.
     * See <a href="#supportedProps"> Supported Properties</a>.
     *
     * @param name
     *      The name of the property to retrieve
     *
     * @return The value of the requested property
     *
     * @throws JsonbConfigException
     *      when there is an error retrieving the given property or value
     *      property name
     * @throws IllegalArgumentException if the name parameter is null.
     */
    public final Object getProperty(final String name) throws JsonbConfigException {
        return configuration.get(name);
    }

    /**
     * Return all configuration properties as an unmodifiable map.
     *
     * @return All configuration properties as an unmodifiable map
     */
    public final Map<String, Object> getAsMap() {
        return Collections.unmodifiableMap(configuration);
    }

    /**
     * Property used to specify whether or not the marshaled JSON data is formatted
     * with linefeeds and indentation.
     *
     * Configures value of {@code JSONB_TO_JSON_FORMATTING} property.
     *
     * @param formatted
     *      True means marshalled data is formatted, false (default)
     *      means no formatting.
     *
     * @return This JsonbConfig instance.
     */
    public final JsonbConfig toJsonFormatting(final Boolean formatted) {
        return setProperty(JSONB_TO_JSON_FORMATTING, formatted);
    }

    /**
     * The Jsonb marshalling {@code toJson()} methods will default to this property
     * for encoding of output JSON data. Default value is 'UTF-8'.
     *
     * Configures value of {@code JSONB_TO_JSON_ENCODING} property.
     *
     * @param encoding
     *      Valid character encoding as defined in the
     *      <a href="http://tools.ietf.org/html/rfc7159">RFC 7159</a> and supported by
     *      Java Platform.
     *
     * @return This JsonbConfig instance.
     */
    public final JsonbConfig toJsonEncoding(final String encoding) {
        return setProperty(JSONB_TO_JSON_ENCODING, encoding);
    }

    /**
     * The Jsonb unmarshalling {@code fromJson()} methods will default to this
     * property encoding of input JSON data if the encoding cannot be detected
     * automatically.
     *
     * Configures value of {@code JSONB_FROM_JSON_ENCODING} property.
     *
     * @param encoding
     *      Valid character encoding as defined in the
     *      <a href="http://tools.ietf.org/html/rfc7159">RFC 7159</a> and supported by
     *      Java Platform.
     *
     * @return This JsonbConfig instance.
     */
    public final JsonbConfig fromJsonEncoding(final String encoding) {
        return setProperty(JSONB_FROM_JSON_ENCODING, encoding);
    }

}
