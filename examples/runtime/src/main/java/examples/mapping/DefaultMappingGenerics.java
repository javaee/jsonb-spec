package examples.mapping;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;
import java.util.*;

import static examples.mapping.Utils.*;

/**
 * @author Martin Vojtek
 */
public class DefaultMappingGenerics {

    public static void main(String[] args) throws Exception {
        Jsonb jsonb = JsonbBuilder.create();
        toJson_generics(jsonb);
        fromJson_generics(jsonb);
    }

    public static void toJson_generics(Jsonb jsonb) throws Exception {

        //standard generic class
        MyGenericClass<String, Integer> myGenericClassField = new MyGenericClass<>();
        myGenericClassField.field1 = "value1";
        myGenericClassField.field2 = 3;

        assertEquals("{\"field1\":\"value1\",\"field2\":3}", jsonb.toJson(myGenericClassField));

        //cyclic generic class
        MyCyclicGenericClass<CyclicSubClass> myCyclicGenericClass = new MyCyclicGenericClass<>();
        CyclicSubClass cyclicSubClass = new CyclicSubClass();
        cyclicSubClass.subField = "subFieldValue";
        myCyclicGenericClass.field1 = cyclicSubClass;

        assertEquals("{\"field1\":{\"subField\":\"subFieldValue\"}}", jsonb.toJson(myCyclicGenericClass));

        //functional interface
        FunctionalInterface<String> myFunction = () -> {return "value1";};

        assertEquals("{}", jsonb.toJson(myFunction));

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

        //nested generic with concrete parameter type
        NestedGenericConcreteClass nestedGenericConcreteClass = new NestedGenericConcreteClass();
        nestedGenericConcreteClass.list = new ArrayList<>();
        nestedGenericConcreteClass.list.add("value1");

        assertEquals("{\"list\":[\"value1\"]}", jsonb.toJson(nestedGenericConcreteClass));

        //generic with wildcard
        GenericWithWildcardClass genericWithWildcardClass = new GenericWithWildcardClass();

        List<Map<String, String>> list = new ArrayList<>();

        genericWithWildcardClass.wildcardList = list;

        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("k1", "v1");

        list.add(stringMap);

        assertEquals("{\"wildcardList\":[{\"k1\":\"v1\"}]}", jsonb.toJson(genericWithWildcardClass));

        //multi level generics
        MyGenericClass<MyGenericClass<String, String>, Integer> multiLevelGeneric = new MyGenericClass<>();

        MyGenericClass<String, String> myGenericClass = new MyGenericClass<>();
        myGenericClass.field1 = "f1";
        myGenericClass.field2 = "f2";

        multiLevelGeneric.field1 = myGenericClass;
        multiLevelGeneric.field2 = 3;

        assertEquals("{\"field1\":{\"field1\":\"f1\",\"field2\":\"f2\"},\"field2\":3}", jsonb.toJson(multiLevelGeneric));

        //bounded generics
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

        assertEquals("{\"boundedSet\":[3],\"superList\":[{\"radius\":2.5}]}", jsonb.toJson(boundedGenericClass));
    }

    public MyGenericClass<String, Integer> myGenericClassField = new MyGenericClass<>();
    public MyCyclicGenericClass<CyclicSubClass> myCyclicGenericClassField = new MyCyclicGenericClass<CyclicSubClass>();
    public MyGenericClass<MyGenericClass<String, String>, Integer> multiLevelGenericClassField = new MyGenericClass<>();
    public BoundedGenericClass<HashSet<Integer>, Circle> boundedGenericClass = new BoundedGenericClass<>();
    public BoundedGenericClass<HashSet<Double>, Circle> otherBoundedGenericClass = new BoundedGenericClass<>();


    public static void fromJson_generics(Jsonb jsonb) throws Exception {
        DefaultMapping defaultMapping = new DefaultMapping();

        MyGenericClass<String, Integer> myGenericInstance = fromJson("{\"field1\":\"value1\", \"field2\":1}",
                DefaultMapping.class.getField("myGenericClassField").getGenericType());

        MyCyclicGenericClass<CyclicSubClass> myCyclicGenericClass = fromJson("{\"field1\":{\"subField\":\"subFieldValue\"}}",
                DefaultMapping.class.getField("myCyclicGenericClassField").getGenericType());

        //unmarshal into (generic) interface is by default unsupported (with the exception of concrete interfaces
        // defined elsewhere in default mapping, e.g. java.lang.Number)

        //nested generic concrete class, I am able to access signature of List<String> from class file
        NestedGenericConcreteClass nestedGenericConcreteClass = jsonb.fromJson("{\"list\":[\"value1\"]}", NestedGenericConcreteClass.class);

        //generic with wildcard

        //wildcardList is treated as List<Object>
        GenericWithWildcardClass genericWithWildcardClass = jsonb.fromJson("{\"wildcardList\":[{\"k1\":\"v1\"}]}", GenericWithWildcardClass.class);
        assert(genericWithWildcardClass.wildcardList.get(0) instanceof LinkedHashMap);

        //multi level generics

        //T,U is treated as Object
        MyGenericClass multiLevelGeneric = jsonb.fromJson("{\"field1\":{\"field1\":\"f1\",\"field2\":\"f2\"},\"field2\":3}", MyGenericClass.class);

        assert(multiLevelGeneric.field1 instanceof LinkedHashMap);
        assert(multiLevelGeneric.field2 instanceof Integer);

        //unmarshal with runtime type
        MyGenericClass<MyGenericClass<String, String>, Integer> myGenericClass = fromJson("{\"field1\":{\"field1\":\"f1\",\"field2\":\"f2\"},\"field2\":3}",
                DefaultMapping.class.getField("multiLevelGenericClassField").getGenericType());

        //bounded generics
        BoundedGenericClass<HashSet<Integer>, Circle> boundedGeneric = fromJson("{\"boundedSet\":[3],\"superList\":[{\"radius\":2.5}]}",
                DefaultMapping.class.getField("boundedGenericClass").getGenericType());

        //exception incompatible types
        try {
            BoundedGenericClass<HashSet<Integer>, Circle> otherGeneric = fromJson("{\"boundedSet\":[3],\"superList\":[{\"radius\":2.5}]}",
                    DefaultMapping.class.getField("otherBoundedGenericClass").getGenericType());
            HashSet<Integer> intSet = otherGeneric.boundedSet;
            Integer intValue = intSet.iterator().next();
            System.out.println("intValue="+intValue);
            assert(false);
        } catch (JsonbException e) {
            //exception - incompatible types
            //Double cannot be converted to Integer
        }
    }

    static class BoundedGenericClass<T extends Set<? extends Number>, U> {
        public List<? super U> superList;
        public T boundedSet;

        public BoundedGenericClass() {}
    }

    static interface FunctionalInterface<T> {
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
