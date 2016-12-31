package net.woniper.tdd.toby.chapter5.test;

import org.junit.runner.JUnitCore;

import java.sql.SQLException;

/**
 * Created by woniper on 2016. 12. 26..
 */
public class TestMain {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        JUnitCore.main("net.woniper.tdd.toby.chapter5.test.UserDaoTest",
                "net.woniper.tdd.toby.chapter5.test.UserServiceTest",
                "net.woniper.tdd.toby.chapter5.test.UserTest");
    }
}
