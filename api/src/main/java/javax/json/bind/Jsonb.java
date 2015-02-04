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
package javax.json.bind;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * <p>
 * {@code Jsonb} provides an abstraction over the JSON binding framework operations:
 *
 * <ul>
 * <li>{@code fromJson}: read JSON input, unmarshal to Java objects content tree
 * <li>{@code toJson}: marshall Java objects content tree from JSON input
 * </ul>
 *
 * <p>
 * Instance of this class is created using {@link javax.json.bind.JsonbBuilder JsonbBuilder}
 * builder methods:
 * <pre>{@code
 * // Example 1 - Creating Jsonb using default JsonbBuilder instance provided by default JsonbProvider
 Jsonb jsonb = JsonbBuilder.create();

 // Example 2 - Creating Jsonb instance for a specific provider specified by a class name
 Jsonb jsonb = JsonbBuilder.newBuilder("foo.bar.ProviderImpl).build();

 // Example 3 - Creating Jsonb instance from a custom provider implementation
 Jsonb jsonb = JsonbBuilder.newBuilder(new JsonbProvider () {
                    public JsonbBuilder create() {
                        return new CustomJsonbBuilder();
                    }
                }).build();
 }</pre>
 *
 * <b>Unmarshalling (reading) from JSON</b><br>
 * <blockquote>
 * Unmarshalling can de-serialize JSON data that represents either an entire JSON
 * document or a subtree of a JSON document.
 * </blockquote>
 * <blockquote>
 * Reading (unmarshalling) object content tree from a File:<br><br>
 *    <pre>
 *     Jsonb jsonb = JsonbBuilder.create();
 *     Book book = jsonb.fromJson(new File("jsonfile.json"), Book.class);</pre>
 * If the unmarshal process is unable to unmarshal the JSON content to an object
 * content tree, fatal error is reported that terminates processing by
 * throwing JsonbException.
 * </blockquote>
 *
 * <p>
 * <b>Marshalling (writing) to JSON</b><br>
 * <blockquote>
 * <p>
 * Marshalling writes the representation of a Java object content tree into
 * JSON data.
 * </blockquote>
 * <blockquote>
 * Writing (marshalling) object content tree to a File:<br><br>
 *    <pre>
 *     Jsonb jsonb = JsonbBuilder.create();
 *     jsonb.toJson(object, new File("foo.json");</pre>
 * Writing (marshalling) to a Writer:<br><br>
 *    <pre>
 *     Jsonb jsonb = JsonbBuilder.create();
 *     jsonb.toJson(object, new PrintWriter(System.out));
 *    </pre>
 * </blockquote>
 *
 * <b>Encoding</b><br>
 * <blockquote>
 * In unmarshalling operations ({@code fromJson}), encoding of JSON data is detected automatically.
 * Use the {@link javax.json.bind.JsonbConfig JsonbConfig} API to configure expected
 * input encoding used within unmarshal operations. Client applications are
 * expected to supply a valid character encoding as defined in the
 * <a href="http://tools.ietf.org/html/rfc7159">RFC 7159</a> and supported by Java Platform.
 *
 * In marshalling operations ({@code toJson}), UTF-8 encoding is used by default
 * for writing JSON data.
 * Use the {@link javax.json.bind.JsonbConfig JsonbConfig} API to configure the
 * output encoding used within marshal operations. Client applications are
 * expected to supply a valid character encoding as defined in the
 * <a href="http://tools.ietf.org/html/rfc7159">RFC 7159</a> and supported by Java Platform.
 * </blockquote>
 *
 * For optimal use, {@code JsonbBuilder} and {@code Jsonb} instances should be
 * reused - for a typical use-case, only one {@code Jsonb} instance is
 * required by an application.
 *
 * <p> All the methods in this class are safe for use by multiple concurrent threads.
 *
 * @see Jsonb
 * @see JsonbBuilder
 * @see java.util.ServiceLoader
 * @author Eugen Cepoi, Martin Grebac, Przemyslaw Bielicki, Inderjeet Singh
 * @since JSON Binding 1.0
 */
public interface Jsonb {

    /**
     * Reads in a JSON data from the specified string and return the resulting
     * content tree.
     *
     * @param str
     *      The string to unmarshal JSON data from.
     * @param type
     *      Type of the content tree's root object.
     * @param <T>
     *      Type of the content tree's root object.
     *
     * @return the newly created root object of the java content tree
     *
     * @throws JsonbException
     *     If any unexpected error(s) occur(s) while unmarshalling.
     * @throws IllegalArgumentException
     *      If any of the parameters is null.
     */
    public <T> T fromJson(String str, Class<T> type) throws JsonbException;

    /**
     * Reads in a JSON data from the specified string and return the resulting
     * content tree.
     *
     * @param str
     *      The string to unmarshal JSON data from.
     * @param type
     *      Type of the content tree's root object.
     * @param <T>
     *      Type of the content tree's root object.
     * @param config
     *      Configuration.
     *
     * @return the newly created root object of the java content tree
     *
     * @throws JsonbException
     *     If any unexpected error(s) occur(s) while unmarshalling.
     * @throws IllegalArgumentException
     *     If any of the parameters is null.
     */
    public <T> T fromJson(String str, Class<T> type, JsonbConfig config) throws JsonbException;

    /**
     * Unmarshal JSON data from the specified Reader and return the
     * resulting content tree.
     *
     * @param reader
     *      The character stream is read as a JSON data. Upon a
     *      successful completion, the stream will be closed by this method.
     * @param type
     *      Type of the content tree's root object.
     * @param <T>
     *      Type of the content tree's root object.
     *
     * @return the newly created root object of the java content tree
     *
     * @throws JsonbException
     *     If any unexpected error(s) occur(s) while unmarshalling.
     * @throws IllegalArgumentException
     *      If any of the parameters is null.
     */
    public <T> T fromJson(Reader reader, Class<T> type) throws JsonbException;

    /**
     * Unmarshal JSON data from the specified Reader and return the
     * resulting content tree.
     *
     * @param reader
     *      The character stream is read as a JSON data. Upon a
     *      successful completion, the stream will be closed by this method.
     * @param type
     *      Type of the content tree's root object.
     * @param <T>
     *      Type of the content tree's root object.
     * @param config
     *      Configuration.
     *
     * @return the newly created root object of the java content tree
     *
     * @throws JsonbException
     *     If any unexpected error(s) occur(s) while unmarshalling.
     * @throws IllegalArgumentException
     *      If any of the parameters is null.
     */
    public <T> T fromJson(Reader reader, Class<T> type, JsonbConfig config) throws JsonbException;

    /**
     * Unmarshal JSON data from the specified InputStream and return the
     * resulting content tree.
     *
     * @param stream
     *      The stream is read as a JSON data. Upon a
     *      successful completion, the stream will be closed by this method.
     * @param type
     *      Type of the content tree's root object.
     * @param <T>
     *      Type of the content tree's root object.
     *
     * @return the newly created root object of the java content tree
     *
     * @throws JsonbException
     *     If any unexpected error(s) occur(s) while unmarshalling.
     * @throws IllegalArgumentException
     *      If any of the parameters is null.
     */
    public <T> T fromJson(InputStream stream, Class<T> type) throws JsonbException;

    /**
     * Unmarshal JSON data from the specified InputStream and return the
     * resulting content tree.
     *
     * @param stream
     *      The stream is read as a JSON data. Upon a
     *      successful completion, the stream will be closed by this method.
     * @param type
     *      Type of the content tree's root object.
     * @param <T>
     *      Type of the content tree's root object.
     * @param config
     *      Configuration.
     *
     * @return the newly created root object of the java content tree
     *
     * @throws JsonbException
     *     If any unexpected error(s) occur(s) while unmarshalling.
     * @throws IllegalArgumentException
     *      If any of the parameters is null.
     */
    public <T> T fromJson(InputStream stream, Class<T> type, JsonbConfig config) throws JsonbException;

    /**
     * Unmarshal JSON data from the specified file and return the resulting
     * content tree.
     *
     * @param file
     *      The file to unmarshal JSON data from.
     * @param type
     *      Type of the content tree's root object.
     * @param <T>
     *      Type of the content tree's root object.
     *
     * @return The newly instantiated root object of the java content tree, of type {@code type}
     *
     * @throws JsonbException
     *     If any unexpected error(s) occur(s) during unmarshalling.
     * @throws IllegalArgumentException
     *     If any of the parameters is null.
     */
    public <T> T fromJson(File file, Class<T> type) throws JsonbException;

    /**
     * Unmarshal JSON data from the specified file and return the resulting
     * content tree.
     *
     * @param file
     *      The file to unmarshal JSON data from.
     * @param type
     *      Type of the content tree's root object.
     * @param <T>
     *      Type of the content tree's root object.
     * @param config
     *      Configuration.
     *
     * @return The newly instantiated root object of the java content tree, of type {@code type}
     *
     * @throws JsonbException
     *     If any unexpected error(s) occur(s) during unmarshalling.
     * @throws IllegalArgumentException
     *     If any of the parameters is null.
     */
    public <T> T fromJson(File file, Class<T> type, JsonbConfig config) throws JsonbException;

    /**
     * Writes the Java object tree with root object {@code object} to a String
     * instance as JSON.
     *
     * @param object
     *      The root object of the object content tree to be marshaled. Must not be null.
     *
     * @return String String instance with marshaled JSON data.
     *
     * @throws JsonbException If any unexpected problem occurs during the
     * marshalling, such as I/O error.
     * @throws IllegalArgumentException If any of the method parameters is null.
     *
     * @since JSON Binding 1.0
     */
    public String toJson(Object object) throws JsonbException;

    /**
     * Writes the Java object tree with root object {@code object} to a String
     * instance as JSON.
     *
     * @param object
     *      The root object of the object content tree to be marshaled. Must not be null.
     * @param config
     *      Configuration.
     *
     * @return String String instance with marshaled JSON data.
     *
     * @throws JsonbException If any unexpected problem occurs during the
     * marshalling, such as I/O error.
     * @throws IllegalArgumentException If any of the method parameters is null.
     *
     * @since JSON Binding 1.0
     */
    public String toJson(Object object, JsonbConfig config) throws JsonbException;

    /**
     * Marshal the object content tree into a file.
     *
     * @param object
     *      The object content tree to be marshaled.
     * @param file
     *      File to be written. If this file already exists, it will be
     *      overwritten.
     *
     * @throws JsonbException If the operation fails, such as due to I/O error.
     * @throws IllegalArgumentException If any of the method parameters is {@code null}.
     *
     * @since JSON Binding 1.0
     */
    public void toJson(Object object, File file) throws JsonbException;

    /**
     * Marshal the object content tree into a file.
     *
     * @param object
     *      The object content tree to be marshaled.
     * @param file
     *      File to be written. If this file already exists, it will be
     *      overwritten.
     * @param config
     *      Configuration.
     *
     * @throws JsonbException If the operation fails, such as due to I/O error.
     * @throws IllegalArgumentException If any of the method parameters is {@code null}.
     *
     * @since JSON Binding 1.0
     */
    public void toJson(Object object, File file, JsonbConfig config) throws JsonbException;

    /**
     * Marshal the object content tree into a Writer character stream.
     *
     * @param object
     *      The object content tree to be marshaled.
     * @param writer
     *      The JSON will be sent as a character stream to the given
     *      {@link Writer}. Upon a successful completion, the stream will be closed
     *      by this method.
     *
     * @throws JsonbException If any unexpected problem occurs during the
     * marshalling.
     * @throws IllegalArgumentException If any of the method parameters is null.
     *
     * @since JSON Binding 1.0
     */
    public void toJson(Object object, Writer writer) throws JsonbException;

    /**
     * Marshal the object content tree into a Writer character stream.
     *
     * @param object
     *      The object content tree to be marshaled.
     * @param writer
     *      The JSON will be sent as a character stream to the given
     *      {@link Writer}. Upon a successful completion, the stream will be closed
     *      by this method.
     * @param config
     *      Configuration.
     *
     * @throws JsonbException If any unexpected problem occurs during the
     * marshalling.
     * @throws IllegalArgumentException If any of the method parameters is null.
     *
     * @since JSON Binding 1.0
     */
    public void toJson(Object object, Writer writer, JsonbConfig config) throws JsonbException;

    /**
     * Marshal the object content tree into output stream.
     *
     * @param object
     *      The object content tree to be marshaled.
     * @param stream
     *      The JSON will be sent as a byte stream to the given
     *      {@link OutputStream}. Upon a successful completion, the stream will be closed
     *      by this method.
     *
     * @throws JsonbException If any unexpected problem occurs during the
     * marshalling.
     * @throws IllegalArgumentException If any of the method parameters is null.
     *
     * @since JSON Binding 1.0
     */
    public void toJson(Object object, OutputStream stream) throws JsonbException;

    /**
     * Marshal the object content tree into output stream.
     *
     * @param object
     *      The object content tree to be marshaled.
     * @param stream
     *      The JSON will be sent as a byte stream to the given
     *      {@link OutputStream}. Upon a successful completion, the stream will be closed
     *      by this method.
     * @param config
     *      Configuration.
     *
     * @throws JsonbException If any unexpected problem occurs during the
     * marshalling.
     * @throws IllegalArgumentException If any of the method parameters is null.
     *
     * @since JSON Binding 1.0
     */
    public void toJson(Object object, OutputStream stream, JsonbConfig config) throws JsonbException;

}
