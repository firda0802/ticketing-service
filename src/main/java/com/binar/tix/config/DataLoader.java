package com.binar.tix.config;

import com.binar.tix.entities.RoleUser;
import com.binar.tix.enums.RoleEnum;
import com.binar.tix.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<RoleUser> listRole = userService.getAllRole();
        if(listRole.isEmpty()){
            RoleUser role1 = new RoleUser(RoleEnum.BUYER.name());
            RoleUser role2 = new RoleUser(RoleEnum.ADMIN.name());
            userService.addRole(role1);
            userService.addRole(role2);
        }
    }
}
