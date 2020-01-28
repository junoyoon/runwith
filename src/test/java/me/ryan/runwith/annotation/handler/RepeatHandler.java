package me.ryan.runwith.annotation.handler;

import me.ryan.runwith.common.RepeatTestMessages;
import me.ryan.runwith.common.TestUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runners.model.Statement;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

public class RepeatHandler extends Statement {

    protected static final Log log = LogFactory.getLog(RepeatHandler.class);

    private final Statement next;
    private final Object target;
    private final Method testMethod;
    private final int repeat;
    private final boolean repeatable;

    private final long timeout;
    private final String[] params;
    private final String method;

    public RepeatHandler(Statement next, Object target, Method testMethod) {
        this(next, target, testMethod, TestUtils.getRepeatCount(testMethod), TestUtils.isRepeatable(testMethod));
    }

    public RepeatHandler(Statement next, Object target, Method testMethod, int repeat, boolean repeatable) {
        this.next = next;
        this.target = target;
        this.testMethod = testMethod;
        this.repeat = repeat;
        this.repeatable = repeatable;
        this.params = TestUtils.getRepeatParams(testMethod);
        this.method = TestUtils.getRepeatMethod(testMethod);
        this.timeout = TestUtils.getRepeatTimeout(testMethod);
    }

    @Override
    public void evaluate() throws Throwable {
        long startTime = System.currentTimeMillis();
        loggingSkip();
        repeatInvoke(startTime);
        loggingEnd(startTime);
    }

    private void repeatInvoke(long startTime) throws Throwable {
        Optional<Object[]> repeatReturnedParam = TestUtils.getRepeatReturnedParam(target, this.method);
        for (int i = 0; i < this.repeat; i++) {
            loggingEach(i);
            invokeEach(repeatReturnedParam, i);
            long elapsed = System.currentTimeMillis() - startTime;
            checkTimeout(i, elapsed);
        }
    }

    private void invokeEach(Optional<Object[]> repeatReturnedParam, int i) throws Throwable {
        if (ArrayUtils.isNotEmpty(this.params)) {
            Object[] paramArgs = TestUtils.getParamArgs(testMethod, this.params[i]);
            testMethod.invoke(target, paramArgs);
        } else if (StringUtils.isNotEmpty(this.method) && repeatReturnedParam.isPresent()) {
            testMethod.invoke(target, (Object[]) repeatReturnedParam.get()[i]);
        } else {
            this.next.evaluate();
        }
    }

    private void loggingEnd(long startTime) {
        long totalElapsed = System.currentTimeMillis() - startTime;
        if (timeout > 0 && log.isInfoEnabled()) {
            log.info(String.format(RepeatTestMessages.REPEAT_FINISH_MESSAGE, totalElapsed, timeout));
        }
    }

    private void checkTimeout(int i, long elapsed) throws TimeoutException {
        if (timeout > 0 && elapsed > timeout) {
            throw new TimeoutException(
                    String.format(RepeatTestMessages.TIMEOUT_EXCEPTION_MESSAGE, elapsed, timeout, (i + 1), this.repeat));
        }
    }

    private void loggingSkip() {
        if (this.repeat < 1 && log.isInfoEnabled()) {
            log.info(String.format(RepeatTestMessages.MINUS_REPEAT_VALUE, this.repeat, this.testMethod.getName()));
        }
    }

    private void loggingEach(int i) {
        if (repeatable && log.isInfoEnabled()) {
            log.info(String.format(RepeatTestMessages.DEFAULT_REPEAT_MESSAGE, i + 1, this.testMethod.getName()));
        }
    }
}
