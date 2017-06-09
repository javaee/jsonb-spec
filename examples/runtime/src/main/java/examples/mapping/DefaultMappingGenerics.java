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
import java.util.*;

import static examples.mapping.Utils.*;

public class DefaultMappingGenerics {

    public static void main(String[] args) throws Exception {
        Jsonb jsonb = JsonbBuilder.create();
        toJson_generics(jsonb);
        fromJson_generics(jsonb);
    }

    public static void toJson_generics(Jsonb jsonb) throws Exception {
        // Standard generic class
        MyGenericClass<String, Integer> myGenericClassField = new MyGenericClass<>();
        myGenericClassField.field1 = "value1";
        myGenericClassField.field2 = 3;

        assertEquals("{\"field1\":\"value1\",\"field2\":3}", jsonb.toJson(myGenericClassField));

        // Cyclic generic class is not supported by default mapping, but may be supported by JSON Binding implementations
        MyCyclicGenericClass<CyclicSubClass> myCyclicGenericClass = new MyCyclicGenericClass<>();
        CyclicSubClass cyclicSubClass = new CyclicSubClass();
        cyclicSubClass.subField = "subFieldValue";
        myCyclicGenericClass.field1 = cyclicSubClass;

        assertEquals("{\"field1\":{\"subField\":\"subFieldValue\"}}", jsonb.toJson(myCyclicGenericClass));

        // Functional interface
        FunctionalInterface<String> myFunction = () -> {return "value1";};

        assertEquals("{\"value\":\"value1\"}", jsonb.toJson(myFunction));

        myFunction = new FunctionalInterface<String>() {

            private String value = "initValue";

            @Override
            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        };

        assertEquals("{\"value\":\"initValue\"}", jsonb.toJson(myFunction));

        // Nested generic with concrete parameter type
        NestedGenericConcreteClass nestedGenericConcreteClass = new NestedGenericConcreteClass();
        nestedGenericConcreteClass.list = new ArrayList<>();
        nestedGenericConcreteClass.list.add("value1");

        assertEquals("{\"list\":[\"value1\"]}", jsonb.toJson(nestedGenericConcreteClass));

        // Generic with wildcard
        GenericWithWildcardClass genericWithWildcardClass = new GenericWithWildcardClass();

        List<Map<String, String>> list = new ArrayList<>();

        genericWithWildcardClass.wildcardList = list;

        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("k1", "v1");

        list.add(stringMap);

        assertEquals("{\"wildcardList\":[{\"k1\":\"v1\"}]}", jsonb.toJson(genericWithWildcardClass));

        // Multi-level generics
        MyGenericClass<MyGenericClass<String, String>, Integer> multiLevelGeneric = new MyGenericClass<>();

        MyGenericClass<String, String> myGenericClass = new MyGenericClass<>();
        myGenericClass.field1 = "f1";
        myGenericClass.field2 = "f2";

        multiLevelGeneric.field1 = myGenericClass;
        multiLevelGeneric.field2 = 3;

        assertEquals("{\"field1\":{\"field1\":\"f1\",\"field2\":\"f2\"},\"field2\":3}", jsonb.toJson(multiLevelGeneric));

        // Bounded generics
        BoundedGenericClass<HashSet<Integer>, Circle> boundedGenericClass = new BoundedGenericClass<>();
        List<Shape> shapeList = new ArrayList<>();
        DefaultMappingGenerics defaultMappingGenerics = new DefaultMappingGenerics();
        Circle circle = defaultMappingGenerics.new Circle();
        circle.setRadius(2.5);
        shapeList.add(circle);
        boundedGenericClass.superList = shapeList;

        HashSet<Integer> intSet = new HashSet<>();
        intSet.add(3);

        boundedGenericClass.boundedSet = intSet;

        assertEquals("{\"boundedSet\":[3],\"superList\":[{\"area\":0.0,\"radius\":2.5}]}", jsonb.toJson(boundedGenericClass));

        List<java.util.Optional<String>> expected = Arrays.asList(Optional.empty(), Optional.ofNullable("first"), Optional.of("second"));

        String json = jsonb.toJson(expected, DefaultMappingGenerics.class.getField("listOfOptionalStringField").getGenericType());
        assertEquals("[null,\"first\",\"second\"]",json);
    }

    public List<Optional<String>> listOfOptionalStringField = new ArrayList<>();

    public MyGenericClass<String, Integer> myGenericClassField = new MyGenericClass<>();
    public MyCyclicGenericClass<CyclicSubClass> myCyclicGenericClassField = new MyCyclicGenericClass<CyclicSubClass>();
    public MyGenericClass<MyGenericClass<String, String>, Integer> multiLevelGenericClassField = new MyGenericClass<>();
    public BoundedGenericClass<HashSet<Integer>, Circle> boundedGenericClass = new BoundedGenericClass<>();
    public BoundedGenericClass<HashSet<Double>, Circle> otherBoundedGenericClass = new BoundedGenericClass<>();


