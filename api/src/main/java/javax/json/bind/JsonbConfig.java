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
package javax.json.bind;

import javax.json.bind.adapter.JsonbAdapter;
import javax.json.bind.adapter.JsonbSimpleAdapter;
import javax.json.bind.config.PropertyNamingStrategy;
import javax.json.bind.config.PropertyVisibilityStrategy;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
 * This object is not thread safe. Implementations are expected to make a defensive copy
 * of the object before applying the configuration.
 *
 * @since JSON Binding 1.0
 */
public class JsonbConfig {

    private final Map<String, Object> configuration = new HashMap<>();

    /**
     * Property used to specify whether or not the marshaled
     * JSON data is formatted with linefeeds and indentation.
     */
    public static final String FORMATTING = "jsonb.formatting";

    /**
     * The Jsonb marshalling {@code toJson()} methods will default to this property
     * for encoding of output JSON data. Default value is 'UTF-8'.
     *
     * The Jsonb unmarshalling {@code fromJson()} methods will default to this
     * property encoding of input JSON data if the encoding cannot be detected
     * automatically.
     */
    public static final String ENCODING = "jsonb.encoding";

    /**
     * Property used to specify custom naming strategy.
     */
    public static final String PROPERTY_NAMING_STRATEGY = "jsonb.property-naming-strategy";

    /**
     * Property used to specify custom order strategy.
     */
    public static final String PROPERTY_ORDER_STRATEGY = "jsonb.property-order-strategy";

    /**
     * Property used to specify null values serialization behavior.
     */
    public static final String NULL_VALUES = "jsonb.null-values";

    /**
     * Property used to specify strict I-JSON serialization compliance.
     */
    public static final String STRICT_IJSON = "jsonb.strict-ijson";

    /**
     * Property used to specify custom visibility strategy.
     */
    public static final String PROPERTY_VISIBILITY_STRATEGY = "jsonb.property-visibility-strategy";

    /**
     * Property used to specify custom mapping adapters for generic types.
     */
    public static final String ADAPTERS = "jsonb.adapters";

    /**
     * Property used to specify custom binary data strategy.
     */
    public static final String BINARY_DATA_STRATEGY = "jsonb.binary-data-strategy";

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
     *
     * @throws IllegalArgumentException if the name parameter is null.
     */
    public final JsonbConfig setProperty(final String name, final Object value) {
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
     * @throws IllegalArgumentException if the name parameter is null.
     */
    public final Optional<Object> getProperty(final String name) {
        return Optional.ofNullable(configuration.get(name));
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
     * Configures value of {@code FORMATTING} property.
     *
     * @param formatted
     *      True means marshaled data is formatted, false (default)
     *      means no formatting.
     *
     * @return This JsonbConfig instance.
     */
    public final JsonbConfig withFormatting(final Boolean formatted) {
        return setProperty(FORMATTING, formatted);
    }

    /**
     * Property used to specify whether null values should be marshalled to JSON document or skipped.
     *
     * Configures value of {@code NULL_VALUES} property.
     *
     * @param marshalNullValues
     *      True means that null values will be marshaled into JSON document,
     *      otherwise they will be effectively skipped.
     *
     * @return This JsonbConfig instance.
     */
    public final JsonbConfig withNullValues(final Boolean marshalNullValues) {
        return setProperty(NULL_VALUES, marshalNullValues);
    }

    /**
     * The binding operations will default to this property
     * for encoding of JSON data. For input data (fromJson), selected encoding is used if
     * the encoding cannot be detected automatically. Default value is 'UTF-8'.
     *
     * Configures value of {@code ENCODING} property.
     *
     * @param encoding
     *      Valid character encoding as defined in the
     *      <a href="http://tools.ietf.org/html/rfc7159">RFC 7159</a> and supported by
     *      Java Platform.
     *
     * @return This JsonbConfig instance.
     */
    public final JsonbConfig withEncoding(final String encoding) {
        return setProperty(ENCODING, encoding);
    }

    /**
     * Property used to specify whether strict I-JSON serialization compliance should be enforced.
     *
     * Configures value of {@code STRICT_IJSON} property.
     *
     * @param enabled
     *      True means data is marshaled in strict compliance according to RFC 7493.
     *
     * @return This JsonbConfig instance.
     */
    public final JsonbConfig withStrictIJSON(final Boolean enabled) {
        return setProperty(STRICT_IJSON, enabled);
    }

    /**
     * Property used to specify custom naming strategy.
     *
     * Configures value of {@code JSONB_PROPERTY_NAMING_STRATEGY} property.
     *
     * @param propertyNamingStrategy
     *      Custom naming strategy which affects serialization and deserialization.
     *
     * @return This JsonbConfig instance.
     */
    public final JsonbConfig withPropertyNamingStrategy(final PropertyNamingStrategy propertyNamingStrategy) {
        return setProperty(PROPERTY_NAMING_STRATEGY, propertyNamingStrategy);
    }

    /**
     * Property used to specify custom naming strategy.
     *
     * Configures value of {@code JSONB_PROPERTY_NAMING_STRATEGY} property.
     *
     * @param propertyNamingStrategy
     *      Predefined naming strategy which affects serialization and deserialization.
     *
     * @return This JsonbConfig instance.
     */
    public final JsonbConfig withPropertyNamingStrategy(final String propertyNamingStrategy) {
        return setProperty(PROPERTY_NAMING_STRATEGY, propertyNamingStrategy);
    }

    /**
     * Property used to specify property order strategy.
     *
     * Configures values of {@code JSONB_PROPERTY_ORDER_STRATEGY} property.
     *
     * @param propertyOrderStrategy
     *      Predefined property order strategy which affects serialization.
     *
     * @return This JsonbConfig instance.
     */
    public final JsonbConfig withPropertyOrderStrategy(final String propertyOrderStrategy) {
        return setProperty(PROPERTY_ORDER_STRATEGY, propertyOrderStrategy);
    }

    /**
     * Property used to specify custom property visibility strategy.
     *
     * Configures value of {@code PROPERTY_VISIBILITY_STRATEGY} property.
     *
     * @param propertyVisibilityStrategy
     *      Custom property visibility strategy which affects serialization and deserialization.
     *
     * @return This JsonbConfig instance.
     */
    public final JsonbConfig withPropertyVisibilityStrategy(final PropertyVisibilityStrategy
                                                                    propertyVisibilityStrategy) {
        return setProperty(PROPERTY_VISIBILITY_STRATEGY, propertyVisibilityStrategy);
    }

    /**
     * Property used to specify custom mapping adapters.
     *
     * Configures value of {@code ADAPTERS} property.
     *
     * Calling withAdapters more than once will merge the adapters with previous value.
     *
     * @param adapters
     *      Custom mapping adapters which affects serialization and deserialization.
     *
     * @return This JsonbConfig instance.
     */
    public final JsonbConfig withAdapters(final JsonbAdapter... adapters) {
        return setProperty(ADAPTERS, adapters);
    }

    /**
     * Property used to specify custom binary data strategy.
     *
     * Configures value of {@code BINARY_DATA_STRATEGY} property.
     *
     * @param binaryDataStrategy
     *      Custom binary data strategy which affects serialization and deserialization.
     *
     * @return This JsonbConfig instance.
     */
    public final JsonbConfig withBinaryDataStrategy(final String binaryDataStrategy) {
        return setProperty(BINARY_DATA_STRATEGY, binaryDataStrategy);
    }
}
