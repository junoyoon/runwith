package me.ryan.runwith.common;

public class TestMessages {
    public static final String DO_NOT_USE_PARAMS_AND_METHOD = "Do not use params and method in @Repeat.";

    public static final String REPEAT_VALUE_AND_RETURNED_METHOD_SIZE_SAME = "Repeat value and number of returned method params doesn't match %d/%d!! It must be same.";

    public static final String REPEAT_VALUE_AND_PARAMS_SIZE_SAME = "Repeat value and number of params doesn't match %d/%d!! It must be same.";

    public static final String TIMEOUT_MUST_BE_ONLY_ONE_DECLARATION = "Test method [%s] has been configured with Repeat's @Repeat(timeout=%s) and JUnit's @Test(timeout=%s) annotations, but only one declaration of a 'timeout' is permitted per test method.";
}
