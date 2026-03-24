package com.nqdung.paymenthub.paging;

import org.springframework.data.domain.Page;

public class PageMapper {
    public static <T> PageResponseProcedure<T> toResponseProcedure(
            Page<T> page,
            String sortBy,
            String sortOrder
    ) {
        return PageResponseProcedure.<T>builder()
                .data(page.getContent())
                .total(page.getTotalElements())
                .page(page.getNumber())
                .size(page.getSize())
                .sortBy(sortBy)
                .sortOrder(sortOrder.toUpperCase())
                .build();
    }

    public static <T> PageResponse<T> toResponse(
            Page<T> page,
            String sortBy,
            String sortOrder
    ) {
        return PageResponse.<T>builder()
                .data(page.getContent())
                .total(page.getTotalElements())
                .page(page.getNumber())
                .size(page.getSize())
                .sortBy(sortBy)
                .sortOrder(sortOrder.toUpperCase())
                .build();
    }

}
