package examples.mapping;

public class Utils {

    public static void assertEquals(Object... objects) {
        if (null == objects || objects.length < 2 || null == objects[0]) {
            throw new IllegalArgumentException("bad parameters");
        }
        for (int i = 0; i < objects.length-1; i++) {
            assert(objects[i].equals(objects[i+1]));
        }
    }

}
