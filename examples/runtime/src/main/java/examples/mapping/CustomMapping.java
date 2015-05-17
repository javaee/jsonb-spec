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
        assertEquals("{\"nillableField\":nill}", jsonb.toJson(new NillableClass()));

        assertEquals("{\"nillableField\":nill}", jsonb.toJson(new NillableClassWithGetter()));

        assertEquals("{\"nillableField\":nill}", jsonb.toJson(new NillableType()));

        assertEquals("{\"nillableField\":nill}", jsonb.toJson(new NillableTypeOverride()));

        JsonbConfig nillableConfig = new JsonbConfig().withSkippedNullValues(false);
        Jsonb nillableJsonb = JsonbBuilder.create(nillableConfig);

        Book book = new Book();
        book.name = null;
        assertEquals("{\"name\":nill}", nillableJsonb.toJson(new Book()));
    }

    public static void toJson_propertyOrder(Jsonb jsonb) {
        PropertyOrderClass propertyOrderClass = new PropertyOrderClass();

        JsonbConfig customPropertyOrderConfig = new JsonbConfig().withPropertyOrderStrategy(new CustomPropertyOrderStrategy());
        Jsonb customPropertyOrderJsonb = JsonbBuilder.create(customPropertyOrderConfig);

        assertEquals("{\"aField\":\"a\",\"dField\":\"d\",\"cField\":\"c\",\"bField\":\"b\"}", customPropertyOrderJsonb.toJson(new PropertyOrderClass()));

        assertEquals("{\"dField\":\"d\",\"cField\":\"c\",\"bField\":\"b\",\"aField\":\"a\"}", jsonb.toJson(new PropertyOrderSpecificClass()));
    }

    @JsonbPropertyOrder({"dField","cField","bField","aField"})
    static class PropertyOrderSpecificClass {
        public String aField = "a";

        public String dField = "d";

        public String cField = "c";

        public String bField = "b";

        public PropertyOrderSpecificClass() {}
    }

    static class PropertyOrderClass {
        public String aField = "a";

        public String dField = "d";

        public String cField = "c";

        public String bField = "b";

        public PropertyOrderClass() {}
    }

    static class NillableClass {

        @JsonbProperty(nillable=true)
        public String nillableField;

        public NillableClass() {}
    }

    static class NillableClassWithGetter {

        public String nillableField;

        public NillableClassWithGetter() {}

        @JsonbProperty(nillable=true)
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

        public NillableType() {}
    }

    @JsonbNillable
    static class NillableTypeOverride {
        public String nillableField;

        @JsonbProperty(nillable=false)
        public String absentField;

        public NillableTypeOverride() {}
    }

    static class Book {
        public String name = "Effective Java";

        public Book() {}
    }

    static class CustomPropertyOrderStrategy implements PropertyOrderStrategy {

        /**
         * Not guaranteed to work in the same way on all the JDKs.
         */
        @Override
        public List<String> getPropertiesOrder(Class clazz, List<String> propertyNames) {

            final Map<String, Integer> orderMap = new HashMap<>();

            int i = 0;
            for (Field field : clazz.getFields()) {
                orderMap.put(field.getName(), i++);
            }

            List<String> orderedList = new ArrayList<>(propertyNames.size());
            orderedList.addAll(propertyNames);

            Collections.sort(orderedList, new Comparator<String>() {
                @Override
                public int compare(String property1, String property2) {

                    int propertyOrder1 = -1;
                    int propertyOrder2 = -1;

                    if (orderMap.containsKey(property1)) {
                        propertyOrder1 = orderMap.get(property1);
                    }

                    if (orderMap.containsKey(property2)) {
                        propertyOrder2 = orderMap.get(property2);
                    }

                    return propertyOrder1 < propertyOrder2 ? -1 : (propertyOrder1 == propertyOrder2 ? 0 : 1);
                }
            });

            return orderedList;
        }
    }

    static class CustomizedName {
        @JsonbProperty("longDesc")
        public String longDescription;

        public CustomizedName() {}
    }

    static class CustomizedNameWithSetter {
        private String longDescription;

        @JsonbProperty("longDesc")
        public String getLongDescription() {
            return longDescription;
        }

        @JsonbProperty("long-desc")
        public void setLongDescription(String longDescription) {
            this.longDescription = longDescription;
        }

        public CustomizedNameWithSetter() {}
    }

}
