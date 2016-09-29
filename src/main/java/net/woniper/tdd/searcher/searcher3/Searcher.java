package net.woniper.tdd.searcher.searcher3;

/**
 * Created by woniper on 2016. 9. 24..
 */
public class Searcher {
    private String text;

    public Searcher(String text) {
        if(isEmptyOrNull(text))
            throw new IllegalArgumentException(text);

        this.text = text;
    }

    private boolean isEmptyOrNull(String text) {
        return text == null || text.isEmpty();
    }

    public String search(String keyword) {
        if(isEmptyOrNull(keyword))
            throw new IllegalArgumentException(keyword);

        if(keyword.length() > text.length())
            throw new IllegalArgumentException(keyword);

        if(!text.contains(keyword))
            throw new IllegalArgumentException(keyword);

        String prefix = getPrefixText(keyword);
        String search = getSearchText(keyword);
        String suffix = getSuffixText(keyword);

        if (isPrefixNumberOrUnderscoreOrAlphabet(prefix))
            return null;

        if (isSuffixNumberOrUnderscoreOrAlphabet(suffix))
            return null;

        return makeSearchTextResult(prefix, search, suffix);
    }

    private String makeSearchTextResult(String prefix, String search, String suffix) {
        return prefix + "{" + search + "}" + suffix;
    }

    private boolean isNumberOrUnderscoreOrAlphabet(char txt) {
        return (txt >= '0' && txt <= '9') || (txt == '_')
                || (txt >= 'A' && txt <= 'Z') || (txt >= 'a' && txt <= 'z');
    }

    private boolean isPrefixNumberOrUnderscoreOrAlphabet(String prefix) {
        if(isEmptyOrNull(prefix))
            return false;

        char prefixLastChar = prefix.charAt(prefix.length() - 1);
        return isNumberOrUnderscoreOrAlphabet(prefixLastChar);
    }

    private boolean isSuffixNumberOrUnderscoreOrAlphabet(String suffix) {
        if(isEmptyOrNull(suffix))
            return false;

        char suffixLastChar = suffix.charAt(0);
        return isNumberOrUnderscoreOrAlphabet(suffixLastChar);
    }

    private String getPrefixText(String keyword) {
        return text.substring(0, text.indexOf(keyword));
    }

    private String getSearchText(String keyword) {
        int idx = text.indexOf(keyword);
        return text.substring(idx, idx + keyword.length());
    }

    private String getSuffixText(String keyword) {
        return text.substring(text.indexOf(keyword) + keyword.length(), text.length());
    }
}
