package me.ryan.runwith.baeldung.test;


import me.ryan.runwith.baeldung.runner.BlockingTestRunner;
import me.ryan.runwith.sample.Calculator;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

//@RunWith(TestRunner.class)
@RunWith(BlockingTestRunner.class)
public class CalculatorTest {
    Calculator calculator = new Calculator();

    @Test
    public void testAddition() {
        System.out.println("in testAddition");
        assertEquals("addition", 8, calculator.add(5, 3));
    }
}
