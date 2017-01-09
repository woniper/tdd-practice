package net.woniper.tdd.toby.chapter7.sqlservice;

/**
 * Created by woniper on 2017. 1. 9..
 */
public interface SqlRegistry {
    void registerSql(String key, String sql);
    String findSql(String key) throws SqlNotFoundException;
}
