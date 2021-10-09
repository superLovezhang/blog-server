package com.tyzz.blog.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-10-09 16:03
 */
public class BlogPage<T> extends Page<T> {
    public BlogPage(long current, long size, long total, boolean searchCount) {
        this.records = Collections.emptyList();
        this.total = 0L;
        this.size = 10L;
        this.current = 1L;
        this.orders = new ArrayList();
        this.optimizeCountSql = true;
        this.searchCount = true;
        if (current > 1L) {
            this.current = current;
        }

        this.size = size;
        this.total = total;
        this.searchCount = searchCount;
    }

    public static <T> BlogPage<T> of(long current, long size) {
        return of(current, size, 0L);
    }

    public static <T> BlogPage<T> of(long current, long size, long total) {
        return of(current, size, total, true);
    }

    public static <T> BlogPage<T> of(long current, long size, boolean searchCount) {
        return of(current, size, 0L, searchCount);
    }

    public static <T> BlogPage<T> of(long current, long size, long total, boolean searchCount) {
        return new BlogPage<T>(current, size, total, searchCount);
    }


    public <B> Page<B> map(Function<T, B> mapper) {
        Page<B> result = new Page<>();
        result.setRecords(super.getRecords()
                .stream()
                .map(mapper)
                .collect(Collectors.toList()));
        result.setSize(super.getSize());
        result.setCurrent(super.getCurrent());
        result.setCountId(super.getCountId());
        result.setMaxLimit(super.getMaxLimit());
        result.setOrders(super.getOrders());
        result.setTotal(super.getTotal());
        result.setPages(super.getPages());
        return result;
    }
}
