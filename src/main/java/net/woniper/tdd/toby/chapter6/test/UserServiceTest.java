package net.woniper.tdd.toby.chapter6.test;

import net.woniper.tdd.toby.chapter6.DaoFactory;
import net.woniper.tdd.toby.chapter6.Level;
import net.woniper.tdd.toby.chapter6.service.TestUserServiceException;
import net.woniper.tdd.toby.chapter6.User;
import net.woniper.tdd.toby.chapter6.dao.MockUserDao;
import net.woniper.tdd.toby.chapter6.dao.UserDao;
import net.woniper.tdd.toby.chapter6.mail.MockMailSender;
import net.woniper.tdd.toby.chapter6.service.UserService;
import net.woniper.tdd.toby.chapter6.service.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;

import static net.woniper.tdd.toby.chapter5.service.UserService.MIN_LOGCOUNT_FOR_SILBER;
import static net.woniper.tdd.toby.chapter5.service.UserService.MIN_RECOMMEND_FOR_GOLD;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.fail;

/**
 * Created by woniper on 2016. 12. 31..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoFactory.class)
//@DirtiesContext
public class UserServiceTest {

    @Autowired private UserService userService;
    @Autowired private UserService testUserService;
    @Autowired private UserDao userDao;

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
//    @DirtiesContext
    public void upgradeAllOrNothing() throws Exception {
        userDao.deleteAll();
        for (User user : users) {
            userDao.add(user);
        }

        try {
            testUserService.upgradeLevels();
            fail("TestUserServiceException expected");
        } catch(TestUserServiceException e) {
        }

        checkLevelUpgrade(users.get(1), false);
    }

    @Test
    public void advisorAutoProxyCreator() throws Exception {
        assertThat(testUserService, instanceOf(Proxy.class));
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
        UserServiceImpl userServiceImpl = new UserServiceImpl();

        MockUserDao mockUserDao = new MockUserDao(this.users);
        userServiceImpl.setUserDao(mockUserDao);

        MockMailSender mockMailSender = new MockMailSender();
        userServiceImpl.setMailSender(mockMailSender);

        userServiceImpl.upgradeLevels();

        List<User> updated = mockUserDao.getUpdated();
        assertThat(updated.size(), is(2));
        checkUserAndLevel(updated.get(0), "user2", Level.SILVER);
        checkUserAndLevel(updated.get(1), "user4", Level.GOLD);


        List<String> requests = mockMailSender.getRequests();
        assertThat(requests.size(), is(2));
        assertThat(requests.get(0), is(users.get(1).getEmail()));
        assertThat(requests.get(1), is(users.get(3).getEmail()));
    }

    @Test
    public void mockUpgradeLevels() throws Exception {
        UserServiceImpl userServiceImpl = new UserServiceImpl();

        UserDao mockUserDao = mock(UserDao.class);
        when(mockUserDao.getAll()).thenReturn(this.users);
        userServiceImpl.setUserDao(mockUserDao);

        MailSender mockMailSender = mock(MailSender.class);
        userServiceImpl.setMailSender(mockMailSender);

        userServiceImpl.upgradeLevels();

        verify(mockUserDao, times(2)).update(any(User.class));
        verify(mockUserDao, times(2)).update(any(User.class));
        verify(mockUserDao).update(users.get(1));
        assertThat(users.get(1).getLevel(), is(Level.SILVER));
        verify(mockUserDao).update(users.get(3));
        assertThat(users.get(3).getLevel(), is(Level.GOLD));

        ArgumentCaptor<SimpleMailMessage> mailMessageArg = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mockMailSender, times(2)).send(mailMessageArg.capture());
        List<SimpleMailMessage> mailMessages = mailMessageArg.getAllValues();
        assertThat(mailMessages.get(0).getTo()[0], is(users.get(1).getEmail()));
        assertThat(mailMessages.get(1).getTo()[0], is(users.get(3).getEmail()));
    }

    @Test
    public void bean() throws Exception {
        assertThat(this.userService, is(notNullValue()));
    }

    private void checkUserAndLevel(User updated, String expectedId, Level level) {
        assertThat(updated.getId(), is(expectedId));
        assertThat(updated.getLevel(), is(level));
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
