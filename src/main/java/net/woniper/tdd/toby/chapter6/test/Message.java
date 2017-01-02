package net.woniper.tdd.toby.chapter6.test;

/**
 * Created by woniper on 2017. 1. 2..
 */
public class Message {
    String text;

    private Message(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static Message newMessage(String text) {
        return new Message(text);
    }
}
