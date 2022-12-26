package com.binar.tix.config;

import com.binar.tix.entities.*;
import com.binar.tix.enums.RoleEnum;
import com.binar.tix.service.InitializeService;
import com.binar.tix.service.NotificationService;
import com.binar.tix.service.UserService;
import com.binar.tix.utility.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    UserService userService;

    @Autowired
    InitializeService initializeService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    SetupData setupData;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<RoleUser> listRole = userService.getAllRole();
        if (listRole.isEmpty()) {
            RoleUser role1 = new RoleUser(RoleEnum.BUYER.name());
            RoleUser role2 = new RoleUser(RoleEnum.ADMIN.name());
            userService.addRole(role1);
            userService.addRole(role2);
        }

        List<NotifCategory> notifCategories = notificationService.getAllNotifCategory();
        if(notifCategories.isEmpty()){
            notificationService.createUpdateNotifCategory(0, "Introduce");
            notificationService.createUpdateNotifCategory(0, "Booking");
            notificationService.createUpdateNotifCategory(0, "Event");
        }

        if(initializeService.dataAdmin().isEmpty()){
            Users admin = new Users();
            admin.setEmail("admin@safly.com");
            admin.setPassword(MD5.encrypt("admin123"));
            admin.setStatus(Boolean.TRUE);
            admin.setRoleId(2);
            admin.setFullName("Safly Admin");
            initializeService.initAdmin(admin);
        }

        setupData.generate();
    }
}
