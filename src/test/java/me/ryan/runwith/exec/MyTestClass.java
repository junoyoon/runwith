package me.ryan.runwith.exec;

import me.ryan.runwith.algo.Fibo;
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
    @Repeat(value = 10, timeout = 30000)  // 전체 수행시간 30초 제
    public void given_fibo_when_numberIs43Repeat10Times_then_returnValueIs433494437AndFinishIn10seconds() {
        Fibo fibo = new Fibo();
        // 1 1 2 3 5 8 13 21 34 ...
        int number = 43;
        int expected = 433494437;
        int result = fibo.calculateFibo(number);
        System.out.println(result);
        assertEquals(expected, result);
    }

    @Test
    @Repeat(value = 2, timeout = 7000)    // 전체 수행시간 7초 제한
    public void given_fibo_when_setTimeout5Second_thenThrowExceptionImmediately() {
        Fibo fibo = new Fibo();
        // 1 1 2 3 5 8 13 21 34 ...
        int number = 43;
        int expected = 433494437;
        int result = fibo.calculateFibo(number);
        System.out.println(result);
        assertEquals(expected, result);
    }

    @Test(/*timeout = 6000 */)
    @Repeat(value = 2, timeout = 7000)    // 타임아웃이 모두 설정된경우 익셉션 처리
    public void given_fibo_when_setTestTimeoutAndRepeatTimeout_thenThrowIllegalStateException() {
        Fibo fibo = new Fibo();
        // 1 1 2 3 5 8 13 21 34 ...
        int number = 43;
        int expected = 433494437;
        int result = fibo.calculateFibo(number);
        System.out.println(result);
        assertEquals(expected, result);
    }
}
