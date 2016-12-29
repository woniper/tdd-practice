package net.woniper.tdd.toby.chapter4;

import java.util.List;

/**
 * Created by woniper on 2016. 12. 29..
 */
public interface UserDao {
    void add(User user);
    User get(String id);
    List<User> getAll();
    void deleteAll();
    int getCount();
}
