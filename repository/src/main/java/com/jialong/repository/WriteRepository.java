package com.jialong.repository;

import com.jialong.repository.mybatis.entitytypeconfigurations.EntityTypeConfigurationContext;

/**
 * Created by 陈家龙 on 2017/3/24.
 */
public interface WriteRepository<Entity> {
    /**
     * 保存更新，即Update
     *
     * @param entity
     * @return
     */
    default int update(Entity entity) {
        return 0;
    }

    /**
     * 插入
     *
     * @param entity
     * @return
     */
    default Object insert(Entity entity) {
        return null;
    }

    /**
     * 删除
     *
     * @param entity
     * @return
     */
    default int delete(Entity entity) {
        return 0;
    }

    void onEntityTypeConfigurationCreating(EntityTypeConfigurationContext<Entity> context);
}
