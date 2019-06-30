package com.example.demo.data.user.queryFilters;

import com.jialong.repository.domain.queryFilters.GetQueryFilter;
import com.jialong.repository.domain.queryFilters.ListQueryFilter;
import lombok.Data;

/**
 * User报表查询对象
 */
@Data
public class UserReportQueryFilter implements GetQueryFilter<Integer> {
    @Override
    public String getFilterKey() {
        return this.getClass().getSimpleName();
    }

    private Integer age;
}
