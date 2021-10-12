package com.tyzz.blog.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description: 自定义的Page对象 便于转换page内部records的泛型
 * @Author: ZhangZhao
 * DateTime: 2021-10-09 16:03
 */
public class BlogPage<T> extends Page<T> {
    protected boolean next = false;

    public BlogPage(long current, long size, long total, boolean searchCount) {
        super(current, size, total, searchCount);
    }

    public BlogPage() {
        super();
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

    public boolean getNext() {
        return this.getCurrent() < this.getPages();
    }

    public <B> BlogPage<B> map(Function<T, B> mapper) {
        BlogPage<B> result = new BlogPage<>();
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
