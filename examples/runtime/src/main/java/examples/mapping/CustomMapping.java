/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2015, 2017 Oracle and/or its affiliates. All rights reserved.
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

package examples.mapping;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.annotation.JsonbNillable;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.config.PropertyNamingStrategy;
import javax.json.bind.config.PropertyOrderStrategy;
import java.lang.reflect.Field;
import java.util.*;

import static examples.mapping.Utils.assertEquals;

public class CustomMapping {

    public static void main(String[] args) throws Exception {
        Jsonb jsonb = JsonbBuilder.create();

        fromJson_customName(jsonb);
        toJson_customName(jsonb);
        customNamePolicy();

        toJson_nillable(jsonb);

        toJson_propertyOrder(jsonb);
    }

    public static void fromJson_customName(Jsonb jsonb) {
        CustomizedName customizedName = jsonb.fromJson("{\"longDesc\":\"This is long description\"}", CustomizedName.class);
        assertEquals("This is long description", customizedName.longDescription);

        CustomizedNameWithSetter customizedNameWithSetter = jsonb.fromJson("{\"long-desc\":\"This is long description\"}", CustomizedNameWithSetter.class);
        assertEquals("This is long description", customizedNameWithSetter.longDescription);
    }

    public static void toJson_customName(Jsonb jsonb) {
        CustomizedName customizedName = new CustomizedName();
        customizedName.longDescription = "This is long description";

        assertEquals("{\"longDesc\":\"This is long description\"}", jsonb.toJson(customizedName));

        CustomizedNameWithSetter customizedNameWithSetter = new CustomizedNameWithSetter();
        customizedNameWithSetter.setLongDescription("This is long description");

        assertEquals("{\"longDesc\":\"This is long description\"}", jsonb.toJson(customizedNameWithSetter));
    }

    public static void customNamePolicy() {
        JsonbConfig caseInsensitiveConfig = new JsonbConfig().withPropertyNamingStrategy(PropertyNamingStrategy.CASE_INSENSITIVE);
        Jsonb caseInsensitiveJsonb = JsonbBuilder.create(caseInsensitiveConfig);

        assertEquals("{\"name\":\"Effective Java\"}", caseInsensitiveJsonb.toJson(new Book()));

        Book book = caseInsensitiveJsonb.fromJson("{\"NAme\":\"Effective Java Second Edition\"}", Book.class);
        assertEquals("Effective Java Second Edition", book.name);
    }

    public static void toJson_nillable(Jsonb jsonb) {
        assertEquals("{\"nillableField\":null}", jsonb.toJson(new NillableClass()));

        assertEquals("{\"nillableField\":null}", jsonb.toJson(new NillableClassWithGetter()));

        assertEquals("{\"nillableField\":null}", jsonb.toJson(new NillableType()));

        assertEquals("{\"nillableField\":null}", jsonb.toJson(new NillableTypeOverride()));

        JsonbConfig nillableConfig = new JsonbConfig().withNullValues(true);
        Jsonb nillableJsonb = JsonbBuilder.create(nillableConfig);

        Book book = new Book();
        book.name = null;
        assertEquals("{\"name\":null}", nillableJsonb.toJson(new Book()));
    }

    public static void toJson_propertyOrder(Jsonb jsonb) {
        assertEquals("{\"dField\":\"d\",\"cField\":\"c\",\"bField\":\"b\",\"aField\":\"a\"}", jsonb.toJson(new PropertyOrderSpecificClass()));
    }

    @JsonbPropertyOrder({"dField", "cField", "bField", "aField"})
    static class PropertyOrderSpecificClass {
        public String aField = "a";

        public String dField = "d";

        public String cField = "c";

        public String bField = "b";

        public PropertyOrderSpecificClass() {
        }
    }

    static class PropertyOrderClass {
        public String aField = "a";

        public String dField = "d";

        public String cField = "c";

        public String bField = "b";

        public PropertyOrderClass() {
        }
    }

    static class NillableClass {
        @JsonbProperty(nillable = true)
        public String nillableField;

        public NillableClass() {
        }
    }

    static class NillableClassWithGetter {
        public String nillableField;

        public NillableClassWithGetter() {
        }

        @JsonbProperty(nillable = true)
        public String getNillableField() {
            return nillableField;
        }

        public void setNillableField(String nillableField) {
            this.nillableField = nillableField;
        }
    }

    @JsonbNillable
    static class NillableType {
        public String nillableField;

        public NillableType() {
        }
    }

    @JsonbNillable
    static class NillableTypeOverride {
        public String nillableField;

        @JsonbProperty(nillable = false)
        public String absentField;

        public NillableTypeOverride() {
        }
    }

    static class Book {
        public String name = "Effective Java";

        public Book() {
        }
    }

    static class CustomizedName {
        @JsonbProperty("longDesc")
        public String longDescription;

        public CustomizedName() {
        }
    }

    static class CustomizedNameWithSetter {
        private String longDescription;

        public CustomizedNameWithSetter() {
        }

        @JsonbProperty("longDesc")
        public String getLongDescription() {
            return longDescription;
        }

        @JsonbProperty("long-desc")
        public void setLongDescription(String longDescription) {
            this.longDescription = longDescription;
        }
    }
}
