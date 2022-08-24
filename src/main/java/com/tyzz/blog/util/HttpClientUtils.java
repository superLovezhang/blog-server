package com.tyzz.blog.util;

import com.tyzz.blog.exception.BlogException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Objects;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-10-11 12:29
 */
@Slf4j
public class HttpClientUtils {
    public static String get(String urlParam) {
        try(CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(new HttpGet(urlParam));
        ) {
            HttpEntity entity = response.getEntity();
            String content = readStream(entity.getContent());
            System.out.println(content);
            EntityUtils.consume(entity);
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BlogException(e.getMessage());
        }
    }

    public static String readStream(InputStream stream) throws IOException {
        if (stream == null) {
            return "";
        }
        try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] bytes = new byte[1024];
            int len = -1;
            while ((len = stream.read(bytes)) != -1) {
                out.write(bytes, 0, len);
            }
            return out.toString();
        }
    }

    public static void main(String[] args) {
        String eduCookie = getEduCookie();
        if (StringUtils.isEmpty(eduCookie)) {
            throw new BlogException("签到cookie获取失败");
        }
        eduCheckIn(eduCookie);
    }

    /**
     * 教育签到
     * @param eduCookie 教育cookie
     */
    public static void eduCheckIn(String eduCookie) {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod("https://mps.zocedu.com/corona/submitHealthCheck/submit");
        postMethod.addRequestHeader("Accept", "*/*");
        postMethod.addRequestHeader("Accept-Encoding", "gzip, deflate, br");
        postMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36 MicroMessenger/7.0.9.501 NetType/WIFI MiniProgramEnv/Windows WindowsWechat");
        postMethod.addRequestHeader("Host", "mps.zocedu.com");
        postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        postMethod.addRequestHeader("Cookie", eduCookie);
        NameValuePair[] params = {
                new NameValuePair("checkPlace", "江苏省-南京市-江宁区"),
                new NameValuePair("contactMethod", "16607925248"),
                new NameValuePair("teacher", null),
                new NameValuePair("temperature", "36"),
                new NameValuePair("isCohabitFever", "否"),
                new NameValuePair("isLeavePalce", ""),
                new NameValuePair("beenPlace", ""),
                new NameValuePair("isContactNcov", ""),
                new NameValuePair("livingPlace", "江西省-九江市-瑞昌市"),
                new NameValuePair("livingPlaceDetail", "祥明家园"),
                new NameValuePair("name1", ""),
                new NameValuePair("relation1", ""),
                new NameValuePair("phone1", ""),
                new NameValuePair("name2", ""),
                new NameValuePair("relation2", ""),
                new NameValuePair("phone2", ""),
                new NameValuePair("remark", ""),
                new NameValuePair("extraInfo", "[]"),
                new NameValuePair("healthStatus", "z"),
                new NameValuePair("emergencyContactMethod", "[]"),
                new NameValuePair("checkPlacePoint", "江苏省-南京市-江宁区"),
                new NameValuePair("checkPlace", "124%2C37"),
                new NameValuePair("checkPlaceDetail", ""),
                new NameValuePair("checkPlaceCountry", ""),
                new NameValuePair("checkPlaceProvince", "江苏省"),
                new NameValuePair("checkPlaceCity", "南京市"),
                new NameValuePair("checkPlaceArea", "江宁区")
        };
        try {
            postMethod.addParameters(params);
            int code = httpClient.executeMethod(postMethod);

            log.info("教育签到接口响应：" + postMethod.getResponseBodyAsString());

            if (isSuccessFully(code)) {
                log.info("教育签到成功");
            }
        } catch (Exception e) {
            log.error("调用教育签到接口异常", e);
            System.out.println("教育签到失败");
        }
    }

    /**
     * 获取教育签到cookie
     * @return cookie值
     */
    public static String getEduCookie() {
        try {
            HttpClient httpClient = new HttpClient();
            long timestamp = new Date().getTime();
            GetMethod getMethod = new GetMethod("https://mps.zocedu.com/corona/submitHealthCheck?schoolCode=10514&userName=&openId=ox4uH1HYdaBIk05KaMRMEoiAcA2w&runPlatform=1&timestamp=" + timestamp);
            getMethod.addRequestHeader("Accept", "*/*");
            getMethod.addRequestHeader("Accept-Encoding", "gzip, deflate, br");
            getMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36 MicroMessenger/7.0.9.501 NetType/WIFI MiniProgramEnv/Windows WindowsWechat");
            getMethod.addRequestHeader("Host", "mps.zocedu.com");
            int code = httpClient.executeMethod(getMethod);

            if (isSuccessFully(code)) {
                Header cookieHeader = getMethod.getResponseHeader("Set-Cookie");
                if (Objects.nonNull(cookieHeader)) {
                    // JSESSIONID=FE00681AD8185DFE1CA239D08BB55B57; Path=/; Secure; HttpOnly
                    String[] splitCookie = cookieHeader.getValue().split(";");
                    if (splitCookie.length > 1) {
                        return splitCookie[0];
                    }
                }
            }
        } catch (Exception e) {
            log.error("调用获取教育cookie接口异常", e);
        }
        return "";
    }

    public static boolean isSuccessFully(int code) {
        return code == 200;
    }
}
