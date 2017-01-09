package net.woniper.tdd.toby.chapter7.sqlservice;

import java.util.HashMap;

/**
 * Created by woniper on 2017. 1. 9..
 */
public class HashMapSqlRegistry implements SqlRegistry {

    private HashMap<String, String> sqlMap = new HashMap<>();

    @Override
    public void registerSql(String key, String sql) {
        sqlMap.put(key, sql);
    }

    @Override
    public String findSql(String key) throws SqlNotFoundException {
        String sql = sqlMap.get(key);
        if(sql == null) {
            throw new SqlNotFoundException();
        } else {
            return sql;
        }
    }
}
