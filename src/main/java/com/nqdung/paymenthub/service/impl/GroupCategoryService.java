package com.nqdung.paymenthub.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupCategoryService implements IGroupCategoryService {

    private final GroupCategoryRepository categoryRepository;
    private final GroupCategoryMapper categoryGroupMapper;
    private final ObjectMapper objectMapper;
    private final PagingService paging;
    private static final List<String> ALLOWED_SORT = List.of("effectiveDate");


    // Thêm tham số danh mục theo nhóm
    @Override
    public GroupCategoryDTO addParamType(GroupCategoryCreateRequest dto) {
        GroupCategoryEntity entity = new GroupCategoryEntity();

        setNewData(entity, dto);

        entity.setStatus(CategoryConstants.STATUS_NEW);
        entity.setIsActive(1);
        entity.setIsDisplay(CategoryConstants.DISPLAY_ALLOW_DELETE);

        entity = categoryRepository.save(entity);
        return categoryGroupMapper.toDTO(entity);
    }

    // Lấy tất cả dữ liệu
    @Override
    public Page<GroupCategoryEntity> getAllWithPaging(int page, int size, String sortBy, String sortOrder) {
        return paging.getPage(categoryRepository, page, size, sortBy, sortOrder, ALLOWED_SORT);
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
        validateEditable(entity);

        // setNewData từ objectMapper
        setNewData(entity, newParam);
        entity.setStatus(CategoryConstants.STATUS_NEW);
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
        validateSendApprove(entity);

        // Kiểm tra tồn tại
        validateDuplicate(entity, id);

        // setNewData từ objectMapper
        setNewData(entity, request);

        entity.setStatus(CategoryConstants.STATUS_PENDING);
        categoryRepository.save(entity);
    }

    // Phê duyệt
    @Override
    public void approve(Long id) {
        GroupCategoryEntity entity = findCategory(id);

        // Kiểm tra xác thực chờ duyệt
        validatePending(entity);

        String newDataJson = entity.getNewData();
        if (newDataJson == null || newDataJson.isEmpty()) {
            throw new RuntimeException("Không tìm thấy newData để phê duyệt");
        }

        // Đọc dữ liệu từ NewData
        GroupCategoryRequest newData = readNewData(entity, GroupCategoryRequest.class);

        applyApprovedData(entity, newData);
        // Setter NEW_DATA
        applyApprovedData(entity, newData);
        entity.setStatus(CategoryConstants.STATUS_APPROVED);
        entity.setIsDisplay(CategoryConstants.DISPLAY_NO_DELETE);
        categoryRepository.save(entity);
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

    private void setNewData(GroupCategoryEntity entity, Object newData) {
        try {
            entity.setNewData(objectMapper.writeValueAsString(newData));
        } catch (JsonProcessingException exception) {
            throw new RuntimeException("Lỗi mapping NEW_DATA");
        }
    }

    // Đọc dữ liệu từ NewData
    private <T> T readNewData(GroupCategoryEntity entity, Class<T> tClass) {
        try {
            return objectMapper.readValue(entity.getNewData(), tClass);
        } catch (JsonProcessingException exception) {
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
