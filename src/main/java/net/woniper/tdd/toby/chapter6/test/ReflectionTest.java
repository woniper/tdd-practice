package net.woniper.tdd.toby.chapter6.test;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by woniper on 2017. 1. 2..
 */
public class ReflectionTest {

    @Test
    public void invokeMethod() throws Exception {
        String name = "woniper";

        assertThat(name.length(), is(7));

        Method lengthMethod = name.getClass().getMethod("length");
        assertThat((Integer)lengthMethod.invoke(name), is(7));

        assertThat(name.charAt(0), is('w'));

        Method charAtMethod = name.getClass().getMethod("charAt", int.class);
        assertThat((Character)charAtMethod.invoke(name, 0), is('w'));
    }
}
