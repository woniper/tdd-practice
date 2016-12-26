package net.woniper.tdd.toby.chapter1;

import net.woniper.tdd.toby.chapter1.connection.ConnectionMaker;
import net.woniper.tdd.toby.chapter1.connection.DConnectionMaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by woniper on 2016. 12. 26..
 */
@Configuration
public class DaoFactory {

    @Bean
    public UserDao userDao() {
        return new UserDao(connectionMaker());
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new DConnectionMaker();
    }
}
