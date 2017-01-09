package net.woniper.tdd.toby.chapter7.service;

import java.util.Map;

/**
 * Created by woniper on 2017. 1. 9..
 */
public class SimpleSqlService implements SqlService {

    private Map<String, String> sqlMap;

    public SimpleSqlService setSqlMap(Map<String, String> sqlMap) {
        this.sqlMap = sqlMap;
        return this;
    }

    @Override
    public String getSql(String key) throws SqlRetrievalFailureException {
        String sql = sqlMap.get(key);
        if(sql == null) {
            throw new SqlRetrievalFailureException(key + "에 대한 SQL을 찾을 수 없습니다.");
        } else {
            return sql;
        }
    }
}
