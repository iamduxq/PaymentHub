package com.nqdung.paymenthub.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class GroupCategoryRequest {
    private Long id;
    private String paramType;
    private String paramValue;
    private String paramName;
    private String componentCode;
    private Integer status;
    private Integer isActive;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date effectiveDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endEffectiveDate;
}
