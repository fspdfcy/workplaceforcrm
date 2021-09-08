package com.fspdfcy.crm.vo;

import java.util.List;

/**
 * @author fsp
 * @version v1.0
 * 创建时间: 2021/9/8
 */
public class PageVo<T> {
    private Integer totalSize;
    private List<T> dataList;

    public PageVo() {
    }

    public Integer getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    @Override
    public String toString() {
        return "PageVo{" +
                "totalSize=" + totalSize +
                ", dataList=" + dataList +
                '}';
    }
}
