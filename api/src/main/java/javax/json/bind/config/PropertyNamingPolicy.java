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

package javax.json.bind.config;

/**
 * <p>
 *     Specifies available property naming policies.
 * </p>
 *
 * <p>
 *     This policy can be set via {@link javax.json.bind.JsonbConfig}.
 * </p>
 *
 * @see javax.json.bind.JsonbConfig
 */
public enum PropertyNamingPolicy {
    /**
     * Using this policy, the property name is unchanged.
     */
    IDENTITY,
    /**
     * Using this policy, the property name is transformed to lower case with dashes.
     * The dashes are on the positions of different case boundaries in the original field name (camel case).
     */
    LOWER_CASE_WITH_DASHES,
    /**
     * Using this policy, the property name is transformed to lower case with underscores.
     * The underscores are on the positions of different case boundaries in the original field name (camel case).
     */
    LOWER_CASE_WITH_UNDERSCORES,
    /**
     * Using this policy, the first character will be capitalized.
     */
    UPPER_CAMEL_CASE,
    /**
     * Using this policy, the first character will be capitalized and the words
     * will be separated by spaces.
     */
    UPPER_CAMEL_CASE_WITH_SPACES,
    /**
     * Using this policy, the serialization will be same as identity.
     * Deserialization will be case insensitive. E.g. property in JSON with name
     * PropertyNAME, will be mapped to field propertyName.
     */
    CASE_INSENSITIVE
}
