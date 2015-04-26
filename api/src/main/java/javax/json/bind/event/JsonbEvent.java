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
 *     This event represents that some error/warning or some condition
 *     was encountered during serialization or deserialization.
 * </p>
 */
public interface JsonbEvent {

    /**
     * Represents informational/expected event.
     */
    public static final int INFO = -1;

    /**
     * Represents unexpected condition.
     */
    public static final int WARNING = 0;

    /**
     * Represents the error which is recoverable.
     */
    public static final int ERROR = 1;

    /**
     * Represents the error which is not recoverable.
     */
    public static final int FATAL_ERROR = 2;

    /**
     * @return Returns the events severity.
     */
    public int getSeverity();

    /**
     * @return Returns a textual description of the event.
     */
    public String getMessage();

    /**
     * Retrieve the linked exception for this warning/error.
     *
     * @return Returns a {@link Throwable} related to the event. In most cases
     * an exception causing the event.
     */
    public Throwable getLinkedException();

    /**
     * <p>
     * Retrieve the locator for this warning/error.
     * </p>
     *
     * @return Returns a description of the location, where the event
     * occurred.
     */
    public JsonbEventLocator getLocator();
}
