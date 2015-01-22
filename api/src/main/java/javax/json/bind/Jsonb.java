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
import java.io.Reader;
import java.io.Writer;
import java.util.Map;
import javax.json.bind.spi.JsonbProvider;

/**
 * This class defines convenience methods for use of JSON Binding as well as
 * factory methods to create JSON Binding processing objects.
 *
 * <p>
 * Convenience methods defined in this class combine several basic operations
 * in the {@link JsonbContext}, {@link JsonbUnmarshaller}, and {@link JsonbMarshaller}.
 * They locate JSON Binding provider instance using the {@link JsonbProvider#provider()}
 * method.
 *
 * <p>
 * All the methods in this class are safe for use by multiple concurrent threads.
 *
 * @author Martin Grebac
 * @since JSON Binding 1.0
 */
public final class Jsonb {

    /**
     * No instantiation allowed.
     */
    private Jsonb() { }

    /**
     * Reads in a Java object tree from the given JSON input.
     *
     * @param file
     *      Reads the entire file as JSON.
     *
     * @return Newly created root object of the Java object tree.
     */
    public static Object unmarshal(final File file) {
        JsonbContext jsonbContext = JsonbProvider.provider().createContext();
        JsonbUnmarshaller jsonbUnmarshaller = jsonbContext.createUnmarshaller();
        return jsonbUnmarshaller.unmarshal(file);
    }

    /**
     * Reads in a Java object tree from the given JSON input.
     *
     * @param file
     *      Reads the entire file as JSON.
     * @param type
     *      Type of the content tree's root object.
     *
     * @return Newly created root object of the Java object tree, of type type.
     */
//    public static <T> T unmarshal(File file, Class<T> type) {
//        JsonbContext jsonbContext = JsonbProvider.provider().createContext(type);
//        JsonbUnmarshaller jsonbUnmarshaller = jsonbContext.createUnmarshaller();
//        return jsonbUnmarshaller.unmarshal(file, type);
//    }

    /**
     * Reads in a Java object tree from the given JSON string.
     *
     * @param jsonString
     *      The string is parsed as JSON data.
     *
     * @return Newly created root object of the Java object tree.
     */
    public static Object unmarshal(final String jsonString) {
        JsonbContext jsonbContext = JsonbProvider.provider().createContext();
        JsonbUnmarshaller jsonbUnmarshaller = jsonbContext.createUnmarshaller();
        return jsonbUnmarshaller.unmarshal(jsonString);
    }

    /**
     * Reads in a Java object tree from the given JSON string.
     *
     * @param jsonString
     *      The string is parsed as JSON data.
     * @param type
     *      Type of the content tree's root object.
     *
     * @return Newly created root object of the Java object tree, of type type.
     */
//    public static <T> T unmarshal(String jsonString, Class<T> type) {
//        JsonbContext jsonbContext = JsonbProvider.provider().createContext(type);
//        JsonbUnmarshaller jsonbUnmarshaller = jsonbContext.createUnmarshaller();
//        return jsonbUnmarshaller.unmarshal(jsonString, type);
//    }

    /**
     * Reads in a Java object tree from the given Reader as JSON data.
     *
     * @param reader
     *      The character stream is read as a JSON data.
     *      Upon a successful completion, the stream will be closed by this method.
     *
     * @return Newly created root object of the Java object tree.
     */
    public static Object unmarshal(final Reader reader) {
        JsonbContext jsonbContext = JsonbProvider.provider().createContext();
        JsonbUnmarshaller jsonbUnmarshaller = jsonbContext.createUnmarshaller();
        return jsonbUnmarshaller.unmarshal(reader);
    }

    /**
     * Reads in a Java object tree from the given Reader as JSON data.
     *
     * @param reader
     *      The character stream is read as a JSON data.
     *      Upon a successful completion, the stream will be closed by this method.
     * @param type
     *      Type of the content tree's root object.
     *
     * @return Newly created root object of the Java object tree, of type type.
     */
//    public static <T> T unmarshal(Reader reader, Class<T> type) {
//        JsonbContext jsonbContext = JsonbProvider.provider().createContext(type);
//        JsonbUnmarshaller jsonbUnmarshaller = jsonbContext.createUnmarshaller();
//        return jsonbUnmarshaller.unmarshal(reader, type);
//    }

