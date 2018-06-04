package com.manas.uss.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author manasranjan
 * utility class for validating url
 */
public class UrlValidator {
	
	//regex to validate a url link
    private static final String URL_REGEX = "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$";

    //pattern formed of the regex
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

    private UrlValidator() {
    }

    /**
     * @param original url
     * @return true/false on validation
     */
    public static boolean validateURL(String url) {
        Matcher m = URL_PATTERN.matcher(url);
        return m.matches();
    }
}
