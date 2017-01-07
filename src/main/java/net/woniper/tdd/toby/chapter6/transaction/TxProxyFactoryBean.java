package net.woniper.tdd.toby.chapter6.transaction;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import java.lang.reflect.Proxy;

/**
 * Created by woniper on 2017. 1. 2..
 */
public class TxProxyFactoryBean implements FactoryBean<Object> {

    Object target;
    PlatformTransactionManager transactionManager;
    String pattern;
    Class<?> serviceInterface;

    public TxProxyFactoryBean setTarget(Object target) {
        this.target = target;
        return this;
    }

    public TxProxyFactoryBean setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        return this;
    }

    public TxProxyFactoryBean setPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public TxProxyFactoryBean setServiceInterface(Class<?> serviceInterface) {
        this.serviceInterface = serviceInterface;
        return this;
    }

    @Override
    public Object getObject() throws Exception {
        TransactionHandler txHandler = new TransactionHandler();
        txHandler.setTarget(target);
        txHandler.setTransactionManager(transactionManager);
        txHandler.setPattern(pattern);
        return Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[] { serviceInterface },
                txHandler
        );
    }

    @Override
    public Class<?> getObjectType() {
        return serviceInterface;
    }

    /**
     * Singleton 인지 체크하는게 아니라
     * getObject() 호출 시 항상 같은 오브젝트를 반환하는지 체크
     * @return
     */
    @Override
    public boolean isSingleton() {
        return false;
    }
}
