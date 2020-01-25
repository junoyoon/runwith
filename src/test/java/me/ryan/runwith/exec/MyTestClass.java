package me.ryan.runwith.exec;

import me.ryan.runwith.algo.Fibo;
import me.ryan.runwith.annotation.Repeat;
import me.ryan.runwith.runner.RepeatRunner;
import me.ryan.runwith.sample.Calculator;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

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

    @Test
    public void given_fibo_when_numberIs6_then_returnValueIs8() {
        Fibo fibo = new Fibo();
        // 1 1 2 3 5 8 13 21 34 ...
        int number = 6;
        int expected = 8;
        int result = fibo.calculateFibo(number);
        System.out.println(result);
        assertEquals(expected, result);
    }

    @Test
    @Repeat(-1)
    public void given_RepeatAnnotation_when_valueIsMinus1_then_skipThisTest() {
        Fibo fibo = new Fibo();
        // 1 1 2 3 5 8 13 21 34 ...
        int number = 7;
        int expected = 13;
        int result = fibo.calculateFibo(number);
        System.out.println(result);
        assertEquals(expected, result);
    }

    @Test
    @Repeat(10)
    public void given_fibo_when_numberIs43Repeat10Times_then_returnValueIs433494437AndFinishIn10seconds() {
        long startTime = System.currentTimeMillis();
        Fibo fibo = new Fibo();
        // 1 1 2 3 5 8 13 21 34 ...
        int number = 43;
        int expected = 433494437;
        int result = fibo.calculateFibo(number);
        System.out.println(result);
        assertEquals(expected, result);
        long elapsed = System.currentTimeMillis() - startTime;
        System.out.println(elapsed);
        assertThat(elapsed, lessThanOrEqualTo(1000L));  // 하나에 1초안에 끝나야 전체가 10초안에 가능

    }

}
