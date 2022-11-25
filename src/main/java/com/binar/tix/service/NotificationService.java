/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.binar.tix.service;

import com.binar.tix.entities.NotifCategory;
import com.binar.tix.payload.ReqSigninup;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Riko
 */
public interface NotificationService {

    void createNotificationCategory(String name);

    List<NotifCategory> getAllNotifCategory();

    Boolean updateNotifCategory(NotifCategory req);

    Boolean deleteCategory(Integer id);
}
