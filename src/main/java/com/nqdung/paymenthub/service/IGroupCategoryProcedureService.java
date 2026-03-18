package com.nqdung.paymenthub.service;

import com.nqdung.paymenthub.dto.GroupCategoryDTO;
import com.nqdung.paymenthub.dto.request.GroupCategoryCreateRequest;
import com.nqdung.paymenthub.dto.request.GroupCategorySearchRequest;
import com.nqdung.paymenthub.dto.request.GroupCategoryUpdateRequest;
import com.nqdung.paymenthub.entity.GroupCategoryEntity;

import java.util.List;

public interface IGroupCategoryProcedureService {
    Long insertCategory(GroupCategoryCreateRequest request); // Thêm danh mục tham số
    List<GroupCategoryEntity> findAll(); // Lấy tất cả dữ liệu
    List<GroupCategoryDTO> findCategoryByParam(GroupCategorySearchRequest searchRequest); // Tìm dữ liệu theo param
    GroupCategoryDTO update(GroupCategoryUpdateRequest request); // Sửa dữ liệu
    GroupCategoryDTO findById(Long id); // Tìm theo id
    void delete(Long id); // Xóa dữ liệu
}
