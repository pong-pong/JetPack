package jetpack;

import jetpack.annotation.JsonKey;
import jetpack.annotation.JsonObject;
import jetpack.exception.AnnotationNotFoundException;
import jetpack.exception.MissingKeyNameException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author pongpong
 * @version 1.0
 * @since 080218
 */

public class JsonFormatter {

    /**
     * Format target object to JSON string
     *
     * @param k the key name of value
     * @param o the object to format
     * @param t the format type of o
     * @param n whether nullable of value : default false
     * @return the formatted string
     */
    public static String toJSON(String k, Object o, FormatType t, boolean n) throws AnnotationNotFoundException, MissingKeyNameException {
        int oInstanceType = getInstanceType(o);
        FormatType oFormatType = getFormatType(o, t);
        StringBuilder s = new StringBuilder("{");
        if (oInstanceType != -1 && oFormatType == null) {
            if (k == null) throw new MissingKeyNameException();
            StringBuilder tmp = new StringBuilder(k + ": ");
            if (oInstanceType == 0) {
                if (!(o instanceof Object[])) {

                } else {
                    tmp.append("[");
                    Object[] arr = (Object[]) o;
                    for (Object cur : arr) tmp.append(cur).append(", ");
                    s.append(tmp.substring(0, tmp.length() - 2)).append("]");
                }
            } else if (oInstanceType == 1) {
                s.append(k).append(": ").append(o);
            } else if (oInstanceType == 2) {
                tmp.append(k).append("[");
                Object[] arr = (Object[]) o;
                for (Object cur : arr) tmp.append("\"").append(cur).append("\", ");
                s.append(tmp.substring(0, tmp.length() - 2)).append("]");
            } else if (oInstanceType == 3) {
                s.append(k).append(": ").append(o);
            } else {
                throw new AnnotationNotFoundException(o);
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

    public static String toJSON(Object o, FormatType t, boolean n) throws AnnotationNotFoundException, MissingKeyNameException {
        return toJSON(null, o, t, n);
    }

    public static String toJSON(Object o, FormatType t) throws AnnotationNotFoundException, MissingKeyNameException {
        return toJSON(null, o, t, false);
    }

    public static String toJSON(Object o, boolean n) throws AnnotationNotFoundException, MissingKeyNameException {
        return toJSON(null, o, null, n);
    }

    public static String toJSON(String k, Object o, FormatType t) throws AnnotationNotFoundException, MissingKeyNameException {
        return toJSON(k, o, t, false);
    }

    public static String toJSON(String k, Object o, boolean n) throws AnnotationNotFoundException, MissingKeyNameException {
        return toJSON(k, o, null, n);
    }

    public static String toJSON(String k, Object o) throws AnnotationNotFoundException, MissingKeyNameException {
        return toJSON(k, o, null, false);
    }

    public static String toJSON(Object o) throws AnnotationNotFoundException, MissingKeyNameException {
        return toJSON(null, o, null, false);
    }

    public static String toJSON(String k, Object[] o, FormatType t, boolean n) throws AnnotationNotFoundException, MissingKeyNameException {
        return toJSON(k, (Object) o, t, n);
    }

    public static String toJSON(String k, Object[] o, FormatType t) throws AnnotationNotFoundException, MissingKeyNameException {
        return toJSON(k, (Object) o, t, false);
    }

    public static String toJSON(String k, Object[] o, boolean n) throws AnnotationNotFoundException, MissingKeyNameException {
        return toJSON(k, (Object) o, null, n);
    }

    public static String toJSON(String k, Object[] o) throws AnnotationNotFoundException, MissingKeyNameException {
        return toJSON(k, (Object) o, null, false);
    }


    /**
     * Format target field
     *
     * @param o the object to get value from
     * @param s the string builder to append formatted info
     * @param f the field to get value
     * @param n whether nullable of value
     */
    private static void formatInfo(Object o, StringBuilder s, Field f, boolean n) throws MissingKeyNameException, AnnotationNotFoundException {
        f.setAccessible(true);
        try {
            if (f.get(o) != null && !n) append(o, s, f, false);
            else append(o, s, f, n);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Append field value to the string builder
     *
     * @param o the object to get value from
     * @param s the string builder to append formatted info
     * @param f the field to get value
     * @param n whether nullable of value
     */
    private static void append(Object o, StringBuilder s, Field f, boolean n) throws MissingKeyNameException, AnnotationNotFoundException {
        try {
            Object i = f.get(o);
            int t = getInstanceType(i);
            if (f.getType().isArray()) {
                s.append(f.getName()).append(": ");
                StringBuilder tmp = new StringBuilder("[");
                Object[] arr = (Object[]) f.get(o);
                if (t == 1) for (Object c : arr) tmp.append(c).append(", ");
                else if (t == 3) for (Object c : arr) tmp.append("\"").append(c).append("\", ");
                if ((arr == null || arr.length == 0) && n) tmp.append("null").append(", ");
                else for (Object c : Objects.requireNonNull(arr)) tmp.append(toJSON(c, n)).append(", ");
                if (s.substring(s.length() - 2, s.length()).equals(", ")) s.append(tmp.substring(0, s.length() - 2)).append("]");
                else s.append(tmp.append("]").toString());
            } else {
                if (t == 1) s.append(f.getName()).append(": ").append(i).append(", ");
                else if (t == 3) s.append(f.getName()).append(": ").append("\"").append(i).append("\", ");
                if (i == null && n) s.append(f.getName()).append(": ").append("null").append(", ");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get FormatType of target object
     *
     * @param o the object to get FormatType
     * @return the applied default type of o
     * @see jetpack.FormatType
     */
    private static FormatType getFormatType(Object o, FormatType t) {
        FormatType r;
        Class c = o.getClass();
        if (t != null) {
            if (t.equals(FormatType.OBJECT)) r = c.getAnnotation(JsonObject.class) != null ? FormatType.OBJECT : null;
            else r = isAnnotationExistsAtFields(c) ? FormatType.KEY : null;
        } else {
            if (c.getAnnotation(JsonObject.class) != null)
                r = ((JsonObject) c.getAnnotation(JsonObject.class)).formatBy();
            else r = isAnnotationExistsAtFields(c) ? FormatType.KEY : null;
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
        if (o instanceof Integer[] || o instanceof int[] || o instanceof Long[] || o instanceof long[] || o instanceof Short[] || o instanceof short[] || o instanceof Byte[] || o instanceof byte[] || o instanceof Boolean[] || o instanceof boolean[] || o instanceof Float[] || o instanceof float[] || o instanceof Double[] || o instanceof double[])
            return 0;
        else if (o instanceof Integer || o instanceof Long || o instanceof Short || o instanceof Byte || o instanceof Boolean || o instanceof Float || o instanceof Double)
            return 1;
        else if (o instanceof String[] || o instanceof Character[] || o instanceof Enum[])
            return 2;
        else if (o instanceof String || o instanceof Character || o instanceof Enum)
            return 3;
        else
            return -1;
    }
}
