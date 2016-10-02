package net.woniper.tdd.test.calculator.calculator2;

/**
 * Created by woniper on 2016. 10. 2..
 */
public class StringCalculator {

    private char[] seperators;
    private static final char[] DEFAULT_SEPERATORS = {','};

    public StringCalculator(char[] seperators) {
        if(isSeperatorsContainsNumber(seperators))
            throw new IllegalArgumentException();

        if(seperators == null) {
            seperators = DEFAULT_SEPERATORS;
        }

        this.seperators = seperators;
    }

    private boolean isSeperatorsContainsNumber(char[] seperators) {
        if(seperators != null) {
            if(0 == seperators.length) {
                return true;
            }

            for (char seperator : seperators) {
                if(isNumber(seperator)) {
                    return true;
                }
            }
        }

        return false;
    }

    public StringCalculator() {
        this(null);
    }

    public boolean isContainsSeperator(char seperator) {
        for (char c : seperators) {
            if(c == seperator) {
                return true;
            }
        }
        return false;
    }

    private boolean isNotContainsSeperator(char seperator) {
        return !isContainsSeperator(seperator);
    }

    public int add(String text) {
        if(isEmptyOrNull(text))
            throw new IllegalArgumentException();

        if(!isTextContainsSeperator(text))
            throw new IllegalArgumentException();

        if(isAllNumber(text))
            return Integer.parseInt(text);

        return sum(text);
    }

    private boolean isTextContainsSeperator(String text) {
        char[] chars = getChars(text);
        for (char c : chars) {
            if(isNotNumber(c) && isNotContainsSeperator(c)) {
                return false;
            }
        }
        return true;
    }

    private boolean isAllNumber(String text) {
        char[] chars = getChars(text);
        for (char c : chars) {
            if(isNotNumber(c)) {
                return false;
            }
        }
        return true;
    }

    private char[] getChars(String text) {
        return text.toCharArray();
    }

    private int sum(String text) {
        int sum = 0;
        String[] numbers = text.split(getSplitRegex(text));
        for (String number : numbers) {
            sum += Integer.parseInt(number);
        }
        return sum;
    }

    private String getSplitRegex(String text) {
        StringBuilder splits = new StringBuilder();
        char[] chars = getChars(text);
        for (char seperator : chars) {
            if(isContainsSeperator(seperator)) {
                String appendSeperator = String.valueOf(seperator);
                if(appendSeperator.equals("|")) {
                    appendSeperator = "\\|";
                }

                if(splits.length() == 0) {
                    splits.append(appendSeperator);
                } else {
                    splits.append("|").append(appendSeperator);
                }
            }
        }

        return splits.toString();
    }

    private boolean isEmptyOrNull(String text) {
        return text == null || text.isEmpty();
    }

    private boolean isNumber(char text) {
        return String.valueOf(text).matches("[0-9]");
    }

    private boolean isNotNumber(char text) {
        return !isNumber(text);
    }
}
