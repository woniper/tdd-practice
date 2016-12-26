package net.woniper.tdd.toby.chapter1;

import net.woniper.tdd.toby.chapter1.connection.ConnectionMaker;

import java.sql.*;

/**
 * Created by woniper on 2016. 12. 26..
 */
public class UserDao {

    /**
     * interface를 구현한 구현체를 주입받기 위함
     * UserDao는 DConnectionMaker인지 NConnectionMaker인지 알 필요가 없다.
     */
    private ConnectionMaker connectionMaker;

    /**
     * 생성자를 통한 주입
     * @see net.woniper.tdd.toby.chapter1.connection.DConnectionMaker
     * @see net.woniper.tdd.toby.chapter1.connection.NConnectionMaker
     * @param connectionMaker
     */
    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = connectionMaker.makeConnection();

        PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?, ?, ?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection c = connectionMaker.makeConnection();

        PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();

        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        c.close();

        return user;
    }

}
