package com.nqdung.paymenthub.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class ActionResponse <T>{
    private String message;
    private T data;
}
