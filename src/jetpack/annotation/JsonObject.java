package jetpack.annotation;

import jetpack.FormatType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author pongpong
 * @version 1.0
 * @since 080218
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonObject {
    FormatType formatBy() default FormatType.OBJECT;
}