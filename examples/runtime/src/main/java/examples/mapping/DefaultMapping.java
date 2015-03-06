package examples.mapping;

import javax.json.*;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static examples.mapping.Utils.*;

/**
 * @author Martin Vojtek
 */
public class DefaultMapping {

    public static void main(String[] args) throws Exception {
        //in this context, words serialize and marshal are used interchangeably
        //in this context, words deserialize and unmarshal are used interchangeably

        //statements true for default mapping

        //no support for unmarshal of polymorphic types
        //no support for unmarshal of anonymous classes

        //fail fast exception strategy

        //handling of null values:
        //objects ->
        //toJson:  null -> property not present
        //fromJson: property not present or null value found -> null value set into field
        //arrays ->
        //toJson: null -> null element in the json array
        //fromJson: null element in the json array -> null element in the list

        //access to fields
        //by default we are using getters/setters methods
        //if getter/setter method is not found, use field

        Jsonb jsonb = JsonbBuilder.create();
        fromJson_Primitives(jsonb);
        toJson_Primitives(jsonb);

        fromJson_Collections(jsonb);
        toJson_Collection(jsonb);

        fromJson_URL_URI(jsonb);
        toJson_URL_URI(jsonb);

        fromJson_Enums(jsonb);
        toJson_Enums(jsonb);

        fromJson_Arrays(jsonb);
        toJson_Arrays(jsonb);

        fromJson_Structures(jsonb);
        toJson_Structures(jsonb);

        fromJson_POJOs(jsonb);
        toJson_POJOs(jsonb);

        fromJson_Inheritance(jsonb);
        toJson_Inheritance(jsonb);

        toJson_Anonymous_Class(jsonb);

        fromJson_Instantiation(jsonb);

        toJson_defaultNames(jsonb);
        fromJson_defaultNames(jsonb);

        toJson_attributesOrdering(jsonb);

        toJson_nullValues(jsonb);
        fromJson_nullValues(jsonb);

        toJson_modifiers(jsonb);
        fromJson_modifiers(jsonb);

        toJson_optional(jsonb);
        fromJson_optional(jsonb);
    }

    public static void fromJson_Primitives(Jsonb jsonb) {
        //String
        String str = jsonb.fromJson("\"some_string\"", String.class);

        //String escaping
        String escapedString = jsonb.fromJson(" \\\" \\\\ \\/ \\b \\f \\n \\r \\t \\u0039", String.class);
        assertEquals(" \" \\ / \b \f \n \r \t 9", escapedString);

        //Character
        Character ch = jsonb.fromJson("\"\uFFFF\"", Character.class);

        //Byte
        Byte byte1 = jsonb.fromJson("1", Byte.class);

        //Short
        Short short1 = jsonb.fromJson("1", Short.class);

        //Integer
        Integer int1 = jsonb.fromJson("1", Integer.class);

        //Long
        Long long1 = jsonb.fromJson("1", Long.class);

        //Float
        Float float1 = jsonb.fromJson("1.2", Float.class);

        //Double
        Double double1 = jsonb.fromJson("1.2", Double.class);

        //BigInteger
        BigInteger bigInteger = jsonb.fromJson("1", BigInteger.class);

        //BigDecimal
        BigDecimal bigDecimal = jsonb.fromJson("1.2", BigDecimal.class);

        //Number
        Number number = (BigDecimal)jsonb.fromJson("1.2", Number.class);

        //Boolean
        Boolean trueValue = jsonb.fromJson("true", Boolean.class);

        //Boolean
        Boolean falseValue = jsonb.fromJson("false", Boolean.class);

        //null
        Object nullValue = jsonb.fromJson("null", Object.class);

        assert(nullValue == null);
    }

    public static void exceptions(Jsonb jsonb) {
        //Exception
        //fail fast strategy by default

        //incompatible types
        try {
            jsonb.fromJson("not_a_number", Integer.class);
            assert(false);
        } catch (JsonbException e) {}

        //incompatible types
        try {
            jsonb.fromJson("[null,1]", int[].class);
            assert(false);
        } catch (JsonbException e) {}

        //bad structure
        try {
            jsonb.fromJson("[1,2", int[].class);
            assert(false);
        } catch (JsonbException e) {}

        //overflow - Value out of range
        try {
            jsonb.fromJson("["+new Integer(Byte.MAX_VALUE + 1)+"]", Byte.class);
            assert(false);
        } catch (JsonbException e) {}

        //underflow - Value out of range
        try {
            jsonb.fromJson("["+new Integer(Byte.MIN_VALUE - 1)+"]", Byte.class);
            assert(false);
        } catch (JsonbException e) {}
    }

