package net.woniper.tdd.toby.chapter6;

import net.woniper.tdd.toby.chapter6.dao.UserDao;
import net.woniper.tdd.toby.chapter6.dao.UserDaoJdbc;
import net.woniper.tdd.toby.chapter6.mail.DummyMailSender;
import net.woniper.tdd.toby.chapter6.proxy.TransactionAdvice;
import net.woniper.tdd.toby.chapter6.service.TestUserServiceImpl;
import net.woniper.tdd.toby.chapter6.service.UserService;
import net.woniper.tdd.toby.chapter6.service.UserServiceImpl;
import net.woniper.tdd.toby.chapter6.service.UserServiceTx;
import net.woniper.tdd.toby.chapter6.test.MessageFactoryBean;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
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
    public Advice transactionAdvice() {
        TransactionAdvice advice = new TransactionAdvice();
        advice.setTransactionManager(transactionManager());
        return advice;
    }

    @Bean
    public Pointcut transactionPointcut() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* *..*ServiceImpl.upgrade*(..))");
        return pointcut;
    }

    @Bean
    public Advisor transactionAdvisor() {
        return new DefaultPointcutAdvisor(transactionPointcut(), transactionAdvice());
    }

    @Bean
    public ProxyFactoryBean userService() {
        ProxyFactoryBean factoryBean = new ProxyFactoryBean();
        factoryBean.setTarget(userServiceImpl());
        factoryBean.addAdvisor(transactionAdvisor());
        return factoryBean;
    }

    @Bean
    public ProxyFactoryBean testUserService() {
        ProxyFactoryBean factoryBean = new ProxyFactoryBean();
        factoryBean.setTarget(testUserServiceImpl());
        factoryBean.addAdvisor(transactionAdvisor());
        return factoryBean;
    }

    @Bean
    public UserService testUserServiceImpl() {
        TestUserServiceImpl testUserService = new TestUserServiceImpl();
        testUserService.setUserDao(userDao());
        testUserService.setMailSender(new DummyMailSender());
        return testUserService;
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
