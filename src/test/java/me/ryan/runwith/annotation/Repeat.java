package me.ryan.runwith.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Repeat {
    int value() default 1;

    long timeout() default 0;   // Timeout for all repeat millisecond

    String[] params() default {};
}
