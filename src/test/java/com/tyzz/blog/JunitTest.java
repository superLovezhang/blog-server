package com.tyzz.blog;

import com.tyzz.blog.util.StringUtils;
import org.junit.jupiter.api.Test;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-10-09 10:57
 */
public class JunitTest {
    @Test
    public void test1() {
        System.out.println(StringUtils.pickCoverFromHtml("</div></div> <div class=\"card-warp d-bg-card d-border-radius mt10\" data-v-8fec359c data-v-aba4f81a><article class=\"d-hide\" data-v-8fec359c data-v-aba4f81a><p><img src=\"https://xdlumia.oss-cn-beijing.aliyuncs.com//blog/images/2021/10/1634395314890.png\" alt=\"image.png\"></p><blockquote><p>10 月 14 日晚间，微软旗下的领（LinkedIn）发布公告表示，将关闭其在中国运营的职业社交网站。这标志着最后一家在中国公开运营的美国主要社交媒体网站退出中国。</p></blockquote>]"));
    }
}
