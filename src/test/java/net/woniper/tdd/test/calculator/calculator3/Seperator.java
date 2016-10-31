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

    public String getRegex(StringCalculator calculator) {
        StringBuilder regex = new StringBuilder();

        for (Character c : getSeperators()) {
            String addRegexText = null;

            if(c == '|') {
                addRegexText = "\\|";
            } else {
                addRegexText = String.valueOf(c);
            }

            if(regex.length() == 0) {
                regex.append(addRegexText);
            } else {
                regex.append("|").append(addRegexText);
            }
        }

        return regex.toString();
    }

    public boolean contains(char seperator) {
        return getSeperators().contains(seperator);
    }

    public boolean add(char seperator) {
        return getSeperators().add(seperator);
    }
}
