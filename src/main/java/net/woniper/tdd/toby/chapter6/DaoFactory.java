package net.woniper.tdd.toby.chapter6;

import net.woniper.tdd.toby.chapter6.dao.UserDao;
import net.woniper.tdd.toby.chapter6.dao.UserDaoJdbc;
import net.woniper.tdd.toby.chapter6.proxy.TxProxyFactoryBean;
import net.woniper.tdd.toby.chapter6.service.UserService;
import net.woniper.tdd.toby.chapter6.service.UserServiceImpl;
import net.woniper.tdd.toby.chapter6.service.UserServiceTx;
import net.woniper.tdd.toby.chapter6.test.MessageFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * Created by woniper on 2016. 12. 26..
 */
@Configuration
public class DaoFactory {

    @Bean
    public TxProxyFactoryBean userService() {
        TxProxyFactoryBean factoryBean = new TxProxyFactoryBean();
        factoryBean.setTarget(userServiceImpl());
        factoryBean.setTransactionManager(transactionManager());
        factoryBean.setPattern("upgradeLevels");
        factoryBean.setServiceInterface(UserService.class);
        return factoryBean;
    }

    @Bean
    public MessageFactoryBean message() {
        MessageFactoryBean factoryBean = new MessageFactoryBean();
        factoryBean.setText("Factory Bean");
        return factoryBean;
    }

    @Bean
    public UserDao userDao() {
        UserDaoJdbc userDao = new UserDaoJdbc();
        userDao.setDataSource(dataSource());
        return userDao;
    }

    @Bean
    public UserService userServiceTx() {
        UserServiceTx userServiceTx = new UserServiceTx();
        userServiceTx.setUserService(userServiceImpl());
        userServiceTx.setTransactionManager(transactionManager());
        return userServiceTx;
    }

    @Bean
    public UserServiceImpl userServiceImpl() {
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDao(userDao());
        userService.setMailSender(mailSender());
        return userService;
    }

    @Bean
    public MailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("mail.server.com");
        return mailSender;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("q1w2e3");

        return dataSource;
    }

}
