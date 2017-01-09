package net.woniper.tdd.toby.chapter7.test;

import org.junit.runner.JUnitCore;

import java.sql.SQLException;

/**
 * Created by woniper on 2016. 12. 26..
 */
public class TestMain {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        JUnitCore.main("net.woniper.tdd.toby.chapter7.test.UserDaoTest",
                "net.woniper.tdd.toby.chapter7.test.UserServiceTest",
                "net.woniper.tdd.toby.chapter7.test.UserTest",
                "net.woniper.tdd.toby.chapter7.test.ReflectionTest",
                "net.woniper.tdd.toby.chapter7.test.ProxyTest",
                "net.woniper.tdd.toby.chapter7.test.FactoryBeanTest",
                "net.woniper.tdd.toby.chapter7.service.jaxb.JaxbTest",
                "net.woniper.tdd.toby.chapter7.service.jaxb.OxmTest");
    }
}
