package me.ryan.runwith.exec;

import me.ryan.runwith.annotation.Repeat;
import me.ryan.runwith.runner.RepeatRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RepeatRunner.class)
public class MyTestClass {

    @Test
    @Repeat(10) // 반복 횟수를 지정한다...
    public void testMyCode10Times() {
        //your test code goes here
    }

    @Test
    @Repeat(5)  // 반복 횟수를 지정한다...
    public void testMyCode5Times() {
        //your test code goes here
    }

}
