package net.woniper.tdd.test.searcher.searcher2;

import org.junit.Test;

/**
 * Created by woniper on 2016. 9. 23..
 *
 * 테스트 메소드를 생각나는대로 모두 정의해보자. (todo처럼)
 */
public class SearcherTest {

    // 생성자 argument Text Empty String, Null 테스트
    @Test
    public void testSearcherConstructorArgumentValidation() throws Exception {
    }

    // search method keyword Empty String, Null 테스트
    @Test
    public void testSearchEmptyOrNullValidation() throws Exception {
    }

    // search method Text와 Keyword 길이 테스트 (text가 keyword에 길이보다 보다 크거나 같아야한다)
    @Test
    public void testTextAndKeywordLengthValidation() throws Exception {
    }

    // search method keyword에 앞 또는 뒤에 숫자가 포함되었는지 테스트
    @Test
    public void testKeywordNumberTextValidation() throws Exception {
    }

    // search method keyword에 앞 또는 뒤에 알파벳가 포함되었는지 테스트
    @Test
    public void testKeywordAlpahbetTextValidation() throws Exception {
    }

    // search method keyword에 앞 또는 뒤에 "_"가 포함되었는지 테스트
    @Test
    public void testKeywordUnderscoreTextValidation() throws Exception {
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
    }
}
