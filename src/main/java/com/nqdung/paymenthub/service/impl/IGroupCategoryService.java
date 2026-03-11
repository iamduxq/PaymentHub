package com.nqdung.paymenthub.service.impl;

import com.nqdung.paymenthub.dto.GroupCategoryDTO;
import com.nqdung.paymenthub.entity.GroupCategoryEntity;

import java.util.List;

public interface IGroupCategoryService {
    GroupCategoryDTO addParamType(GroupCategoryDTO groupCategoryDTO);
    List<GroupCategoryEntity> getAll();
    GroupCategoryEntity findById(Long id);
    GroupCategoryDTO editParam(Long id, GroupCategoryDTO newParam);
    void delete(Long id);
}
