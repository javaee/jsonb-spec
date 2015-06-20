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

package javax.json.bind.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 *     Annotation indicates that result of the annotated non-void method
 *     or field or constructor/factory method parameter will be used as the single value to serialize
 *     for the instance.
 * </p>
 *
 * <p>
 *     At most one method of a <code>Class</code> can be annotated with this annotation;
 *     if more than one is found, an exception will be thrown.
 * </p>
 *
 * <p>
 *     If the return value of such a method is {@code java.lang.String} or some other primitive type
 *     like {@code java.lang.Integer}, the resulting single value will be unwrapped.
 * </p>
 * Example:
 * <pre>{@code
 *     class SimpleClass {
 *
 *         private int value = 5;
 *
 *         &#64;JsonbValue
 *         public String singleValue() {
 *             return "SimleClassValue("+value+")";
 *         }
 *     }
 *
 *     class OuterClass {
 *         public int id = 356;
 *
 *         public SimpleClass simpleClass = new SimpleClass();
 *     }
 *   }</pre>
 * Serialization output for new OuterClass() will be {"id":356,"simpleClass":"SimpleClassValue(5)"}
 *
 *
 * <p>
 *     If the return value of such a method is some mappable class different than {@code java.lang.String}
 *     or some other primitive type like {@code java.lang.Integer}, the resulting single value will be wrapped.
 *     The resulting single value will be inside curly braces as any other mappable class.
 * </p>
 * Example:
 * <pre>{@code
 *     class MappableClass {
 *         public int id = 1;
 *         public int value = 2;
 *         public MappableClass(int id, int value) {
 *             this.id = id;
 *             this.value = value;
 *         }
 *     }
 *
 *     class SimpleClass {
 *         private int value = 5;
 *
 *         &#64;JsonbValue
 *         public MappableClass singleValue() {
 *             return new MappableClass(1, value);
 *         }
 *     }
 *
 *     class OuterClass {
 *         public int id = 356;
 *
 *         public SimpleClass simpleClass = new SimpleClass();
 *     }
 *   }</pre>
 * Serialization output for new OuterClass() will be {"id":356,"simpleClass":{"id":1,"value":5}}
 *
 *
 * <p><b>Usage</b></p>
 * <p> The {@code @JsonbValue} annotation can be used with the following
 *     program elements:
 * <ul>
 *   <li> a JavaBean property </li>
 *   <li> field </li>
 *   <li> parameter </li>
 * </ul>
 */
@JsonbAnnotation
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
public @interface JsonbValue {
}
