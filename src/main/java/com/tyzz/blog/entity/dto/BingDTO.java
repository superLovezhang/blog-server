package com.tyzz.blog.entity.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-10-11 13:44
 */
@Data
public class BingDTO {
    private List<BingImage> images = new ArrayList();
    private Object tooltips;
}

@Data
class BingImage {
    private String startdate;
    private String fullstartdate;
    private String enddata;
    private String url;
    private String urlbase;
    private String copyright;
    private String copyrightlink;
    private String title;
    private String quiz;
    private boolean wp;
    private String hsh;
    private int drk;
    private int top;
    private int bot;
    private Object hs;
}