    public static void toJson_Primitives(Jsonb jsonb) {

        //String
        assertEquals("\"some_string\"", jsonb.toJson("some_string"));

        //escaped String
        assertEquals("\" \\\\ \\\" / \\b \\f \\n \\r \\t 9\"", jsonb.toJson(" \\ \" / \b \f \n \r \t \u0039"));

        //Character
        assertEquals("\"\uFFFF\"", jsonb.toJson('\uFFFF'));

        //Byte
        assertEquals("1", jsonb.toJson((byte)1));

        //Short
        assertEquals("1", jsonb.toJson((short)1));

        //Integer
        assertEquals("1", jsonb.toJson(1));

        //Long
        assertEquals("5", jsonb.toJson(5L));

        //Float
        assertEquals("1.2", jsonb.toJson(1.2f));

        //Double
        assertEquals("1.2", jsonb.toJson(1.2));

        //BigInteger
        assertEquals("1", jsonb.toJson(new BigInteger("1")));

        //BigDecimal
        assertEquals("1.2", jsonb.toJson(new BigDecimal("1.2")));

        //Number
        assertEquals("1.2", jsonb.toJson((java.lang.Number)1.2));

        //Boolean true
        assertEquals("true", jsonb.toJson(true));

        //Boolean false
        assertEquals("false", jsonb.toJson(false));

        //null
        assertEquals("null", jsonb.toJson(null));
    }

    public static void fromJson_Structures(Jsonb jsonb) {

        //Map
        Map<String, Object> map = (LinkedHashMap<String,Object>)jsonb.fromJson("{\"name\":\"unknown object\"}", Object.class);

        //mapping for number  -> Integer, Long, BigDecimal
        Map<String, Object> mapWithBigDecimal = (Map<String, Object>)jsonb.fromJson("{\"intValue\":5,\"longValue\":17179869184,\"otherValue\":1.2}", Object.class);
        assert(mapWithBigDecimal.get("intValue") instanceof Integer);
        assert(mapWithBigDecimal.get("longValue") instanceof Long);
        assert(mapWithBigDecimal.get("otherValue") instanceof BigDecimal);

        //Collection
        Collection<Object> collection = (ArrayList<Object>)jsonb.fromJson("[{\"value\":\"first\"}, {\"value\":\"second\"}]", Object.class);

        //JsonStructure
        JsonStructure jsonStructure = jsonb.fromJson("{\"name\":\"unknown object\"}", JsonStructure.class);

        //JsonObject
        JsonObject jsonObject = jsonb.fromJson("{\"name\":\"unknown object\"}", JsonObject.class);

        //JsonArray
        JsonArray jsonArray = jsonb.fromJson("[{\"value\":\"first\"},{\"value\":\"second\"}]", JsonArray.class);

        //JsonValue
        JsonValue jsonValue = jsonb.fromJson("1", JsonValue.class);
    }

    public static void toJson_Structures(Jsonb jsonb) {

        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonObject jsonObject = factory.createObjectBuilder().
                add("name", "home").
                add("city", "Prague")
                .build();

        //JsonObject
        assertEquals("{\"name\":\"home\",\"city\":\"Prague\"}", jsonb.toJson(jsonObject));

        JsonArray jsonArray = factory.createArrayBuilder().add(jsonObject).add(jsonObject).build();

        //JsonArray
        assertEquals("[{\"name\":\"home\",\"city\":\"Prague\"},{\"name\":\"home\",\"city\":\"Prague\"}]", jsonb.toJson(jsonArray));

        //JsonStructure
        assertEquals("[{\"name\":\"home\",\"city\":\"Prague\"},{\"name\":\"home\",\"city\":\"Prague\"}]", jsonb.toJson((JsonStructure)jsonArray));

        //JsonValue
        assertEquals("true", jsonb.toJson(JsonValue.TRUE));

        //Map
        Map<String, Object> commonMap = new LinkedHashMap<>();
        commonMap.put("first", 1);
        commonMap.put("second", 2);

        assertEquals("{\"first\":1,\"second\":2}", jsonb.toJson(commonMap));

        //Collection
        Collection<Object> commonList = new ArrayList<>();
        commonList.add(1);
        commonList.add(2);

        assertEquals("[1,2]", jsonb.toJson(commonList));
    }

