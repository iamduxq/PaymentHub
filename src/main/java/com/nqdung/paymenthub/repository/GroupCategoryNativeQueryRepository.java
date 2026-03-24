package com.nqdung.paymenthub.repository;

import com.nqdung.paymenthub.dto.request.GroupCategoryCreateRequest;
import com.nqdung.paymenthub.dto.request.GroupCategorySearchRequest;
import com.nqdung.paymenthub.dto.request.GroupCategoryUpdateRequest;
import com.nqdung.paymenthub.entity.GroupCategoryEntity;
import com.nqdung.paymenthub.paging.PageResponse;
import com.nqdung.paymenthub.paging.PagingNativeService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class GroupCategoryNativeQueryRepository {

    private final PagingNativeService pagingService;

    @PersistenceContext
    private EntityManager entityManager;

    // Lấy dữ liêu
    public List<GroupCategoryEntity> findAll() {
        String sql = "SELECT * FROM PMH_GROUP_CATEGORY";
        return entityManager.createNativeQuery(sql, GroupCategoryEntity.class).getResultList();
    }

    // Lấy dữ liệu có phân trang
    public PageResponse<GroupCategoryEntity> getAllWithPaging(int page, int size, String sortBy, String sortOrder) {
        String baseSql = "SELECT * FROM PMH_GROUP_CATEGORY";
        String countSql = "SELECT COUNT(*) FROM PMH_GROUP_CATEGORY";
        return pagingService.paging(
                baseSql,
                countSql,
                page,
                size,
                sortBy,
                sortOrder,
                GroupCategoryEntity.class
        );
    }

    // Tìm theo danh mục id
    public GroupCategoryEntity findById(Long id) {
        String sql = "SELECT * FROM PMH_GROUP_CATEGORY WHERE ID = :id";
        return (GroupCategoryEntity) entityManager
                .createNativeQuery(sql, GroupCategoryEntity.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    // Thêm danh mục tham số
    public int addParamCategory(GroupCategoryCreateRequest request) {
        String sql = """
                INSERT INTO PMH_GROUP_CATEGORY(
                                PARAM_TYPE, PARAM_VALUE, PARAM_NAME, COMPONENT_CODE,
                                EFFECTIVE_DATE, END_EFFECTIVE_DATE, DESCRIPTION)
                VALUES
                                (:paramType, :paramValue, :paramName, :componentCode,
                                :effectiveDate, :endEffectiveDate, :description)
                """;
        return entityManager.createNativeQuery(sql)
                .setParameter("paramType", request.getParamType())
                .setParameter("paramValue", request.getParamValue())
                .setParameter("paramName", request.getParamName())
                .setParameter("componentCode", request.getComponentCode())
                .setParameter("effectiveDate", request.getEffectiveDate())
                .setParameter("endEffectiveDate", request.getEndEffectiveDate())
                .setParameter("description", request.getDescription())
                .executeUpdate();
    }

    // Tìm kiếm
    public List<GroupCategoryEntity> search(GroupCategorySearchRequest request) {
        String sql = """
                SELECT * FROM PMH_GROUP_CATEGORY
                WHERE PARAM_TYPE = :paramType AND PARAM_VALUE = :paramValue AND PARAM_NAME = :paramName 
                AND IS_ACTIVE = :isActive AND STATUS = :status
                """;
        return entityManager.createNativeQuery(sql, GroupCategoryEntity.class)
                .setParameter("paramType", request.getParamType())
                .setParameter("paramValue", request.getParamValue())
                .setParameter("paramName", request.getParamName())
                .setParameter("isActive", request.getIsActive())
                .setParameter("status", request.getStatus())
                .getResultList();
    }

    // Sửa thông số cấu hình
    public int editParamGroup(Long id, GroupCategoryUpdateRequest request) {
        String sql = """
        UPDATE PMH_GROUP_CATEGORY
        SET
            PARAM_VALUE = :paramValue,
            PARAM_NAME = :paramName,
            COMPONENT_CODE = :componentCode,
            EFFECTIVE_DATE = :effectiveDate,
            END_EFFECTIVE_DATE = :endEffectiveDate,
            DESCRIPTION = :description
        WHERE ID = :id
        """;
        return entityManager.createNativeQuery(sql)
                .setParameter("paramValue", request.getParamValue())
                .setParameter("paramName", request.getParamName())
                .setParameter("componentCode", request.getComponentCode())
                .setParameter("effectiveDate", request.getEffectiveDate())
                .setParameter("endEffectiveDate", request.getEndEffectiveDate())
                .setParameter("description", request.getDescription())
                .setParameter("id", id)
                .executeUpdate();
    }

    public int deleteParamGroup(Long id) {
        String sql = """
                DELETE FROM PMH_GROUP_CATEGORY
                WHERE ID = :id
                """;
        return entityManager.createNativeQuery(sql)
                .setParameter("id", id)
                .executeUpdate();
    }

}
