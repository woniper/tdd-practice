package net.woniper.tdd.test.searcher.searcher2;

/**
 * Created by woniper on 2016. 9. 23..
 */
public class Searcher {

    private String text;

    public Searcher(String text) {
        if(isEmptyOrNull(text))
            throw new IllegalArgumentException();

        this.text = text;
    }

    public String search(String keyword) {
        if(isEmptyOrNull(keyword))
            throw new IllegalArgumentException();

        if(isKeywordGreatThenText(keyword))
            throw new IllegalArgumentException();

        if(!isKeywordContainsInText(keyword))
            return null;

        String prefix = getTextPrefix(keyword);
        String searchText = getSearchText(keyword);
        String suffix = getTextSuffix(keyword);

        if(containsPrefixNumberOrAlphabetOrUnderscore(prefix))
            return null;

        if(containsSuffixNumberOrAlphabetOrUnderscore(suffix))
            return null;

        return makeSearchText(prefix, searchText, suffix);
    }

    private String makeSearchText(String prefix, String searchText, String suffix) {
        return prefix + "{" + searchText + "}" + suffix;
    }

    private String getTextPrefix(String keyword) {
        return text.substring(0, text.indexOf(keyword));
    }

    private String getSearchText(String keyword) {
        return text.substring(text.indexOf(keyword), text.indexOf(keyword) + keyword.length());
    }

    private String getTextSuffix(String keyword) {
        return text.substring(text.indexOf(keyword) + keyword.length(), text.length());
    }

    private boolean containsSuffixNumberOrAlphabetOrUnderscore(String suffix) {
        return !isEmptyOrNull(suffix) && containsNumberOrAlphabetOrUnderscore(suffix.substring(0, 1));

    }

    private boolean containsPrefixNumberOrAlphabetOrUnderscore(String prefix) {
        if(isEmptyOrNull(prefix))
            return false;

        int prefixLength = prefix.length();
        return containsNumberOrAlphabetOrUnderscore(prefix.substring(prefixLength - 1, prefixLength));
    }

    private boolean containsNumberOrAlphabetOrUnderscore(String text) {
        return text.matches("[0-9a-zA-Z]") || "_".equals(text);
    }

    private boolean isKeywordContainsInText(String keyword) {
        return text.contains(keyword);
    }

    private boolean isEmptyOrNull(String text) {
        return text == null || text.isEmpty();
    }

    private boolean isKeywordGreatThenText(String keyword) {
        return keyword.length() > text.length();
    }
}
