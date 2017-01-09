package net.woniper.tdd.toby.chapter7.dao;

import net.woniper.tdd.toby.chapter7.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by woniper on 2017. 1. 2..
 */
public class MockUserDao implements UserDao {

    private List<User> users;
    private List<User> updated = new ArrayList<>();

    public MockUserDao(List<User> users) {
        this.users = users;
    }

    public List<User> getUpdated() {
        return updated;
    }

    @Override
    public void add(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User get(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<User> getAll() {
        return this.users;
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getCount() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(User user) {
        updated.add(user);
    }
}
