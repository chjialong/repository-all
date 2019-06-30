package com.example.demo.data.user;

import com.example.demo.domain.User;
import com.jialong.repository.mybatis.MybatisRepositoryImpl;
import com.jialong.repository.mybatis.entitytypeconfigurations.EntityTypeConfiguration;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl extends MybatisRepositoryImpl<User> implements UserRepository {
    public UserRepositoryImpl(SqlSessionTemplate sqlSessionTemplate) {
        super(sqlSessionTemplate, User.class);
    }

    @Override
    public void onEntityTypeConfigurationCreating(EntityTypeConfiguration<User> entityTypeConfiguration) {
        entityTypeConfiguration.property(p -> p.getId()).name("id").isKey();
//        entityTypeConfiguration.property(p1 -> p1.getChildren());
    }
}
