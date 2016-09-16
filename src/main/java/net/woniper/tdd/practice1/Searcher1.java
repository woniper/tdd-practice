package net.woniper.tdd.practice1;

/**
 * Created by woniper on 2016. 8. 22..
 * TDD 없이 바로 요구 사항 코딩
 */
public class Searcher1 {

    private String text;
    private String regexTest = "[a-z]";

    /**
     * 기본 생성자에 null로 생성자를 다시 호출하는게 좋을까?
     * 아니면 아예 기본 생성자를 만들지 않는게 좋을까?
     * 요구 사항을 다시 생각해보자.
     */
    public Searcher1() {
        this(null);
    }

    public Searcher1(String text) {
        setText(text);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void print(String keyword) {
        String selectKeyword = getKeyword(keyword);
    }

    private String getKeyword(String keyword) {
        String text = getText();

        if(null == text || null == keyword) {
            throw new NullPointerException();
        }

        if(keyword.length() > text.length()) {
            throw new IllegalArgumentException("NotValidText");
        }

        int index = text.indexOf(keyword);
//        int lastIndex = text.lastIndexOf();

        if(index < 0) {
            throw new IllegalArgumentException("NotValidText");
        }

        return null;
    }

    public String getText() {
        return text;
    }
}
