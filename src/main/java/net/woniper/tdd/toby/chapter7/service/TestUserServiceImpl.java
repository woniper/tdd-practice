package net.woniper.tdd.toby.chapter7.service;

import net.woniper.tdd.toby.chapter7.User;

/**
 * Created by woniper on 2016. 12. 31..
 */
public class TestUserServiceImpl extends UserServiceImpl {
    private String id = "user4";

    protected void upgradeLevel(User user) {
        if(user.getId().equals(this.id)) {
            throw new TestUserServiceException();
        }

        super.upgradeLevel(user);
    }
}
