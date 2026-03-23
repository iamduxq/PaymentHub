package com.nqdung.paymenthub.controller;

import com.nqdung.paymenthub.dto.GroupCategoryDTO;
import com.nqdung.paymenthub.dto.request.GroupCategoryCreateRequest;
import com.nqdung.paymenthub.dto.request.GroupCategorySearchRequest;
import com.nqdung.paymenthub.dto.request.GroupCategoryUpdateRequest;
import com.nqdung.paymenthub.mapper.GroupCategoryMapper;
import com.nqdung.paymenthub.service.IGroupCategoryProcedureService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/procedure/category")
@RequiredArgsConstructor
public class GroupCategoryProcedureController {
    private final IGroupCategoryProcedureService service;
    private final GroupCategoryMapper mapper;

    @GetMapping
    public List<GroupCategoryDTO> findAll() {
        return mapper.toDTOList(service.findAll());
    }

    @GetMapping("/get-all")
    public Page<GroupCategoryDTO> getAllWithPaging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return service.getAllWithPaging(page, size).map(mapper::toDTO);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody GroupCategoryCreateRequest request) {
        service.insertCategory(request);
        return ResponseEntity.ok("Thêm thành công");
    }

    @PostMapping("/search")
    public List<GroupCategoryDTO> search(@RequestBody GroupCategorySearchRequest searchRequest) {
        return service.findCategoryByParam(searchRequest);
    }

    @PutMapping("/update")
    public GroupCategoryDTO update(@RequestBody GroupCategoryUpdateRequest request) {
        return service.update(request);
    }

    @GetMapping("/{id}")
    public GroupCategoryDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
