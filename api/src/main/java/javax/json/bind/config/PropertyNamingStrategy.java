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

package javax.json.bind.config;

/**
 * <p>Allows to define custom property naming strategy.
 * Specifies predefined property naming strategies.
 * Does not override JsonbProperty value.</p>
 *
 * <p>This strategy can be set via {@link javax.json.bind.JsonbConfig}.</p>
 *
 * @see javax.json.bind.JsonbConfig
 * @since JSON Binding 1.0
 */
public interface PropertyNamingStrategy {
    /**
     * Using this strategy, the property name is unchanged.
     */
    String IDENTITY = "IDENTITY";

    /**
     * Using this strategy, the property name is transformed to lower case with dashes.
     * The dashes are on the positions of different case boundaries in the original field name (camel case).
     */
    String LOWER_CASE_WITH_DASHES = "LOWER_CASE_WITH_DASHES";

    /**
     * Using this strategy, the property name is transformed to lower case with underscores.
     * The underscores are on the positions of different case boundaries in the original field name (camel case).
     */
    String LOWER_CASE_WITH_UNDERSCORES = "LOWER_CASE_WITH_UNDERSCORES";

    /**
     * Using this strategy, the first character will be capitalized.
     */
    String UPPER_CAMEL_CASE = "UPPER_CAMEL_CASE";

    /**
     * Using this strategy, the first character will be capitalized and the words
     * will be separated by spaces.
     */
    String UPPER_CAMEL_CASE_WITH_SPACES = "UPPER_CAMEL_CASE_WITH_SPACES";

    /**
     * Using this strategy, the serialization will be same as identity.
     * Deserialization will be case insensitive. E.g. property in JSON with name
     * PropertyNAME, will be mapped to field propertyName.
     */
    String CASE_INSENSITIVE = "CASE_INSENSITIVE";

    /**
     * Translates the property name into its JSON field name representation.
     *
     * @param propertyName Name of the property to translate.
     * @return Translated JSON field name.
     */
    String translateName(String propertyName);
}
