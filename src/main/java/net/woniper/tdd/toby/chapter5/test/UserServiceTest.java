package net.woniper.tdd.toby.chapter5.test;

import net.woniper.tdd.toby.chapter5.DaoFactory;
import net.woniper.tdd.toby.chapter5.Level;
import net.woniper.tdd.toby.chapter5.User;
import net.woniper.tdd.toby.chapter5.dao.UserDao;
import net.woniper.tdd.toby.chapter5.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by woniper on 2016. 12. 31..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoFactory.class)
public class UserServiceTest {

    @Autowired private UserService userService;
    @Autowired private UserDao userDao;

    List<User> users;

    @Before
    public void setUp() throws Exception {
        users = Arrays.asList(
                new User("user1", "name1", "1234", Level.BASIC, 49, 0),
                new User("user2", "name2", "1234", Level.BASIC, 50, 0),
                new User("user3", "name3", "1234", Level.SILVER, 60, 29),
                new User("user4", "name4", "1234", Level.SILVER, 60, 30),
                new User("user5", "name5", "1234", Level.GOLD, 100, 100)
        );
    }

    @Test
    public void add() throws Exception {
        userDao.deleteAll();

        User userWithLevel = users.get(4);
        User userWithoutLevel = users.get(0);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        assertThat(userWithLevelRead.getLevel(), is(userWithLevel.getLevel()));
        assertThat(userWithoutLevelRead.getLevel(), is(userWithoutLevel.getLevel()));
    }

    @Test
    public void upgradeLevels() throws Exception {
        userDao.deleteAll();

        for (User user : users) {
            userDao.add(user);
        }

        userService.upgradeLevels();

        checkLevel(users.get(0), Level.BASIC);
        checkLevel(users.get(1), Level.SILVER);
        checkLevel(users.get(2), Level.SILVER);
        checkLevel(users.get(3), Level.GOLD);
        checkLevel(users.get(4), Level.GOLD);
    }

    @Test
    public void bean() throws Exception {
        assertThat(this.userService, is(notNullValue()));
    }

    private void checkLevel(User user, Level exepectedLevel) {
        User userUpdate = userDao.get(user.getId());
        assertThat(userUpdate.getLevel(), is(exepectedLevel));
    }
}
