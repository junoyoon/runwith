package me.ryan.runwith.runner;

import me.ryan.runwith.annotation.Repeat;
import me.ryan.runwith.annotation.handler.RepeatHandler;
import org.junit.internal.runners.model.ReflectiveCallable;
import org.junit.internal.runners.statements.Fail;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

public class RepeatRunner extends BlockJUnit4ClassRunner {

    /**
     * Creates a BlockJUnit4ClassRunner to run {@code klass}
     *
     * @param klass
     * @throws InitializationError if the test class is malformed.
     */
    public RepeatRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected Statement methodBlock(FrameworkMethod method) {
        Object test;
        try {
            test = new ReflectiveCallable() {
                @Override
                protected Object runReflectiveCall() throws Throwable {
                    return createTest();
                }
            }.run();
        } catch (Throwable e) {
            return new Fail(e);
        }

        Statement statement = methodInvoker(method, test);
        statement = possiblyExpectingExceptions(method, test, statement);
        statement = withBefores(method, test, statement);
        statement = withAfters(method, test, statement);
        statement = withRepeats(method, test, statement);
        return statement;
    }

    protected Statement withRepeats(FrameworkMethod frameworkMethod, Object testInstance, Statement next) {
        Repeat repeat = frameworkMethod.getAnnotation(Repeat.class);
        int repeatCount = 1;
        if (repeat != null) {
            repeatCount = repeat.value();
        }
        return new RepeatHandler(next, frameworkMethod.getMethod(), repeatCount);
    }
}
