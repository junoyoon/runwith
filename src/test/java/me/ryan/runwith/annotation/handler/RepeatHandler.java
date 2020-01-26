package me.ryan.runwith.annotation.handler;


import me.ryan.runwith.common.TestUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runners.model.Statement;

import java.lang.reflect.Method;
import java.util.concurrent.TimeoutException;

public class RepeatHandler extends Statement {

    protected static final Log log = LogFactory.getLog(RepeatHandler.class);

    private final Statement next;
    private final Object target;
    private final Method testMethod;
    private final int repeat;
    private final boolean repeatable;

    private final String[] params;

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
    }

    @Override
    public void evaluate() throws Throwable {
        long startTime = System.currentTimeMillis();
        long timeout = TestUtils.getRepeatTimeout(testMethod);
        if (this.repeat < 1 && log.isInfoEnabled()) {
            log.info(String.format("Repeat value is %d. Skip [%s]] test.", this.repeat, this.testMethod.getName()));
        }
        for (int i = 0; i < this.repeat; i++) {
            if (repeatable && log.isInfoEnabled()) {
                log.info(String.format("Repeat # %d Method is [%s]", i + 1, this.testMethod.getName()));
            }
            if (ArrayUtils.isNotEmpty(this.params)) {
                Object[] paramArgs = TestUtils.getParamArgs(testMethod, this.params[i]);
                testMethod.invoke(target, paramArgs);
            } else {
                this.next.evaluate();
            }
            long elapsed = System.currentTimeMillis() - startTime;
            if (timeout > 0 && elapsed > timeout) {
                throw new TimeoutException(
                        String.format("All repeat test took %s ms; limit was %s ms. Repeat count is %d/%d.", elapsed, timeout, (i + 1), this.repeat));
            }
        }
        long totalElapsed = System.currentTimeMillis() - startTime;
        if (timeout > 0 && log.isInfoEnabled()) {
            log.info(String.format("Repeat finished. Total elapsed %s/%s ms.", totalElapsed, timeout));
        }
    }
}
