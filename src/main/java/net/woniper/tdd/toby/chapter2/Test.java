package net.woniper.tdd.toby.chapter2;

import org.junit.runner.JUnitCore;

import java.sql.SQLException;

/**
 * Created by woniper on 2016. 12. 26..
 */
public class Test {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        JUnitCore.main("net.woniper.tdd.toby.chapter2.UserDaoTest");
    }
}
