package com.jialong.repository.domain.queryFilters;

/**
 * Get操作过滤参数对象
 * Created by 陈家龙 on 2017/3/23.
 */
public class GetQueryFilterImpl<IdType, ReturnType> implements GetQueryFilter<ReturnType> {
    public GetQueryFilterImpl() {
    }

    public GetQueryFilterImpl(IdType id) {
        this.id = id;
    }

    @Override
    public String getFilterKey() {
        return "get";
    }

    private IdType id;

    public IdType getId() {
        return id;
    }

    public void setId(IdType id) {
        this.id = id;
    }
}