     /**
     * Returns a new instance of JsonbContext class. The client application has to
     * provide list of classes that the new context object needs to recognize.
     *
     * The implementation will not only recognize the provided classes, but it will
     * also recognize any classes that are directly or indirectly statically referenced
     * Subclasses of referenced classes, nor {@link javax.json.bind.annotation.JsonTransient} annotated classes
     * are recognized.
     *
     * @param classes
     *      List of java classes to be recognized by the new {@link JsonbContext}.
     *      Can be empty, in which case a default {@link JsonbContext} which only
     *      recognizes default spec-defined classes will be returned.
     *
     * @return JsonbContext
     *      A new instance of JsonbContext class. Always a non-null valid object.
     *
     * @throws javax.json.bind.JsonbException
     *      If an error was encountered while creating the JsonbContext instance,
     *      such as (but not limited to) no JSON Binding provider is found, classes
     *      provide conflicting annotations.
     *
     * @throws IllegalArgumentException
     *      If the parameter contains {@code null}
     */
    public static JsonbContext createContext(final Class<?> ... classes) {
        return JsonbProvider.provider().createContext(classes);
    }

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
     *      Contains spec defined and provider specific configuration properties. Can be empty.
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
     *      such as (but not limited to) no JSON Binding provider is found, classes
     *      provide conflicting annotations.
     *
     * @throws IllegalArgumentException
     *      If the parameter contains {@code null}
     */
    public static JsonbContext createContext(final Map<String, ?> configuration, final Class<?> ... classes) {
        return JsonbProvider.provider().createContext(configuration, classes);
    }

    /**
     * Writes a Java object tree to JSON String.
     *
     * @param object
     *      The Java object to be marshaled into JSON.
     *      This parameter must not be null.
     *
     * @return String
     *      The JSON data will be returned in the form of String instance.
     *
     * @throws JsonbException
     *      If the operation fails, such as due to I/O error.
     *
     * @throws IllegalArgumentException
     *      If object is {@code null}
     */
    public static String marshal(final Object object) {
        JsonbContext jsonbContext = Jsonb.createContext(object.getClass());
        JsonbMarshaller jsonMarshaller = jsonbContext.createMarshaller();
        return jsonMarshaller.marshal(object);
    }

    /**
     * Writes a Java object tree to Writer as JSON character stream.
     *
     * @param object
     *      The Java object to be marshaled into JSON.
     *      This parameter must not be null.
     *
     * @param writer
     *      The JSON will be sent as a character stream to the given {@link Writer}.
     *      Upon a successful completion, the stream will be closed by this method.
     *
     * @throws JsonbException
     *      If the operation fails, such as due to I/O error.
     *
     * @throws IllegalArgumentException
     *      If any of parameters is {@code null}
     */
    public static void marshal(final Object object, final Writer writer) {
        JsonbContext jsonbContext = Jsonb.createContext(object.getClass());
        JsonbMarshaller jsonMarshaller = jsonbContext.createMarshaller();
        jsonMarshaller.marshal(object, writer);
    }

    /**
     * Writes a Java object tree to a File in JSON format.
     *
     * @param object
     *      The Java object to be marshaled into JSON.
     *      This parameter must not be null.
     *
     * @param file
     *      The JSON will be written to the given {@link File}. If the file already
     *      exists, it will be overwritten.
     *
     * @throws JsonbException
     *      If the operation fails, such as due to I/O error.
     *
     * @throws IllegalArgumentException
     *      If any of parameters is {@code null}
     */
    public static void marshal(final Object object, final File file) {
        JsonbContext jsonbContext = Jsonb.createContext(object.getClass());
        JsonbMarshaller jsonMarshaller = jsonbContext.createMarshaller();
        jsonMarshaller.marshal(object, file);
    }

}
