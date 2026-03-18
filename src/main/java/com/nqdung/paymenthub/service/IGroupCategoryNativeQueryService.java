package com.nqdung.paymenthub.service;

import com.nqdung.paymenthub.dto.request.GroupCategoryCreateRequest;
import com.nqdung.paymenthub.dto.request.GroupCategoryUpdateRequest;
import com.nqdung.paymenthub.entity.GroupCategoryEntity;

import java.util.List;

public interface IGroupCategoryNativeQueryService {
    List<GroupCategoryEntity> findAll();
    GroupCategoryEntity findById(Long id);
    int addCategory(GroupCategoryCreateRequest request);
    int updateCategory(Long id, GroupCategoryUpdateRequest request);
    int deleteCategory(Long id);
}
