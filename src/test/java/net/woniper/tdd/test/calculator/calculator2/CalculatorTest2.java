package net.woniper.tdd.test.calculator.calculator2;

import net.woniper.tdd.calculator.calculator2.StringCalculator;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by woniper on 2016. 9. 26..
 */
public class CalculatorTest2 {

    private StringCalculator defaultCalculator;
    private StringCalculator calculator;

    @Before
    public void setUp() throws Exception {
        this.defaultCalculator = new StringCalculator();
        this.calculator = new StringCalculator(new char[] {',', '\n', '|'});
    }

    // constructor argument length이 0인 경우 thrown ex
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorInjectSeperatorIsEmpty() throws Exception {
        // given
        char[] seperator = {};

        // when, then
        StringCalculator calculator = new StringCalculator(seperator);
    }

    // seperator validation (숫자는 구분자로 불가능)
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorSeperatorNotNumber() throws Exception {
        // given
        char[] seperators = {',', '2'};

        // when, then
        StringCalculator calculator = new StringCalculator(seperators);
    }

    // constructor argument null은 기본 구분자로 셋업
    @Test
    public void testConstructorInjectSeperatorIsNull() throws Exception {
        // given
        StringCalculator calculator = new StringCalculator(null);

        // when
        boolean condition = calculator.isContainsSeperator(',');

        // then
        assertTrue(condition);
    }

    // add method empty string, null 체크
    @Test
    public void testAddEmptyStringAndNull() throws Exception {
        assertAddEmptyStringAndNull("");
        assertAddEmptyStringAndNull(null);
    }

    private void assertAddEmptyStringAndNull(String text) {
        IllegalArgumentException exception = null;

        try {
            defaultCalculator.add(text);
        } catch (Exception e) {
            exception = (IllegalArgumentException)e;
        }

        assertThat(exception, isA(IllegalArgumentException.class));
    }

    // add method default seperator result5 테스트
    @Test
    public void testAddResult5() throws Exception {
        assertAddResult(defaultCalculator, "1,2,1,1", 5);
    }

    private void assertAddResult(StringCalculator stringCalculator, String text, int expected) {
        int actual = stringCalculator.add(text);
        assertEquals(expected, actual);
    }

    // add method default seperator result13 테스트
    @Test
    public void testAddResult13() throws Exception {
        assertAddResult(defaultCalculator, "5,3,1,4", 13);
    }

    // add method default seperator result100 테스트
    @Test
    public void testAddResult100() throws Exception {
        assertAddResult(defaultCalculator, "100", 100);
    }

    // add method default seperator result103 테스트
    @Test
    public void testAddResult103() throws Exception {
        assertAddResult(defaultCalculator, "103", 103);
    }

    // add method constructor inject seperator result5 테스트
    @Test
    public void testSeperatorAddResult5() throws Exception {
        assertAddResult(calculator, "1,2\n1|1", 5);
    }

    // add method constructor inject seperator result13 테스트
    @Test
    public void testSeperatorAddResult13() throws Exception {
        assertAddResult(calculator, "1,2\n3|4,3", 13);
    }

    // add method constructor inject seperator result100 테스트
    @Test
    public void testSeperatorAddResult100() throws Exception {
        assertAddResult(calculator, "100", 100);
    }

    // isContainsSeperator method 테스트
    @Test
    public void testIsContainsSeperator() throws Exception {
        // given
        char[] seperators = {',', '\n', '+'};
        StringCalculator calculator = new StringCalculator(seperators);

        // when, then
        assertTrue(calculator.isContainsSeperator(','));
        assertTrue(calculator.isContainsSeperator('\n'));
        assertTrue(calculator.isContainsSeperator('+'));
        assertFalse(calculator.isContainsSeperator('*'));
        assertFalse(calculator.isContainsSeperator('$'));
    }

    // add method seperator 유효성 테스트
    @Test
    public void testAddValidSeperator() throws Exception {
        assertAddValidSeperator(calculator, "1.2.3");
        assertAddValidSeperator(calculator, "1&2|3");
        assertAddValidSeperator(calculator, "1,2^3");
        assertAddValidSeperator(calculator, "1\n2@3");
    }

    private void assertAddValidSeperator(StringCalculator calculator, String addText) {
        IllegalArgumentException exception = null;
        try {
            calculator.add(addText);
        } catch (Exception e) {
            exception = (IllegalArgumentException)e;
        }
        assertThat(exception, isA(IllegalArgumentException.class));
    }
}
