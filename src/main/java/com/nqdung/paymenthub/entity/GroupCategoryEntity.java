package com.nqdung.paymenthub.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "PMH_GROUP_CATEGORY")
@Getter
@Setter
public class GroupCategoryEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PARAM_NAME")
    private String paramName;

    @Column(name = "PARAM_VALUE")
    private String paramValue;

    @Column(name = "PARAM_TYPE")
    private String paramType;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "COMPONENT_CODE")
    private String componentCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPONENT_CODE", referencedColumnName = "COMPONENT_CODE", insertable = false, updatable = false)
    private ComponentsEntity component;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "IS_ACTIVE")
    private Integer isActive;

    @Column(name = "IS_DISPLAY")
    private Integer isDisplay;

    @Column(name = "NEW_DATA")
    private String newData;

    @Column(name = "EFFECTIVE_DATE")
    private Date effectiveDate;

    @Column(name = "END_EFFECTIVE_DATE")
    private Date endEffectiveDate;

}
