package net.woniper.tdd.searcher.searcher3;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Created by woniper on 2016. 9. 24..
 * searcher2.SearcherTest 테스트 케이스 보다 좀 더 많은 테스트 케이스를 작성해보자.
 */
public class SearcherTest {

    // constructor text validation 체크
    @Test
    public void testConstructorArgumentThrownEx() throws Exception {
        assertTextValidation("");
        assertTextValidation(null);
    }

    private void assertTextValidation(String text) {
        IllegalArgumentException exception = null;

        try {
            new Searcher(text);
        } catch (Exception e) {
            exception = (IllegalArgumentException)e;
        }

        assertThat(exception, isA(IllegalArgumentException.class));
    }

    // search 메소드 keyword validation 체크
    @Test
    public void testKeywordArgumentThrownEx() throws Exception {
        assertKeywordValidation("");
        assertKeywordValidation(null);
    }

    private void assertKeywordValidation(String keyword) {
        IllegalArgumentException exception = null;

        try {
            new Searcher("no a flex").search(keyword);
        } catch (Exception e) {
            exception = (IllegalArgumentException)e;
        }

        assertThat(exception, isA(IllegalArgumentException.class));
    }

    // search 메소드 text, keyword length 체크
    @Test(expected = IllegalArgumentException.class)
    public void testSearchTextAndKeywordLengthThrownEx() throws Exception {
        new Searcher("no a flex").search("no a flexing");
    }

    // search 메소드 text prefix 테스트
    @Test
    public void testSearchPrefixThrowIsNull() throws Exception {
        assertSearchValidationIsNull("0flex");
        assertSearchValidationIsNull("01flex");
        assertSearchValidationIsNull("_flex");
        assertSearchValidationIsNull("Aflex");
        assertSearchValidationIsNull("Zflex");
        assertSearchValidationIsNull("aflex");
        assertSearchValidationIsNull("zflex");
    }

    private void assertSearchValidationIsNull(String text) {
        // given
        Searcher searcher = new Searcher(text);

        // when
        String expected = searcher.search("flex");

        // then
        assertNull(expected);
    }

    // search 메소드 text suffix 테스트
    @Test
    public void testSearchSuffixIsNull() throws Exception {
        assertSearchValidationIsNull("flex0");
        assertSearchValidationIsNull("flex10");
        assertSearchValidationIsNull("flex_");
        assertSearchValidationIsNull("flexA");
        assertSearchValidationIsNull("flexZ");
        assertSearchValidationIsNull("flexa");
        assertSearchValidationIsNull("flexz");
    }

    // search 메소드 정상 로직 테스트
    @Test
    public void testSearch() throws Exception {
        assertSearch("no a flex", "no a {flex}");
        assertSearch("test flex text", "test {flex} text");
        assertSearch("test %flex text", "test %{flex} text");
        assertSearch("test flex# text", "test {flex}# text");
        assertSearch("no a flex?", "no a {flex}?");
        assertSearch("no a flexing%", null);
        assertSearch("reflex", null);
    }

    private void assertSearch(String text, String actual) {
        // given
        Searcher searcher = new Searcher(text);

        // when
        String expected = searcher.search("flex");

        // then
        assertEquals(expected, actual);
    }

}
