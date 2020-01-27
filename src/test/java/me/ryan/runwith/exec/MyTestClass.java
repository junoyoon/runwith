package me.ryan.runwith.exec;

import me.ryan.runwith.algo.Fibo;
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
    @TestDescription("반복이 없는 테스트. 피보나치 수열 테스트")
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
    @TestDescription("@Repeat 값이 1보다 작은 경우(-1 테스트) 테스트를 수행하지 않는다. 테스트는 성공으로 처리된다.")
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
    @Repeat(0)
    @TestDescription("@Repeat 값이 1보다 작은 경우(0 테스트) 테스트를 수행하지 않는다. 테스트는 성공으로 처리된다.")
    public void given_RepeatAnnotation_when_valueIsZero_then_skipThisTest() {
        Fibo fibo = new Fibo();
        // 1 1 2 3 5 8 13 21 34 ...
        int number = 7;
        int expected = 13;
        int result = fibo.calculateFibo(number);
        System.out.println(result);
        assertEquals(expected, result);
    }

    @Test
    @Repeat(value = 10, timeout = 30000)  // 전체 수행시간 30초 제한
    @TestDescription("피보나치 수열 테스트 10회를 제한시간 30초 이내에 수행한다.")
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
    @Repeat(value = 10, timeout = 100)  // 전체 수행시간 0.1초 제한
    @TestDescription("빠른 피보나치 수열 테스트 10회를 제한시간 0.1초 이내에 수행한다.")
    public void given_fastFibo_when_numberIs43Repeat10Times_then_returnValueIs433494437AndFinishIn10seconds() {
        Fibo fibo = new Fibo();
        // 1 1 2 3 5 8 13 21 34 ...
        int number = 43;
        int expected = 433494437;
        int result = fibo.calculateFibo(number, true);
        System.out.println(result);
        assertEquals(expected, result);
    }

    @Test
    @Repeat(value = 2, timeout = 7000)    // 전체 수행시간 7초 제한
    @TestDescription("피보나치 수열 테스트 2회를 제한시간 7초 이내에 수행한다.")
    public void given_fibo_when_setTimeout5Second_thenThrowExceptionImmediately() {
        Fibo fibo = new Fibo();
        // 1 1 2 3 5 8 13 21 34 ...
        int number = 43;
        int expected = 433494437;
        int result = fibo.calculateFibo(number);
        System.out.println(result);
        assertEquals(expected, result);
    }

    @Test(timeout = 6000)
    @Repeat(value = 2, timeout = 7000)    // 타임아웃이 모두 설정된경우 익셉션 처리
    @TestDescription("@Test 의 timeout 설정과 @Repeat 의 timeout 설정이 모두 되어있으면 익셉션 처리한다. " +
            "- 실패하는 테스트")
    public void given_fibo_when_setTestTimeoutAndRepeatTimeout_thenThrowIllegalStateException() {
        Fibo fibo = new Fibo();
        // 1 1 2 3 5 8 13 21 34 ...
        int number = 43;
        int expected = 433494437;
        int result = fibo.calculateFibo(number);
        System.out.println(result);
        assertEquals(expected, result);
    }

    @Test
    @Repeat(value = 5, params = {"3, 2", "4, 3", "5, 5", "6, 8", "7, 13"})  // 파라미터 반복 수행 테스트
    public void given_fibo_when_parameters_thenSuccessTest(int number, int expected) {
        Fibo fibo = new Fibo();
        int result = fibo.calculateFibo(number);
        assertEquals(expected, result);
    }

    @Test
//    @Repeat(value = 6, params = {"3, 2", "4, 3", "5, 5", "6, 8", "7, 13"})  // 파라미터 갯수와 repeat value 가 다르면 에러.
    @Repeat(value = 5, params = {"3, 2", "4, 3", "5, 5", "6, 8", "7, 13"})  // 파라미터 갯수와 repeat value 가 다르면 에러.
    public void given_fibo_when_differentValueAndParametersSize_thenThrowException(int number, int expected) {
        Fibo fibo = new Fibo();
        int result = fibo.calculateFibo(number);
        assertEquals(expected, result);
    }

    @Test
    @Repeat(value = 5, params = {"3, 2", "4, 3", "5, 5", "6, 8", "7, true"})
    // 테스트 케이스에서 사용하는 인자 타입과 params 의 타입이 다른경우 에러.
    public void given_fibo_when_differentTestCaseParamsAndParametersType_thenThrowException(int number, int expected) {
        Fibo fibo = new Fibo();
        int result = fibo.calculateFibo(number);
        assertEquals(expected, result);
    }
}
