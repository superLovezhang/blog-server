package com.tyzz.blog.util;

import com.tyzz.blog.exception.BlogException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-10-11 12:29
 */
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
}
