package com.binar.tix.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqCreateNotification {
    private int notificationCategoryId;
    private String title;
    private String content;
    private int userId;

}
