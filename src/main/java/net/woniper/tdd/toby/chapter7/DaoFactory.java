package net.woniper.tdd.toby.chapter7;

import net.woniper.tdd.toby.chapter7.dao.UserDao;
import net.woniper.tdd.toby.chapter7.dao.UserDaoJdbc;
import net.woniper.tdd.toby.chapter7.mail.DummyMailSender;
import net.woniper.tdd.toby.chapter7.service.TestUserServiceImpl;
import net.woniper.tdd.toby.chapter7.service.UserService;
import net.woniper.tdd.toby.chapter7.service.UserServiceImpl;
import net.woniper.tdd.toby.chapter7.sqlservice.*;
import net.woniper.tdd.toby.chapter7.test.MessageFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.oxm.castor.CastorMarshaller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by woniper on 2016. 12. 26..
 */
@Configuration
@EnableTransactionManagement
public class DaoFactory {

    @Bean
    public SqlService sqlService() {
        OxmSqlService sqlService = new OxmSqlService();
        sqlService.setUnmarshaller(unmarshaller());
        sqlService.setSqlmapFile("sqlmap.xml");
        sqlService.setSqlRegistry(new HashMapSqlRegistry());
        sqlService.setSqlmap(new ClassPathResource("net/woniper/tdd/toby/chapter7/dao/sqlmap.xml"));
        return sqlService;
    }

    @Bean
    public CastorMarshaller unmarshaller() {
        CastorMarshaller marshaller = new CastorMarshaller();
        marshaller.setMappingLocation(new ClassPathResource("mapping.xml", BaseSqlService.class));
        return marshaller;
    }

    @Bean
    public SqlRegistry sqlRegistry() {
        return new HashMapSqlRegistry();
    }

    @Bean
    public SqlReader sqlReader() {
        JaxbXmlSqlReader sqlReader = new JaxbXmlSqlReader();
        sqlReader.setSqlmapFile("sqlmap.xml");
        return sqlReader;
    }

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
        userDao.setSqlService(sqlService());
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
