package net.woniper.tdd.test.toby.chapter2;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * 학습 테스트
 * Created by woniper on 2016. 12. 27..
 */
public class JUnitTest {

    static JUnitTest testObject;

    @Test
    public void test1() throws Exception {
        assertThat(this, is(not(sameInstance(testObject))));
        testObject = this;
    }

    @Test
    public void test2() throws Exception {
        assertThat(this, is(not(sameInstance(testObject))));
        testObject = this;
    }

    @Test
    public void test3() throws Exception {
        assertThat(this, is(not(sameInstance(testObject))));
        testObject = this;
    }
}
