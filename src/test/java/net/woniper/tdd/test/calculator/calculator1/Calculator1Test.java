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

    private Calculator calculator;

    @Before
    public void setUp() throws Exception {
        calculator = new Calculator();
    }

    // 더하기 테스트
    @Test
    public void testAddResult3() throws Exception {
        // given
        String text = "1,2";
        int actual = 3;

        // when
        int expected = calculator.add(text);

        // then
        assertEquals(actual, expected);
    }

    @Test
    public void testAddResult5() throws Exception {
        // given
        String text = "3,2";
        int actual = 5;

        // when
        int expected = calculator.add(text);

        // then
        assertEquals(actual, expected);
    }

    @Test
    public void testAddResult100() throws Exception {
        // given
        String text = "100";


    }

    @Test
    public void testAddSpecialCharacterIllegalArgThrownEx() throws Exception {
        assertAddIllegalArgumentThrownEx("[1,2,3]");
        assertAddIllegalArgumentThrownEx("[1.2.3]");
        assertAddIllegalArgumentThrownEx("1.2.3");
        assertAddIllegalArgumentThrownEx("1|2|3");
        assertAddIllegalArgumentThrownEx("1&2&3");
        assertAddIllegalArgumentThrownEx("(1,2,3)");
    }

    @Test
    public void testAddIllegalArgThrownEx() throws Exception {
        assertAddIllegalArgumentThrownEx("");
        assertAddIllegalArgumentThrownEx(null);
    }

    private void assertAddIllegalArgumentThrownEx(String text) {
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
