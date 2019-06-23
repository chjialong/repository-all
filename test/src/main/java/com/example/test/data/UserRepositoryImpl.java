package com.example.test.data;

import com.example.test.domain.User;
import com.jialong.repository.mybatis.MybatisRepositoryImpl;
import com.jialong.repository.mybatis.entitytypeconfigurations.EntityTypeConfigurationContext;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl extends MybatisRepositoryImpl<User> implements UserRepository {
    public UserRepositoryImpl(SqlSessionTemplate sqlSessionTemplate) {
        super(sqlSessionTemplate, User.class);
    }


    @Override
    public void onEntityTypeConfigurationCreating(EntityTypeConfigurationContext<User> context) {
        context.entity(User.class).property(p -> p.getId()).name("id").isKey();
    }
}
