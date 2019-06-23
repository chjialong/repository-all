package com.jialong.repository.domain.paging;

/**
 * Created by 陈家龙 on 2017/3/23.
 */
public class BasePaged implements Paged {
    /**
     * 只为了序列化
     */
    public BasePaged() {
    }

    public BasePaged(int pageIndex, int pageSize, int totalCount) {
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
        this.totalCount = totalCount;
    }

    private int pageSize;
    private int pageIndex;
    private int totalCount;

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
        if (this.pageSize == 0) {
            return 1;
        }
        int totalPages = this.totalCount / this.pageSize;
        if (this.totalCount % this.pageSize > 0)
            totalPages++;
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

