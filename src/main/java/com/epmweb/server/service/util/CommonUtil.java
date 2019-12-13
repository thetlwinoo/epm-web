package com.epmweb.server.service.util;

public class CommonUtil {
    public static boolean isBlank(String param) {
        return param.isEmpty() || param.trim().equals("");
    }

    public static String handleize(String text) {
        String _handle =  text.toLowerCase()
            .replaceAll("\\s+", "-")
            .replaceAll("[^\\w\\-]+", "")
            .replaceAll("\\-\\-+", "-")
            .replaceAll("^-+", "")
            .replaceAll("-+$", "");

        return _handle;
    }
}
