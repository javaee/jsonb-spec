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

package javax.json.bind.event;

/**
 * <p>
 * A basic event handler interface for processing events.
 * </p>
 *
 * <p>
 * If an application needs to implement customized event handling, it must implement
 * this interface and then register it with {@link javax.json.bind.JsonbConfig}.
 * </p>
 *
 * <p>
 * If the {@code handleEvent} method throws an unchecked runtime exception, the JAXB Provider
 * must treat that as if the method returned false, effectively terminating whatever
 * operation was in progress at the time.
 * </p>
 *
 * <p>
 * Modifying the Java content tree within your event handler is undefined
 * by the specification and may result in unexpected behaviour.
 * </p>
 *
 * <p>
 * Failing to return false from the handleEvent method after encountering a fatal error
 * is undefined by the specification and may result in unexpected behavior.
 * </p>
 */
public interface JsonbEventHandler {
    /** <p>The {@code handleEvent} method is invoked by the
     * JSON-B provider, if a problem (or some situation) was found. The events
     * {@link JsonbEventLocator} may be
     * used to locate the source of the problem.</p>
     *
     * @param event The event being reported to the JSON-B user.
     * @return True as an indicator that the JSON-B provider should
     *   attempt to continue its current operation. This will not always work.
     *   In particular, you cannot expect that the operation
     *   continues, if a fatal error was reported. False to
     *   indicate that the JSON-B provider should terminate the
     *   operation and through an appropriate exception.
     * @throws IllegalArgumentException The parameter is null.
     */
    public boolean handleEvent(JsonbEvent event);
}
