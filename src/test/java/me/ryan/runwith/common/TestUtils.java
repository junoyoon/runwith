package me.ryan.runwith.common;

import me.ryan.runwith.annotation.Repeat;
import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;

import java.lang.reflect.Method;

public class TestUtils {

    public static int getRepeatCount(Method method) {
        Repeat repeat = method.getAnnotation(Repeat.class);
        return (repeat == null ? 1 : repeat.value());
    }

    public static boolean isRepeatable(Method method) {
        return method.getAnnotation(Repeat.class) != null ? true : false;
    }

    public static long getRepeatTimeout(Method method) {
        Repeat repeat = method.getAnnotation(Repeat.class);
        return (repeat == null ? 0 : Math.max(0, repeat.timeout()));
    }

    public static long getTestTimeout(FrameworkMethod frameworkMethod) {
        Test test = frameworkMethod.getAnnotation(Test.class);
        return (test == null ? 0 : Math.max(0, test.timeout()));
    }
}
