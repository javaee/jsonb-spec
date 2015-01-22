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

package javax.json.bind.spi;

import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import javax.json.bind.JsonbContext;
import javax.json.bind.JsonbException;

/**
 * Service provider for JSON Binding processing objects.
 *
 * @author Martin Grebac
 * @see JsonbContext
 * @since JSON Binding 1.0
 */
public abstract class JsonbProvider {

    /**
     * A constant representing the name of the default
     * {@code JsonbProvider} implementation class.
     */
    private static final String DEFAULT_PROVIDER = "org.eclipse.persistence.jsonb.JsonbProvider";

    /**
     *
     * Creates a JSON Binding provider object. The provider is loaded using the
     * {@link ServiceLoader#load(Class)} method. If there are no available
     * service providers, this method returns the default service provider.
     *
     * @see java.util.ServiceLoader
     *
     * @throws JsonbException if there is no provider found, or there is a problem
     *         instantiating the provider instance.
     *
     * @return a JsonbProvider instance
     */
    @SuppressWarnings("UseSpecificCatch")
    public static JsonbProvider provider() {
        ServiceLoader<JsonbProvider> loader = ServiceLoader.load(JsonbProvider.class);
        Iterator<JsonbProvider> it = loader.iterator();
        if (it.hasNext()) {
            return it.next();
        }

        try {
            Class<?> clazz = Class.forName(DEFAULT_PROVIDER);
            return (JsonbProvider) clazz.newInstance();
        } catch (ClassNotFoundException x) {
            throw new JsonbException("JSON Binding provider " + DEFAULT_PROVIDER + " not found", x);
        } catch (Exception x) {
            throw new JsonbException("JSON Binding provider " + DEFAULT_PROVIDER
                                        + " could not be instantiated: " + x, x);
        }
    }

    /**
     *
     * Creates a JSON Binding provider object. The provider is loaded using the
     * {@link ServiceLoader#load(Class)} method. The first provider of JsonbProvider
     * class from list of providers returned by ServiceLoader.load call is returned.
     * If no provider is found, JsonbException is thrown.
     *
     * @param providerName providerName Class name (class.getName) to be chosen from the list of providers
     *          returned by ServiceLoader.load(JsonbProvider.class) call.
     *
     * @throws IllegalArgumentException if providerName is null.
     *
     * @throws JsonbException if there is no provider found, or there is a problem
     *         instantiating the provider instance.
     *
     * @see java.util.ServiceLoader
     *
     * @return a JsonbProvider instance
     */
    @SuppressWarnings("UseSpecificCatch")
    public static JsonbProvider provider(final String providerName) {
        if (providerName == null) {
            throw new IllegalArgumentException();
        }
        ServiceLoader<JsonbProvider> loader = ServiceLoader.load(JsonbProvider.class);
        Iterator<JsonbProvider> it = loader.iterator();
        while (it.hasNext()) {
            JsonbProvider provider = it.next();
            if (provider.getClass().getName().equals(provider.getClass().getName())) {
                return provider;
            }
        }

        throw new JsonbException("JSON Binding provider " + DEFAULT_PROVIDER + " not found",
                                 new ClassNotFoundException(providerName));
    }

    /**
     * Returns a new instance of JsonbContext class.
     * The client application has to provide list of classes that the new context
     * object needs to recognize.
     *
     * The implementation will not only recognize the provided classes, but it will
     * also recognize any classes that are directly or indirectly statically referenced
     * from provided classes. Subclasses of referenced classes, nor
     * {@link javax.json.bind.annotation.JsonTransient} annotated classes are recognized.
     *
     * @param classes
     *      List of java classes to be recognized by the new {@link JsonbContext}.
     *      Can be empty, in which case a default {@link JsonbContext} which only
     *      recognizes default spec-defined classes will be returned.
     *
     * @return JsonbContext
     *      A new instance of JsonbContext class. Always a non-null valid object.
     *
     * @throws JsonbException
     *      If an error was encountered while creating the JsonbContext instance,
     *      such as (but not limited to) classes provide conflicting annotations.
     *
     * @throws IllegalArgumentException
     *      If the parameter contains {@code null}
     */
    public abstract JsonbContext createContext(Class<?>... classes);

    /**
     * Returns a new instance of JsonbContext class configured with a specific
     * configuration.
     * The client application has to provide list of classes that the new context
     * object needs to recognize.
     *
     * The implementation will not only recognize the provided classes, but it will
     * also recognize any classes that are directly or indirectly statically referenced
     * from provided classes. Subclasses of referenced classes, nor
     * {@link javax.json.bind.annotation.JsonTransient} annotated classes are recognized.
     *
     * @param configuration
     *      Contains spec defined and provider specific configuration properties. Can be
     *      null, which is same as empty list.
     *
     * @param classes
     *      List of java classes to be recognized by the new {@link JsonbContext}.
     *      Can be empty, in which case a default {@link JsonbContext} which only
     *      recognizes default spec-defined classes will be returned.
     *
     * @return JsonbContext
     *      A new instance of JsonbContext class. Always a non-null valid object.
     *
     * @throws JsonbException
     *      If an error was encountered while creating the JsonbContext instance,
     *      such as (but not limited to) classes provide conflicting annotations.
     *
     * @throws IllegalArgumentException
     *      If the parameter contains {@code null}
     */
    public abstract JsonbContext createContext(Map<String, ?> configuration, Class<?>... classes);

}
