package net.woniper.tdd.test.searcher.searcher1;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Created by woniper on 2016. 9. 19..
 */
public class SearcherTest {

    private final String SEARCH_TEXT = "flex";

//    flex?  --> {flex}?
//    (flex) --> ({flex})
//    reflex --> 없음
//    yes, flex1 --> 없음
//    no, flexible --> 없음
//    no a flex --> no a {flex}

    @Test
    public void testSearchText() throws Exception {
        assertSearchText("flex?", "{flex}?");
        assertSearchText("(flex)", "({flex})");
        assertSearchText("no a flex", "no a {flex}");
    }

    private void assertSearchText(String keyword, String expected) {
        // given
        Searcher searcher = new Searcher(keyword);

        // when
        String actual = searcher.search(SEARCH_TEXT);

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void testSuffixQuestionText() throws Exception {
        // given
        String expected = "{flex}?";
        Searcher searcher = new Searcher("flex?");

        // when
        String actual = searcher.search(SEARCH_TEXT);

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void testSearchTextIllegalArgumentThrownEx() throws Exception {
        assertSearchIllegalArgumentEx("");
        assertSearchIllegalArgumentEx(null);
    }

    @Test
    public void testGetSearchTextIllegalArgumentThrownEx() throws Exception {
        assertGetSearchTextIllegalArgumentEx("");
        assertGetSearchTextIllegalArgumentEx(null);
    }

    @Test
    public void testPrefixAndSuffixTextIsNull() throws Exception {
        // test prefix
        assertPrefixOrSuffixTextIsNull("0flex");
        assertPrefixOrSuffixTextIsNull("_flex");
        assertPrefixOrSuffixTextIsNull("Aflex");

        // test suffix
        assertPrefixOrSuffixTextIsNull("flex1");
        assertPrefixOrSuffixTextIsNull("flex_");
        assertPrefixOrSuffixTextIsNull("flexA");
    }

    private void assertPrefixOrSuffixTextIsNull(String searchText) {
        // given
        Searcher searcher = new Searcher(searchText);

        // when
        String actual = searcher.search(SEARCH_TEXT);

        // then
        assertNull(actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSearchTextLength() throws Exception {
        // given
        Searcher searcher = new Searcher("flex");

        // when
        searcher.search("flex123");
    }

    @Test
    public void testDefaultText() throws Exception {
        // given
        Searcher searcher = new Searcher("flex?");
        String expected = "{flex}?";

        // when
        String actual = searcher.search(SEARCH_TEXT);

        // then
        assertEquals(expected, actual);
    }

    private void assertSearchIllegalArgumentEx(String text) {
        IllegalArgumentException exception = null;
        try {
            new Searcher(text);
        } catch (Exception e) {
            exception = (IllegalArgumentException)e;
        }
        assertThat(exception, isA(IllegalArgumentException.class));
    }

    private void assertGetSearchTextIllegalArgumentEx(String text) {
        IllegalArgumentException exception = null;
        try {
            new Searcher("test").search(text);
        } catch (Exception e) {
            exception = (IllegalArgumentException)e;
        }
        assertThat(exception, isA(IllegalArgumentException.class));
    }
}