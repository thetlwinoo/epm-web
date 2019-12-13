package com.epmweb.server.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";
    
    public static final String SYSTEM_ACCOUNT = "system";
    public static final String DEFAULT_LANGUAGE = "en";
    public static final String ANONYMOUS_USER = "anonymoususer";
    public static final String PAYPAL_CLIENT_ID = "AfcFPXfelT4JVXiooBdxmYOCLTuHyPl-rMS9k__pU8IIySV3pzqG46WLDcuwQox4t2wFnCaFGS9PbkTB";
    public static final String PAYPAL_CLIENT_SECRET = "EIMnEf-B29pks8RkMSy6HbWGeTKIHSP7PRcYGUfD8d3KMzMT9L6GElDN8ZdJzqM2xiBHWSvchInfN0vT";

    private Constants() {
    }
}
