package net.woniper.tdd.test.calculator.calculator1;

/**
 * Created by woniper on 2016. 9. 28..
 */
public class Calculator {

    private String[] seperates;
    private static final String[] DEFAULT_SEPERATE = {","};

    public Calculator() {
        this(null);
    }

    public Calculator(String[] seperates) {
        this.seperates = seperates == null ? DEFAULT_SEPERATE : seperates;
    }

    public int add(String text) {
        if (isEmptyOrNull(text))
            throw new IllegalArgumentException();

        if (isContainsSeperateAndNumber(text))
            throw new IllegalArgumentException();

        if (isAllNumber(text))
            return Integer.parseInt(text);

        String[] splitNumbers = convertSplitNumbers(text);
        return sum(splitNumbers);
    }

    private int sum(String[] splitNumbers) {
        int sum = 0;

        for (String number : splitNumbers) {
            sum += Integer.parseInt(number);
        }

        return sum;
    }

    private String[] convertSplitNumbers(String text) {
        StringBuilder splitRegex = new StringBuilder();
        char[] chars = text.toCharArray();
        for (char c : chars) {
            String str = String.valueOf(c);
            if(isContainsSeperate(str)) {
                if(0 == splitRegex.length()) {
                    splitRegex.append(str);
                } else {
                    splitRegex.append("|").append(str);
                }
            }
        }
        return text.split(splitRegex.toString());
    }

    private boolean isAllNumber(String text) {
        char[] chars = text.toCharArray();
        for (char c : chars) {
            if(isNotNumber(c)) {
                return false;
            }
        }
        return true;
    }

    private boolean isContainsSeperate(CharSequence text) {
        for (String seperate : seperates) {
            if(seperate.contains(text)) {
                return true;
            }
        }
        return false;
    }

    private boolean isNotContainsSeperate(CharSequence text) {
        return !isContainsSeperate(text);
    }

    private boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isNotNumber(char c) {
        return !isNumber(c);
    }

    private boolean isContainsSeperateAndNumber(String text) {
        char[] chars = text.toCharArray();
        for (char c : chars) {
            if(isNotContainsSeperate(String.valueOf(c)) && isNotNumber(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean isEmptyOrNull(String text) {
        return text == null || text.isEmpty();
    }
}
