package net.woniper.tdd.test.searcher.searcher2;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Created by woniper on 2016. 9. 23..
 *
 * 테스트 메소드를 생각나는대로 모두 정의한 후 TDD 해보자. (todo처럼)
 */
public class SearcherTest {

    // 생성자 argument Text Empty String, Null 테스트
    @Test
    public void testSearcherConstructorArgumentValidation() throws Exception {
        assertSearcherConstructorArgument("");
        assertSearcherConstructorArgument(null);
    }

    private void assertSearcherConstructorArgument(String text) {
        IllegalArgumentException exception = null;

        try {
            new Searcher(text);
        } catch (Exception e) {
            exception = (IllegalArgumentException) e;
        }

        assertThat(exception, isA(IllegalArgumentException.class));
    }

    // search method keyword Empty String, Null 테스트
    @Test
    public void testSearchEmptyOrNullValidation() throws Exception {
        assertSearchKeyword("");
        assertSearchKeyword(null);
    }

    private void assertSearchKeyword(String keyword) {
        IllegalArgumentException exception = null;

        try {
            new Searcher("flex").search(keyword);
        } catch (Exception e) {
            exception = (IllegalArgumentException) e;
        }

        assertThat(exception, isA(IllegalArgumentException.class));
    }

    // search method Text와 Keyword 길이 테스트 (text가 keyword에 길이보다 보다 크거나 같아야한다)
    @Test(expected = IllegalArgumentException.class)
    public void testTextAndKeywordLengthValidation() throws Exception {
        new Searcher("1").search("12");
    }

    // search method text에 keyword 문자열이 포함되어있는지 테스트
    @Test
    public void testKeywordContainsInTextValidation() throws Exception {
        // given
        Searcher searcher = new Searcher("no a flex");

        // when
        String expected = searcher.search("flexing");

        // then
        assertNull(expected);
    }

    // search method keyword에 앞 또는 뒤에 숫자가 포함되었는지 테스트
    @Test
    public void testKeywordNumberTextValidation() throws Exception {
        assertSearchResultIsNull("0flex");
        assertSearchResultIsNull("flex0");
    }

    // search method keyword에 앞 또는 뒤에 알파벳가 포함되었는지 테스트
    @Test
    public void testKeywordAlphabetTextValidation() throws Exception {
        assertSearchResultIsNull("Aflex");
        assertSearchResultIsNull("flexB");
        assertSearchResultIsNull("aflex");
        assertSearchResultIsNull("flexb");
    }

    // search method keyword에 앞 또는 뒤에 "_"가 포함되었는지 테스트
    @Test
    public void testKeywordUnderscoreTextValidation() throws Exception {
        assertSearchResultIsNull("_flex");
        assertSearchResultIsNull("flex_");
    }

    private void assertSearchResultIsNull(String text) {
        // given
        Searcher searcher = new Searcher(text);

        // when
        String expected = searcher.search("flex");

        // then
        assertNull(expected);
    }

    // 예시로 주어진 keyword 테스트
    /**
     * [입력 --> 출력]
     * reflex --> 없음
     * yes, flex1 --> 없음
     * no, flexible --> 없음
     */
    @Test
    public void testExampleKeywordIsNull() throws Exception {
        assertSearchResultIsNull("reflex");
        assertSearchResultIsNull("yes, flex1");
        assertSearchResultIsNull("no, flexible");
    }

    // 예시로 주어진 keyword 테스트
    /**
     * [입력 --> 출력]
     * flex?  --> {flex}?
     * (flex) --> ({flex})
     * no a flex --> no a {flex}
     */
    @Test
    public void testExampleKeywordIsNotNull() throws Exception {
        assertSearchResultIsNotNull("flex?", "{flex}?");
        assertSearchResultIsNotNull("(flex)", "({flex})");
        assertSearchResultIsNotNull("no a flex", "no a {flex}");
    }

    private void assertSearchResultIsNotNull(String text, String actual) {
        // given
        Searcher searcher = new Searcher(text);

        // when
        String expected = searcher.search("flex");

        // then
        assertEquals(expected, actual);
    }
}
