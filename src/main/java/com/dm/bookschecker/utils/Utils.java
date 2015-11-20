package com.dm.bookschecker.utils;

import java.io.File;

public final class Utils {

    private Utils() {
    }

    public static String createPath(Object... objects) {
        StringBuilder sb = new StringBuilder();
        for (Object obj : objects) {
            sb.append(obj.toString()).append(File.separator);
        }
        return sb.toString();
    }

    public static int getStartPage(String previewPages){
        String[] pages = previewPages.split("-");
        if (pages.length < 2) {
            throw new IllegalArgumentException();
        }
        return Integer.parseInt(pages[0]);
    }

    public static int getEndPage(String previewPages){
        String[] pages = previewPages.split("-");
        if (pages.length < 2) {
            throw new IllegalArgumentException();
        }
        return Integer.parseInt(pages[1]);
    }
}
