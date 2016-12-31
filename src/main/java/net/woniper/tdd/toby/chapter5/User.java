package net.woniper.tdd.toby.chapter5;

/**
 * Created by woniper on 2016. 12. 26..
 */
public class User {

    String id;
    String name;
    String password;

    Level level;
    int login;
    int recommend;

    public User() {}

    public User(String id, String name, String password, Level level, int login, int recommend) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.level = level;
        this.login = login;
        this.recommend = recommend;
    }

    public void upgradeLevel() {
        Level nextLevel = this.level.nextLevel();

        if(nextLevel == null) {
            throw new IllegalStateException(this.level + "은 업그레이드가 불가능합니다.");
        } else {
            this.level = nextLevel;
        }
    }

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

    public Level getLevel() {
        return level;
    }

    public User setLevel(Level level) {
        this.level = level;
        return this;
    }

    public int getLogin() {
        return login;
    }

    public User setLogin(int login) {
        this.login = login;
        return this;
    }

    public int getRecommend() {
        return recommend;
    }

    public User setRecommend(int recommend) {
        this.recommend = recommend;
        return this;
    }

    public String getEmail() {
        return getId() + "@naver.com";
    }
}
