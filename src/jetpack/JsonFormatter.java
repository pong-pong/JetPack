package jetpack;

import jetpack.annotation.JsonKey;
import jetpack.annotation.JsonObject;

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
     * @param k the name of value
     * @param o the object to format
     * @param t the format type of o
     * @param n whether nullable of value
     * @return the formatted string
     */
    public static String toJSON(String k, Object o, FormatType t, boolean n) {
        int oInstanceType = getInstanceType(o);
        FormatType oFormatType = getFormatType(o, t);
        StringBuilder s = new StringBuilder("{");
        if (oInstanceType != -1) {
            if (oInstanceType == 1) {

            } else if (oInstanceType == 2) {

            } else if (oInstanceType == 3) {

            } else {

            }
        } else {
            Class c = o.getClass();
            if (oFormatType.equals(FormatType.OBJECT))
                for (Field f : c.getDeclaredFields())
                    formatInfo(o, s, f, n);
            else if (oFormatType.equals(FormatType.KEY))
                for (Field f : c.getDeclaredFields())
                    if (f.getAnnotation(JsonKey.class) != null)
                        formatInfo(o, s, f, n);
        }
        if (s.substring(s.length() - 2, s.length()).equals(", ")) return s.substring(0, s.length() - 2) + "}";
        else return s.toString() + "}";
    }

    /**
     * Format target field
     *
     * @param o the object to get value from
     * @param s the string builder to append formatted info
     * @param f the field to get value
     * @param n whether nullable of value
     */
    private static void formatInfo(Object o, StringBuilder s, Field f, boolean n) {

    }

    /**
     * Get FormatType of target object
     *
     * @param o the object to get FormatType
     * @return the applied default type of o
     * @see jetpack.FormatType
     */
    private static FormatType getFormatType(Object o, FormatType t) {
        FormatType r = null;
        Class c = o.getClass();
        if (t != null) {
            if (t.equals(FormatType.OBJECT)) r = c.getAnnotation(JsonObject.class) != null ? FormatType.OBJECT : null;
            else r = isAnnotationExistsAtFields(c) ? FormatType.KEY : null;
        } else {
            if (c.getAnnotation(JsonObject.class) != null)
                r = ((JsonObject) c.getAnnotation(JsonObject.class)).formatBy();
            else
                r = isAnnotationExistsAtFields(c) ? FormatType.KEY : null;
        }
        return r;
    }

    /**
     * Check if the annotation exists at fields
     *
     * @param c the class to check
     * @return true if exists
     */
    private static boolean isAnnotationExistsAtFields(Class c) {
        boolean r = false;
        for (Field f : c.getDeclaredFields()) {
            if (f.getAnnotation(JsonKey.class) != null) {
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
