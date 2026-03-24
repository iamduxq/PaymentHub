package com.nqdung.paymenthub.paging;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PageResponseProcedure<T> {
    private List<T> data;
    private long total;
    private int page;
    private int size;
    private String sortBy;
    private String sortOrder;

    public PageResponseProcedure(List<T> data, long total, int page, int size, String sortBy, String sortOrder) {
        this.data = data;
        this.total = total;
        this.page = page;
        this.size = size;
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
    }
}
