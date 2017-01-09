package net.woniper.tdd.toby.chapter7.dao;

import net.woniper.tdd.toby.chapter7.Level;
import net.woniper.tdd.toby.chapter7.User;
import net.woniper.tdd.toby.chapter7.service.SqlService;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by woniper on 2016. 12. 26..
 */
public class UserDaoJdbc implements UserDao {

    private SqlService sqlService;

    public UserDaoJdbc setSqlService(SqlService sqlService) {
        this.sqlService = sqlService;
        return this;
    }

    /**
     * Spring JdbcTemplate
     * Template/Callback pattern
     */
    private JdbcTemplate jdbcTemplate;
    private RowMapper<User> rowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            user.setLevel(Level.valueOf(rs.getInt("level")));
            user.setLogin(rs.getInt("login"));
            user.setRecommend(rs.getInt("recommend"));

            return user;
        }
    };

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate();
        this.jdbcTemplate.setDataSource(dataSource);
    }

    @Override
    public void add(final User user) {
        this.jdbcTemplate.update(this.sqlService.getSql("userAdd"),
                user.getId(), user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend());
    }

    @Override
    public User get(final String id) {
        return this.jdbcTemplate.queryForObject(this.sqlService.getSql("userGet"),
                new Object[] {id}, this.rowMapper);
    }

    @Override
    public void deleteAll() {
        this.jdbcTemplate.update(this.sqlService.getSql("userDeleteAll"));
    }

    @Override
    public int getCount() {
        return this.jdbcTemplate.query(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                return connection.prepareStatement(sqlService.getSql("userGetCount"));
            }
        }, new ResultSetExtractor<Integer>() {
            @Override
            public Integer extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                resultSet.next();
                return resultSet.getInt(1);
            }
        });
    }

    @Override
    public void update(User user) {
        this.jdbcTemplate.update(this.sqlService.getSql("userUpdate"),
                user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getId());
    }

    @Override
    public List<User> getAll() {
        return this.jdbcTemplate.query(this.sqlService.getSql("userGetAll"), this.rowMapper);
    }
}
