package com.nqdung.paymenthub.service;

import com.nqdung.paymenthub.dto.GroupCategoryDTO;
import com.nqdung.paymenthub.dto.request.GroupCategoryCreateRequest;
import com.nqdung.paymenthub.dto.request.GroupCategoryRequest;
import com.nqdung.paymenthub.dto.request.GroupCategorySearchRequest;
import com.nqdung.paymenthub.dto.request.GroupCategoryUpdateRequest;
import com.nqdung.paymenthub.entity.GroupCategoryEntity;
import com.nqdung.paymenthub.response.ActionResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IGroupCategoryService {
    GroupCategoryDTO addParamType(GroupCategoryCreateRequest groupCategoryDTO);
    Page<GroupCategoryEntity> getAllWithPaging(int page, int size, String sortBy, String sortOrder);
    GroupCategoryEntity findById(Long id);
    ActionResponse<GroupCategoryDTO> editParam(Long id, GroupCategoryUpdateRequest newParam, boolean isSendApprove);
    void delete(Long id);
    List<GroupCategoryEntity> findCategoryWithCustomMatches(GroupCategorySearchRequest request);
    void sendToApprove(Long id, GroupCategoryRequest request);
    void sendToApprove(Long id);
    void approve(Long id);
    void cancelApprove(Long id);
}
