package com.nqdung.paymenthub.paging;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class PagingNativeService {

    @PersistenceContext
    private EntityManager entityManager;

    public <T> PageResponse<T> paging(
            String baseUrl,
            String countSql,
            int page,
            int size,
            String sortBy,
            String sortOrder,
            Class<T> tClass
    ) {
        int offset = page * size;
        String order = "";
        if (sortBy != null && !sortBy.isEmpty()) {
            order = " ORDER BY " + sortBy + (Objects.equals(sortOrder, "DESC") ? " DESC" : " ASC");
        }

        String sql = baseUrl + order;
        List<T> data = entityManager.createNativeQuery(sql, tClass)
                .setFirstResult(offset)
                .setMaxResults(size)
                .getResultList();

        long total = ((Number) entityManager.createNativeQuery(countSql).getSingleResult()).longValue();
        return new PageResponse<>(data, total, page, size, sortBy, sortOrder);
    }


}
