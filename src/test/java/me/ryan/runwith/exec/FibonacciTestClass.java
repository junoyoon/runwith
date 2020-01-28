package me.ryan.runwith.exec;

import me.ryan.runwith.algo.Fibo;
import me.ryan.runwith.annotation.Repeat;
import me.ryan.runwith.annotation.TestDescription;
import me.ryan.runwith.runner.RepeatRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(RepeatRunner.class)
public class FibonacciTestClass {

    Fibo fibo = new Fibo();

    @Test
    @TestDescription("반복이 없는 테스트. 피보나치 수열 테스트")
    public void given_fibo_when_numberIs6_then_returnValueIs8() {
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
        // 1 1 2 3 5 8 13 21 34 ...
        int number = 7;
        int expected = 13;
        int result = fibo.calculateFibo(number);
        System.out.println(result);
        assertEquals(expected, result);
    }

    @Test
    @Repeat(value = 10, timeout = 6000)  // 전체 수행시간 6초 제한
    @TestDescription("피보나치 수열 테스트 10회를 제한시간 6초 이내에 수행한다.")
    public void given_fibo_when_numberIs43Repeat10Times_then_returnValueIs433494437AndFinishIn10seconds() {
        // 1 1 2 3 5 8 13 21 34 ...
        int number = 40;
        int expected = 102334155;
        int result = fibo.calculateFibo(number);
        System.out.println(result);
        assertEquals(expected, result);
    }

    @Test
    @Repeat(value = 10, timeout = 100)  // 전체 수행시간 0.1초 제한
    @TestDescription("빠른 피보나치 수열 테스트 10회를 제한시간 0.1초 이내에 수행한다.")
    public void given_fastFibo_when_numberIs43Repeat10Times_then_returnValueIs433494437AndFinishIn10seconds() {
        // 1 1 2 3 5 8 13 21 34 ...
        int number = 43;
        int expected = 433494437;
        int result = fibo.calculateFibo(number, true);
        System.out.println(result);
        assertEquals(expected, result);
    }

    @Test
    @Repeat(value = 2, timeout = 5000)    // 전체 수행시간 5초 제한
    @TestDescription("피보나치 수열 테스트 2회를 제한시간 5초 이내에 수행한다.")
    public void given_fibo_when_setTimeout5Second_thenThrowExceptionImmediately() {
        // 1 1 2 3 5 8 13 21 34 ...
        int number = 42;
        int expected = 267914296;
        int result = fibo.calculateFibo(number);
        System.out.println(result);
        assertEquals(expected, result);
    }

    @Test(timeout = 6000)
    @Repeat(value = 2, timeout = 7000)    // 타임아웃이 모두 설정된경우 익셉션 처리
    @TestDescription("@Test 의 timeout 설정과 @Repeat 의 timeout 설정이 모두 되어있으면 익셉션 처리한다. " +
            "- 실패하는 테스트")
    public void given_fibo_when_setTestTimeoutAndRepeatTimeout_thenThrowIllegalStateException() {
        // 1 1 2 3 5 8 13 21 34 ...
        int number = 43;
        int expected = 433494437;
        int result = fibo.calculateFibo(number);
        System.out.println(result);
        assertEquals(expected, result);
    }

    @Test
    @Repeat(value = 5, params = {"3, 2", "4, 3", "5, 5", "6, 8", "7, 13"})  // 파라미터 반복 수행 테스트
    @TestDescription("피보나치 수열에서 파라미터를 통해 반복수행하는 테스트.")
    public void given_fibo_when_parameters_thenSuccessTest(int number, int expected) {
        int result = fibo.calculateFibo(number);
        assertEquals(expected, result);
    }

    @Test
    @Repeat(value = 6, params = {"3, 2", "4, 3", "5, 5", "6, 8", "7, 13"})  // 파라미터 갯수와 repeat value 가 다르면 에러.
    @TestDescription("@Repeat value 와 prams 의 size 가 다르면 익셉션 처리." +
            "- 실패하는 테스트")
    public void given_fibo_when_differentValueAndParametersSize_thenThrowException(int number, int expected) {
        int result = fibo.calculateFibo(number);
        assertEquals(expected, result);
    }

    @Test
    @Repeat(value = 5, params = {"3, 2", "4, 3", "5, 5", "6, 8", "7, true"})
    // 테스트 케이스에서 사용하는 인자 타입과 params 의 타입이 다른경우 에러.
    @TestDescription("테스트 케이스의 인자값의 타입과 param 에서 들어오는 타입이 다른경우 익셉션 처리." +
            "- 실패하는 테스트")
    public void given_fibo_when_differentTestCaseParamsAndParametersType_thenThrowException(int number, int expected) {
        int result = fibo.calculateFibo(number);
        assertEquals(expected, result);
    }

    @Test
    @Repeat(value = 2, method = "paramMethod")
    @TestDescription("피보나치 수열에서 파라미터를 메소드로 받아 테스트를 수행한다.")
    public void given_paramsMethod_whenRepeatParamsWithMethod_thenRunTestWithParams(int number, int expected) {
        int result = fibo.calculateFibo(number);
        assertEquals(expected, result);
    }

    private Object[] paramMethod() {
        return new Object[]{
                new Object[]{6, 8},
                new Object[]{5, 5}
        };
    }

    @Test
    @Repeat(value = 2, method = "paramIntMethod")
    @TestDescription("피보나치 수열에서 파라미터를 int 타입 메소드로 받아 테스트를 수행하면 익셉션 처리." +
            "- 실패하는 테스트 - 반환값과 반환값의 멤버는 모두 Object[] 이어야 한다. ")
    public void given_paramsMethod_whenRepeatParamsWithMethod_thenRunTestWithIntParams(int number, int expected) {
        int result = fibo.calculateFibo(number);
        assertEquals(expected, result);
    }

    private Object[] paramIntMethod() {
        return new Object[]{
                new int[]{6, 8},
                new int[]{5, 5}
        };
    }

    @Test
    @Repeat(value = 2, params = {"6, 8", "5, 5"}, method = "bothParamMethod")
    @TestDescription("params 와 method 를 동시에 사용하는 경우 익셉션 처리." +
            "- 실패하는 테스트")
    public void given_Fibo_whenUseParamsAndMethod_thenThrowException(int number, int expected) {
        int result = fibo.calculateFibo(number);
        assertEquals(expected, result);
    }

    private Object[] bothParamMethod() {
        return new Object[]{
                new Object[]{6, 8},
                new Object[]{5, 5}
        };
    }

    @Test
    @Repeat(value = 2, method = "overParamMethod")
    @TestDescription("피보나치 수열에서 value 와 사이즈가 다른 파라미터를 다른 메소드로 받아 테스트를 수행하면 익셉션 처리." +
            "- 실패하는 테스트")
    public void given_paramsMethod_whenNotEaualValueAndParamMethodSize_thenThrowException(int number, int expected) {
        int result = fibo.calculateFibo(number);
        assertEquals(expected, result);
    }

    private Object[] overParamMethod() {
        return new Object[]{
                new Object[]{6, 8},
                new Object[]{5, 5},
                new Object[]{10, 55}
        };
    }
}
