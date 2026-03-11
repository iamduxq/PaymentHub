package com.nqdung.paymenthub.repository;

import com.nqdung.paymenthub.entity.GroupCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupCategoryRepository extends JpaRepository<GroupCategoryEntity, Long> {
    boolean existsByParamType(String paramType);
}
