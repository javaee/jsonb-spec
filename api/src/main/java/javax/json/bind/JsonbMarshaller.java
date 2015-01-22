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
import java.io.Writer;

/**
 * <p>
 * The <tt>JsonbMarshaller</tt> class is responsible for governing the process
 * of serializing Java content trees into JSON data.
 *
 * <p>
 * Marshaling to a File:
 * <blockquote>
 *    <pre>
 *       m.marshal(object, new File("foo.json");
 *    </pre>
 * </blockquote>
 *
 * <p>
 * Marshalling to a Writer:
 * <blockquote>
 *    <pre>
 *       m.marshal(object, new PrintWriter(System.out));
 *    </pre>
 * </blockquote>
 *
 * <p>
 * <b>Encoding</b><br>
 * <blockquote>
 * By default, the JsonbMarshaller will use UTF-8 encoding when generating JSON data.
 * Use the {@link #setProperty(String,Object) setProperty} API to change the output
 * encoding used within the marshal operations. Client applications are
 * expected to supply a valid character encoding as defined in the
 * <a href="http://tools.ietf.org/html/rfc7159">RFC 7159</a> and supported by Java Platform.
 * </blockquote>
 *
 * <p>
 * <a name="supportedProps"></a>
 * <b>Supported Properties</b><br>
 * <blockquote>
 * <p>
 * All JSON Binding providers are required to support the following set of properties.
 * Some providers may support additional properties.
 * <dl>
 *   <dt><tt>jsonb.formatted.output</tt> - value must be a java.lang.Boolean
 *   <dd>This property controls whether or not the JsonbMarshaller will format
 *       the resulting JSON data with line breaks and indentation. A
 *       true value for this property indicates human readable indented
 *       data, while a false value indicates unformatted data.
 *       The JsonbMarshaller will default to false (unformatted) if this
 *       property is not specified.
 * </dl>
 * </blockquote>
 *
 * <p>
 * <a name="marshalEventCallback"></a>
 * <b>Marshal Event Callbacks</b><br>
 * <blockquote>
 * "The {@link JsonbMarshaller} provides callback mechanisms
 * that allow application specific processing during key points in the
 * marshalling process. In 'class defined' event callbacks, application
 * specific code placed in JSON Binding mapped classes is triggered during
 * marshalling.
 *
 * <p>
 * Class defined event callback methods allow any JSON Binding mapped class to specify
 * its own specific callback methods by defining methods annotated with following
 * annotations:
 * <blockquote>
 * <pre>
 *   // Invoked by JsonbMarshaller after it has created an instance of this object.
 *  {@literal @}JsonPreMarshal
 *   boolean beforeMarshal();
 *
 *   // Invoked by JsonbMarshaller after it has marshalled all properties of this object.
 *  {@literal @}JsonPostMarshal
 *   void afterMarshal();
 </pre>
 * </blockquote>
 * The class defined event callback methods should be used when the callback method requires
 * access to non-public methods and/or fields of the class.
 * <p>
 * An event callback method throwing an exception terminates the current marshal process.
 * </blockquote>
 *
 * @author Martin Grebac
 * @see JsonbContext
 * @see JsonbUnmarshaller
 * @since JSON Binding 1.0
 */
public interface JsonbMarshaller {

    /**
     * The name of the property used to specify whether or not the marshaled
     * JSON data is formatted with linefeeds and indentation.
     */
    public static final String JSONB_FORMATTED_OUTPUT = "jsonb.formatted.output";

    /**
     * Marshal the object content tree into a String instance.
     *
     * @param object
     *      The object content tree to be marshaled.
     * @return String instance with marshaled JSON data.
     * @throws JsonbException
     *      If any unexpected problem occurs during the marshaling.
     * @throws IllegalArgumentException
     *      If any of the method parameters is null.
     * @since JSON Binding 1.0
     */
    public String marshal(Object object) throws JsonbException;

    /**
     * Marshal the object content tree into a file.
     *
     * @param object
     *      The object content tree to be marshaled.
     * @param file
     *      File to be written. If this file already exists, it will be overwritten.
     * @throws JsonbException
     *      If any unexpected problem occurs during the marshaling.
     * @throws IllegalArgumentException
     *      If any of the method parameters is null.
     * @since JSON Binding 1.0
     */
    public void marshal(Object object, File file) throws JsonbException;

    /**
     * Marshal the object content tree into a Writer character stream.
     *
     * @param object
     *      The object content tree to be marshaled.
     * @param writer
     *      The JSON will be sent as a character stream to the given {@link Writer}.
     *      Upon a successful completion, the stream will be closed by this method.
     * @throws JsonbException
     *      If any unexpected problem occurs during the marshaling.
     * @throws IllegalArgumentException
     *      If any of the method parameters is null.
     * @since JSON Binding 1.0
     */
    public void marshal(Object object, Writer writer) throws JsonbException;

    /**
     * Set the particular property in the underlying implementation of
     * <tt>JsonbMarshaller</tt>. The method can only be used to set one of
     * the standard JSON Binding properties defined in this class or a provider specific
     * property. Attempting to set an undefined property will result in
     * a JsonbConfigurationException being thrown.
     *
     * @param name the name of the property to be set. This value can either
     *              be specified using one of the constant fields or a user
     *              supplied string.
     * @param value the value of the property to be set
     *
     * @throws JsonbConfigurationException when there is an error processing the given
     *                            property or value
     * @throws IllegalArgumentException
     *      If the name parameter is null
     */
    public void setProperty(String name, Object value) throws JsonbConfigurationException;

    /**
     * Get the particular property in the underlying implementation of
     * <tt>JsonbMarshaller</tt>.  This method can only be used to get one of
     * the standard JAXB defined properties above or a provider specific
     * property.  Attempting to get an undefined property will result in
     * a JsonbConfigurationException being thrown.  See <a href="#supportedProps">
     * Supported Properties</a>.
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
