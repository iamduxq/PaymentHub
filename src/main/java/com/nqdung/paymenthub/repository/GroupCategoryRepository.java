package com.nqdung.paymenthub.repository;

import com.nqdung.paymenthub.entity.GroupCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupCategoryRepository extends JpaRepository<GroupCategoryEntity, Long> {
    @Query("""
        SELECT g FROM GroupCategoryEntity g
        WHERE g.paramType = :paramType AND g.paramValue = :paramValue
        AND g.paramName = :paramName AND g.componentCode = :componentCode AND g.status = :status
""")
    List<GroupCategoryEntity> findGroupCategoryByParamType(String paramType, String paramValue, String paramName, String componentCode, Integer status);
}
