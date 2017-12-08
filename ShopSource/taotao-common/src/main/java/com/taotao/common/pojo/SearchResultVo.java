package com.taotao.common.pojo;

import java.io.Serializable;
import java.util.List;

public class SearchResultVo implements Serializable {
    private long totalPages;
    private List<SearchItem> itemList;
    private long recordCount;

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public List<SearchItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SearchItem> itemList) {
        this.itemList = itemList;
    }
}
