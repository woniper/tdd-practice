package net.woniper.tdd.toby.chapter6;

import net.woniper.tdd.toby.chapter6.dao.UserDao;
import net.woniper.tdd.toby.chapter6.dao.UserDaoJdbc;
import net.woniper.tdd.toby.chapter6.mail.DummyMailSender;
import net.woniper.tdd.toby.chapter6.service.TestUserServiceImpl;
import net.woniper.tdd.toby.chapter6.service.UserService;
import net.woniper.tdd.toby.chapter6.service.UserServiceImpl;
import net.woniper.tdd.toby.chapter6.test.MessageFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by woniper on 2016. 12. 26..
 */
@Configuration
@EnableTransactionManagement
public class DaoFactory {

//    @Bean
//    public Advice transactionAdvice() {
//        TransactionInterceptor advice = new TransactionInterceptor();
//        advice.setTransactionManager(transactionManager());
//        Properties properties = new Properties();
//        properties.put("get*", "PROPAGATION_REQUIRED,readOnly,timeout_30");
//        properties.put("upgrade*", "PROPAGATION_REQUIRES_NEW,ISOLATION_SERIALIZABLE");
//        properties.put("*", "PROPAGATION_REQUIRES");
//        advice.setTransactionAttributes(properties);
//        return advice;
//    }
//
//    @Bean
//    public Pointcut transactionPointcut() {
//        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
//        pointcut.setExpression("execution(* *..*ServiceImpl.upgrade*(..))");
//        return pointcut;
//    }
//
//    @Bean
//    public Advisor transactionAdvisor() {
//        return new DefaultPointcutAdvisor(transactionPointcut(), transactionAdvice());
//    }
//
//    @Bean
//    public ProxyFactoryBean userService() {
//        ProxyFactoryBean factoryBean = new ProxyFactoryBean();
//        factoryBean.setTarget(userServiceImpl());
//        factoryBean.addAdvisor(transactionAdvisor());
//        return factoryBean;
//    }
//
//    @Bean
//    public ProxyFactoryBean testUserService() {
//        ProxyFactoryBean factoryBean = new ProxyFactoryBean();
//        factoryBean.setTarget(testUserServiceImpl());
//        factoryBean.addAdvisor(transactionAdvisor());
//        return factoryBean;
//    }

    @Bean
    public UserService testUserService() {
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
    public UserServiceImpl userService() {
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
