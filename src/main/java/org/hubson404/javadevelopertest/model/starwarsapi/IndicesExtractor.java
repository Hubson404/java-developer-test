package org.hubson404.javadevelopertest.model.starwarsapi;

public class IndicesExtractor {

    public static long getIndexFromUrl(String url) {
        String[] splitUrl = url.split("/");
        String index = splitUrl[splitUrl.length - 1];
        return Long.parseLong(index);
    }
}
