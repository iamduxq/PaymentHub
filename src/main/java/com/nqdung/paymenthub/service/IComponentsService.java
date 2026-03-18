package com.nqdung.paymenthub.service;

import com.nqdung.paymenthub.entity.ComponentsEntity;

import java.util.List;

public interface IComponentsService {
    List<ComponentsEntity> findAll();
}
