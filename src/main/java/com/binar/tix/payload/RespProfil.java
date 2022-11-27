package com.binar.tix.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class RespProfil {
    private Integer userId;
    private String fullName;
    private String email;
    private Date birthDate;
    private String phoneNo;
    private String address;
}
