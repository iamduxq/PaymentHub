package com.nqdung.paymenthub.repository;

import com.nqdung.paymenthub.dto.request.GroupCategoryCreateRequest;
import com.nqdung.paymenthub.dto.request.GroupCategorySearchRequest;
import com.nqdung.paymenthub.dto.request.GroupCategoryUpdateRequest;
import com.nqdung.paymenthub.entity.GroupCategoryEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GroupCategoryProcedureRepository {
    @PersistenceContext
    private EntityManager entityManager;

    // Thêm danh mục
    @Transactional
    public Long insertCategory(GroupCategoryCreateRequest request) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("NQD_PRC_GROUPCATEGORY_INSERT");
        query.registerStoredProcedureParameter("P_PARAM_TYPE", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("P_PARAM_VALUE", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("P_PARAM_NAME", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("P_COMPONENT_CODE", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("P_EFFECTIVE_DATE", Date.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("P_END_EFFECTIVE_DATE", Date.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("P_DESCRIPTION", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("P_ID", Long.class, ParameterMode.OUT);
        query.setParameter("P_PARAM_TYPE", request.getParamType());
        query.setParameter("P_PARAM_VALUE", request.getParamValue());
        query.setParameter("P_PARAM_NAME", request.getParamName());
        query.setParameter("P_COMPONENT_CODE", request.getComponentCode());
        query.setParameter("P_EFFECTIVE_DATE", request.getEffectiveDate());
        query.setParameter("P_END_EFFECTIVE_DATE", request.getEndEffectiveDate());
        query.setParameter("P_DESCRIPTION", request.getDescription());
        query.execute();
        return ((Number) query.getOutputParameterValue("P_ID")).longValue();
    }

    // Lấy dữ liệu bảng
    @SuppressWarnings("unchecked")
    public List<GroupCategoryEntity> findAll() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("NQD_PRC_GROUPCATEGORY_GETALL", GroupCategoryEntity.class);
        query.registerStoredProcedureParameter("P_RESULT", void.class, ParameterMode.REF_CURSOR);
        return query.getResultList();
    }

    // Tìm kiếm theo tiêu chí
    @SuppressWarnings("unchecked")
    public List<GroupCategoryEntity> search(GroupCategorySearchRequest request) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("NQD_PRC_GROUPCATEGORY_SEARCH", GroupCategoryEntity.class);
        query.registerStoredProcedureParameter("D_PARAM_TYPE", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("D_PARAM_VALUE", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("D_PARAM_NAME", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("D_IS_ACTIVE", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("D_STATUS", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("P_RESULT", void.class, ParameterMode.REF_CURSOR);
        query.setParameter("D_PARAM_TYPE", request.getParamType());
        query.setParameter("D_PARAM_VALUE", request.getParamValue());
        query.setParameter("D_PARAM_NAME", request.getParamName());
        query.setParameter("D_IS_ACTIVE", request.getIsActive());
        query.setParameter("D_STATUS", request.getStatus());
        query.execute();
        return query.getResultList();
    }

    // Update dữ liệu tham số
    @Transactional
    public void update(GroupCategoryUpdateRequest request) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("NQD_PRC_GROUP_CATEGORY_UPDATE");
        query.registerStoredProcedureParameter("D_PARAM_VALUE", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("D_PARAM_NAME", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("D_COMPONENT_CODE", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("D_EFFECTIVE_DATE", Date.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("D_END_EFFECTIVE_DATE", Date.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("D_DESCRIPTION", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("D_ID", Long.class, ParameterMode.IN);
        query.setParameter("D_PARAM_VALUE", request.getParamValue());
        query.setParameter("D_PARAM_NAME", request.getParamName());
        query.setParameter("D_COMPONENT_CODE", request.getComponentCode());
        query.setParameter("D_EFFECTIVE_DATE", request.getEffectiveDate());
        query.setParameter("D_END_EFFECTIVE_DATE", request.getEndEffectiveDate());
        query.setParameter("D_DESCRIPTION", request.getDescription());
        query.setParameter("D_ID", request.getId());
        query.execute();
        entityManager.clear();
    }

    // Tìm kiếm theo Id
    public GroupCategoryEntity findById(Long id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("NQD_PRC_GROUP_CATEGORY_SEARCH_ID", GroupCategoryEntity.class);
        query.registerStoredProcedureParameter("D_ID", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("D_RESULT", void.class, ParameterMode.REF_CURSOR);
        query.setParameter("D_ID", id);
        query.execute();
        List<GroupCategoryEntity> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }


    // Xóa dữ liệu
    @Transactional
    public void delete(Long id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("NQD_PRC_GROUP_CATEGORY_DELETE");
        query.registerStoredProcedureParameter("D_ID", Long.class, ParameterMode.IN);
        query.setParameter("D_ID", id);
        query.execute();
    }

}
