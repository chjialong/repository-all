package com.jialong.repository.domain.paging;


/**
 * Created by 陈家龙 on 2017/3/23.
 */
public interface Paged {

    /**
     * 当前页号
     * @return
     */
    int getPageIndex();

    /**
     * 页大小
     * @return
     */
    int getPageSize();

    /**
     * 总记录行数
     * @return
     */
    int getTotalCount();

    /**
     * 总页数
     * @return
     */
    int getTotalPages();

    /**
     * 是否有上一页
     * @return
     */
    boolean hasPreviousPage();

    /**
     * 是否有下一页
     * @return
     */
    boolean hasNextPage();
}
