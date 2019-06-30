package com.example.demo.data.user.queryFilters;

import com.example.demo.domain.User;
import com.jialong.repository.domain.queryFilters.ListQueryFilter;
import lombok.Data;

/**
 * User简单查询对象
 */
@Data
public class UserSimpleQueryFilter implements ListQueryFilter<User> {
    @Override
    public String getFilterKey() {
        return this.getClass().getSimpleName();
    }

    private Integer age;
}