    public static void fromJson_Collections(Jsonb jsonb) {

        //support unmarshal of java.util.Collection and java.util.Map and its subinterfaces and implementing (sub)classes

        //Collection, Map

        //Set, HashSet, NavigableSet, SortedSet, TreeSet, LinkedHashSet, TreeHashSet

        //HashMap, NavigableMap, SortedMap, TreeMap, LinkedHashMap, TreeHashMap

        //List, ArrayList, LinkedList

        //Deque, ArrayDeque, Queue, PriorityQueue

        Collection<Object> collection = jsonb.fromJson("[\"first\",\"second\"]", Collection.class);

        Map<String, Object> map = jsonb.fromJson("{\"first\":\"second\"}", Map.class);

        //concrete implementation of Map
        HashMap<String, Object> hashMap = jsonb.fromJson("{\"first\":\"second\"}", HashMap.class);

        //concrete implementation of Collection
        ArrayList<Object> arrayList = jsonb.fromJson("[\"first\",\"second\"]", ArrayList.class);

        //deque
        Deque<String> dequeList = jsonb.fromJson("[\"first\",\"second\"]", Deque.class);
        assert(dequeList.size() == 2);
        assertEquals("first", dequeList.getFirst());
        assertEquals("second", dequeList.getLast());

        //JSON Binding supports default unmarshal of the following interfaces
        //syntax: interface -> default implementation

        //Collection -> ArrayList
        //Set -> HashSet
        //NavigableSet -> TreeSet
        //SortedSet -> TreeSet
        //Map -> HashMap
        //SortedMap -> TreeMap
        //NavigableMap -> TreeMap
        //Deque -> ArrayDeque
        //Queue -> ArrayDeque

        //any implementation of Collection and Map with public default constructor is deserializable

    }

    public static void toJson_Collection(Jsonb jsonb) {
        Collection<Integer> collection = Arrays.asList(1, 2, 3);

        assertEquals("[1,2,3]", jsonb.toJson(collection));

        Map<String, Integer> map = new HashMap<>();
        map.put("1",1);
        map.put("2",2);
        map.put("3",3);

        assertEquals("{\"1\":1,\"2\":2,\"3\":3}", jsonb.toJson(map));

        //any implementation of Collection and Map is serializable

        //deque
        Deque<String> deque = new ArrayDeque<>();
        deque.add("first");
        deque.add("second");

        assertEquals("[\"first\",\"second\"]", jsonb.toJson(deque));
    }

    public static void fromJson_Arrays(Jsonb jsonb) {

        //support of arrays of types that JSON Binding is able to deserialize
        //Byte[], Short[], Integer[] Long[], Float[], Double[], BigInteger[], BigDecimal[], Number[]
        //Object[], JsonArray[], JsonObject[], JsonStructure[]
        //String[], Character[]
        //byte[], short[], int[], long[], float[], double[], char[], boolean[]
        //java.net.URL[], java.net.URI[]
        //Map[], Collection[], other collections ...
        //enum, EnumSet, EnumMap
        //support of multidimensional arrays


        //Several examples

        //Byte arrays
        Byte[] byteArray = jsonb.fromJson("[1,2]", Byte[].class);

        //Integer array
        Integer[] integerArray = jsonb.fromJson("[1,2]", Integer[].class);

        //int array
        int[] intArray = jsonb.fromJson("[1,2]", int[].class);

        //String arrays
        String[] stringArray = jsonb.fromJson("[\"first\",\"second\"]", String[].class);

        //multidimensional arrays
        String[][] stringMultiArray = jsonb.fromJson("[[\"first\", \"second\"], [\"third\" , \"fourth\"]]", String[][].class);

        //default mapping should handle multidimensional arrays of types supported by default mapping, e.g. Map
        Map<String, Object>[][] mapMultiArray = jsonb.fromJson("[[{\"1\":2}, {\"3\":4}],[{\"5\":6},{\"7\":8}]]", Map[][].class);
    }

