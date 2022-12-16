package com.binar.tix.payload;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RespDataGoogle {
    private String fullName;
    private String email;
    private String type;
    private String token;
}
