package com.tyzz.blog.util;

import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.tyzz.blog.constant.BlogConstant.DEFAULT_DUMMY_ELEMENT;
import static com.tyzz.blog.constant.BlogConstant.SPLIT_CHAR;

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

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String generateRandomCode(int count) {
        Random random = new Random();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < count; i++) {
            result.append(random.nextInt(10));
        }
        return result.toString();
    }

    public static String generateRedisKey(String prefix, String ...params) {
        StringBuilder key = new StringBuilder(prefix);
        for (String param : params) {
            key.append(SPLIT_CHAR);
            key.append(param);
        }
        return key.toString();
    }
    public static String generateRedisKey(String prefix, Long ...params) {
        StringBuilder key = new StringBuilder(prefix);
        for (Long param : params) {
            key.append(SPLIT_CHAR);
            key.append(param.toString());
        }
        return key.toString();
    }

    public static String[] splitRedisKey(String s) {
        return s.split(SPLIT_CHAR);
    }

    public static <T> Set<T> removeDummyElement(Set<T> elements) {
        return elements.stream()
                .filter(e -> !DEFAULT_DUMMY_ELEMENT.equals(e))
                .collect(Collectors.toSet());
    }

    public static boolean isNotBlank(String value) {
        return com.baomidou.mybatisplus.core.toolkit.StringUtils.isNotBlank(value);
    }

    public static boolean isBlank(String result) {
        return com.baomidou.mybatisplus.core.toolkit.StringUtils.isBlank(result);
    }
}
