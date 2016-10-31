package net.woniper.tdd.calculator.calculator3;

/**
 * Created by woniper on 2016. 10. 5..
 */
public class StringCalculator {

    private String text;
    private Seperator seperator;
    private Number number;

    public StringCalculator(String text) {
        if(isEmptyOrNull(text))
            throw new IllegalArgumentException();

        this.text = text;
    }

    private boolean isEmptyOrNull(String text) {
        return text == null || text.isEmpty();
    }

    public Number getNumber() {
        if(number == null) {
            return createNumber();
        }

        return this.number;
    }

    private Number createNumber() {
        number = new Number();
        String[] numbers = getNumberArray();
        for (String num : numbers) {
            number.addNumber(Integer.parseInt(num));
        }
        return number;
    }

    private String[] getNumberArray() {
        return text.split(getSeperator().getRegex(this));
    }

    public Seperator getSeperator() {
        if(seperator == null) {
            return createSeperator();
        }

        return this.seperator;
    }

    private Seperator createSeperator() {
        seperator = new Seperator();
        return seperator;
    }

    public boolean containsSeperator(char seperator) {
        return getSeperator().contains(seperator);
    }

    public void addSeperator(char seperator) {
        getSeperator().add(seperator);
    }

    public int sum() {
        return getNumber().sum();
    }

    public int multiply() {
        return getNumber().multiply();
    }
}
