package com.nqdung.paymenthub.service;

import com.nqdung.paymenthub.dto.GroupCategoryDTO;
import com.nqdung.paymenthub.dto.request.GroupCategoryCreateRequest;
import com.nqdung.paymenthub.dto.request.GroupCategorySearchRequest;
import com.nqdung.paymenthub.dto.request.GroupCategoryUpdateRequest;
import com.nqdung.paymenthub.entity.GroupCategoryEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IGroupCategoryService {
    GroupCategoryDTO addParamType(GroupCategoryCreateRequest groupCategoryDTO);
    Page<GroupCategoryEntity> getAll(int page, int size, String sortBy, boolean ascending);
    GroupCategoryEntity findById(Long id);
    GroupCategoryDTO editParam(Long id, GroupCategoryUpdateRequest newParam);
    void delete(Long id);
    List<GroupCategoryEntity> findCategoryWithCustomMatches(GroupCategorySearchRequest request);
}
