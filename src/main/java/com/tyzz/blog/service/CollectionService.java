package com.tyzz.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyzz.blog.dao.CollectionDao;
import com.tyzz.blog.entity.Article;
import com.tyzz.blog.entity.Collection;
import com.tyzz.blog.entity.User;
import com.tyzz.blog.entity.vo.BasePageVO;
import com.tyzz.blog.exception.BlogException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * (Collection)表服务实现类
 *
 * @author makejava
 * @since 2021-09-26 10:24:49
 */
@Service("collectionService")
public class CollectionService {
    @Resource
    private CollectionDao collectionDao;
    @Resource
    private ArticleService articleService;


    public Page<Collection> pageByUser(User user, BasePageVO pageVO) {
        Page<Collection> page = Page.of(pageVO.getPage(), pageVO.getSize());
        QueryWrapper<Collection> wrapper = new QueryWrapper<>();
        wrapper.eq("userId", user.getUserId())
               .isNotNull("articleId");
        return collectionDao.selectPage(page, wrapper);
    }

    public void collect(User user, Long articleId) {
        Article article = Optional.ofNullable(articleService.selectOneById(articleId))
                .orElseThrow(() -> new BlogException("该文章不存在"));
        QueryWrapper<Collection> wrapper = new QueryWrapper<>();
        wrapper.eq("articleId", article.getArticleId())
               .eq("userId", user.getUserId());
        Collection collection = collectionDao.selectOne(wrapper);
        if (collection == null) {
            Collection build = Collection.builder()
                    .articleId(articleId)
                    .userId(user.getUserId())
                    .build();
            collectionDao.insert(build);
            return;
        }
        collectionDao.deleteById(collection.getCollectionId());
    }
}