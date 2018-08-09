package jetpack.exception;

public class MissingKeyNameException extends Exception {
    public MissingKeyNameException() {
        super("the basic data type(ex. primitive, wrapper, string) object(objects) need key name : use toJSON(String k, Object o)");
    }
}
