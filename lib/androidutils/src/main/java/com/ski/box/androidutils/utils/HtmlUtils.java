package com.ski.box.androidutils.utils;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * <pre>
 *     time   : 2020/08/17
 *     desc   :
 * </pre>
 */
public class HtmlUtils {
    /**
     * html转义
     *
     * @param input HTML
     * @return String
     */
    public static String escapeHtml(final String input) {
        return StringEscapeUtils.escapeHtml4(input);
    }

    /**
     * html反转义
     *
     * @param input String
     * @return HTML
     */
    public static String unescapeHtml(final String input) {
        return StringEscapeUtils.unescapeHtml4(input);
    }
}
