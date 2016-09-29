package net.woniper.tdd.test.calculator.calculator1;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by woniper on 2016. 9. 28..
 */
public class Calculator1Test {

    private Calculator defaultCalculator;
    private Calculator calculator;

    @Before
    public void setUp() throws Exception {
        defaultCalculator = new Calculator();
        calculator = new Calculator(new String[]{",", "\n"});
    }

    // 더하기 테스트
    @Test
    public void testAddResult3() throws Exception {
        // given
        String text = "1,2";
        int expected = 3;

        // when
        int actual = defaultCalculator.add(text);

        // then
        assertEquals(actual, expected);
    }

    @Test
    public void testAddResult5() throws Exception {
        // given
        String text = "3,2";
        int expected = 5;

        // when
        int actual = defaultCalculator.add(text);

        // then
        assertEquals(actual, expected);
    }

    @Test
    public void testAddResult100() throws Exception {
        // given
        String text = "100";
        int expected = 100;

        // when
        int actual = defaultCalculator.add(text);

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void testAddSpecialCharacterIllegalArgThrownEx() throws Exception {
        assertAddIllegalArgumentThrownEx("[1,2,3]", defaultCalculator);
        assertAddIllegalArgumentThrownEx("[1.2.3]", defaultCalculator);
        assertAddIllegalArgumentThrownEx("1.2.3", defaultCalculator);
        assertAddIllegalArgumentThrownEx("1|2|3", defaultCalculator);
        assertAddIllegalArgumentThrownEx("1&2&3", defaultCalculator);
        assertAddIllegalArgumentThrownEx("(1,2,3)", defaultCalculator);

        assertAddIllegalArgumentThrownEx("[1\n2\n3]", calculator);
        assertAddIllegalArgumentThrownEx("[1.2.3]", calculator);
        assertAddIllegalArgumentThrownEx("1.2.3", calculator);
        assertAddIllegalArgumentThrownEx("1|2|3", calculator);
        assertAddIllegalArgumentThrownEx("1&2&3", calculator);
        assertAddIllegalArgumentThrownEx("(1\n2\n3)", calculator);
        assertAddIllegalArgumentThrownEx("1\n2\n3", calculator);
    }

    @Test
    public void testAddIllegalArgThrownEx() throws Exception {
        assertAddIllegalArgumentThrownEx("", defaultCalculator);
        assertAddIllegalArgumentThrownEx(null, defaultCalculator);

        assertAddIllegalArgumentThrownEx("", calculator);
        assertAddIllegalArgumentThrownEx(null, calculator);
    }

    private void assertAddIllegalArgumentThrownEx(String text, Calculator calculator) {
        IllegalArgumentException exception = null;

        try {
            calculator.add(text);
        } catch (Exception e) {
            exception = (IllegalArgumentException)e;
        }
        assertThat(exception, isA(IllegalArgumentException.class));
    }

    // 빼기 테스트

    // 곱하기 테스트

    // 나누기 테스트

}
