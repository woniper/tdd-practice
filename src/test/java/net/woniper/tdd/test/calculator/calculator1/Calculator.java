package net.woniper.tdd.test.calculator.calculator1;

/**
 * Created by woniper on 2016. 9. 28..
 */
public class Calculator {

    private static final String SEPERATE = ",";

    public int add(String text) {
        if(isEmptyOrNull(text))
            throw new IllegalArgumentException();

        if(isContainSpecialCharacter(text))
            throw new IllegalArgumentException();

        if(!isContainSeperate(text) && isContainNumber(text))
            return Integer.parseInt(text);

        int sum = 0;
        String[] numbers = text.split(",");
        for (String number : numbers) {
            sum += Integer.parseInt(number);
        }

        return sum;
    }

    private boolean isContainSeperate(String text) {
        return text.contains(",");
    }

    private boolean isContainNumber(String text) {
        return text.matches("[0-9]");
    }

    private boolean isContainSpecialCharacter(String text) {
        char[] chars = text.toCharArray();
        for (char c : chars) {
            if(',' != c && !(c >= '0' && c <= '9')) {
                return true;
            }
        }
        return false;
    }

    private boolean isEmptyOrNull(String text) {
        return text == null || text.isEmpty();
    }
}
