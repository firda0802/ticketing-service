package com.binar.tix.service;

import com.binar.tix.entities.NotifCategory;
import com.binar.tix.entities.Notifications;
import com.binar.tix.payload.ReqCreateNotification;
import com.binar.tix.repository.NotifCategoryRepository;
import com.binar.tix.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService{

    @Autowired
    private NotificationRepository notifRepository;
    @Autowired
    private NotifCategoryRepository notifCategoryRepository;

    @Override
    public void createNotifUsers(ReqCreateNotification req) {
        Notifications notif = new Notifications();
        notif.setStatus(Boolean.TRUE);
        notif.setTitle(req.getTitle());
        notif.setContent(req.getContent());
        notif.setNotificationCategoryId(req.getNotificationCategoryId());
        notif.setUserId(req.getUserId());
        notifRepository.save(notif);
    }

    @Override
    public Page<Notifications> getNotifUsers(Pageable paging, int idUsers) {
        notifRepository.clearNotif(idUsers);
        return notifRepository.findByUserId(idUsers, paging);
    }

    @Override
    public int countNotifUsers(int idUsers) {
        return notifRepository.getCount(idUsers).getJumlah();
    }

    @Override
    public void createUpdateNotifCategory(int id, String name) {
        NotifCategory notifCategory = notifCategoryRepository.findById(id).orElse(new NotifCategory());
        notifCategory.setNotifcategoryName(name);
        notifCategoryRepository.saveAndFlush(notifCategory);
    }

    @Override
    public List<NotifCategory> getAllNotifCategory() {
        return notifCategoryRepository.findAll();
    }

    @Override
    public Boolean deleteCategory(Integer id) {
        Optional<NotifCategory> data = notifCategoryRepository.findById(id);
        if(data.isPresent()){
            NotifCategory update = data.get();
            notifCategoryRepository.delete(update);
            return true;
        }else{
            return false;
        }
    }
}
