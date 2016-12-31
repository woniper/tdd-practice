package net.woniper.tdd.toby.chapter5.service;

import net.woniper.tdd.toby.chapter5.Level;
import net.woniper.tdd.toby.chapter5.User;
import net.woniper.tdd.toby.chapter5.dao.UserDao;

import java.util.List;

/**
 * Created by woniper on 2016. 12. 31..
 */
public class UserService {

    UserDao userDao;
    public static final int MIN_LOGCOUNT_FOR_SILBER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 30;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void upgradeLevels() {
        List<User> users = userDao.getAll();

        for (User user : users) {
            if(canUpgradeLevel(user)) {
                upgradeLevel(user);
            }
        }
    }

    public void add(User user) {
        if(user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }

        userDao.add(user);
    }

    /**
     * upgrade 여부도 User에 존재하면 안되나?
     * @param user
     * @return
     */
    private boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();

        switch (currentLevel) {
            case BASIC: return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILBER);
            case SILVER: return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
            case GOLD: return false;
            default: throw new IllegalArgumentException("Unknown Level:" + currentLevel);
        }
    }

    private void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
    }
}
