package net.woniper.tdd.toby.chapter6.test;

import net.woniper.tdd.toby.chapter6.DaoFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by woniper on 2017. 1. 2..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoFactory.class)
public class FactoryBeanTest {

    @Autowired private ApplicationContext context;

    @Test
    public void getMessageFromFactoryBean() throws Exception {
        Object message = context.getBean("message");
        assertTrue(message.getClass() == Message.class);
        assertThat(((Message)message).getText(), is("Factory Bean"));
    }

    @Test
    public void getFactoryBean() throws Exception {
        Object factoryBean = context.getBean("&message");
        assertTrue(factoryBean.getClass() == MessageFactoryBean.class);
    }
}
