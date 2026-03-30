package com.nqdung.paymenthub.service;

import com.nqdung.paymenthub.dto.GroupCategoryDTO;
import com.nqdung.paymenthub.dto.request.GroupCategoryCreateRequest;
import com.nqdung.paymenthub.dto.request.GroupCategorySearchRequest;
import com.nqdung.paymenthub.dto.request.GroupCategoryUpdateRequest;
import com.nqdung.paymenthub.entity.GroupCategoryEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IGroupCategoryProcedureService {
    Long insertCategory(GroupCategoryCreateRequest request); // Thêm danh mục tham số
    List<GroupCategoryEntity> findAll(); // Lấy tất cả dữ liệu
    List<GroupCategoryDTO> findCategoryByParam(GroupCategorySearchRequest searchRequest); // Tìm dữ liệu theo param
    GroupCategoryDTO update(GroupCategoryUpdateRequest request); // Sửa dữ liệu
    GroupCategoryDTO findById(Long id); // Tìm theo id
    void delete(Long id); // Xóa dữ liệu
    Page<GroupCategoryEntity> getAllWithPaging(int page, int size, String sortBy, String sortOrder); // Lấy tất cả dữ liệu với paging
    Page<GroupCategoryEntity> searchDynamic1(GroupCategorySearchRequest request); // Search dynamic
    Page<GroupCategoryEntity> searchDynamic4(GroupCategorySearchRequest request);
}
