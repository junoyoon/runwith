package me.ryan.runwith.exec;

import me.ryan.runwith.annotation.Repeat;
import me.ryan.runwith.runner.RepeatRunner;
import me.ryan.runwith.sample.Calculator;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RepeatRunner.class)
public class MyTestClass {

    Calculator calculator = new Calculator();

    @Test
    @Repeat(10) // 반복 횟수를 지정한다...
    public void testMyCode10Times() {
        //your test code goes here
        System.out.println(calculator.add(5, 5));
    }

    @Test
    @Repeat(5)  // 반복 횟수를 지정한다...
    public void testMyCode5Times() {
        //your test code goes here
        System.out.println(calculator.add(2, 3));
    }

}
