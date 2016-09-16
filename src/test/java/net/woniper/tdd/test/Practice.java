package net.woniper.tdd.test;

import org.junit.Test;

/**
 * Created by woniper on 2016. 8. 22..
 */
public class Practice {
    @Test
    public void name() throws Exception {
        String text = "abckeyabc123";
        String keyword = "keyaa";

        int index = text.indexOf(keyword);
        int length = keyword.length();

//        System.out.println(index);
//        System.out.println(length);
//        System.out.println(text.substring(index, index + length));

        char[] chars = keyword.toCharArray();
        int beforeIndex = 0;
        int lastIndex = 0;
        for (char aChar : chars) {
            System.out.println(text.indexOf(aChar, index));
        }


    }
}
