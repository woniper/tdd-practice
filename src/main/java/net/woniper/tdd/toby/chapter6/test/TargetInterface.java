package net.woniper.tdd.toby.chapter6.test;

/**
 * Created by woniper on 2017. 1. 3..
 */
public interface TargetInterface {
    void hello();
    void hello(String a);
    int minus(int a, int b) throws RuntimeException;
    int plus(int a, int b);
    void method();
}
