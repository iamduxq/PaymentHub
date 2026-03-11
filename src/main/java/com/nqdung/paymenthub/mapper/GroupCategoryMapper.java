package com.nqdung.paymenthub.mapper;

import com.nqdung.paymenthub.dto.GroupCategoryDTO;
import com.nqdung.paymenthub.entity.GroupCategoryEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GroupCategoryMapper {
    public GroupCategoryDTO toDTO(GroupCategoryEntity entity) {
        if (entity == null) return null;
        GroupCategoryDTO dto = new GroupCategoryDTO();
        dto.setID(entity.getID());
        dto.setParamType(entity.getParamType());
        dto.setParamValue(entity.getParamValue());
        dto.setParamName(entity.getParamName());
        dto.setStatus(entity.getStatus());
        dto.setIsActive(entity.getIsActive());
        dto.setIsDisplay(entity.getIsDisplay());
        dto.setDescription(entity.getDescription());
        dto.setComponentCode(entity.getComponentCode());
        dto.setEffectiveDate(entity.getEffectiveDate());
        dto.setEndEffectiveDate(entity.getEndEffectiveDate());
        return dto;
    }

    public List<GroupCategoryDTO> toDTOList(List<GroupCategoryEntity> entities) {
        if (entities == null) return List.of();
        return entities.stream().map(this::toDTO).toList();
    }
}
