package net.woniper.tdd.toby.chapter6.test;

import net.woniper.tdd.toby.chapter6.proxy.Hello;
import net.woniper.tdd.toby.chapter6.proxy.HelloTarget;
import net.woniper.tdd.toby.chapter6.proxy.HelloUppercase;
import net.woniper.tdd.toby.chapter6.proxy.UppercaseHandler;
import org.junit.Test;

import java.lang.reflect.Proxy;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by woniper on 2017. 1. 2..
 */
public class ProxyTest {

    @Test
    public void simpleProxy() {
        Hello hello = new HelloTarget();
        assertThat(hello.sayHello("woniper"), is("Hello woniper"));
        assertThat(hello.sayHi("woniper"), is("Hi woniper"));
        assertThat(hello.sayThankYou("woniper"), is("Thank You woniper"));
    }

    @Test
    public void targetProxy() throws Exception {
        Hello hello = new HelloUppercase();
        assertThat(hello.sayHello("woniper"), is("HELLO WONIPER"));
        assertThat(hello.sayHi("woniper"), is("HI WONIPER"));
        assertThat(hello.sayThankYou("woniper"), is("THANK YOU WONIPER"));
    }

    @Test
    public void dynamicProxy() throws Exception {
        Hello hello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{Hello.class},
                new UppercaseHandler(new HelloTarget())
        );
        assertThat(hello.sayHello("woniper"), is("HELLO WONIPER"));
        assertThat(hello.sayHi("woniper"), is("HI WONIPER"));
        assertThat(hello.sayThankYou("woniper"), is("THANK YOU WONIPER"));
    }

}