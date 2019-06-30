package com.jialong.repository.mybatis.entitytypeconfigurations;

/**
 * 持久化选项
 */
public enum SaveOption {
//    /**
//     * 不保存
//     */
//    Ignore,

    /**
     * 可选。如果只有此选项的数据列要更新，那就不会执行数据库持久化操作。
     */
    Optional,

//    /**
//     * 此选项的数据列无论是否发生变更都会保存持久化到数据库。
//     */
//    Required,

    /**
     * 默认。即发生了变更才会执行数据库持久化操作。
     */
    Default;
}
