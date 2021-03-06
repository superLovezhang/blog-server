package com.tyzz.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tyzz.blog.dao.LoginRecordDao;
import com.tyzz.blog.entity.pojo.LoginRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * (LoginRecord)表服务接口
 *
 * @author ZhangZhao
 * @since 2022-05-14 17:38:52
 */
@Service
@RequiredArgsConstructor
public class LoginRecordService {
    private final LoginRecordDao loginRecordDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public Object save(LoginRecord record) {
        return loginRecordDao.insert(record);
    }

    public LoginRecord lastRecord(Long userId) {
        QueryWrapper<LoginRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
                .orderByDesc("create_time");
        return loginRecordDao.selectOne(wrapper);
    }
}