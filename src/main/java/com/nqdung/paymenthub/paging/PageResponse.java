package com.nqdung.paymenthub.paging;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class PageResponse<T> {
    private List<T> data;
    private long total;
    private int page;
    private int size;
    private String sortBy;
    private boolean ascending;

    public PageResponse(List<T> data, long total, int page, int size, String sortBy, boolean ascending) {
        this.data = data;
        this.total = total;
        this.page = page;
        this.size = size;
        this.sortBy = sortBy;
        this.ascending = ascending;
    }
}
