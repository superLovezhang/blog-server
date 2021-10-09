package com.tyzz.blog;

import com.tyzz.blog.util.StringUtils;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-10-09 10:57
 */
public class JunitTest {
    public static void main(String[] args) {
        String camelStr = "userInformationData";
        String underscoreStr = "user_information_data";
        System.out.println(StringUtils.camelToUnderscore(camelStr));
        System.out.println(StringUtils.underscoreToCamel(underscoreStr));
    }
}
