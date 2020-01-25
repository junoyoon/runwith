package me.ryan.runwith.annotation.handler;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runners.model.Statement;

import java.lang.reflect.Method;

public class RepeatHandler extends Statement {

    protected static final Log log = LogFactory.getLog(RepeatHandler.class);

    private final Statement next;
    private final Method testMethod;
    private final int repeat;

    public RepeatHandler(Statement next, Method testMethod, int repeat) {
        this.next = next;
        this.testMethod = testMethod;
        this.repeat = repeat;
    }

    @Override
    public void evaluate() throws Throwable {
        for (int i = 0; i < this.repeat; i++) {
            if (log.isInfoEnabled()) {
                log.info(String.format("Repeat # %d Method is [%s]", i + 1, this.testMethod.getName()));
            }
            this.next.evaluate();
        }
    }
}
