package com.nqdung.paymenthub.service.impl;

import com.nqdung.paymenthub.dto.GroupCategoryDTO;
import com.nqdung.paymenthub.dto.request.GroupCategoryCreateRequest;
import com.nqdung.paymenthub.dto.request.GroupCategorySearchRequest;
import com.nqdung.paymenthub.dto.request.GroupCategoryUpdateRequest;
import com.nqdung.paymenthub.entity.GroupCategoryEntity;
import com.nqdung.paymenthub.mapper.GroupCategoryMapper;
import com.nqdung.paymenthub.repository.GroupCategoryProcedureRepository;
import com.nqdung.paymenthub.service.IGroupCategoryProcedureService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupCategoryProcedureService implements IGroupCategoryProcedureService {
    private final GroupCategoryProcedureRepository procedure;
    private final GroupCategoryMapper mapper;

    @Override
    public Long insertCategory(GroupCategoryCreateRequest request) {
        return procedure.insertCategory(request);
    }

    @Override
    public List<GroupCategoryEntity> findAll() {
        return procedure.findAll();
    }

    @Override
    public List<GroupCategoryDTO> findCategoryByParam(GroupCategorySearchRequest searchRequest) {
        return mapper.toDTOList(procedure.search(searchRequest));
    }

    @Override
    public GroupCategoryDTO update(GroupCategoryUpdateRequest request) {
        System.out.println("Update ID: " + request.getId());
        procedure.update(request);
        GroupCategoryEntity update = procedure.findById(request.getId());
        System.out.println("After update: " + update);
        if (update == null) {
            throw new RuntimeException("Dữ liệu không tồn tại");
        }
        return mapper.toDTO(update);
    }

    @Override
    public GroupCategoryDTO findById(Long id) {
        GroupCategoryEntity entity = procedure.findById(id);
        if (entity == null) {
            throw new RuntimeException("Không tìm thấy dữ liệu với id: " + id);
        }
        return mapper.toDTO(entity);
    }

    @Override
    public void delete(Long id) {
        GroupCategoryEntity entity = procedure.findById(id);
        if (entity != null) {
            procedure.delete(entity.getId());
        }
    }

    @Override
    public Page<GroupCategoryEntity> getAllWithPaging(int page, int size, String sortBy, String sortOrder) {
        return procedure.getAllWithPaging(page, size, sortBy, sortOrder);
    }
}
