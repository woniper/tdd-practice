package net.woniper.tdd.toby.chapter1;

import net.woniper.tdd.toby.chapter1.connection.NConnectionMaker;

import java.sql.SQLException;

/**
 * Created by woniper on 2016. 12. 26..
 */
public class UserDaoTest {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        /**
         * 생성자를 통해 NConnectionMaker 주입
         */
        UserDao dao = new UserDao(new NConnectionMaker());

        User user = new User();
        user.setId("woniper");
        user.setName("이경원");
        user.setPassword("1234");

        dao.add(user);
        System.out.println(user.getId() + " 등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());
        System.out.println(user2.getId() + " 조회 성공");
    }
}
