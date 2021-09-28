package com.tyzz.blog.service;

import com.tyzz.blog.dao.LabelDao;
import com.tyzz.blog.entity.Label;
import com.tyzz.blog.entity.dto.LabelDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
}