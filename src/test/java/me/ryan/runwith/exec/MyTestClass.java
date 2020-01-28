package me.ryan.runwith.exec;

import me.ryan.runwith.annotation.Repeat;
import me.ryan.runwith.annotation.TestDescription;
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
    @TestDescription("테스트 코드를 10회 반복한다. - 예제 테스트")
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
    @TestDescription("테스트 코드를 5회 반복한다. - 예제 테스트")
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
    @Repeat(1)  // 반복 횟수를 지정한다...
    @TestDescription("테스트 코드를 1회 반복한다. - 예제 테스트")
    public void testMyCode1Times() {
        //your test code goes here
        int a = -1;
        int b = 2;
        int expected = 1;
        int result = calculator.add(a, b);
        System.out.println(result);
        assertEquals(expected, result);
    }

    @Test
    @Repeat(0)  // 반복 횟수를 지정한다...
    @TestDescription("테스트 코드를 0회 반복한다. - 해당 테스트는 스킵되고 assert 결과에 상관 없이 성공처리된다.")
    public void testMyCode0Times() {
        //your test code goes here
        int a = -1;
        int b = 1;
        int expected = 0;
        int result = calculator.add(a, b);
        System.out.println(result);
        assertEquals(expected, result);
    }

    @Test
    @Repeat(0)  // 반복 횟수를 지정한다...
    @TestDescription("테스트 코드를 0회 반복한다. - 해당 테스트는 스킵되고 assert 결과에 상관 없이 성공처리된다.")
    public void testMyCode0TimesWithNotEqualExpected() {
        //your test code goes here
        int a = 1;
        int b = 1;
        int expected = 3;
        int result = calculator.add(a, b);
        System.out.println(result);
        assertEquals(expected, result);
    }

    @Test
    @Repeat(-12345)  // 반복 횟수를 지정한다...
    @TestDescription("테스트 코드를 마이너스(음수)회 반복한다. - 해당 테스트는 스킵되고 assert 결과에 상관 없이 성공처리된다.")
    public void testMyCodeMinusTimesWithNotEqualExpected() {
        //your test code goes here
        int a = 1;
        int b = 1;
        int expected = 3;
        int result = calculator.add(a, b);
        System.out.println(result);
        assertEquals(expected, result);
    }
}
