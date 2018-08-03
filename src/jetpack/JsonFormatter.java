package jetpack;

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
        int oType = getType(o);

        return null;
    }

    /**
     * Check if object type is primitive(array) or string(array)
     *
     * @param o the object to check
     * @return -1 if not, 0 if primitive array, 1 if primitive, 2 if string array, 3 if string
     */
    private static int getType(Object o) {
        if (o instanceof Integer[] || o instanceof Long[] || o instanceof Short[] || o instanceof Byte[] || o instanceof Boolean[] || o instanceof Float[] || o instanceof Double[] || o instanceof Character[])
            return 0;
        else if (o instanceof Integer || o instanceof Long || o instanceof Short || o instanceof Byte || o instanceof Boolean || o instanceof Float || o instanceof Double || o instanceof Character)
            return 1;
        else if (o instanceof String[])
            return 2;
        else if (o instanceof String)
            return 3;
        else
            return -1;
    }
}
