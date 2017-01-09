package net.woniper.tdd.toby.chapter7.sqlservice;

/**
 * Created by woniper on 2017. 1. 9..
 */
public class DefaultSqlService extends BaseSqlService {
    public DefaultSqlService() {
        setSqlRegistry(new HashMapSqlRegistry());
        setSqlReader(new JaxbXmlSqlReader());
    }
}
