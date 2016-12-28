package net.woniper.tdd.toby.chapter3;

import net.woniper.tdd.toby.chapter3.statement.StatementStrategy;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by woniper on 2016. 12. 28..
 */
public class JdbcContext {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void executeSql(final String query) throws SQLException {
        workWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                return c.prepareStatement(query);
            }
        });
    }

    public void workWithStatementStrategy(StatementStrategy strategy) throws SQLException{
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = dataSource.getConnection();
            ps = strategy.makePreparedStatement(c);

            ps.executeUpdate();
        } catch(SQLException e) {
            throw e;
        } finally {
            if(ps != null) try { ps.close(); } catch (SQLException e) { throw e; }
            if(c != null) try { c.close(); } catch (SQLException e) { throw e; }
        }
    }
}
