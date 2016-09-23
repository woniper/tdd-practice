package net.woniper.tdd.test.searcher.searcher1;

/**
 * Created by woniper on 2016. 9. 19..
 */
public class Searcher1 {

    private String text;

    public Searcher1(String text) {
        if(!validText(text))
            throw new IllegalArgumentException();

        this.text = text;
    }

    public String search(String searchKeyword) {
        if(!validText(searchKeyword) || assertTextAndKeywordLength(searchKeyword))
            throw new IllegalArgumentException();

        if(!this.text.contains(searchKeyword))
            return null;

        int firstIndex = text.indexOf(searchKeyword);
        int textIndex = firstIndex + searchKeyword.length();
        int lastIndex = text.length();

        String prefix = text.substring(0, firstIndex);
        String keyword = text.substring(firstIndex, textIndex);
        String suffix = text.substring(textIndex, lastIndex);

        if(assertPrefixText(prefix) || assertSuffixText(suffix))
            return null;

        return prefix + "{" + keyword + "}" + suffix;
    }

    private boolean assertTextAndKeywordLength(String searchKeyword) {
        return searchKeyword.length() > text.length();
    }

    private boolean assertSuffixText(String suffix) {
        if(!validText(suffix))
            return false;

        int suffixLength = suffix.length();
        String suffixLastText = suffix.substring(suffixLength - 1, suffixLength);
        return suffixLastText.matches("[0-9a-zA-Z]") || "_".equals(suffixLastText);
    }

    private boolean assertPrefixText(String prefix) {
        if(!validText(prefix))
            return false;

        int prefixLength = prefix.length();
        String prefixLastText = prefix.substring(prefixLength - 1, prefixLength);
        return prefixLastText.matches("[0-9a-zA-Z]") || "_".equals(prefixLastText);
    }

    private boolean validText(String text) {
        return text != null && !text.isEmpty();
    }
}
