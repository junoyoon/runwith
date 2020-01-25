package me.ryan.runwith.common;

import me.ryan.runwith.annotation.Repeat;

import java.lang.reflect.Method;

public class TestUtils {

    public static int getRepeatCount(Method method) {
        Repeat repeat = method.getAnnotation(Repeat.class);
        if (repeat == null) {
            return 1;
        }
        return repeat.value();
    }

    public static boolean isRepeatable(Method method) {
        if (method.getAnnotation(Repeat.class) != null) {
            return true;
        }
        return false;
    }
}
