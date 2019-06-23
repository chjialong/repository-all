package com.jialong.repository.mybatis.entitytypeconfigurations;

import java.util.function.Function;

public class EntityPropertyConfiguration<T> {
    private String name;
    private Function getter;
    private ColumnType columnType;

    public <R> EntityPropertyConfiguration(Function<T, R> getMethod) {
        this.getter = getMethod;
    }

    public <R> EntityPropertyConfiguration isKey() {
        this.columnType = ColumnType.Key;
        return this;
    }

    public <R> EntityPropertyConfiguration isConcurrencyToken() {
        this.columnType = ColumnType.ConcurrencyToken;
        return this;
    }

    public <R> EntityPropertyConfiguration name(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public ColumnType getColumnType() {
        return columnType;
    }

    public <R> R getValue(T t) {
        return (R) this.getter.apply(t);
    }

}