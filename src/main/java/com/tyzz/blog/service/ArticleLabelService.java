package com.tyzz.blog.service;

import com.tyzz.blog.dao.ArticleLabelDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (ArticleLabel)表服务实现类
 *
 * @author makejava
 * @since 2021-10-05 23:05:22
 */
@Service("articleLabelService")
public class ArticleLabelService {
    @Resource
    private ArticleLabelDao articleLabelDao;

    public List<Long> listHotIds() {
        return articleLabelDao.listHotIds();
    }
}