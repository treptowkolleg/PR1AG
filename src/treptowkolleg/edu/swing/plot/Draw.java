package treptowkolleg.edu.swing.plot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Draw {
    int order() default Integer.MAX_VALUE;
    String when() default "PROD"; // DEV
}
