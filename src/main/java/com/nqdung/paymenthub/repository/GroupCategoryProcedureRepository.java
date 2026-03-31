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
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public class GroupCategoryProcedureRepository {
    @PersistenceContext
    private EntityManager entityManager;

    // Thêm danh mục
    @Transactional
    public Long insertCategory(GroupCategoryCreateRequest request, boolean isSendApprove) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("NQD_PRC_GROUPCATEGORY_INSERT");
        query.registerStoredProcedureParameter("D_PARAM_TYPE", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("D_PARAM_VALUE", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("D_PARAM_NAME", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("D_COMPONENT_CODE", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("D_EFFECTIVE_DATE", Date.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("D_END_EFFECTIVE_DATE", Date.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("D_DESCRIPTION", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("D_IS_SEND_APPROVE", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("D_ID", Long.class, ParameterMode.OUT);
        query.setParameter("D_PARAM_TYPE", request.getParamType());
        query.setParameter("D_PARAM_VALUE", request.getParamValue());
        query.setParameter("D_PARAM_NAME", request.getParamName());
        query.setParameter("D_COMPONENT_CODE", request.getComponentCode());
        query.setParameter("D_EFFECTIVE_DATE", request.getEffectiveDate());
        query.setParameter("D_END_EFFECTIVE_DATE", request.getEndEffectiveDate());
        query.setParameter("D_DESCRIPTION", request.getDescription());
        query.setParameter("D_IS_SEND_APPROVE", isSendApprove ? 1 : 0);
        query.execute();
        return ((Number) query.getOutputParameterValue("D_ID")).longValue();
    }

    // Lấy dữ liệu bảng
    @SuppressWarnings("unchecked")
    public List<GroupCategoryEntity> findAll() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("NQD_PRC_GROUPCATEGORY_GETALL", GroupCategoryEntity.class);
        query.registerStoredProcedureParameter("P_RESULT", void.class, ParameterMode.REF_CURSOR);
        return query.getResultList();
    }

    // Lấy tất cả dữ liệu với paging
    public Page<GroupCategoryEntity> getAllWithPaging(int page, int size, String sortBy, String order) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("NQD_PRC_GROUP_CATEGORY_GETALL_WITH_PAGING", GroupCategoryEntity.class);
        query.registerStoredProcedureParameter("D_PAGE", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("D_SIZE", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("D_SORT_BY", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("D_ORDER", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("D_CURSOR", void.class, ParameterMode.REF_CURSOR);
        query.registerStoredProcedureParameter("D_TOTAL", Integer.class, ParameterMode.OUT);
        query.setParameter("D_PAGE", page);
        query.setParameter("D_SIZE", size);
        query.setParameter("D_SORT_BY", sortBy);
        query.setParameter("D_ORDER", order);
        List<GroupCategoryEntity> result = query.getResultList();
        Number totalNumber = (Number) query.getOutputParameterValue("D_TOTAL");
        long total = (totalNumber != null) ? totalNumber.longValue() : 0L;
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy));
        return new PageImpl<>(result, pageable, total);
    }

    // Dynamic search
    @SuppressWarnings("unchecked")
    public Page<GroupCategoryEntity> searchDynamic(GroupCategorySearchRequest request) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("NQD_PRC_GROUP_CATEGORY_SEARCH_PAGING", GroupCategoryEntity.class);
        query.registerStoredProcedureParameter("P_PARAM_NAME", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("P_PARAM_TYPE", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("P_PARAM_VALUE", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("P_STATUS", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("P_IS_ACTIVE", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("P_PAGE", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("P_SIZE", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("P_SORT_BY", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("P_SORT_ORDER", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("P_TOTAL_ROWS", Long.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("P_CURSOR", void.class, ParameterMode.REF_CURSOR);

        query.setParameter("P_PARAM_NAME", request.getParamName());
        query.setParameter("P_PARAM_TYPE", request.getParamType());
        query.setParameter("P_PARAM_VALUE", request.getParamValue());
        query.setParameter("P_STATUS", request.getStatus());
        query.setParameter("P_IS_ACTIVE", request.getIsActive());
        query.setParameter("P_PAGE", request.getPage() + 1);
        query.setParameter("P_SIZE", request.getSize());
        query.setParameter("P_SORT_BY", request.getSortBy());
        query.setParameter("P_SORT_ORDER", request.getSortOrder());

        Long total = (Long) query.getOutputParameterValue("P_TOTAL_ROWS");
        List<GroupCategoryEntity> data = query.getResultList();

        return new PageImpl<>(data, PageRequest.of(request.getPage(), request.getSize()), total);
    }

    // Update dữ liệu tham số
    @Transactional
    public void update(GroupCategoryUpdateRequest request, boolean isSendApprove) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("NQD_PRC_GROUP_CATEGORY_UPDATE");
        query.registerStoredProcedureParameter("D_ID", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("D_PARAM_TYPE", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("D_PARAM_VALUE", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("D_PARAM_NAME", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("D_COMPONENT_CODE", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("D_EFFECTIVE_DATE", Date.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("D_END_EFFECTIVE_DATE", Date.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("D_DESCRIPTION", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("D_IS_SEND_APPROVE", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("D_RESULT", Integer.class, ParameterMode.OUT);
        query.setParameter("D_ID", request.getId());
        query.setParameter("D_PARAM_TYPE", request.getParamType());
        query.setParameter("D_PARAM_VALUE", request.getParamValue());
        query.setParameter("D_PARAM_NAME", request.getParamName());
        query.setParameter("D_COMPONENT_CODE", request.getComponentCode());
        query.setParameter("D_EFFECTIVE_DATE", request.getEffectiveDate());
        query.setParameter("D_END_EFFECTIVE_DATE", request.getEndEffectiveDate());
        query.setParameter("D_DESCRIPTION", request.getDescription());
        query.setParameter("D_IS_SEND_APPROVE", isSendApprove ? 1 : 0);
        query.execute();
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

    // Hủy phê duyệt
    @Transactional
    public void cancel(Long id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("NQD_GROUP_CATEGORY_CANCEL_APPROVE");
        query.registerStoredProcedureParameter("D_ID", Long.class, ParameterMode.IN);
        query.setParameter("D_ID", id);
        query.execute();
    }

    // Từ chối phê duyệt
    @Transactional
    public void reject(Long id, String reason) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("NQD_PRC_GROUP_CATEGORY_REJECT");
        query.registerStoredProcedureParameter("D_ID", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("D_REASON", String.class, ParameterMode.IN);
        query.setParameter("D_ID", id);
        query.setParameter("D_REASON", reason);
        query.execute();
    }
}
