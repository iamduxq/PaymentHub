package com.nqdung.paymenthub.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PMH_COMPONENTS")
@Getter @Setter
public class ComponentsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Long id;

    @Column(name = "COMPONENT_CODE")
    private String componentCode;
}
