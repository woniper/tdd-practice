package net.woniper.tdd.toby.chapter7.test;

import net.woniper.tdd.toby.chapter7.Level;
import net.woniper.tdd.toby.chapter7.User;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by woniper on 2016. 12. 31..
 */
public class UserTest {
    User user;

    @Before
    public void setUp() {
        user = new User();
    }

    @Test
    public void upgradeLevel() {
        Level[] levels = Level.values();

        for (Level level : levels) {
            if(level.nextLevel() == null) continue;

            user.setLevel(level);
            user.upgradeLevel();
            assertThat(user.getLevel(), is(level.nextLevel()));
        }
    }

    @Test(expected = IllegalStateException.class)
    public void cannotUpgradeLevel() throws Exception {
        Level[] levels = Level.values();

        for (Level level : levels) {
            if(level.nextLevel() != null) continue;
            user.setLevel(level);
            user.upgradeLevel();
        }

    }
}
