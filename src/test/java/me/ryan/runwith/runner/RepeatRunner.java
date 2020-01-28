package me.ryan.runwith.runner;

import me.ryan.runwith.annotation.handler.RepeatHandler;
import me.ryan.runwith.common.TestUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.internal.runners.model.ReflectiveCallable;
import org.junit.internal.runners.statements.Fail;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import static org.junit.internal.runners.rules.RuleMemberValidator.RULE_METHOD_VALIDATOR;

public class RepeatRunner extends BlockJUnit4ClassRunner {

    protected static final Log log = LogFactory.getLog(RepeatRunner.class);

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
    protected void collectInitializationErrors(List<Throwable> errors) {
        validateNoNonStaticInnerClass(errors);
        validateConstructor(errors);
        validateFields(errors);
        validateMethods(errors);
    }

    private void validateMethods(List<Throwable> errors) {
        RULE_METHOD_VALIDATOR.validate(getTestClass(), errors);
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
        try {
            statement = withRepeats(method, test, statement);
        } catch (Exception e) {
            e.printStackTrace();
            return new Fail(e);
        }
        return statement;
    }

    protected Statement withRepeats(FrameworkMethod frameworkMethod, Object target, Statement next) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        checkTestTimeoutAndRepeatTimeoutValidation(frameworkMethod);
        checkRepeatValueAndPraramsSizeValidation(frameworkMethod);
        checkRepeatValueAndMethodParamsSzieValidation(frameworkMethod, target);
        checkRepeatParamsAndMethodParamsBothUseValidation(frameworkMethod);

        return new RepeatHandler(next, target, frameworkMethod.getMethod());
    }

    private void checkRepeatParamsAndMethodParamsBothUseValidation(FrameworkMethod frameworkMethod) {
        String[] repeatParams = TestUtils.getRepeatParams(frameworkMethod.getMethod());
        String repeatMethod = TestUtils.getRepeatMethod(frameworkMethod.getMethod());
        if (ArrayUtils.isNotEmpty(repeatParams) && StringUtils.isNotEmpty(repeatMethod)) {
            String msg = "Do not use params and method in @Repeat.";
            log.error(msg);
            throw new IllegalStateException(msg);
        }
    }

    private void checkRepeatValueAndMethodParamsSzieValidation(FrameworkMethod frameworkMethod, Object target) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int repeatValue = TestUtils.getRepeatCount(frameworkMethod.getMethod());
        String repeatMethod = TestUtils.getRepeatMethod(frameworkMethod.getMethod());
        if (StringUtils.isNotEmpty(repeatMethod)) {
            Optional<Object[]> repeatReturnedParam = TestUtils.getRepeatReturnedParam(target, repeatMethod);
            repeatReturnedParam.ifPresent(r -> {
                if ((repeatValue > 1 && r.length > 0) &&
                        repeatValue != r.length) {
                    String msg = String.format("Repeat value and number of returned method params doesn't match %d/%d!! It must be same.", repeatValue, r.length);
                    log.error(msg);
                    throw new IllegalArgumentException(msg);
                }
            });
        }
    }

    private void checkRepeatValueAndPraramsSizeValidation(FrameworkMethod frameworkMethod) {
        int repeatValue = TestUtils.getRepeatCount(frameworkMethod.getMethod());
        String[] repeatParams = TestUtils.getRepeatParams(frameworkMethod.getMethod());
        if ((repeatValue > 1 && repeatParams.length > 0) &&
                repeatValue != repeatParams.length) {
            String msg = String.format("Repeat value and number of params doesn't match %d/%d!! It must be same.", repeatValue, repeatParams.length);
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }
    }

    private void checkTestTimeoutAndRepeatTimeoutValidation(FrameworkMethod frameworkMethod) {
        long testTimeout = TestUtils.getTestTimeout(frameworkMethod);
        long repeatTimeout = TestUtils.getRepeatTimeout(frameworkMethod.getMethod());
        if (testTimeout > 0 && repeatTimeout > 0) {
            String msg = String.format("Test method [%s] has been configured with Repeat's @Repeat(timeout=%s) and " +
                    "JUnit's @Test(timeout=%s) annotations, but only one declaration of a 'timeout' is " +
                    "permitted per test method.", frameworkMethod.getMethod(), repeatTimeout, testTimeout);
            log.error(msg);
            throw new IllegalStateException(msg);
        }
    }
}
