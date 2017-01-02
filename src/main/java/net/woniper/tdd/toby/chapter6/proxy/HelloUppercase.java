package net.woniper.tdd.toby.chapter6.proxy;

/**
 * Created by woniper on 2017. 1. 2..
 */
public class HelloUppercase implements Hello {

    private Hello hello;

    public HelloUppercase() {
        this.hello = new HelloTarget();
    }

    @Override
    public String sayHello(String name) {
        return hello.sayHello(name).toUpperCase();
    }

    @Override
    public String sayHi(String name) {
        return hello.sayHi(name).toUpperCase();
    }

    @Override
    public String sayThankYou(String name) {
        return hello.sayThankYou(name).toUpperCase();
    }
}
