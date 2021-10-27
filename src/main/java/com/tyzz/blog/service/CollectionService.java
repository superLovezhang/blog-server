package com.tyzz.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tyzz.blog.common.BlogPage;
import com.tyzz.blog.dao.CollectionDao;
import com.tyzz.blog.entity.Article;
import com.tyzz.blog.entity.Collection;
import com.tyzz.blog.entity.User;
import com.tyzz.blog.entity.dto.BasePageDTO;
import com.tyzz.blog.entity.vo.CollectionVO;
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
    @Resource
    private UserService userService;


    public BlogPage<CollectionVO> pageByUser(User user, BasePageDTO pageVO) {
        BlogPage<Collection> page = BlogPage.of(pageVO.getPage(), pageVO.getSize());
        QueryWrapper<Collection> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", user.getUserId())
               .isNotNull("article_id");
        return collectionDao.selectPage(page, wrapper)
                .map(this::pojoToVO);
    }

    public void collect(User user, Long articleId) {
        Article article = Optional.ofNullable(articleService.selectOneById(articleId))
                .orElseThrow(() -> new BlogException("该文章不存在"));
        QueryWrapper<Collection> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", article.getArticleId())
               .eq("user_id", user.getUserId());
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

    public Collection findOneByUserAndArticle(User currentUser, Article article) {
        if (currentUser == null) {
            return null;
        }
        QueryWrapper<Collection> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", currentUser.getUserId());
        wrapper.eq("article_id", article.getArticleId());
        return collectionDao.selectOne(wrapper);
    }

    public Integer countByArticle(Article article) {
        QueryWrapper<Collection> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", article.getArticleId());
        return collectionDao.selectCount(wrapper);
    }

    public CollectionVO pojoToVO(Collection collection) {
        return CollectionVO.builder()
                .collectionId(collection.getCollectionId())
                .article(articleService.pojoToVO(articleService.selectOneById(collection.getArticleId())))
                .user(userService.pojoToVO(userService.selectById(collection.getUserId())))
                .build();
    }
}