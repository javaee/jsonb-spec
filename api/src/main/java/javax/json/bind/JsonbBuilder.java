/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2015, 2017 Oracle and/or its affiliates. All rights reserved.
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
package javax.json.bind;

import javax.json.bind.spi.JsonbProvider;
import javax.json.spi.JsonProvider;

/**
 * JsonbBuilder class provides the client's entry point to the JSON Binding
 * API. It builds {@link javax.json.bind.Jsonb Jsonb} instances based on all
 * parameters and configuration provided before calling {@code build()} method.
 *
 * For most use-cases, only one instance of JsonbBuilder is required within the
 * application.
 *
 * @see Jsonb
 * @see java.util.ServiceLoader
 * @since JSON Binding 1.0
 */
public interface JsonbBuilder {

    /**
     * Set configuration which will be set to the newly created
     * {@link javax.json.bind.Jsonb Jsonb} instance.
     *
     * @param config
     *      Configuration for {@link javax.json.bind.Jsonb Jsonb} instance.
     *
     * @return This {@code JsonbBuilder} instance.
     */
    JsonbBuilder withConfig(JsonbConfig config);

    /**
     * Provides a <a href="https://jcp.org/en/jsr/detail?id=353">JSON-P</a> provider
     * to be used for all <a href="https://jcp.org/en/jsr/detail?id=353">JSON-P</a> related operations.
     *
     * @param jsonpProvider
     *      {@link javax.json.spi.JsonProvider JsonProvider} instance
     *      to be used by Jsonb to lookup JSON-P implementation.
     *
     * @return This {@code JsonbBuilder} instance.
     */
    JsonbBuilder withProvider(JsonProvider jsonpProvider);

    /**
     * Returns a new instance of {@link javax.json.bind.Jsonb Jsonb} based on the
     * parameters and configuration specified previously in this builder.
     *
     * @return Jsonb A new instance of {@link javax.json.bind.Jsonb Jsonb} class.
     * Always a non-null valid object.
     *
     * @throws javax.json.bind.JsonbException If an error was encountered while
     * creating the Jsonb instance, such as (but not limited to) no JSON
     * Binding provider found, or classes provide conflicting annotations.
     *
     * @throws IllegalArgumentException If there's an error processing the set
     * parameters, such as the non-null parameter is assigned null value, or
     * unrecognized property is set in
     * {@link javax.json.bind.JsonbConfig JsonbConfig}.
     */
    Jsonb build();

    /**
     * Create a new {@link javax.json.bind.Jsonb} instance using the default
     * {@code JsonbBuilder} implementation provided as returned from
     * {@link javax.json.bind.spi.JsonbProvider#provider()} method.
     *
     * @return new {@link javax.json.bind.Jsonb Jsonb} instance.
     */
    static Jsonb create() {
        return JsonbProvider.provider().create().build();
    }

    /**
     * Create a new {@link javax.json.bind.Jsonb} instance using the default
     * {@code JsonbBuilder} implementation provided as returned from
     * {@link javax.json.bind.spi.JsonbProvider#provider()} method, configured
     * with provided configuration.
     *
     * @param config
     *      Provided configuration for {@link javax.json.bind.Jsonb} instance.
     *
     * @return new {@link javax.json.bind.Jsonb Jsonb} instance.
     */
    static Jsonb create(JsonbConfig config) {
        return JsonbProvider.provider().create().withConfig(config).build();
    }

    /**
     * Create a new {@code JsonbBuilder} instance as returned by the default
     * {@link javax.json.bind.spi.JsonbProvider#provider()} method.
     *
     * @return new {@code JsonbBuilder} instance.
     */
    static JsonbBuilder newBuilder() {
        return JsonbProvider.provider().create();
    }

    /**
     * Create a new {@code JsonbBuilder} instance as returned by
     * {@link javax.json.bind.spi.JsonbProvider#provider(String)}
     * method.
     *
     * @param providerName
     *      Provider class name to be looked up by {@link java.util.ServiceLoader ServiceLoader}.
     *
     * @return new {@code JsonbBuilder} instance.
     */
    static JsonbBuilder newBuilder(final String providerName) {
        return JsonbProvider.provider(providerName).create();
    }

    /**
     * Create a new {@code JsonbBuilder} instance as returned by
     * {@code provider#create} call.
     *
     * @param provider
     *      {@link javax.json.spi.JsonProvider JsonProvider} instance
     *      used for creating {@code JsonBuilder instances}.
     *
     * @return new {@code JsonbBuilder} instance.
     */
    static JsonbBuilder newBuilder(final JsonbProvider provider) {
        return provider.create();
    }

}
