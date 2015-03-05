package examples.mapping;

import javax.json.bind.JsonbException;
import java.lang.reflect.Type;

/**
 * @author Martin Vojtek
 */
public class Utils {
    /**
     *  Will be introduced into Jsonb.
     */
    public static <T> T fromJson(String json, Type runtimeType) throws JsonbException {
        return null;
    }

    public static void assertEquals(Object... objects) {
        if (null == objects || objects.length < 2 || null == objects[0]) {
            throw new IllegalArgumentException("bad parameters");
        }
        for (int i = 0; i < objects.length-1; i++) {
            assert(objects[i].equals(objects[i+1]));
        }
    }

}
