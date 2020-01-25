package me.ryan.runwith.annotation.handler;


import me.ryan.runwith.common.TestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runners.model.Statement;

import java.lang.reflect.Method;

public class RepeatHandler extends Statement {

    protected static final Log log = LogFactory.getLog(RepeatHandler.class);

    private final Statement next;
    private final Method testMethod;
    private final int repeat;
    private final boolean repeatable;

    public RepeatHandler(Statement next, Method testMethod) {
        this(next, testMethod, TestUtils.getRepeatCount(testMethod), TestUtils.isRepeatable(testMethod));
    }

    public RepeatHandler(Statement next, Method testMethod, int repeat, boolean repeatable) {
        this.next = next;
        this.testMethod = testMethod;
        this.repeat = repeat;
        this.repeatable = repeatable;
    }

    @Override
    public void evaluate() throws Throwable {
        if (this.repeat < 1 && log.isInfoEnabled()) {
            log.info(String.format("Repeat value is %d. Skip [%s]] test.", this.repeat, this.testMethod.getName()));
        }
        for (int i = 0; i < this.repeat; i++) {
            if (repeatable && log.isInfoEnabled()) {
                log.info(String.format("Repeat # %d Method is [%s]", i + 1, this.testMethod.getName()));
            }
            this.next.evaluate();
        }
    }
}
