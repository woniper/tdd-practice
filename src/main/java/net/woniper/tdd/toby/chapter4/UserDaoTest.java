package net.woniper.tdd.toby.chapter4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * junit 실행 순서
 * 1. 테스트 클래스에서 @Test가 붙은 public이고 void형이며 파라미터가 없는 테스트 메소드를 모두 찾는다.
 * 2. 테스트 클래스의 오브젝트를 하나 만든다.
 * 3. @Before가 붙은 메소드가 있으면 실행한다.
 * 4. @Test가 붙은 메소드를 하나 호출하고 테스트 결과를 저장해둔다.
 * 5. @After가 붙은 메소드가 있으면 실행한다.
 * 6. 나머지 테스트 메소드에 대해 2~5번을 반복한다. (테스트 마다 객체를 생성)
 * 7. 모든 테스트의 결과를 종합해서 돌려준다.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoFactory.class)
public class UserDaoTest {

    @Autowired private UserDao dao;
    @Autowired private DataSource dataSource;

    private User user1;
    private User user2;

    /**
     * fixture : 테스트를 수행하는데 필요한 정보나 오브젝트를 픽스처라고 한다.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        this.user1 = new User("woniper", "kw", "1234");
        this.user2 = new User("01b", "yi", "1234");
    }

    @Test
    public void sqlExceptionTranslate() throws Exception {
        dao.deleteAll();

        try {
            dao.add(user1);
            dao.add(user1);
        } catch (DuplicateKeyException e) {
            SQLException sqlEx = (SQLException)e.getRootCause();
            SQLExceptionTranslator translator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
            assertEquals(translator.translate(null, null, sqlEx).getClass(), DuplicateKeyException.class);
        }

    }

    @Test(expected = DataIntegrityViolationException.class)
    public void duplicateKey() throws Exception {
        dao.deleteAll();

        dao.add(user1);
        dao.add(user1);
    }

    @Test
    public void getAll() throws Exception {
        dao.deleteAll();

        List<User> users0 = dao.getAll();
        assertThat(users0.size(), is(0));

        dao.add(user1);
        List<User> users1 = dao.getAll();
        assertThat(users1.size(), is(1));
        checkSameUser(user1, users1.get(0));

        dao.add(user2);
        List<User> users2 = dao.getAll();
        assertThat(users2.size(), is(2));
        checkSameUser(user2, users2.get(0));
        checkSameUser(user1, users2.get(1));
    }

    private void checkSameUser(User user1, User user2) {
        assertThat(user1.getId(), is(user2.getId()));
        assertThat(user1.getName(), is(user2.getName()));
        assertThat(user1.getPassword(), is(user2.getPassword()));
    }

    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {
        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        dao.add(user2);
        assertThat(dao.getCount(), is(2));

        User userget1 = dao.get(user1.getId());
        assertThat(userget1.getName(), is(user1.getName()));
        assertThat(userget1.getPassword(), is(user1.getPassword()));

        User userget2 = dao.get(user2.getId());
        assertThat(userget2.getName(), is(user2.getName()));
        assertThat(userget2.getPassword(), is(user2.getPassword()));

    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getUserFailure() throws Exception {
        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.get("unknown_id");
    }
}
