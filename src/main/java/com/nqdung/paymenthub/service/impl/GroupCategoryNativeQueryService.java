package com.nqdung.paymenthub.service.impl;

import com.nqdung.paymenthub.dto.GroupCategoryDTO;
import com.nqdung.paymenthub.dto.request.GroupCategoryCreateRequest;
import com.nqdung.paymenthub.dto.request.GroupCategoryUpdateRequest;
import com.nqdung.paymenthub.entity.GroupCategoryEntity;
import com.nqdung.paymenthub.mapper.GroupCategoryMapper;
import com.nqdung.paymenthub.paging.PageResponse;
import com.nqdung.paymenthub.repository.GroupCategoryNativeQueryRepository;
import com.nqdung.paymenthub.service.IGroupCategoryNativeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupCategoryNativeQueryService implements IGroupCategoryNativeQueryService {

    private final GroupCategoryNativeQueryRepository nativeQuery;
    private final GroupCategoryMapper mapper;

    @Override
    public List<GroupCategoryEntity> findAll() {
        return nativeQuery.findAll();
    }

    @Override
    public PageResponse<GroupCategoryDTO> getAllWithPaging(int page, int size, String sortBy, boolean asc) {
        PageResponse<GroupCategoryEntity> pageEntity = nativeQuery.getAllWithPaging(page, size, sortBy, asc);
        List<GroupCategoryDTO> dtos = mapper.toDTOList(pageEntity.getData());
        return new PageResponse<>(dtos, pageEntity.getTotal(), pageEntity.getPage(), pageEntity.getSize(), sortBy, asc);
    }


    @Override
    public GroupCategoryEntity findById(Long id) {
        return nativeQuery.findById(id);
    }

    @Override
    public int addCategory(GroupCategoryCreateRequest request) {
        return nativeQuery.addParamCategory(request);
    }

    @Override
    public int updateCategory(Long id, GroupCategoryUpdateRequest request) {
        return nativeQuery.editParamGroup(id, request);
    }

    @Override
    public int deleteCategory(Long id) {
        return nativeQuery.deleteParamGroup(id);
    }
}
