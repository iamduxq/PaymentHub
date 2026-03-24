package com.nqdung.paymenthub.controller;

import com.nqdung.paymenthub.dto.GroupCategoryDTO;
import com.nqdung.paymenthub.dto.request.GroupCategoryCreateRequest;
import com.nqdung.paymenthub.dto.request.GroupCategoryUpdateRequest;
import com.nqdung.paymenthub.entity.GroupCategoryEntity;
import com.nqdung.paymenthub.mapper.GroupCategoryMapper;
import com.nqdung.paymenthub.paging.PageResponse;
import com.nqdung.paymenthub.service.IGroupCategoryNativeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/native/category")
@RequiredArgsConstructor
public class GroupCategoryNativeController {
    private final IGroupCategoryNativeQueryService nativeQuery;
    private final GroupCategoryMapper mapper;

    @GetMapping
    public List<GroupCategoryEntity> findAll() {
        return nativeQuery.findAll();
    }

    @GetMapping("/getAll")
    public PageResponse<GroupCategoryDTO> getAllWithPaging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder
    ) {
        return nativeQuery.getAllWithPaging(page, size, sortBy, sortOrder);
    }

    @GetMapping("/search/{id}")
    public GroupCategoryEntity findById(@PathVariable Long id) {
        return nativeQuery.findById(id);
    }

    @PostMapping("/add-category")
    public ResponseEntity<?> addCategory(@RequestBody GroupCategoryCreateRequest request) {
        int result = nativeQuery.addCategory(request);
        if (result > 0) {
            return ResponseEntity.ok("Thêm danh mục tham số thành công");
        }
        return ResponseEntity.badRequest().body("Thêm danh mục thất bại");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody GroupCategoryUpdateRequest request) {
        int result = nativeQuery.updateCategory(id, request);
        return result > 0 ? ResponseEntity.ok("Chỉnh sửa thành công")
                : ResponseEntity.badRequest().body("Chỉnh sửa thất bại");
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        int rs = nativeQuery.deleteCategory(id);
        return rs > 0 ? ResponseEntity.ok("Xóa thành công")
                : ResponseEntity.badRequest().body("Xóa thất bại");
    }

}
