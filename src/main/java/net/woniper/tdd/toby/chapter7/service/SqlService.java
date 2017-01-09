package net.woniper.tdd.toby.chapter7.service;

/**
 * Created by woniper on 2017. 1. 9..
 */
public interface SqlService {
    String getSql(String key) throws SqlRetrievalFailureException;
}
