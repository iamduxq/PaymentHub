package com.nqdung.paymenthub.repository;

import com.nqdung.paymenthub.entity.GroupCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupCategoryRepository extends JpaRepository<GroupCategoryEntity, Long>, JpaSpecificationExecutor<GroupCategoryEntity> {
    // Kiểm tra tồn tại tham số (loại trừ ID hiện tại nếu đang sửa)
    boolean existsByParamNameAndParamTypeAndParamValueAndIdNot(String paramName, String paramType, String paramValue, Long id);
}
