package com.tyzz.blog.schedule.service.redis;

import com.tyzz.blog.util.StringUtils;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Description:
 * 点赞同步抽象类
 * @Author: ZhangZhao
 * DateTime: 2022-05-10 10:21
 */
public abstract class RedisSync {
    // next handler reference
    protected RedisSync next;

    /**
     * 对外暴露的同步方法
     */
    public void sync() {
        List<String> likeKeys = getLikeKeys();
        for (String key : likeKeys) {
            Long entityId = entityId(key);
            // if current entity id is null then skip the next code;
            if (entityId == null) {
                continue;
            }

            Set<Long> rdbIds = StringUtils.removeDummyElement(getRdbIds(key));
            Set<Long> dbIds = getDbIds(entityId);
            List<List<Long>> insertAndDelIds = findInsertAndDelIds(rdbIds, dbIds);
            for (Long userId : obtainInsertIds(insertAndDelIds)) {
                likeSave(entityId, userId);
            }
            for (Long userId : obtainDelIds(insertAndDelIds)) {
                likeDel(entityId, userId);
            }

            // some operations after sync data
            afterDataSync(key, entityId);
        }

        if (next != null) {
            next.sync();
        }
    }

    /**
     * 获取当前点赞数据的key列表
     * @return
     */
    protected abstract List<String> getLikeKeys();

    /**
     * 根据当前key从redis中获取value
     * @param key redis的key
     * @return
     */
    protected abstract Set<Long> getRdbIds(String key);

    /**
     * 根据当前entityId从数据库中获取value
     * @param entityId 行数据id
     * @return
     */
    protected abstract Set<Long> getDbIds(Long entityId);

    /**
     * 点赞数据保存操作
     * @param entityId 实体id
     * @param userId 用户id
     */
    protected abstract void likeSave(Long entityId, Long userId);

    /**
     * 点赞数据删除操作
     * @param entityId 实体id
     * @param userId 用户id
     */
    protected abstract void likeDel(Long entityId, Long userId);


    protected List<Long> obtainInsertIds(@NonNull List<List<Long>> insertAndDelIds) {
        return insertAndDelIds.size() != 0 ? insertAndDelIds.get(0) : new ArrayList<>();
    }

    protected List<Long> obtainDelIds(@NonNull List<List<Long>> insertAndDelIds) {
        return insertAndDelIds.size() > 1 ? insertAndDelIds.get(1) : new ArrayList<>();
    }

    /**
     * 当前redis的key同步之后的子类重写的后置操作
     * 默认空实现
     * @param key
     * @param entityId
     */
    protected void afterDataSync(String key, Long entityId) {}

    /**
     * 根据redis的key获取对应实体id
     * @param key redis的key
     * @return
     */
    private Long entityId(String key) {
        String[] splitArr = StringUtils.splitRedisKey(key);
        if (splitArr.length == 3) {
            return Long.parseLong(splitArr[2]);
        }
        return null;
    }


    /**
     * 找出rdb和db数据中要插入和删除的id列表
     * @param rdb rdb的userId列表
     * @param db db的userId列表
     * @return
     */
    protected List<List<Long>> findInsertAndDelIds(Set<Long> rdb, Set<Long> db) {
        ArrayList<List<Long>> result = new ArrayList<>();
        result.add(new ArrayList<>(rdb.size()));
        result.add(new ArrayList<>(db.size()));
        Iterator<Long> dbIterator = db.iterator();
        while (dbIterator.hasNext()) {
            Long id = dbIterator.next();
            if (!rdb.contains(id)) {
                result.get(1).add(id);
            } else {
                rdb.remove(id);
            }
        }
        result.get(0).addAll(new ArrayList<>(rdb));
        return result;
    }

    public void setNext(RedisSync next) {
        this.next = next;
    }
}
