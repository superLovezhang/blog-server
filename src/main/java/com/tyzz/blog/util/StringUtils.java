package com.tyzz.blog.util;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-10-09 10:47
 */
public class StringUtils {
    private static final Pattern coverPattern = Pattern.compile("src=\"(.+?)\"");

    public static String camelToUnderscore(String camelStr) {
        if (isEmpty(camelStr)) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < camelStr.length(); i++) {
            char currentChar = camelStr.charAt(i);
            if (Character.isUpperCase(currentChar)) {
                result.append('_');
                result.append(Character.toLowerCase(currentChar));
                continue;
            }
            result.append(currentChar);
        }
        return result.toString();
    }

    public static String underscoreToCamel(String underscoreStr) {
        if (isEmpty(underscoreStr)) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        boolean turnUppercase = false;
        for (int i = 0; i < underscoreStr.length(); i++) {
            char currentChar = underscoreStr.charAt(i);
            if (currentChar == '_') {
                turnUppercase = true;
                continue;
            }
            if (turnUppercase) {
                result.append(Character.toUpperCase(currentChar));
                turnUppercase = false;
                continue;
            }
            result.append(currentChar);
        }
        return result.toString();
    }

    public static String htmlToPlainText(String html) {
        return html.replaceAll("<\\/?.+?\\/?>", "");
    }

    public static String pickCoverFromHtml(String html) {
        Matcher matcher = coverPattern.matcher(html);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static String generateRandomCode(int count) {
        Random random = new Random();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < count; i++) {
            result.append(random.nextInt(10));
        }
        return result.toString();
    }
}
