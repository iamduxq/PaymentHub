package com.nqdung.paymenthub.controller;

import com.nqdung.paymenthub.entity.ComponentsEntity;
import com.nqdung.paymenthub.service.IComponentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/components")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class ComponentController {

    private final IComponentsService componentsService;

    @GetMapping
    public List<ComponentsEntity> getAllComponent() {
        return componentsService.findAll();
    }
}
