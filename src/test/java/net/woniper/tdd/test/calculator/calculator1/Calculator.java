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
        if(seperates != null) {
            this.seperates = seperates;
        } else {
            this.seperates = DEFAULT_SEPERATE;
        }
    }

    public int add(String text) {
        if (isEmptyOrNull(text))
            throw new IllegalArgumentException();

        if (isContainSpecialCharacter(text))
            throw new IllegalArgumentException();

        if (!isContainSeperate(text) && isContainNumber(text))
            return Integer.parseInt(text);

        int sum = 0;
        String[] numbers = text.split(",");
        for (String number : numbers) {
            sum += Integer.parseInt(number);
        }

        return sum;
    }

    private boolean isContainSeperate(CharSequence text) {
        for (String seperate : seperates) {
            if(seperate.contains(text)) {
                return true;
            }
        }
        return false;
    }

    private boolean isContainNumber(String text) {
        return text.matches("[0-9]");
    }

    private boolean isContainSpecialCharacter(String text) {
        char[] chars = text.toCharArray();
        for (char c : chars) {
            if(!isContainSeperate(String.valueOf(c)) && !(c >= '0' && c <= '9')) {
                return true;
            }
        }
        return false;
    }

    private boolean isEmptyOrNull(String text) {
        return text == null || text.isEmpty();
    }
}
