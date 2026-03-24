package com.nqdung.paymenthub.paging;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PagingService {
    public <T>Page<T> getPage(JpaRepository<T, ?> repository, int page, int size, String sortBy, String sortOrder, List<String> allowedSortBy) {
        // Kiểm tra allowedSortBy chứa sortBy
        if (!allowedSortBy.contains(sortBy)) sortBy = allowedSortBy.get(0);

        Sort.Direction direction = Sort.Direction.DESC;
        if (sortOrder != null && sortOrder.equalsIgnoreCase("asc")) {
            direction = Sort.Direction.ASC;
        }

        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(page, size, sort);
        return repository.findAll(pageable);
    }
}
