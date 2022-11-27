package com.binar.tix.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingResponse {
    private Long totalData;
    private int totalPaging;
}
