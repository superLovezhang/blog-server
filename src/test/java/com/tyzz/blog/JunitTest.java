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
        String htmlStr = "<div>1211</div><span>我还是非常爱你的</span>";
        System.out.println(StringUtils.htmlToPlainText(htmlStr));
    }
}