    public static void toJson_Arrays(Jsonb jsonb) {

        //support of arrays of types that JSON Binding is able to serialize
        //Byte[], Short[], Integer[] Long[], Float[], Double[], BigInteger[], BigDecimal[], Number[]
        //Object[], JsonArray[], JsonObject[], JsonStructure[]
        //String[], Character[]
        //byte[], short[], int[], long[], float[], double[], char[], boolean[]
        //java.net.URL[], java.net.URI[]
        //Map[], Collection[], other collections ...
        //enum, EnumSet, EnumMap
        //support of multidimensional arrays


        //Several examples

        Byte[] byteArray = {1, 2, 3};

        assertEquals("[1,2,3]", jsonb.toJson(byteArray));

        Integer[] integerArray = {1, 2, 3};

        assertEquals("[1,2,3]", jsonb.toJson(integerArray));

        int[] intArray = {1, 2, 3};

        assertEquals("[1,2,3]", jsonb.toJson(intArray));

        String[] stringArray = {"first", "second", "third"};

        assertEquals("[\"first\",\"second\",\"third\"]", jsonb.toJson(stringArray));

        String[][] stringMultiArray = {{"first", "second"},{"third", "fourth"}};

        assertEquals("[[\"first\",\"second\"],[\"third\",\"fourth\"]]", jsonb.toJson(stringMultiArray));

        Map<String, Object>[][] mapMultiArray = new LinkedHashMap[2][2];

        mapMultiArray[0][0] = new LinkedHashMap<>(1);
        mapMultiArray[0][0].put("0", 0);
        mapMultiArray[0][1] = new LinkedHashMap<>(1);
        mapMultiArray[0][1].put("0", 1);
        mapMultiArray[1][0] = new LinkedHashMap<>(1);
        mapMultiArray[1][0].put("1", 0);
        mapMultiArray[1][1] = new LinkedHashMap<>(1);
        mapMultiArray[1][1].put("1", 1);

        assertEquals("[[{\"0\":0},{\"0\":1}],[{\"1\":0},{\"1\":1}]]", jsonb.toJson(mapMultiArray));
    }

    public EnumSet<Language> languageEnumSet = EnumSet.of(Language.Czech);
    public EnumMap<Language, String> languageEnumMap = new EnumMap<>(Language.class);

    public static void fromJson_Enums(Jsonb jsonb) throws Exception {

        EnumSet<Language> languageEnumSet = fromJson("[\"Slovak\", \"English\"]", DefaultMapping.class.getField("languageEnumSet").getGenericType());

        EnumMap<Language, String> languageEnumMap = fromJson("[\"Slovak\" : \"sk\", \"Czech\" : \"cz\"]", DefaultMapping.class.getField("languageEnumMap").getGenericType());
    }

    public static void toJson_Enums(Jsonb jsonb) {

        Language language = Language.Slovak;

        assertEquals("\"Slovak\"", jsonb.toJson(language));

        EnumSet<Language> languageEnumSet = EnumSet.of(Language.Czech, Language.Slovak);

        assertEquals("\"Czech\",\"Slovak\"", jsonb.toJson(languageEnumSet));

        EnumMap<Language, String> languageEnumMap = new EnumMap<>(Language.class);
        languageEnumMap.put(Language.Czech, "cz");
        languageEnumMap.put(Language.English, "en");

        assertEquals("{\"Czech\":\"cz\",\"English\":\"en\"}", languageEnumMap);
    }

    private enum Language {
        English, Slovak, Czech
    }

    public static void fromJson_POJOs(Jsonb jsonb) {

        POJO pojo = jsonb.fromJson("{\"id\":1, \"name\":\"pojoName\"}", POJO.class);

        //just public nested class
        POJOWithNestedClass pojoWithNestedClass = jsonb.fromJson("{\"id\":1, \"name\":\"pojo_name\", \"nestedClass\" : {\"nestedId\":2, \"nestedName\" : \"nestedPojoName\"}}", POJOWithNestedClass.class);

        //just public nested class
        POJOWithNestedClass.NestedClass nestedClass = jsonb.fromJson("{\"nestedId\":2, \"nestedName\" : \"nestedPojoName\"}", POJOWithNestedClass.NestedClass.class);

        POJOWithStaticNestedClass pojoWithStaticNestedClass = jsonb.fromJson("{\"id\":1, \"name\":\"pojoName\"}", POJOWithStaticNestedClass.class);

        POJOWithStaticNestedClass.StaticNestedClass staticNestedClass = jsonb.fromJson("{\"nestedId\":2, \"nestedName\" : \"nestedPojoName\"}", POJOWithStaticNestedClass.StaticNestedClass.class);

        POJOWithMixedFieldAccess pojoWithMixedFieldAccess = jsonb.fromJson("{\"id\":5, \"name\":\"new_name\", \"active\":true, \"valid\":true}", POJOWithMixedFieldAccess.class);

        assert(pojoWithMixedFieldAccess.id.intValue() == 10);
        assert(pojoWithMixedFieldAccess.name.equals("new_name"));
        assert(pojoWithMixedFieldAccess.active);
        assert(pojoWithMixedFieldAccess.valid);
    }

