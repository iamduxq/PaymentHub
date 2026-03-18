package com.nqdung.paymenthub.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GroupCategorySearchRequest {
    private String paramType;
    private String paramValue;
    private String paramName;
    private Integer status;
    private Integer isActive;
}
