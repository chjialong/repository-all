package com.jialong.repository.mybatis.entitytypeconfigurations;

import java.util.function.Function;

public class EntityPropertyConfiguration<T> {
    private String name;
    private Function getter;
    private ColumnType columnType;
    private SaveOption saveOption = SaveOption.Default;

    public <R> EntityPropertyConfiguration(Function<T, R> getMethod) {
        this.getter = getMethod;
    }

    /**
     * 设置此属性是主键
     * @param <R>
     * @return
     */
    public <R> EntityPropertyConfiguration isKey() {
        this.columnType = ColumnType.Key;
        return this;
    }

    /**
     * 配置数据库中对应列用于乐观并发检测
     * @param <R>
     * @return
     */
    public <R> EntityPropertyConfiguration isConcurrencyToken() {
        this.columnType = ColumnType.ConcurrencyToken;
        return this;
    }

    /**
     * 设置此属性的持久化选项
     * @param option
     * @param <R>
     * @return
     */
    public <R> EntityPropertyConfiguration setSaveOption(SaveOption option) {
        this.saveOption = option;
        return this;
    }

    /**
     * 设置此属性的名称，即此属性对应的field Name。
     * @param name
     * @param <R>
     * @return
     */
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