    public static void fromJson_generics(Jsonb jsonb) throws Exception {
        DefaultMapping defaultMapping = new DefaultMapping();

        MyGenericClass<String, Integer> myGenericInstance = jsonb.fromJson("{\"field1\":\"value1\", \"field2\":1}",
                DefaultMappingGenerics.class.getField("myGenericClassField").getGenericType());

        // Cyclic generic class is not supported by default mapping, but may be supported by JSON Binding implementations
        MyCyclicGenericClass<CyclicSubClass> myCyclicGenericClass = jsonb.fromJson("{\"field1\":{\"subField\":\"subFieldValue\"}}",
                DefaultMappingGenerics.class.getField("myCyclicGenericClassField").getGenericType());

        // Deserialize into (generic) interface is by default unsupported (with the exception of concrete interfaces
        // defined elsewhere in default mapping, e.g. java.lang.Number)

        // Nested generic concrete class, I am able to access signature of List<String> from class file
        NestedGenericConcreteClass nestedGenericConcreteClass = jsonb.fromJson("{\"list\":[\"value1\"]}", NestedGenericConcreteClass.class);

        // Generic with wildcard

        // WildcardList is treated as List<Object>
        GenericWithWildcardClass genericWithWildcardClass = jsonb.fromJson("{\"wildcardList\":[{\"k1\":\"v1\"}]}", GenericWithWildcardClass.class);
        assert(genericWithWildcardClass.wildcardList.get(0) instanceof Map);

        // Multi-level generics

        // T,U is treated as Object
        MyGenericClass multiLevelGeneric = jsonb.fromJson("{\"field1\":{\"field1\":\"f1\",\"field2\":\"f2\"},\"field2\":3}", MyGenericClass.class);

        assert(multiLevelGeneric.field1 instanceof Map);
        assert(multiLevelGeneric.field2 instanceof Integer);

        // Deserialize with runtime type
        MyGenericClass<MyGenericClass<String, String>, Integer> myGenericClass = jsonb.fromJson("{\"field1\":{\"field1\":\"f1\",\"field2\":\"f2\"},\"field2\":3}",
                DefaultMappingGenerics.class.getField("multiLevelGenericClassField").getGenericType());

        // Bounded generics
        BoundedGenericClass<HashSet<Integer>, Circle> boundedGeneric = jsonb.fromJson("{\"boundedSet\":[3],\"superList\":[{\"radius\":2.5}]}",
                DefaultMappingGenerics.class.getField("boundedGenericClass").getGenericType());

        // Exception incompatible types
        try {
            BoundedGenericClass<HashSet<Integer>, Circle> otherGeneric = jsonb.fromJson("{\"boundedSet\":[3],\"superList\":[{\"radius\":2.5}]}",
                    DefaultMappingGenerics.class.getField("otherBoundedGenericClass").getGenericType());
            HashSet<Integer> intSet = otherGeneric.boundedSet;
            Integer intValue = intSet.iterator().next();
            System.out.println("intValue="+intValue);
            assert(false);
        } catch (ClassCastException e) {
            // Exception - incompatible types
            // Double cannot be converted to Integer
        }
    }

    static class BoundedGenericClass<T extends Set<? extends Number>, U> {
        public List<? super U> superList;
        public T boundedSet;

        public BoundedGenericClass() {}
    }

    interface FunctionalInterface<T> {
        public T getValue();
    }

    static class MyGenericClass<T,U> {
        public T field1;
        public U field2;

        public MyGenericClass() {}
    }

    static class MyCyclicGenericClass<T extends MyCyclicGenericClass<? extends T>> {
        public T field1;

        public MyCyclicGenericClass() {}
    }

    static class CyclicSubClass extends MyCyclicGenericClass<CyclicSubClass> {
        public String subField;

        public CyclicSubClass() {}
    }

    static class NestedGenericConcreteClass {
        public List<String> list;

        public NestedGenericConcreteClass() {}
    }

    static class GenericWithWildcardClass {
        public List<?> wildcardList;

        public GenericWithWildcardClass() {}
    }

    public class Shape {
        private double area;

        public Shape() {
        }

        public double getArea() {
            return area;
        }

        public void setArea(double area) {
            this.area = area;
        }
    }

    public class Circle extends Shape {
        private double radius;

        public Circle() {
            super();
        }

        public double getRadius() {
            return radius;
        }

        public void setRadius(double radius) {
            this.radius = radius;
        }
    }
}
