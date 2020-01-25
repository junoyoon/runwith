package me.ryan.runwith.annotation.handler;

import org.junit.runners.model.Statement;

import java.lang.reflect.Method;

public class RepeatHandler extends Statement {

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
            this.next.evaluate();
        }
    }
}
