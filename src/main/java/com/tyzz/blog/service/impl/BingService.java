package com.tyzz.blog.service.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyzz.blog.entity.dto.BasePageDTO;
import com.tyzz.blog.entity.dto.BingDTO;
import com.tyzz.blog.exception.BlogException;
import com.tyzz.blog.util.HttpClientUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-10-11 12:26
 */
@Service
public class BingService {
    private static final String BING_WALL_PAPER_URL_PATTERN = "https://cn.bing.com/HPImageArchive.aspx?format=js&idx=%d&n=%d&mkt=zh-CN";
    private static final String TODAY_WALL_PAPER = "https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=zh-CN";
    private static final int MAX_REQUEST_LENGTH = 8;
    @Resource
    private ObjectMapper om;

    public ArrayList<Object> list(BasePageDTO pageDTO) {
        ArrayList<Object> result = new ArrayList<>();
        try {
            int page = pageDTO.getPage(),
                    size = pageDTO.getSize(),
                    requestCount = (int) Math.ceil(size / (MAX_REQUEST_LENGTH * 1.0)),
                    requestSize = MAX_REQUEST_LENGTH,
                    requestStart = (page - 1) * size;
            for (int i = 1; i <= requestCount; i++,
                    requestSize = MAX_REQUEST_LENGTH,
                    requestStart += MAX_REQUEST_LENGTH,
                    size -= MAX_REQUEST_LENGTH
            ) {
                if (size <= MAX_REQUEST_LENGTH) {
                    requestSize = size;
                }
                String url = String.format(BING_WALL_PAPER_URL_PATTERN, requestStart, requestSize);
                //万恶的String 自动给json两头加上双引号
                om.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
                BingDTO dto = om.readValue(HttpClientUtils.get(url), BingDTO.class);
                result.addAll(dto.getImages());
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new BlogException(e.getMessage());
        }
        return result;
    }

    public BingDTO getTodayPaper() throws JsonProcessingException {
        return om.readValue(HttpClientUtils.get(TODAY_WALL_PAPER), BingDTO.class);
    }
}
