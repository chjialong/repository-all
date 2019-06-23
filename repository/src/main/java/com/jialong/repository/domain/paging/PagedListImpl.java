package com.jialong.repository.domain.paging;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈家龙 on 2017/3/23.
 */
public class PagedListImpl<T> extends ArrayList<T> implements PagedList<T> {

    //region constructot

    /**
     * 对source进行分页
     *
     * @param source
     * @param pageIndex
     * @param pageSize
     */
    public PagedListImpl(List<T> source, int pageIndex, int pageSize) {
        this(pageIndex, pageSize, source.size());
        source.stream().skip(this.getPageIndex() * this.getPageSize()).limit(this.getPageSize()).forEach(t -> this.add(t));
    }

    /**
     * 把source转成PagedList
     *
     * @param source
     * @param pageIndex
     * @param pageSize
     * @param totalCount 总记录数
     */
    public PagedListImpl(List<T> source, int pageIndex, int pageSize, int totalCount) {
        this(pageIndex, pageSize, totalCount);
        this.addAll(source);
    }

    /**
     * 不对source进行分页。当前页的数据就是source，但总页数参数会自动计算。
     *
     * @param source
     * @param pageIndex
     * @param pageSize
     * @param totalCount
     */
    public PagedListImpl(T[] source, int pageIndex, int pageSize, int totalCount) {
        this(pageIndex, pageSize, totalCount);
        for (T t : source) {
            this.add(t);
        }
    }

    /**
     * 不对source进行分页。当前页的数据就是source，且只有当前这页数据。
     *
     * @param source
     */
    public PagedListImpl(List<T> source) {
        this(0, source.size() == 0 ? 10 : source.size(), source.size());
        this.addAll(source);
    }

    /// <summary>
    /// 构造函数
    /// </summary>
    /// <param name="pageIndex"></param>
    /// <param name="pageSize"></param>
    /// <param name="totalCount"></param>
    public PagedListImpl(int pageIndex, int pageSize, int totalCount) {
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
        this.totalCount = totalCount;
        this.totalPages = this.totalCount / this.pageSize;

        if (this.totalCount % this.pageSize > 0)
            this.totalPages++;
    }
    //endregion

    private int pageSize;
    private int pageIndex;
    private int totalCount;
    private int totalPages;

    @Override
    public int getPageIndex() {
        return pageIndex;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public int getTotalCount() {
        return totalCount;
    }

    @Override
    public int getTotalPages() {
        return totalPages;
    }

    @Override
    public boolean hasPreviousPage() {
        return (pageIndex > 0);
    }

    @Override
    public boolean hasNextPage() {
        return (pageIndex + 1 < this.getTotalPages());
    }
}
