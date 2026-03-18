package com.nqdung.paymenthub.repository;

import com.nqdung.paymenthub.entity.ComponentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComponentsRepository extends JpaRepository<ComponentsEntity, Long> {
}
