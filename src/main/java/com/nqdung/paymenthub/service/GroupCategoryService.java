package com.nqdung.paymenthub.service;

import com.nqdung.paymenthub.dto.GroupCategoryDTO;
import com.nqdung.paymenthub.dto.request.GroupCategoryCreateRequest;
import com.nqdung.paymenthub.dto.request.GroupCategoryUpdateRequest;
import com.nqdung.paymenthub.entity.GroupCategoryEntity;
import com.nqdung.paymenthub.mapper.GroupCategoryMapper;
import com.nqdung.paymenthub.repository.GroupCategoryRepository;
import com.nqdung.paymenthub.service.impl.IGroupCategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupCategoryService implements IGroupCategoryService {

    private final GroupCategoryRepository categoryRepository;
    private final GroupCategoryMapper categoryGroupMapper;

    // Thêm tham số danh mục theo nhóm
    @Override
    public GroupCategoryDTO addParamType(GroupCategoryCreateRequest dto) {
        GroupCategoryEntity entity = new GroupCategoryEntity();
        entity.setParamType(dto.getParamType());
        entity.setParamValue(dto.getParamValue());
        entity.setParamName(dto.getParamName());
        entity.setComponentCode(dto.getComponentCode());
        entity.setEffectiveDate(dto.getEffectiveDate());
        entity.setEndEffectiveDate(dto.getEndEffectiveDate());
        entity.setStatus(dto.getStatus());
        entity.setIsActive(dto.getIsActive());
        entity.setIsDisplay(dto.getIsDisplay());
        entity.setNewData(dto.getNewData());
        entity.setDescription(dto.getDescription());
        entity = categoryRepository.save(entity);
        return categoryGroupMapper.toDTO(entity);
    }

    @Override
    public List<GroupCategoryEntity> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public GroupCategoryEntity findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục"));
    }

    @Transactional
    @Override
    public GroupCategoryDTO editParam(Long id, GroupCategoryUpdateRequest newParam) {
        GroupCategoryEntity param = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục"));
        param.setParamValue(newParam.getParamValue());
        param.setParamName(newParam.getParamName());
        param.setComponentCode(newParam.getComponentCode());
        param.setEffectiveDate(newParam.getEffectiveDate());
        param.setEndEffectiveDate(newParam.getEndEffectiveDate());
        param.setDescription(newParam.getDescription());
        return categoryGroupMapper.toDTO(param);
    }

    @Override
    public List<GroupCategoryEntity> findByParamCategory(String paramType, String paramValue,
                                                         String paramName, String componentCode, Integer status) {
        List<GroupCategoryEntity> categories = categoryRepository.findGroupCategoryByParamType(paramType, paramValue, paramName, componentCode, status);
        if (categories.isEmpty()) {
            throw new RuntimeException("Không có danh mục nào");
        }
        return categories;
    }

    @Override
    public void delete(Long id) {
        GroupCategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy dữ liệu"));
        categoryRepository.delete(category);
    }
}
