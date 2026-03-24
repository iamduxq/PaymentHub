package com.nqdung.paymenthub.service;

import com.nqdung.paymenthub.dto.GroupCategoryDTO;
import com.nqdung.paymenthub.dto.request.GroupCategoryCreateRequest;
import com.nqdung.paymenthub.dto.request.GroupCategoryUpdateRequest;
import com.nqdung.paymenthub.entity.GroupCategoryEntity;
import com.nqdung.paymenthub.paging.PageResponse;

import java.util.List;

public interface IGroupCategoryNativeQueryService {
    List<GroupCategoryEntity> findAll();
    PageResponse<GroupCategoryDTO> getAllWithPaging(int page, int size, String sortBy, String sortOrder);
    GroupCategoryEntity findById(Long id);
    int addCategory(GroupCategoryCreateRequest request);
    int updateCategory(Long id, GroupCategoryUpdateRequest request);
    int deleteCategory(Long id);
}
