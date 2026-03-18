package com.nqdung.paymenthub.service.impl;

import com.nqdung.paymenthub.entity.ComponentsEntity;
import com.nqdung.paymenthub.repository.ComponentsRepository;
import com.nqdung.paymenthub.service.IComponentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComponentsService implements IComponentsService {

    private final ComponentsRepository componentsRepository;

    @Override
    public List<ComponentsEntity> findAll() {
        return componentsRepository.findAll();
    }
}
