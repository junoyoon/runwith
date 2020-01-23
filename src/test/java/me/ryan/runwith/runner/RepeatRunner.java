package me.ryan.runwith.runner;

import me.ryan.runwith.annotation.handler.RepeatHandler;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

import java.lang.reflect.Method;

public class RepeatRunner extends Runner {

    private Class testClass;

    public RepeatRunner(Class testClass) {
        this.testClass = testClass;
    }

    private RepeatHandler repeatHandler = new RepeatHandler();

    @Override
    public Description getDescription() {
        return Description.createTestDescription(testClass, "Repeat Runner Description");
    }

    @Override
    public void run(RunNotifier notifier) {
        System.out.println("running the tests from MyRunner : " + testClass);
        try {
            Object testObject = testClass.getDeclaredConstructor().newInstance();
            for (Method method : testClass.getMethods()) {
                if (method.isAnnotationPresent(Test.class)) {
                    repeatHandler.handleInternal(notifier, testObject, method);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
