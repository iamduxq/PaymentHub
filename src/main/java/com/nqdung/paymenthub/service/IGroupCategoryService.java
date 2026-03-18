package com.nqdung.paymenthub.service;

import com.nqdung.paymenthub.dto.GroupCategoryDTO;
import com.nqdung.paymenthub.dto.request.GroupCategoryCreateRequest;
import com.nqdung.paymenthub.dto.request.GroupCategorySearchRequest;
import com.nqdung.paymenthub.dto.request.GroupCategoryUpdateRequest;
import com.nqdung.paymenthub.entity.GroupCategoryEntity;

import java.util.List;

public interface IGroupCategoryService {
    GroupCategoryDTO addParamType(GroupCategoryCreateRequest groupCategoryDTO);
    List<GroupCategoryEntity> getAll();
    GroupCategoryEntity findById(Long id);
    GroupCategoryDTO editParam(Long id, GroupCategoryUpdateRequest newParam);
    void delete(Long id);
    List<GroupCategoryEntity> findCategoryWithCustomMatches(GroupCategorySearchRequest request);
}
