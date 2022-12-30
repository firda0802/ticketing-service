package com.binar.tix.payload;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReqSigninGoogle {
    private String email;
    private String fullName;
    private String token;
}
