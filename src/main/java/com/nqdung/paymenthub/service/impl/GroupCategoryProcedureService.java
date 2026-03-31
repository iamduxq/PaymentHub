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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupCategoryProcedureService implements IGroupCategoryProcedureService {
    private final GroupCategoryProcedureRepository procedure;
    private final GroupCategoryMapper mapper;

    @Override
    @Transactional
    public Long insertCategory(GroupCategoryCreateRequest request, boolean isSendApprove) {
        return procedure.insertCategory(request, isSendApprove);
    }

    @Override
    public List<GroupCategoryEntity> findAll() {
        return procedure.findAll();
    }

    @Override
    @Transactional
    public void update(Long id, GroupCategoryUpdateRequest request, boolean isSendApprove) {
        request.setId(id);
        System.out.println("Update ID: " + request.getId());
        procedure.update(request, isSendApprove);
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
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        GroupCategoryEntity entity = procedure.findById(id);
        if (entity != null) {
            procedure.delete(entity.getId());
        } else {
            throw new RuntimeException("Không tìm thấy dữ liệu để xóa!");
        }
    }

    @Override
    public Page<GroupCategoryEntity> getAllWithPaging(int page, int size, String sortBy, String sortOrder) {
        return procedure.getAllWithPaging(page, size, sortBy, sortOrder);
    }

    @Override
    public Page<GroupCategoryEntity> searchDynamic(GroupCategorySearchRequest request) {
        return procedure.searchDynamic(request);
    }

    @Override
    public void cancel(Long id) {
        GroupCategoryEntity entity = procedure.findById(id);
        if (entity == null) {
            throw new RuntimeException("Không tìm thấy tham số cấu hình");
        }
        procedure.cancel(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reject(Long id, String reason) {
        GroupCategoryEntity entity = procedure.findById(id);
        if (entity == null) {
            throw new RuntimeException("Không tìm thấy tham số cấu hình");
        }
        procedure.reject(id, reason);
    }

}
