package com.nqdung.paymenthub.controller;

import com.nqdung.paymenthub.dto.GroupCategoryDTO;
import com.nqdung.paymenthub.dto.request.GroupCategoryCreateRequest;
import com.nqdung.paymenthub.dto.request.GroupCategorySearchRequest;
import com.nqdung.paymenthub.dto.request.GroupCategoryUpdateRequest;
import com.nqdung.paymenthub.mapper.GroupCategoryMapper;
import com.nqdung.paymenthub.paging.PageMapper;
import com.nqdung.paymenthub.paging.PageResponseProcedure;
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
    public PageResponseProcedure<GroupCategoryDTO> getAllWithPaging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "EFFECTIVE_DATE") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortOrder
    ) {
        Page<GroupCategoryDTO> result = service.getAllWithPaging(page, size, sortBy, sortOrder).map(mapper::toDTO);
        return PageMapper.toResponseProcedure(result, sortBy, sortOrder);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(
            @RequestBody GroupCategoryCreateRequest request,
            @RequestParam(name = "isSendApprove", defaultValue = "false") boolean isSendApprove
    ) {
            service.insertCategory(request, isSendApprove);
            String successMessage = isSendApprove ? "Gửi duyệt thành công" : "Lưu thành công";
            return ResponseEntity.ok(successMessage);
    }

    @GetMapping("/search")
    public PageResponseProcedure<GroupCategoryDTO> searchDynamic(
            GroupCategorySearchRequest request
            ) {
        Page<GroupCategoryDTO> result = service.searchDynamic(request).map(mapper::toDTO);
        return PageMapper.toResponseProcedure(result, request);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody GroupCategoryUpdateRequest request,
            @RequestParam(defaultValue = "false") boolean isSendApprove
    ) {
            request.setId(id);
            service.update(id, request, isSendApprove);
            String message = isSendApprove ? "Cập nhật và gửi duyệt thành công!" : "Lưu thay đổi thành công!";
            return ResponseEntity.ok(message);
    }

    @GetMapping("/{id}")
    public GroupCategoryDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Xóa tham số cấu hình thành công");
    }

    @PostMapping("/cancel-approve/{id}")
    public ResponseEntity<?> cancel(@PathVariable Long id) {
        service.cancel(id);
        return ResponseEntity.ok("Hủy phê duyệt thành công");
    }

    @PostMapping("/reject/{id}")
    public ResponseEntity<?> reject(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "Nội dung không phù hợp") String reason
    ) {
        service.reject(id, reason);
        return ResponseEntity.ok("Đã từ chối phê duyệt tham số cấu hình");
    }
}
