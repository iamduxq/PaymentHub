package com.nqdung.paymenthub.controller;

import com.nqdung.paymenthub.dto.GroupCategoryDTO;
import com.nqdung.paymenthub.dto.request.GroupCategoryCreateRequest;
import com.nqdung.paymenthub.dto.request.GroupCategorySearchRequest;
import com.nqdung.paymenthub.dto.request.GroupCategoryUpdateRequest;
import com.nqdung.paymenthub.entity.GroupCategoryEntity;
import com.nqdung.paymenthub.service.IGroupCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class GroupCategoryController {

    private final IGroupCategoryService groupCategoryService;

    // JPA Query
    // Thêm danh mục tham số
    @PostMapping("/add-param-type")
    public ResponseEntity<?> addParamType(@RequestBody GroupCategoryCreateRequest groupCategory) {
        return ResponseEntity.ok(groupCategoryService.addParamType(groupCategory));
    }

    // Lấy tất cả danh mục
    @GetMapping
    public List<GroupCategoryEntity> getAll() {
        return groupCategoryService.getAll();
    }

    // Tìm danh mục theo id
    @GetMapping("/search/{id}")
    public GroupCategoryEntity findById(@PathVariable Long id) {
        return groupCategoryService.findById(id);
    }

    // Tìm kiếm danh mục theo nhóm
    @PostMapping("/search")
    public ResponseEntity<List<GroupCategoryEntity>> search(@RequestBody GroupCategorySearchRequest request) {
        return ResponseEntity.ok(groupCategoryService.findCategoryWithCustomMatches(request));
    }

    // Sửa danh mục theo id
    @PutMapping("/edit/{id}")
    public GroupCategoryDTO editParam(@PathVariable Long id, @RequestBody GroupCategoryUpdateRequest newParam) {
        return groupCategoryService.editParam(id, newParam);
    }

    // Xóa danh mục theo id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        groupCategoryService.delete(id);
        return ResponseEntity.ok().build();
    }
}
