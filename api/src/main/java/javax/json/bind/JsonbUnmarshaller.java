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
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
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

import java.io.File;
import java.io.Reader;

/**
 * The {@code JsonbUnmarshaller} class governs the process of deserializing JSON
 * data into newly created Java content trees.
 *
 * <p>
 * Unmarshaling from a File:
 * <blockquote>
 *    <pre>
 *     JsonbUnmarshaller u = Jsonb.createContext(Foo.class).createUnmarshaller();
 *     Object o = u.unmarshal(new File("jsonfile.json"));
 *   </pre>
 *    <pre>
 *     JsonbUnmarshaller u = Jsonb.createContext(Foo.class).createUnmarshaller();
 *     Foo foo = u.unmarshal(new File("jsonfile.json"), Foo.class);
 *   </pre>
 * </blockquote>
 *
 * <p>
 * <b>Unmarshaling JSON Data</b><br>
 * <blockquote>
 * Unmarshaling can de-serialize JSON data that represents either an entire JSON document
 * or a subtree of a JSON document.
 * These unmarshal methods utilize {@link javax.json.bind.JsonbContext}'s type definitions to
 * to initiate the unmarshaling of the root element of JSON data. When the {@link JsonbContext}'s
 * mappings are not sufficient to unmarshal the root element of JSON data,
 * the application can assist the unmarshaling process by using the 'unmarshal by
 * declaredType' methods.</blockquote>
 *
 * <blockquote>
 * An unmarshal method never returns null. If the unmarshal process is unable to unmarshal
 * the root of JSON content to a JSON mapped object, a fatal error is reported that
 * terminates processing by throwing JsonbException.
 * </blockquote>
 *
 * <p>
 * <a name="supportedProps"></a>
 * <b>Supported Properties</b><br>
 * <blockquote>
 * <p>
 * There currently are not any properties required to be supported by all
 * JSON Binding providers on JsonbUnmarshaller.  However, some providers may support
 * their own set of provider specific properties.
 * </blockquote>
 *
 * <p>
 * <a name="unmarshalEventCallback"></a>
 * <b>Unmarshal Event Callbacks</b><br>
 * <blockquote>
 * The {@link javax.json.bind.JsonbUnmarshaller} provides callback mechanisms
 * that allow application specific processing during key points in the
 * unmarshaling process. In 'class defined' event callbacks, application
 * specific code placed in JSON Binding mapped classes is triggered during
 * unmarshaling.
 * <p>
 * 'Class defined' event callback methods allow any JAXB mapped class to specify
 * its own specific callback methods by defining methods annotated with the following
 * annotations:
 * <blockquote>
 * <pre>
// This method is called immediately after the object is created and before the unmarshalling of this
// object begins. The callback provides an opportunity to initialize JavaBean properties prior to unmarshalling.
{@literal @}JsonPreUnmarshal
void beforeUnmarshal(Object parent);

//This method is called after all the properties are unmarshalled for this object,
//but before this object is set to the parent object.
{@literal @}JsonPostUnmarshal
void afterUnmarshal(Object parent);
 </pre>
 * </blockquote>
 * The class defined callback methods should be used when the callback method requires
 * access to non-public methods and/or fields of the class.
 *
 * </blockquote>
 *
 * @author Martin Grebac
 * @see JsonbContext
 * @see JsonbMarshaller
 * @since JSON Binding 1.0
 */
public interface JsonbUnmarshaller {

    /**
     * Unmarshal JSON data from the specified file and return the resulting
     * content tree.
     *
     * @param file the file to unmarshal JSON data from
     *
     * @return the newly created root object of the java content tree
     *
     * @throws JsonbException
     *     If any unexpected error(s) occur(s) while unmarshaling.
     * @throws IllegalArgumentException
     *     If the file parameter is null.
     */
    public Object unmarshal(File file) throws JsonbException;

