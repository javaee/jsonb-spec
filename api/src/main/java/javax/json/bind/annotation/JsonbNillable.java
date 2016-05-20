/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2016 Oracle and/or its affiliates. All rights reserved.
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

package javax.json.bind.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.TYPE;

/**
 * <p>
 *     Specifies how the annotated field will be serialized into JSON in case the value of given field is null.
 * </p>
 *
 * <p>
 *     There are two possible values which can be specified.
 *     In case of true, given field will be serialized as key/value pair with value null.
 *     In case of false, given field will not be serialized.
 * </p>
 *
 * <p>
 *     For java Optionals, an empty optional is treated same as null for other field types.
 * </p>
 *
 * <p>
 *     If the annotation is specified on type, all accessors (field or a JavaBean property) of the given type
 *     are handled as if they have been annotated with {@code @JsonNillable}.
 *
 *     If the annotation is specified on package, all types within that package are handled as if they have been
 *     annotated with {@code @JsonNillable}.
 * </p>
 *
 * <p>
 *     Serialization of given field (or a JavaBean property) is affected only if the value of
 *     given field (or a JavaBean property) is null.
 * </p>
 *
 * <p><b>Usage</b></p>
 * <p> The {@code @JsonbNillable} annotation can be used with the following
 *     program elements:
 * <ul>
 *   <li> a JavaBean property </li>
 *   <li> package </li>
 * </ul>
 *
 * @since JSON Binding 1.0
 */
@JsonbAnnotation
@Retention(RetentionPolicy.RUNTIME)
@Target({ANNOTATION_TYPE, TYPE, PACKAGE})
public @interface JsonbNillable {

    /**
     * True if field with null value should be serialized as key/value pair into JSON with null value.
     */
    boolean value() default true;
}
