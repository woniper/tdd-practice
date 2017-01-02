package net.woniper.tdd.toby.chapter6.test;

import net.woniper.tdd.toby.chapter6.DaoFactory;
import net.woniper.tdd.toby.chapter6.Level;
import net.woniper.tdd.toby.chapter6.TestUserServiceException;
import net.woniper.tdd.toby.chapter6.User;
import net.woniper.tdd.toby.chapter6.dao.UserDao;
import net.woniper.tdd.toby.chapter6.mail.DummyMailSender;
import net.woniper.tdd.toby.chapter6.mail.MockMailSender;
import net.woniper.tdd.toby.chapter6.service.TestUserService;
import net.woniper.tdd.toby.chapter6.service.UserService;
import net.woniper.tdd.toby.chapter6.service.UserServiceImpl;
import net.woniper.tdd.toby.chapter6.service.UserServiceTx;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;
import java.util.List;

import static net.woniper.tdd.toby.chapter5.service.UserService.MIN_LOGCOUNT_FOR_SILBER;
import static net.woniper.tdd.toby.chapter5.service.UserService.MIN_RECOMMEND_FOR_GOLD;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.util.AssertionErrors.fail;

/**
 * Created by woniper on 2016. 12. 31..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoFactory.class)
@DirtiesContext
public class UserServiceTest {

    @Autowired private UserService userServiceTx;
    @Autowired private UserServiceImpl userServiceImpl;
    @Autowired private UserDao userDao;
    @Autowired private PlatformTransactionManager transactionManager;

    List<User> users;

    @Before
    public void setUp() throws Exception {
        users = Arrays.asList(
                new User("user1", "name1", "1234", Level.BASIC, MIN_LOGCOUNT_FOR_SILBER-1, 0),
                new User("user2", "name2", "1234", Level.BASIC, MIN_LOGCOUNT_FOR_SILBER, 0),
                new User("user3", "name3", "1234", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD-1),
                new User("user4", "name4", "1234", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD),
                new User("user5", "name5", "1234", Level.GOLD, 100, Integer.MAX_VALUE)
        );
    }

    @Test
    public void upgradeAllOrNothing() throws Exception {
        TestUserService testUserService = new TestUserService(users.get(3).getId());
        testUserService.setUserDao(userDao);
        testUserService.setMailSender(new DummyMailSender());

        UserServiceTx userServiceTx = new UserServiceTx();
        userServiceTx.setUserService(testUserService);
        userServiceTx.setTransactionManager(transactionManager);

        userDao.deleteAll();
        for (User user : users) {
            userDao.add(user);
        }

        try {
            userServiceTx.upgradeLevels();
            fail("TestUserServiceException expected");
        } catch(TestUserServiceException e) {
        }

        checkLevelUpgrade(users.get(1), false);
    }

    @Test
    public void add() throws Exception {
        userDao.deleteAll();

        User userWithLevel = users.get(4);
        User userWithoutLevel = users.get(0);

        userServiceTx.add(userWithLevel);
        userServiceTx.add(userWithoutLevel);

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

        MockMailSender mockMailSender = new MockMailSender();
        userServiceImpl.setMailSender(mockMailSender);

        userServiceImpl.upgradeLevels();

        checkLevelUpgrade(users.get(0), false);
        checkLevelUpgrade(users.get(1), true);
        checkLevelUpgrade(users.get(2), false);
        checkLevelUpgrade(users.get(3), true);
        checkLevelUpgrade(users.get(4), false);

        List<String> requests = mockMailSender.getRequests();
        assertThat(requests.size(), is(2));
        assertThat(requests.get(0), is(users.get(1).getEmail()));
        assertThat(requests.get(1), is(users.get(3).getEmail()));
    }

    @Test
    public void bean() throws Exception {
        assertThat(this.userServiceTx, is(notNullValue()));
    }

    private void checkLevelUpgrade(User user, boolean upgraded) {
        User userUpdate = userDao.get(user.getId());
        if(upgraded) {
            assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
        } else {
            assertThat(userUpdate.getLevel(), is(user.getLevel()));
        }
    }
}
