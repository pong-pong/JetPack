package jetpack.exception;

/**
 * @author pongpong
 * @version 1.0
 * @since 030818
 */

public class AnnotationNotFoundException extends Exception {
    public AnnotationNotFoundException(Object object) {
        super("at " + object.getClass().toString());
    }
}
