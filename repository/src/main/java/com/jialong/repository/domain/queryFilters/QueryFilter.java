package com.jialong.repository.domain.queryFilters;

/**
 * Created by 陈家龙 on 2017/3/23.
 */
public interface QueryFilter<ReturnType> {
    /**
     * 对象的查询业务Key
     */
    String getFilterKey();
}
