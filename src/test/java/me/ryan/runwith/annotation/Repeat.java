package me.ryan.runwith.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Repeat {
    int value() default 1;  // If less than one, all test is skip.

    long timeout() default 0;   // Timeout for all repeat millisecond.

    String[] params() default {};

    String method() default ""; // Parameter method name. Both the return value and the member of the return value must be Object[].
}
