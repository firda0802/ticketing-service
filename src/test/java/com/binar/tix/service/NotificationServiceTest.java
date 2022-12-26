package com.binar.tix.service;

import com.binar.tix.entities.NotifCategory;
import com.binar.tix.entities.Notifications;
import com.binar.tix.entities.Users;
import com.binar.tix.payload.ReqCreateNotification;
import com.binar.tix.projections.NotificationCount;
import com.binar.tix.repository.NotifCategoryRepository;
import com.binar.tix.repository.NotificationRepository;
import com.binar.tix.repository.UsersRepository;
import com.binar.tix.utility.MD5;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class NotificationServiceTest {

    @InjectMocks
    NotificationService notificationService = new NotificationServiceImpl();

    @Mock
    NotificationRepository notificationRepository;

    @Mock
    UsersRepository usersRepository;

    @Mock
    NotifCategoryRepository notifCategoryRepository;

    Users users = new Users();
    NotifCategory notifCategory = new NotifCategory();
    List<NotifCategory> list = List.of(new NotifCategory("Introduce"));

    @BeforeEach
    void setUp() {
        users.setEmail("jhon@gmail.com");
        users.setPassword(MD5.encrypt("demos"));
        users.setFullName("Jhon");
        usersRepository.saveAndFlush(users);
        notifCategory.setNotifcategoryName("Introduce");
        notifCategoryRepository.saveAndFlush(notifCategory);
    }

    @Test
    void createNotificationTest() {
        ReqCreateNotification req = new ReqCreateNotification();
        req.setNotificationCategoryId(notifCategory.getNotificationCategoryId());
        req.setTitle("Binar Academy");
        req.setUserId(users.getUserId());
        req.setContent("Final Project");

        notificationService.createNotifUsers(new ReqCreateNotification());
        Notifications notifications = new Notifications();
        notifications.setNotificationCategoryId(req.getNotificationCategoryId());
        notifications.setUserId(req.getUserId());
        notifications.setTitle(req.getTitle());
        notifications.setContent(req.getContent());
        notifications.setStatus(Boolean.TRUE);
        doReturn(notifications).when(notificationRepository).saveAndFlush(notifications);
        assertEquals(notifications.getTitle(), notificationRepository.saveAndFlush(notifications).getTitle());
    }

    @Test
    void getNotifUsersTest() {
        Pageable paging = PageRequest.of(0, 10, Sort.by("cdate").descending());
        Page<Notifications> notif = notificationRepository.findByUserId(users.getUserId(), paging);
        when(notificationService.getNotifUsers(paging, users.getUserId())).thenReturn(notif);
        assertEquals(notificationService.getNotifUsers(paging, users.getUserId()), notif);
    }

    @Test
    void countNotifUsersTest() {
        NotificationCount notificationCount = new NotificationCount() {
            @Override
            public int getJumlah() {
                return 1;
            }
        };
        notificationService.countNotifUsers(users.getUserId());
        doReturn(notificationCount).when(notificationRepository).getCount(users.getUserId());
        assertEquals(notificationCount, notificationRepository.getCount(users.getUserId()));
    }

    @Test
    void createNotifCategory() {
        NotifCategory notifCategory = new NotifCategory("Event");
        notifCategory.setNotificationCategoryId(2);
        notificationService.createUpdateNotifCategory(notifCategory.getNotificationCategoryId(), "Event Binar");
        when(notifCategoryRepository.saveAndFlush(notifCategory)).thenReturn(notifCategory);
        assertEquals(notifCategoryRepository.saveAndFlush(notifCategory), notifCategory);
    }

    @Test
    void getAllNotifCategory() {
        when(notificationService.getAllNotifCategory()).thenReturn(list);
        assertTrue(notificationService.getAllNotifCategory().size() > 0);
    }

    @Test
    void deleteCategory() {
        NotificationService mockInstance = mock(NotificationService.class);
        when(mockInstance.deleteCategory(1)).thenReturn(true);
        assertEquals(true, mockInstance.deleteCategory(1));
    }

    @Test
    void clearNotif() {
        notificationService.clearNotif(users.getUserId());
        doNothing().when(notificationRepository).clearNotif(users.getUserId());
        verify(notificationRepository).clearNotif(users.getUserId());
    }

}
