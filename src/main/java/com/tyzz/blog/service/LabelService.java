package com.tyzz.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyzz.blog.dao.LabelDao;
import com.tyzz.blog.entity.Label;
import com.tyzz.blog.entity.dto.LabelDTO;
import com.tyzz.blog.entity.dto.LabelPageDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Label)表服务实现类
 *
 * @author makejava
 * @since 2021-09-26 10:24:49
 */
@Service("labelService")
public class LabelService {
    @Resource
    private LabelDao labelDao;
    @Resource
    private ArticleLabelService articleLabelService;

    public void save(LabelDTO labelDTO) {
        Label label = Label.builder()
                .labelId(labelDTO.getLabelId())
                .labelName(labelDTO.getLabelName())
                .build();
        labelDao.insert(label);
    }

    public void remove(Long labelId) {
        labelDao.deleteById(labelId);
    }

    public Page<Label> listPage(LabelPageDTO labelPageDTO) {
        QueryWrapper<Label> wrapper = new QueryWrapper<>();
        Page<Label> page = Page.of(labelPageDTO.getPage(), labelPageDTO.getSize());
        wrapper.orderByDesc(labelPageDTO.getSortColumn());
        return labelDao.selectPage(page, wrapper);
    }

    public List<Label> hotList() {
        List<Long> labelIds = articleLabelService.listHotIds();
        return labelDao.selectBatchIds(labelIds);
    }
}