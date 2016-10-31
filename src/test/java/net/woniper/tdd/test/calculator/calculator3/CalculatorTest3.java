package net.woniper.tdd.test.calculator.calculator3;

import net.woniper.tdd.calculator.calculator3.Number;
import net.woniper.tdd.calculator.calculator3.StringCalculator;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by woniper on 2016. 9. 26..
 */
public class CalculatorTest3 {

    private String defaultText = "1,2,3";
    private StringCalculator stringCalculator;

    @Before
    public void setUp() throws Exception {
        this.stringCalculator = new StringCalculator(defaultText);
    }

    // Text
    // validation
    @Test
    public void testTextValidation() throws Exception {
        assertTextConstructorValidation("");
        assertTextConstructorValidation(null);
    }

    private void assertTextConstructorValidation(String text) {
        IllegalArgumentException exception = null;
        try {
            new StringCalculator(text);
        } catch(Exception e) {
            exception = (IllegalArgumentException)e;
        }
        assertThat(exception, isA(IllegalArgumentException.class));
    }

    // get Number Object
    @Test
    public void testTextGetDefaultNumber() throws Exception {
        Number number = stringCalculator.getNumber();
        List<Integer> numbers = number.getNumbers();
        assertThat(numbers.get(0), is(1));
        assertThat(numbers.get(1), is(2));
        assertThat(numbers.get(2), is(3));
    }

    @Test
    public void testTextGetNumber() throws Exception {
        // given
        String str = "2,3,4";
        StringCalculator stringCalculator = new StringCalculator(str);

        // when
        Number number = stringCalculator.getNumber();
        List<Integer> numbers = number.getNumbers();

        // then
        assertThat(numbers.get(0), is(2));
        assertThat(numbers.get(1), is(3));
        assertThat(numbers.get(2), is(4));
    }

    @Test
    public void testTextAddSeperatorAndGetNumber() throws Exception {
        // given
        String str = "2,3|5";
        StringCalculator stringCalculator = new StringCalculator(str);
        stringCalculator.addSeperator('|');

        // when
        Number number = stringCalculator.getNumber();
        List<Integer> numbers = number.getNumbers();

        // then
        assertThat(numbers.get(0), is(2));
        assertThat(numbers.get(1), is(3));
        assertThat(numbers.get(2), is(5));
    }

    // get Seperator Object
    @Test
    public void testTextGetDefaultSeperator() throws Exception {
        assertTrue(stringCalculator.containsSeperator(','));
    }

    @Test
    public void testSetSeperatorAndContainsSeperator() throws Exception {
        // given
        StringCalculator stringCalculator = new StringCalculator("1,2|3");
        stringCalculator.addSeperator('|');

        // when
        boolean condition1 = stringCalculator.containsSeperator(',');
        boolean condition2 = stringCalculator.containsSeperator('|');

        // then
        assertTrue(condition1);
        assertTrue(condition2);
    }

    // 숫자만 포함 되어 있는지 체크
    @Test
    public void testOnlyNumberSum() throws Exception {
        // given
        StringCalculator stringCalculator = new StringCalculator("100");

        // when
        int sum = stringCalculator.sum();

        // then
        assertThat(100, is(sum));
    }

    @Test
    public void testSum() throws Exception {
        // when
        int sum = this.stringCalculator.sum();

        // then
        assertThat(6, is(sum));
    }

    @Test
    public void testOnlyNumberMultiply() throws Exception {
        // given
        StringCalculator stringCalculator = new StringCalculator("200");

        // when
        int multiply = stringCalculator.multiply();

        // then
        assertThat(200, is(multiply));
    }

    @Test
    public void testDefaultMultiply() throws Exception {
        // when
        int multiply = this.stringCalculator.multiply();

        // then
        assertThat(6, is(multiply));
    }

    @Test
    public void testMultiply() throws Exception {
        // given
        StringCalculator stringCalculator = new StringCalculator("1,4,10");

        // when
        int multiply = stringCalculator.multiply();

        // then
        assertThat(40, is(multiply));
    }
}
