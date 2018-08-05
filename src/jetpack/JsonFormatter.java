package jetpack;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author pongpong
 * @version 1.0
 * @since 080218
 */

public class JsonFormatter {

    /**
     * Format target object to JSON string
     *
     * @param o        the object to format
     * @param t        the format type of o
     * @param nullable whether nullable of value
     * @return the formatted string
     */
    public static String toJSON(Object o, FormatType t, boolean nullable) {
        int oInstanceType = getInstanceType(o);

        if (oInstanceType != -1) {

        } else if (o instanceof Object[]) {

        } else {

        }

        return null;
    }

    /**
     * Get FormatType of target object
     *
     * @param o the object to get FormatType
     * @return the applied default type of o
     * @see jetpack.FormatType
     */
    private static FormatType getFormatType(Object o, FormatType t) {
        Class c = o.getClass();
        return null;
    }

    /**
     * Check if the annotation exists at fields
     *
     * @param c the class to check
     * @param a the annotation to check
     * @return true if exists
     */
    private static boolean isAnnotationExistsAtFields(Class c, Class<? extends Annotation> a) {
        boolean r = false;
        for (Field f : c.getDeclaredFields()) {
            if (f.getAnnotation(a) != null) {
                r = true;
                break;
            }
        }
        return r;
    }

    /**
     * Get instance type of target object
     *
     * @param o the object to get instance type
     * @return -1 if not, 0 if primitive array, 1 if primitive, 2 if string or char array, 3 if string or char
     */
    private static int getInstanceType(Object o) {
        if (o instanceof Integer[] || o instanceof Long[] || o instanceof Short[] || o instanceof Byte[] || o instanceof Boolean[] || o instanceof Float[] || o instanceof Double[])
            return 0;
        else if (o instanceof Integer || o instanceof Long || o instanceof Short || o instanceof Byte || o instanceof Boolean || o instanceof Float || o instanceof Double)
            return 1;
        else if (o instanceof String[] || o instanceof Character[])
            return 2;
        else if (o instanceof String || o instanceof Character)
            return 3;
        else
            return -1;
    }
}
