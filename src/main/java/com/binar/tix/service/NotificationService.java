/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.binar.tix.service;

import com.binar.tix.entities.NotifCategory;
import com.binar.tix.entities.Notifications;
import com.binar.tix.payload.ReqCreateNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 *
 * @author Riko
 */
public interface NotificationService {

    void createNotifUsers(ReqCreateNotification req);

    Page<Notifications> getNotifUsers(Pageable paging, int userId);

    int countNotifUsers(int userId);
    void createUpdateNotifCategory(int id, String name);

    List<NotifCategory> getAllNotifCategory();

    Boolean deleteCategory(Integer id);

    void clearNotif(int userId);
}
