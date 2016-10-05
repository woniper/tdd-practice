package net.woniper.tdd.test.calculator.calculator2;

/**
 * Created by woniper on 2016. 10. 2..
 */
public class StringCalculator {

    /**
     * seperator 객체를 만들어 객체로 관리
     */
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

    public StringCalculator() {
        this(null);
    }

    /**
     * 생성자로 주입된 seperators에 숫자가 포함되어 있는지 체크
     * @param seperators
     * @return
     */
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

    /**
     * 유효한 seperator 인지 체크
     * @param seperator
     * @return
     */
    public boolean isContainsSeperator(char seperator) {
        for (char c : seperators) {
            if(c == seperator) {
                return true;
            }
        }
        return false;
    }

    // parameter인 text를 관리하고 validation할 수 있는 Text 객체가 필요해 보인다.
    /**
     * 문자열 합계
     * @param text
     * @return
     */
    public int add(String text) {
        if(isEmptyOrNull(text))
            throw new IllegalArgumentException();

        // seperator(구분자) 객체 필요
        if(!isTextContainsSeperator(text))
            throw new IllegalArgumentException();

        // Number 객체 필요
        if(isAllNumber(text))
            return Integer.parseInt(text);

        return sum(text);
    }

    /**
     * @see this.isContainsSeperator
     * @param seperator
     * @return
     */
    private boolean isNotContainsSeperator(char seperator) {
        return !isContainsSeperator(seperator);
    }

    /**
     * 합계를 구하기 위한(add method) text에 유효한 seperator만 포함되어 있는지 체크
     * @param text
     * @return
     */
    private boolean isTextContainsSeperator(String text) {
        char[] chars = getChars(text);
        for (char c : chars) {
            if(isNotNumber(c) && isNotContainsSeperator(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 합계를 구하기 위한(add method) text가 seperator없이 모두 숫자인지 체크
     * @param text
     * @return
     */
    private boolean isAllNumber(String text) {
        char[] chars = getChars(text);
        for (char c : chars) {
            if(isNotNumber(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * text를 char array로 변환
     * @param text
     * @return
     */
    private char[] getChars(String text) {
        return text.toCharArray();
    }

    /**
     * 합계
     * @param text
     * @return
     */
    private int sum(String text) {
        int sum = 0;
        String[] numbers = text.split(getSplitRegex(text));
        for (String number : numbers) {
            sum += Integer.parseInt(number);
        }
        return sum;
    }

    /**
     * text를 split으로 문자열을 구분하기 위해 regex 문자열 생성
     * @param text
     * @return
     */
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

    /**
     * 문자가 숫자인이 체크
     * @param text
     * @return
     */
    private boolean isNumber(char text) {
        return String.valueOf(text).matches("[0-9]");
    }

    private boolean isNotNumber(char text) {
        return !isNumber(text);
    }
}
