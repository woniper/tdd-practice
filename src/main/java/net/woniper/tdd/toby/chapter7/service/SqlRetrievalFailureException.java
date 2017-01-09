package net.woniper.tdd.toby.chapter7.service;

/**
 * Created by woniper on 2017. 1. 9..
 */
public class SqlRetrievalFailureException extends RuntimeException {
    public SqlRetrievalFailureException(String message) {
        super(message);
    }

    public SqlRetrievalFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
