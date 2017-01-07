package net.woniper.tdd.toby.chapter6.service;

import net.woniper.tdd.toby.chapter6.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by woniper on 2017. 1. 2..
 */
@Transactional
public interface UserService {
    void add(User user);
    void deleteAll();
    void update(User user);
    void upgradeLevels();

    @Transactional(readOnly = true)
    User get(String id);

    @Transactional(readOnly = true)
    List<User> getAll();
}
