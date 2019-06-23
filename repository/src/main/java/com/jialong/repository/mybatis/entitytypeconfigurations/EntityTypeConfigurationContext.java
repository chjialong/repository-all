package com.jialong.repository.mybatis.entitytypeconfigurations;

import java.util.concurrent.ConcurrentHashMap;

public class EntityTypeConfigurationContext<T> {
    private EntityTypeConfigurationContext() {
    }

    public static EntityTypeConfigurationContext INSTANCE;

    static {
        INSTANCE = new EntityTypeConfigurationContext();
    }

    ConcurrentHashMap<Class, EntityTypeConfiguration> entityTypeConfigurationMap = new ConcurrentHashMap<>();

    public EntityTypeConfiguration<T> entity(Class<T> clazz) {
        if (!this.entityTypeConfigurationMap.containsKey(clazz)) {
            EntityTypeConfiguration<T> configuration = new EntityTypeConfiguration<>();
            this.entityTypeConfigurationMap.put(clazz, configuration);
        }
        return this.entityTypeConfigurationMap.get(clazz);
    }
}
