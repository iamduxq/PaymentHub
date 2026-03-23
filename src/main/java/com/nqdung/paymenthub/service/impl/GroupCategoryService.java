package com.nqdung.paymenthub.service.impl;

import com.nqdung.paymenthub.constant.CategoryConstants;
import com.nqdung.paymenthub.dto.GroupCategoryDTO;
import com.nqdung.paymenthub.dto.request.GroupCategoryCreateRequest;
import com.nqdung.paymenthub.dto.request.GroupCategoryRequest;
import com.nqdung.paymenthub.dto.request.GroupCategorySearchRequest;
import com.nqdung.paymenthub.dto.request.GroupCategoryUpdateRequest;
import com.nqdung.paymenthub.entity.GroupCategoryEntity;
import com.nqdung.paymenthub.mapper.GroupCategoryMapper;
import com.nqdung.paymenthub.paging.PagingService;
import com.nqdung.paymenthub.repository.GroupCategoryRepository;
import com.nqdung.paymenthub.repository.GroupCategorySpecification;
import com.nqdung.paymenthub.service.IGroupCategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.json.JsonParseException;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupCategoryService implements IGroupCategoryService {

    private final GroupCategoryRepository categoryRepository;
    private final GroupCategoryMapper categoryGroupMapper;
    private final ObjectMapper objectMapper;
    private final PagingService paging;

    // Thêm tham số danh mục theo nhóm
    @Override
    public GroupCategoryDTO addParamType(GroupCategoryCreateRequest dto) {
        GroupCategoryEntity entity = new GroupCategoryEntity();

        try {
            String json = objectMapper.writeValueAsString(dto);
            entity.setNewData(json);
        } catch (JsonParseException exception) {
            throw new RuntimeException("Lỗi mapping NEW_DATA");
        }

        // Workflow mâu thuẫn test sau
//        entity.setParamType(dto.getParamType());
//        entity.setParamValue(dto.getParamValue());
//        entity.setParamName(dto.getParamName());
//        entity.setComponentCode(dto.getComponentCode());
//        entity.setEffectiveDate(dto.getEffectiveDate());
//        entity.setEndEffectiveDate(dto.getEndEffectiveDate());
//        entity.setDescription(dto.getDescription());
//        entity.setStatus(CategoryConstants.STATUS_NEW);
        entity.setIsActive(1);
        entity.setIsDisplay(CategoryConstants.DISPLAY_ALLOW_DELETE);

        entity = categoryRepository.save(entity);
        return categoryGroupMapper.toDTO(entity);
    }

    // Lấy tất cả dữ liệu
    @Override
    public Page<GroupCategoryEntity> getAllWithPaging(int page, int size, String sortBy, boolean ascending) {
        return paging.getPage(categoryRepository, page, size, sortBy, ascending, List.of("effectiveDate"));
    }


    // Tìm record theo id
    @Override
    public GroupCategoryEntity findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục"));
    }

    // Sửa dữ liệu
    @Transactional
    @Override
    public GroupCategoryDTO editParam(Long id, GroupCategoryUpdateRequest newParam) {
        GroupCategoryEntity entity = findCategory(id);

        // Kiểm tra xác thực chỉnh sửa
        if (entity.getStatus() == CategoryConstants.STATUS_PENDING) {
            throw new RuntimeException("Trạng thái đang chờ duyệt, không thể chỉnh sửa");
        }

        // setNewData từ objectMapper
        try {
            String json = objectMapper.writeValueAsString(newParam);
            entity.setNewData(json);
            entity.setStatus(CategoryConstants.STATUS_NEW);
            categoryRepository.save(entity);
        } catch (JsonParseException exception) {
            throw new RuntimeException("Lỗi đóng gói dữ liệu");
        }

//        param.setParamValue(newParam.getParamValue());
//        param.setParamName(newParam.getParamName());
//        param.setComponentCode(newParam.getComponentCode());
//        param.setEffectiveDate(newParam.getEffectiveDate());
//        param.setEndEffectiveDate(newParam.getEndEffectiveDate());
//        param.setDescription(newParam.getDescription());
        return categoryGroupMapper.toDTO(entity);
    }


    // Xóa dữ liệu
    @Override
    public void delete(Long id) {
        GroupCategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy dữ liệu"));
        categoryRepository.delete(category);
    }


    // Tìm kiếm động
    @Override
    public List<GroupCategoryEntity> findCategoryWithCustomMatches(GroupCategorySearchRequest request) {
        Specification<GroupCategoryEntity> spec = GroupCategorySpecification.filter(request);
        return categoryRepository.findAll(spec);
    }

    // Gửi duyệt
    @Transactional
    @Override
    public void sendToApprove(Long id, GroupCategoryRequest request) {
        GroupCategoryEntity entity = findCategory(id);

        // Kiểm tra xác thực phê duyệt
        int currStatus = entity.getStatus();
        if (currStatus != CategoryConstants.STATUS_NEW
                && currStatus != CategoryConstants.STATUS_REJECTED
                && currStatus != CategoryConstants.STATUS_CANCELLED) {
            throw new RuntimeException("Trạng thái không hợp lệ để gửi phê duyệt");
        }

        // Kiểm tra tồn tại
        validateDuplicate(entity, id);

        try {
            String newDataJS = objectMapper.writeValueAsString(request);
            entity.setNewData(newDataJS);
        } catch (JsonParseException exception) {
            throw new RuntimeException("Lỗi chuyển đổi dữ lieute NEW_DATA", exception);
        }
        entity.setStatus(CategoryConstants.STATUS_PENDING);
        categoryRepository.save(entity);
    }

    // Phê duyệt
    @Override
    public void approve(Long id) {
        GroupCategoryEntity entity = findCategory(id);

        if (entity.getStatus() != CategoryConstants.STATUS_PENDING) {
            throw new RuntimeException("Chỉ có thể phê duyệt ở trạng thái Chờ duyệt");
        }

        String newDataJson = entity.getNewData();
        if (newDataJson == null || newDataJson.isEmpty()) {
            throw new RuntimeException("Không tìm thấy newData để phê duyệt");
        }

        try {
            GroupCategoryRequest newData = objectMapper.readValue(newDataJson, GroupCategoryRequest.class);

            // Setter NEW_DATA
            applyApprovedData(entity, newData);
            categoryRepository.save(entity);
        } catch (JsonParseException e) {
            throw new RuntimeException("Lỗi khi phê duyệt NEW_DATA", e);
        }
    }

    @Override
    public void cancelApprove(Long id) {
        GroupCategoryEntity entity = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tham số cấu hình!"));

        if (entity.getStatus() != CategoryConstants.STATUS_APPROVED) {
            throw new RuntimeException("Chỉ có thể hủy phê duyệt ở trong trạng thái đã phê duyệt!");
        }

        entity.setStatus(CategoryConstants.STATUS_CANCELLED);
        categoryRepository.save(entity);
    }

    // Tìm danh mục theo id
    private GroupCategoryEntity findCategory(Long id) {
        return findById(id);
    }

    private <T> T readNewData(GroupCategoryEntity entity, Class<T> tClass) {
        try {
            return objectMapper.readValue(entity.getNewData(), tClass);
        } catch (JsonParseException exception) {
            throw new RuntimeException("Không thể đọc NEW_DATA", exception);
        }
    }

    // VALIDATION
    // Kiểm tra xác thực chỉnh sửa
    private void validateEditable(GroupCategoryEntity entity) {
        if (entity.getStatus() == CategoryConstants.STATUS_PENDING) {
            throw new RuntimeException("Trạng thái đang chờ duyệt không thể chỉnh sửa");
        }
    }

    // Kiểm tra xác thực phê duyệt
    private void validateSendApprove(GroupCategoryEntity entity) {
        int status = entity.getStatus();
        if (status != CategoryConstants.STATUS_NEW
        && status != CategoryConstants.STATUS_REJECTED
        && status != CategoryConstants.STATUS_CANCELLED) {
            throw new RuntimeException("Trạng thái không hợp lệ để gửi duyệt");
        }
    }

    // Kiểm tra xác thực chờ duyệt
    private void validatePending(GroupCategoryEntity entity) {
        if (entity.getStatus() != CategoryConstants.STATUS_PENDING) {
            throw new RuntimeException("Chỉ phê duyệt khi đang chờ duyệt");
        }
    }

    // Kiểm tra tồn tại
    private void validateDuplicate(GroupCategoryEntity req, Long id) {
        boolean checkDuplicate = categoryRepository.existsByParamNameAndParamTypeAndParamValueAndIdNot(
                req.getParamName(),
                req.getParamType(),
                req.getParamValue(),
                id);
        if (checkDuplicate) throw new IllegalArgumentException("Tham số cấu hình đã tôn tại");
    }

    // Setter NEW_DATA
    private void applyApprovedData(GroupCategoryEntity entity, GroupCategoryRequest newData) {
        entity.setParamName(newData.getParamName());
        entity.setParamValue(newData.getParamValue());
        entity.setParamType(newData.getParamType());
        entity.setDescription(newData.getDescription());
        entity.setComponentCode(newData.getComponentCode());
        entity.setEffectiveDate(newData.getEffectiveDate());
        entity.setEndEffectiveDate(newData.getEndEffectiveDate());
        entity.setIsActive(newData.getIsActive());
        entity.setStatus(CategoryConstants.STATUS_APPROVED);
        entity.setIsActive(1);
        entity.setIsDisplay(CategoryConstants.DISPLAY_NO_DELETE);
        entity.setNewData(null);
    }
}
