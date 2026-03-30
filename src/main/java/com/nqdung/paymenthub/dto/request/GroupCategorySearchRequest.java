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
    private String newData;
    private Integer page = 0;
    private Integer size = 20;
    private String sortBy = "EFFECTIVE_DATE";
    private String sortOrder = "desc";
}
