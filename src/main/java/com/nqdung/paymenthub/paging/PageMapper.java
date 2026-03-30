package com.nqdung.paymenthub.paging;

import com.nqdung.paymenthub.dto.request.GroupCategorySearchRequest;
import org.springframework.data.domain.Page;

public class PageMapper {
    // Search
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

    // SearchDynamic
    public static <T> PageResponseProcedure<T> toResponseProcedure(
            Page<T> page,
            GroupCategorySearchRequest request
    ) {
        return PageResponseProcedure.<T>builder()
                .data(page.getContent())
                .total(page.getTotalPages())
                .page(request.getPage())
                .size(request.getSize())
                .sortBy(request.getSortBy())
                .sortOrder(request.getSortOrder())
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