    /**
     * Unmarshal JSON data from the specified file and return the resulting
     * content tree.
     *
     * @param file the file to unmarshal JSON data from
     * @param type
     *      Type of the content tree's root object.
     * @param <T>
     *      Type of the content tree's root object.
     *
     * @return the newly created root object of the java content tree
     *
     * @throws JsonbException
     *     If any unexpected error(s) occur(s) while unmarshaling.
     * @throws IllegalArgumentException
     *     If any of the parameters is null.
     */
    public <T> T unmarshal(File file, Class<T> type) throws JsonbException;

    /**
     * Unmarshal JSON data from the specified string and return the resulting
     * content tree.
     *
     * @param str the string to unmarshal JSON data from
     *
     * @return the newly created root object of the java content tree
     *
     * @throws JsonbException
     *     If any unexpected error(s) occur(s) while unmarshaling.
     * @throws IllegalArgumentException
     *     If any of the parameters is null.
     */
    public Object unmarshal(String str) throws JsonbException;

    /**
     * Unmarshal JSON data from the specified string and return the resulting
     * content tree.
     *
     * @param str the string to unmarshal JSON data from
     * @param type
     *      Type of the content tree's root object.
     * @param <T>
     *      Type of the content tree's root object.
     *
     * @return the newly created root object of the java content tree
     *
     * @throws JsonbException
     *     If any unexpected error(s) occur(s) while unmarshaling.
     * @throws IllegalArgumentException
     *      If any of the parameters is null.
     */
    public <T> T unmarshal(String str, Class<T> type) throws JsonbException;

    /**
     * Unmarshal JSON data from the specified Reader and return the
     * resulting content tree.
     *
     * @param reader the Reader to unmarshal JSON data from.
     *
     * @return the newly created root object of the java content tree
     *
     * @throws JsonbException
     *     If any unexpected error(s) occur(s) while unmarshaling.
     * @throws IllegalArgumentException
     *      If any of the parameters is null.
     */
    public Object unmarshal(Reader reader) throws JsonbException;

    /**
     * Unmarshal JSON data from the specified Reader and return the
     * resulting content tree.
     *
     * @param reader the Reader to unmarshal JSON data from.
     * @param type
     *      Type of the content tree's root object.
     * @param <T>
     *      Type of the content tree's root object.
     *
     * @return the newly created root object of the java content tree
     *
     * @throws JsonbException
     *     If any unexpected error(s) occur(s) while unmarshaling.
     * @throws IllegalArgumentException
     *      If any of the parameters is null.
     */
    public <T> T unmarshal(Reader reader, Class<T> type) throws JsonbException;

    /**
     * Set the particular property in the underlying implementation of
     * <tt>JsonbUnmarshaller</tt>. This method can only be used to set one of
     * the standard JSON Binding properties defined in this class or a provider specific
     * property. Attempting to set an undefined property will result in
     * a JsonbConfigurationException being thrown.
     *
     * @param name the name of the property to be set. This value can either
     *              be specified using one of the constant fields or a user
     *              supplied string.
     * @param value the value of the property to be set
     *
     * @return 'this' instance, for fluent support
     *
     * @throws JsonbConfigurationException when there is an error processing the given
     *                            property or value
     * @throws IllegalArgumentException
     *      If the name parameter is null
     */
    public JsonbUnmarshaller setProperty(String name, Object value) throws JsonbConfigurationException;

    /**
     * Get the particular property in the underlying implementation of
     * <tt>JsonbUnmarshaller</tt>.  This method can only be used to get one of
     * the standard JSON Binding properties defined in this class, or a provider specific
     * property.  Attempting to get an undefined property will result in
     * a JsonbConfigurationException being thrown.
     *
     * @param name the name of the property to retrieve
     * @return the value of the requested property
     *
     * @throws JsonbConfigurationException
     *      when there is an error retrieving the given property or value
     *      property name
     * @throws IllegalArgumentException
     *      If the name parameter is null
     */
    public Object getProperty(String name) throws JsonbConfigurationException;

}
