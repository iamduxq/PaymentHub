package com.nqdung.paymenthub.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GroupCategorySearchRequest {
    private String paramType;
    private String paramValue;
    private String paramName;
    private String componentCode;
    private Integer status;
}
