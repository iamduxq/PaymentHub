package com.nqdung.paymenthub.service.impl;

import com.nqdung.paymenthub.dto.GroupCategoryDTO;
import com.nqdung.paymenthub.dto.request.GroupCategoryCreateRequest;
import com.nqdung.paymenthub.dto.request.GroupCategoryUpdateRequest;
import com.nqdung.paymenthub.entity.GroupCategoryEntity;

import java.util.List;

public interface IGroupCategoryService {
    GroupCategoryDTO addParamType(GroupCategoryCreateRequest groupCategoryDTO);
    List<GroupCategoryEntity> getAll();
    GroupCategoryEntity findById(Long id);
    GroupCategoryDTO editParam(Long id, GroupCategoryUpdateRequest newParam);
    List<GroupCategoryEntity> findByParamCategory(String paramType, String paramValue, String paramName, String componentCode, Integer status);
    void delete(Long id);
}
