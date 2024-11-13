package com.contentfarm.api.domain.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseResult<T> {
    private Integer code;
    private String message;
    private T data;
}
