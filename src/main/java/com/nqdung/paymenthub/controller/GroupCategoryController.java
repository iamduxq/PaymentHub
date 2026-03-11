package com.nqdung.paymenthub.controller;

import com.nqdung.paymenthub.dto.GroupCategoryDTO;
import com.nqdung.paymenthub.entity.GroupCategoryEntity;
import com.nqdung.paymenthub.service.impl.IGroupCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class GroupCategoryController {

    private final IGroupCategoryService groupCategoryService;

    @PostMapping("/add-param-type")
    public ResponseEntity<?> addParamType(@RequestBody GroupCategoryDTO groupCategory) {
        return ResponseEntity.ok(groupCategoryService.addParamType(groupCategory));
    }

    @GetMapping
    public List<GroupCategoryEntity> getAll() {
        return groupCategoryService.getAll();
    }

    @GetMapping("/search/{id}")
    public GroupCategoryEntity findById(@PathVariable Long id) {
        return groupCategoryService.findById(id);
    }

    @PutMapping("/edit/{id}")
    public GroupCategoryDTO editParam(@PathVariable Long id, @RequestBody GroupCategoryDTO newParam) {
        return groupCategoryService.editParam(id, newParam);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<GroupCategoryEntity> delete(@PathVariable Long id) {
        groupCategoryService.delete(id);
        return ResponseEntity.ok().build();
    }
}
