package com.jialong.repository.domain.queryFilters;

import java.util.Date;

/**
 * Created by 陈家龙 on 2017/3/23.
 */
public abstract class PagedQueryFilter<ReturnType> implements QueryFilter<ReturnType> {
    /**
     * 从0开始
     */
    protected int pageIndex;
    protected int pageSize;
    protected Date beginTime;
    protected Date endTime;
    protected boolean returnRowCount;

    /**
     *
     * @return
     */
    public boolean getReturnRowCount(){
        return this.returnRowCount;
    }

    /**
     * 是否返回总数据行数
     * @param value
     */
    public void setReturnRowCount(boolean value){
        this.returnRowCount = value;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 起始数据行，从0开始
     * 每页100，第0页：StartingRowIndex = 0，EndingRowIndex = 100
     */
    public int getStartingRowIndex() {
        return this.getPageIndex() * this.getPageSize();
    }

    /**
     * 结束数据行
     * 每页100，第0页：StartingRowIndex = 0，EndingRowIndex = 100
     */
    public int getEndingRowIndex() {
        return (this.getPageIndex() + 1) * this.getPageSize();
    }

    /**
     * 一定要重写此字段，区分各个Filter
     *
     * @return
     */
    public abstract String getFilterKey();
}


