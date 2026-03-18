package com.nqdung.paymenthub.repository;

import com.nqdung.paymenthub.dto.request.GroupCategorySearchRequest;
import com.nqdung.paymenthub.entity.GroupCategoryEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class GroupCategorySpecification {
    public static Specification<GroupCategoryEntity> filter(GroupCategorySearchRequest request) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getParamType() != null && !request.getParamType().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("paramType")),
                        "%" + request.getParamType().toLowerCase() + "%")
                );
            }
            if (request.getParamName() != null && !request.getParamName().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("paramName")),
                        "%" + request.getParamName().toLowerCase() + "%")
                );
            }
            if (request.getParamValue() != null && !request.getParamValue().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("paramValue")),
                        "%" + request.getParamValue().toLowerCase() + "%")
                );
            }
            if (request.getStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"),
                        request.getStatus())
                );
            }
            if (request.getIsActive() != null) {
                predicates.add(criteriaBuilder.equal(root.get("isActive"),
                        request.getIsActive())
                );
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
