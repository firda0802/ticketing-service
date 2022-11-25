package com.binar.tix.service;

import com.binar.tix.entities.NotifCategory;
import com.binar.tix.repository.NotifCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService{

    @Autowired
    private NotifCategoryRepository notifCategoryRepository;

    @Override
    public void createNotificationCategory(String name) {
        NotifCategory notifCategory = new NotifCategory();
        notifCategory.setNotifcategoryName(name);
        notifCategoryRepository.save(notifCategory);
    }

    @Override
    public List<NotifCategory> getAllNotifCategory() {
        return notifCategoryRepository.findAll();
    }

    @Override
    public Boolean updateNotifCategory(NotifCategory req) {
        Optional<NotifCategory> data = notifCategoryRepository.findById(req.getNotif_category_id());
        if(data.isPresent()){
            NotifCategory update = data.get();
            update.setNotifcategoryName(req.getNotifcategoryName());
            notifCategoryRepository.saveAndFlush(update);
            return true;
        }else{
            return false;
        }
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
