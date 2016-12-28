package net.woniper.tdd.toby.chapter3;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
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
public class UserDaoTest {

    private UserDao dao;

    /**
     * fixture : 테스트를 수행하는데 필요한 정보나 오브젝트를 픽스처라고 한다.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        dao = new UserDao();

        // 테스트 코드에서 dao에 SingleConnectionDataSource를 직접 DI 하기 때문에 @DirtiesContext 가 필요하다.
        DataSource dataSource = new SingleConnectionDataSource("jdbc:mysql://localhost:3306/test", "root", "q1w2e3", true);
        dao.setDataSource(dataSource);
    }

    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {
        User user1 = new User("woniper", "kw", "1234");
        User user2 = new User("01b", "yi", "1234");

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
