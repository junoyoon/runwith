package me.ryan.runwith.annotation.handler;

import me.ryan.runwith.annotation.Repeat;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RepeatHandler {

    public <T> T handleInternal(RunNotifier notifier, T targetObject, Method method) {
        Repeat repeat = method.getAnnotation(Repeat.class);
        int testCount;
        if (repeat != null && repeat.value() > 1) {
            testCount = repeat.value();
        } else {
            testCount = 1;
        }
        method.setAccessible(true);
        try {
            for (int i = 0; i < testCount; i++) {
                notifier.fireTestStarted(Description.EMPTY);
                method.invoke(targetObject);
                notifier.fireTestFinished(Description.EMPTY);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return targetObject;
    }
}
