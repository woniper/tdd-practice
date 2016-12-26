package net.woniper.tdd.toby.chapter1.connection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by woniper on 2016. 12. 26..
 */
public interface ConnectionMaker {
    Connection makeConnection() throws ClassNotFoundException, SQLException;
}
