package com.nqdung.paymenthub.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class GroupCategoryCreateRequest {
    private Long ID;
    private String paramName;
    private String paramValue;
    private String paramType;
    private String description;
    private String componentCode;
    private Integer status;
    private Integer isActive;
    private Integer isDisplay;
    private String newData;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date effectiveDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endEffectiveDate;
}
