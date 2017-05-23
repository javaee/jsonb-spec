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
package javax.json.bind.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Annotation provides way how to set custom number format to field or JavaBean property.</p>
 *
 * <p>The pattern format is specified in {@link java.text.DecimalFormat}</p>
 *
 * <p><b>Usage</b></p>
 * <p>The {@code @JsonbNumberFormat} annotation can be used with the following program elements:</p>
 * <ul>
 *   <li> field </li>
 *   <li> getter/setter </li>
 *   <li> type </li>
 *   <li> parameter </li>
 *   <li> package </li>
 * </ul>
 *
 * @since JSON Binding 1.0
 */
@JsonbAnnotation
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD,
        ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER, ElementType.PACKAGE})
public @interface JsonbNumberFormat {

    /**
     * Value that indicates that default {@link java.util.Locale}.
     */
    String DEFAULT_LOCALE = "##default";

    /**
     * Specifies the number pattern to use.
     *
     * @return Number pattern to use.
     */
    String value() default "";

    /**
     * Custom {@link java.util.Locale} to use.
     *
     * @return Custom locale to use.
     */
    String locale() default DEFAULT_LOCALE;
}
