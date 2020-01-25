package me.ryan.runwith.exec;

import me.ryan.runwith.annotation.Repeat;
import me.ryan.runwith.runner.RepeatRunner;
import me.ryan.runwith.sample.Calculator;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(RepeatRunner.class)
public class MyTestClass {

    Calculator calculator = new Calculator();

    @Test
    @Repeat(10) // 반복 횟수를 지정한다...
    public void testMyCode10Times() {
        //your test code goes here
        int a = 5;
        int b = 5;
        int expected = 10;
        int result = calculator.add(a, b);
        System.out.println(result);
        assertEquals(expected, result);
    }

    @Test
    @Repeat(5)  // 반복 횟수를 지정한다...
    public void testMyCode5Times() {
        //your test code goes here
        int a = 2;
        int b = 3;
        int expected = 5;
        int result = calculator.add(a, b);
        System.out.println(result);
        assertEquals(expected, result);
    }

}
