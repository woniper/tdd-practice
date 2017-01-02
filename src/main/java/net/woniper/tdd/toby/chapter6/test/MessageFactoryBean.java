package net.woniper.tdd.toby.chapter6.test;

import org.springframework.beans.factory.FactoryBean;

/**
 * Created by woniper on 2017. 1. 2..
 */
public class MessageFactoryBean implements FactoryBean<Message> {
    String text;

    public MessageFactoryBean setText(String text) {
        this.text = text;
        return this;
    }

    @Override
    public Message getObject() throws Exception {
        return Message.newMessage(text);
    }

    @Override
    public Class<?> getObjectType() {
        return Message.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
