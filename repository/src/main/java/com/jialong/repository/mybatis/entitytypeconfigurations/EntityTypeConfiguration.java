package com.jialong.repository.mybatis.entitytypeconfigurations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class EntityTypeConfiguration<T> {
    List<EntityPropertyConfiguration> propertyConfigurations = new ArrayList<>();

    public <R> EntityPropertyConfiguration<T> property(Function<T, R> getMethod) {
        EntityPropertyConfiguration<T> propertyConfiguration = new EntityPropertyConfiguration<>(getMethod);
        this.propertyConfigurations.add(propertyConfiguration);
        return propertyConfiguration;
    }

    public EntityPropertyConfiguration<T> getProperty(ColumnType columnType) {
        for (EntityPropertyConfiguration property : this.propertyConfigurations) {
            if (property.getColumnType() != columnType) {
                continue;
            }
            return property;
        }
        return null;
    }
}