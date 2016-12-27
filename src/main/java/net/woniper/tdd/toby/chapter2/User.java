package net.woniper.tdd.toby.chapter2;

/**
 * Created by woniper on 2016. 12. 26..
 */
public class User {

    String id;
    String name;
    String password;

    public String getId() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }
}
