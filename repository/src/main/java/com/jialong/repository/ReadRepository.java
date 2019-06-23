package com.jialong.repository;

import com.jialong.repository.domain.paging.PagedList;
import com.jialong.repository.domain.paging.PagedListImpl;
import com.jialong.repository.domain.queryFilters.GetQueryFilter;
import com.jialong.repository.domain.queryFilters.ListQueryFilter;
import com.jialong.repository.domain.queryFilters.PagedQueryFilter;
import com.jialong.repository.domain.queryFilters.QueryFilter;

import java.util.ArrayList;
import java.util.List;

public interface ReadRepository<Entity> {

    /**
     * 获取单个数据
     */
    default <E> Entity get(E id) {
        return null;
    }

    /**
     * 获取单个数据
     */
    default <ReturnType> ReturnType get(GetQueryFilter<ReturnType> getQueryFilter) {
        return null;
    }

    /**
     * 查询
     *
     * @param filter
     * @return
     */
    default <ReturnType> List<ReturnType> find(ListQueryFilter<ReturnType> filter) {
        return new ArrayList<>(0);
    }

    /**
     * 分页查询
     *
     * @param filter
     * @return
     */
    default <ReturnType> PagedList<ReturnType> find(PagedQueryFilter<ReturnType> filter) {
        return new PagedListImpl<>(new ArrayList<>(0));
    }
}
