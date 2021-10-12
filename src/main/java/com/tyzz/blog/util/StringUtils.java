package com.tyzz.blog.util;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-10-09 10:47
 */
public class StringUtils {
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

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
