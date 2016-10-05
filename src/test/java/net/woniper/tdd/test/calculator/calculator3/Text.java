package net.woniper.tdd.test.calculator.calculator3;

/**
 * Created by woniper on 2016. 10. 5..
 */
public class Text {

    private String text;

    public Text(String text) {
        if(isEmptyOrNull(text))
            throw new IllegalArgumentException();

        this.text = text;
    }

    private boolean isEmptyOrNull(String text) {
        return text == null || text.isEmpty();
    }

    public Number getNumber() {
        return createNumber();
    }

    private Number createNumber() {
        Number number = new Number();

        String[] split = getNumberArray();
        for (String s : split) {
            number.addNumber(Integer.parseInt(s));
        }
        return number;
    }

    private String[] getNumberArray() {
        return text.split(",");
    }

    public Seperator getSeperator() {
        return createSeperator();
    }

    private Seperator createSeperator() {
        Seperator seperator = new Seperator();
        return seperator;
    }
}
