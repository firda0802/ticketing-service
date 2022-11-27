package com.binar.tix.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReqUpdateUser {
    private String fullName;
    private Date birthDate;
    private String phoneNo;
    private String address;
}