    public static void toJson_POJOs(Jsonb jsonb) {

        POJO pojo = new POJO();
        pojo.setId(1);
        pojo.setName("pojoName");

        assertEquals("{\"id\":1,\"name\":\"pojoName\"}", jsonb.toJson(pojo));

        //pojo with nested class
        POJOWithNestedClass pojoWithNestedClass = new POJOWithNestedClass();
        pojoWithNestedClass.setName("pojoName");
        pojoWithNestedClass.setId(1);

        POJOWithNestedClass.NestedClass nestedClass = pojoWithNestedClass.new NestedClass();
        nestedClass.setNestedId(2);
        nestedClass.setNestedName("nestedPojoName");

        pojoWithNestedClass.setNestedClass(nestedClass);

        assertEquals("{\"id\":1,\"name\":\"pojo_name\",\"nestedClass\":{\"nestedId\":2,\"nestedName\":\"nestedPojoName\"}}", jsonb.toJson(pojoWithNestedClass));

        //nested class
        assertEquals("{\"nestedId\":2,\"nestedName\":\"nestedPojoName\"}", jsonb.toJson(nestedClass));

        //pojo with static nested class
        POJOWithStaticNestedClass pojoWithStaticNestedClass = new POJOWithStaticNestedClass();
        pojoWithStaticNestedClass.setId(1);
        pojoWithStaticNestedClass.setName("pojoName");

        assertEquals("{\"id\":1,\"name\":\"pojoName\"}", jsonb.toJson(pojoWithStaticNestedClass));

        //static nested class
        POJOWithStaticNestedClass.StaticNestedClass staticNestedClass = new POJOWithStaticNestedClass.StaticNestedClass();
        staticNestedClass.setNestedId(2);
        staticNestedClass.setNestedName("nestedPojoName");

        assertEquals("{\"nestedId\":2,\"nestedName\":\"nestedPojoName\"}", jsonb.toJson(staticNestedClass));

        POJOWithMixedFieldAccess pojoWithMixedFieldAccess = new POJOWithMixedFieldAccess();

        assertEquals("{\"id\":2,\"name\":\"pojoName\",\"active\":true,\"valid\":false}", jsonb.toJson(pojoWithMixedFieldAccess));
    }

    private static class POJO {
        private Integer id;
        private String name;

        public POJO() {
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        //other supported attributes
    }

    private static class POJOWithNestedClass {
        private Integer id;
        private String name;
        private NestedClass nestedClass;

        public POJOWithNestedClass() {
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public NestedClass getNestedClass() {
            return nestedClass;
        }

        public void setNestedClass(NestedClass nestedClass) {
            this.nestedClass = nestedClass;
        }

        //other supported attributes

        public class NestedClass {
            private Integer nestedId;
            private String nestedName;

            public NestedClass() {
            }

            public Integer getNestedId() {
                return nestedId;
            }

            public void setNestedId(Integer nestedId) {
                this.nestedId = nestedId;
            }

            public String getNestedName() {
                return nestedName;
            }

            public void setNestedName(String nestedName) {
                this.nestedName = nestedName;
            }
        }
    }

    private static class POJOWithStaticNestedClass {
        private Integer id;
        private String name;

        public POJOWithStaticNestedClass() {
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        //other supported attributes

        public static class StaticNestedClass {
            private Integer nestedId;
            private String nestedName;

            public StaticNestedClass() {
            }

            public Integer getNestedId() {
                return nestedId;
            }

            public void setNestedId(Integer nestedId) {
                this.nestedId = nestedId;
            }

            public String getNestedName() {
                return nestedName;
            }

            public void setNestedName(String nestedName) {
                this.nestedName = nestedName;
            }
        }
    }

    private static class POJOWithMixedFieldAccess {
        public Integer id = 1;
        public String name = "pojoName";
        public Boolean active = false;
        public Boolean valid = null;

