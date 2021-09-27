package com.tyzz.blog.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyzz.blog.common.Result;
import com.tyzz.blog.enums.ResponseCode;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-09-27 11:03
 */
public class ResponseUtils {
    public static void unauthenticatedResponse(HttpServletResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(Result.result(ResponseCode.PERMISSION_FAIL)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
