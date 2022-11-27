package com.binar.tix.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PagingRequest {
    private int limit;
    private int pageNumber;
    private Integer id;
    private String search;
}