        public Integer getId() {
            return 2;
        }

        public void setId(Integer id) {
            this.id = id * 2;
        }

        public Boolean getActive() {
            return true;
        }

        public void setActive(Boolean active) {
            this.active = active;
        }

        public Boolean isValid() {
            return false;
        }

        public void setValid(Boolean valid) {
            this.valid = valid;
        }
    }

    private static void fromJson_Inheritance(Jsonb jsonb) {
        //we need public constructor
        Dog animal = jsonb.fromJson("{\"age\":5, \"name\":\"Rex\"}", Dog.class);
    }

    public static void toJson_Inheritance(Jsonb jsonb) {

        DefaultMapping defaultMapping = new DefaultMapping();

        Dog dog = defaultMapping.new Dog();
        dog.setAge(5);
        dog.setName("Rex");

        assertEquals("{\"age\":5,\"name\":\"Rex\"}", jsonb.toJson(dog), jsonb.toJson((Animal)dog));
    }

    public class Animal {
        int age;

        public Animal() {
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    public class Dog extends Animal {
        private String name;

        public Dog() {
            super();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static void toJson_Anonymous_Class(Jsonb jsonb) {
        //same mechanism as POJOs with inheritance

        assertEquals("{\"id\":1,\"name\":\"pojoName\"}", new POJO() {
            @Override
            public Integer getId() {
                return 1;
            }

            @Override
            public String getName() {
                return "pojoName";
            }
        });

    }

    public static void fromJson_URL_URI(Jsonb jsonb) {
        java.net.URL url = jsonb.fromJson("\"https://www.jcp.org/en/jsr/detail?id=367#3\"", java.net.URL.class);

        java.net.URI uri = jsonb.fromJson("\"mailto:users@jsonb-spec.java.net\"", java.net.URI.class);
    }

    public static void toJson_URL_URI(Jsonb jsonb) throws Exception {

        java.net.URL url = new java.net.URL("https://www.jcp.org/en/jsr/detail?id=367#3");

        assertEquals("\"https://www.jcp.org/en/jsr/detail?id=367#3\"", jsonb.toJson(url));

        java.net.URI uri = new java.net.URI("mailto:users@jsonb-spec.java.net");

        assertEquals("\"https://www.jcp.org/en/jsr/detail?id=367#3\"", jsonb.toJson(uri));
    }

    static class POJOWithoutDefaultArgConstructor {
        public String id;

        public POJOWithoutDefaultArgConstructor(String id) {
            this.id = id;
        }
    }

    static class POJOWithPrivateConstructor {
        public String id;

        private POJOWithPrivateConstructor() {
        }
    }

    public static void fromJson_Instantiation(Jsonb jsonb) {

        //public or protected constructor must be present

        try {
            POJOWithoutDefaultArgConstructor pojo = jsonb.fromJson("{\"id\":\"1\"}", POJOWithoutDefaultArgConstructor.class);
            assert(false);
        } catch (JsonbException e) {}

        try {
            jsonb.fromJson("{\"id\":\"1\"}", POJOWithPrivateConstructor.class);
            assert(false);
        } catch (JsonbException e) {}
    }

    public static void toJson_defaultNames(Jsonb jsonb) {
        DefaultTestNames defaultTestNames = new DefaultTestNames();
        String result = jsonb.toJson(defaultTestNames);
        assertEquals("{" +
                "\"_12ac\":\"_12ac\"," +
                "\"_23_45_a\":\"_23_45_a\"," +
                "\"_Ab\":\"_Ab\"," +
                "\"_AB\":\"_AB\"," +
                "\"_ABc\":\"_ABc\"," +
                "\"a\":\"a\"," +
                "\"A\":\"A\"," +
                "\"a_bC\":\"a_bC\"," +
                "\"A_Bc\":\"A_Bc\"," +
                "\"ABC\":\"ABC\"," +
                "\"abc\":\"abc\"," +
                "\"DdB_ee\":\"DdB_ee\"," +
                "\"okNot_Ok\":\"okNot_Ok\"," +
                "\"okNot_ok\":\"okNot_ok\"," +
                "\"okNotOk\":\"okNotOk\"" +
                "}", result);
    }

    public static void fromJson_defaultNames(Jsonb jsonb) {
        DefaultNames defaultNames = jsonb.fromJson("{\"defaultName\":\"newName\"}", DefaultNames.class);
        assertEquals("newName", defaultNames.defaultName);

        try {
            jsonb.fromJson("{\"defaultNAME\":\"newName\"}", DefaultNames.class);
            assert(false);
        } catch (JsonbException e) {}
    }

    static class DefaultNames {
        public String defaultName;

        public DefaultNames() {}
    }

    static class DefaultTestNames {
        public String a = "a";
        public String A = "A";
        public String ABC = "ABC";
        public String abc = "abc";
        public String a_bC = "a_bC";
        public String A_Bc = "A_Bc";
        public String _12ac = "_12ac";
        public String _Ab = "_Ab";
        public String _AB = "_AB";
        public String _ABc = "_ABc";
        public String okNotOk = "okNotOk";
        public String okNot_Ok = "okNot_Ok";
        public String okNot_ok = "okNot_ok";
        public String DdB_ee = "DdB_ee";
        public String _23_45_a = "_23_45_a";
    }

    public static void toJson_attributesOrdering(Jsonb jsonb) {
        //lexicographical order
        AttributesOrderingClass attributesOrderingClass = new AttributesOrderingClass();
        attributesOrderingClass.aField = "text";
        attributesOrderingClass.cField = "text";
        attributesOrderingClass.bField = "text";

        assertEquals("{\"aField\":\"text\",\"bField\":\"text\",\"cField\":\"text\"}", jsonb.toJson(attributesOrderingClass));
    }

    public static void toJson_nullValues(Jsonb jsonb) {
        //array
        List<String> stringList = new ArrayList<>();
        stringList.add("value1");
        stringList.add(null);
        stringList.add("value3");

        assertEquals("[\"value1\",null,\"value3\"]", jsonb.toJson(stringList));

        //java object
        POJO pojo = new POJO();
        pojo.id = 1;
        pojo.name = null;

        assertEquals("{\"id\":1}", jsonb.toJson(pojo));
    }

    public static void fromJson_nullValues(Jsonb jsonb) {
        //array
        ArrayList<Object> stringList = jsonb.fromJson("[\"value1\",null,\"value3\"]", ArrayList.class);
        assert(stringList.size() == 3);
        Iterator<Object> iterator = stringList.iterator();
        assertEquals("value1", iterator.next());
        assert(null == iterator.next());
        assertEquals("value3", iterator.next());

        //java object
        POJOWithInitialValue pojoWithInitialValue = jsonb.fromJson("{\"name\":\"newName\"}", POJOWithInitialValue.class);
        assert(pojoWithInitialValue.id.intValue() == 4);
        assertEquals("newName", pojoWithInitialValue.name);

        POJOWithInitialValue pojoWithNullValue = jsonb.fromJson("{\"name\":\"newName\",\"id\":null}", POJOWithInitialValue.class);
        assert(pojoWithInitialValue.id == null);
        assertEquals("newName", pojoWithInitialValue.name);
    }

    public static void toJson_modifiers(Jsonb jsonb) {
        ModifiersClass modifiersClass = new ModifiersClass();
        assertEquals("{\"finalField\":\"finalValue\",\"regularField\":\"regularValue\"}", jsonb.toJson(modifiersClass));
    }

    public static void fromJson_modifiers(Jsonb jsonb) {
        try {
            ModifiersClass modifiersClass = jsonb.fromJson("{\"finalField\":\"finalValue\",\"regularField\":\"regularValue\"}", ModifiersClass.class);
            assert(false);
        } catch (JsonbException e) {
            //unmarshal of final field is not supported
        }

        try {
            ModifiersClass modifiersClass = jsonb.fromJson("{\"staticField\":\"staticValue\",\"regularField\":\"regularValue\"}", ModifiersClass.class);
            assert(false);
        } catch (JsonbException e) {
            //unmarshal of static field is not supported
        }

        try {
            ModifiersClass modifiersClass = jsonb.fromJson("{\"transientField\":\"transientValue\",\"regularField\":\"regularValue\"}", ModifiersClass.class);
            assert(false);
        } catch (JsonbException e) {
            //unmarshal of transient field is not supported
        }

        try {
            ModifiersClass modifiersClass = jsonb.fromJson("{\"unknownField\":\"unknownValue\",\"regularField\":\"regularValue\"}", ModifiersClass.class);
            assert(false);
        } catch (JsonbException e) {
            //unmarshal of unknown field is not supported
        }
    }

    public static void toJson_optional(Jsonb jsonb) {

        //Optional
        assertEquals("\"strValue\"", jsonb.toJson(Optional.of("strValue")));

        assertEquals("null", jsonb.toJson(Optional.ofNullable(null)));

        assertEquals("null", jsonb.toJson(Optional.empty()));

        assertEquals("{\"optionalField\":null}", jsonb.toJson(new OptionalClass()));

        OptionalClass optionalClass = new OptionalClass();
        optionalClass.optionalField = Optional.of("value");

        assertEquals("{\"optionalField\",\"value\"}", optionalClass);

        OptionalClass nullOptionalClass = new OptionalClass();
        nullOptionalClass.optionalField = Optional.ofNullable(null);

        assertEquals("{\"optionalField\":null}", jsonb.toJson(nullOptionalClass));

        OptionalClass nullOptionalField = new OptionalClass();
        nullOptionalField.optionalField = null;

        assertEquals("{}", jsonb.toJson(nullOptionalField));

        //OptionalInt
        assertEquals("1", jsonb.toJson(OptionalInt.of(1)));
        assertEquals("null", jsonb.toJson(OptionalInt.empty()));

        //OptionalLong
        assertEquals("123", jsonb.toJson(OptionalLong.of(123)));
        assertEquals("null", jsonb.toJson(OptionalLong.empty()));

        //OptionalDouble
        assertEquals("1.2", jsonb.toJson(OptionalDouble.of(1.2)));
        assertEquals("null", jsonb.toJson(OptionalDouble.empty()));
    }

    public static void fromJson_optional(Jsonb jsonb) {
        //Optional
        Optional<String> stringValue = jsonb.fromJson("\"optionalString\"", Optional.class);
        assert(stringValue.isPresent());
        assertEquals("optionalString", stringValue.get());

        Optional<String> nullStringValue = jsonb.fromJson("null", Optional.class);
        assert(!nullStringValue.isPresent());

        OptionalClass optionalClass = jsonb.fromJson("{\"optionalField\":\"value\"}", OptionalClass.class);
        assert(optionalClass.optionalField.isPresent());
        assertEquals("value", optionalClass.optionalField.get());

        OptionalClass emptyOptionalClass = jsonb.fromJson("{}", OptionalClass.class);
        assert(!emptyOptionalClass.optionalField.isPresent());

        OptionalClass nullOptionalClass = jsonb.fromJson("{\"optionalField\":null}", OptionalClass.class);
        assert(nullOptionalClass.optionalField == null);

        //OptionalInt
        OptionalInt optionalInt = jsonb.fromJson("1", OptionalInt.class);
        assert(optionalInt.isPresent());
        assert(optionalInt.getAsInt() == 1);

        OptionalInt emptyOptionalInt = jsonb.fromJson("null", OptionalInt.class);
        assert(!emptyOptionalInt.isPresent());

        //OptionalLong
        OptionalLong optionalLong = jsonb.fromJson("123", OptionalLong.class);
        assert(optionalLong.isPresent());
        assert(optionalLong.getAsLong() == 123L);

        OptionalLong emptyOptionalLong = jsonb.fromJson("null", OptionalLong.class);
        assert(!emptyOptionalLong.isPresent());

        //OptionalDouble
        OptionalDouble optionalDouble = jsonb.fromJson("1.2", OptionalDouble.class);
        assert(optionalDouble.isPresent());
        assert(optionalDouble.getAsDouble() == 1.2);

        OptionalDouble emptyOptionalDouble = jsonb.fromJson("null", OptionalDouble.class);
        assert(!emptyOptionalDouble.isPresent());

        //invalid
        try {
            jsonb.fromJson("[]", OptionalInt.class);
            assert(false);
        } catch (JsonbException e) {
            //empty field cannot be converted to OptionalInt
        }
    }

    static class OptionalClass {
        public Optional<String> optionalField = Optional.empty();

        public OptionalClass() {}
    }

    static class ModifiersClass {
        public final String finalField = "finalValue";
        public static String staticField = "staticValue";
        public transient String transientField = "transientValue";
        public String regularField = "regularValue";

        public ModifiersClass() {}
    }

    static class POJOWithInitialValue {
        public Integer id = 4;
        public String name;

        public POJOWithInitialValue() {}
    }

    static class AttributesOrderingClass {
        public String aField;
        public String cField;
        public String bField;

        public AttributesOrderingClass() {}
    }



}
