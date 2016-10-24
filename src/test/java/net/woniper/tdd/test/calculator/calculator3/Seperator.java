package net.woniper.tdd.test.calculator.calculator3;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by woniper on 2016. 10. 5..
 */
public class Seperator {

    private Set<Character> seperators = new HashSet<>();

    private static final char DEFAULT_SEPERATOR = ',';

    public Seperator() {
        this.seperators.add(DEFAULT_SEPERATOR);
    }

    public Set<Character> getSeperators() {
        return seperators;
    }
}
