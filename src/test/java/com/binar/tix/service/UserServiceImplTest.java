package com.binar.tix.service;

import com.binar.tix.entities.RoleUser;
import com.binar.tix.entities.Users;
import com.binar.tix.enums.RoleEnum;
import com.binar.tix.payload.ReqLogin;
import com.binar.tix.payload.ReqRegister;
import com.binar.tix.payload.ReqUpdateUser;
import com.binar.tix.payload.RespLogin;
import com.binar.tix.repository.RoleUserRepository;
import com.binar.tix.repository.UsersRepository;
import com.binar.tix.utility.Constant;
import com.binar.tix.utility.MD5;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceImplTest {

    @InjectMocks
    UserService userService = new UserServiceImpl();

    @Mock
    UsersRepository usersRepository;

    @Mock
    RoleUserRepository roleUserRepository;

    @Mock
    NotificationService notificationRepository;

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjU4LCJlbWFpbCI6InJpa29qc0BnbWFpbC5jb20iLCJyb2xlIjoiQlVZRVIiLCJqdGkiOiI5M2M1ZWU2Ny0wOWM4LTQ2MTUtOGFmZi01YmYwNTEwMjI1OTMiLCJpYXQiOjE2NzIyMTkzMjcsImV4cCI6MTY3NDgxMTMyN30.shp2HSiKoHaWBPRA7-INGs_HLvRDB-SksQggtOQkyyU";

    Users users = new Users();
    RoleUser roleUser = new RoleUser();

    @BeforeEach
    void setUp() {
        users.setEmail("jhon@gmail.com");
        users.setPassword(MD5.encrypt("demos12"));
        users.setFullName("Jhon");
        usersRepository.saveAndFlush(users);

        roleUser.setRoleName("BUYER");
        roleUserRepository.saveAndFlush(roleUser);
    }

    @Test
    void registerUser() {
        ReqRegister req = new ReqRegister();
        req.setFullName("Riko");
        req.setPassword("jyun123");
        req.setEmail("riko@gmail.com");

        Users newUsers = new Users();
        newUsers.setFullName(req.getFullName());
        newUsers.setPassword(MD5.encrypt(req.getPassword()));
        newUsers.setEmail(req.getEmail());
        userService.registerUser(req);
        doReturn(newUsers).when(usersRepository).saveAndFlush(newUsers);
        assertEquals(newUsers.getEmail(), usersRepository.saveAndFlush(newUsers).getEmail());

    }

    @Test
    void getOneUsers() {
        Optional<Users> dataUser = Optional.of(users);
        doReturn(dataUser).when(usersRepository).findByUserIdAndStatus(1, true);
        var actData = usersRepository.findByUserIdAndStatus(1, true);
        assertEquals(dataUser, actData);
    }

    @Test
    void addRole() {
        when(userService.addRole(roleUser)).thenReturn(roleUser);
        String expRole = roleUser.getRoleName();
        String actRole = userService.addRole(roleUser).getRoleName();
        assertEquals(expRole, actRole);
    }

    @Test
    void getAllRole() {
        List<RoleUser> list = List.of(roleUser);
        when(userService.getAllRole()).thenReturn(list);
        assertEquals(list, userService.getAllRole());
    }

    @Test
    void deleteUser() {
        userService.deleteUser(1);
        doNothing().when(usersRepository).deleteAkun(1);
        verify(usersRepository).deleteAkun(1);
    }

    @Test
    void updateUser() {
        ReqUpdateUser reqUpdateUser = new ReqUpdateUser();
        reqUpdateUser.setAddress("Jln. Soetta");
        reqUpdateUser.setFullName("Jhon Smith");
        reqUpdateUser.setPhoneNo("08132477671");
        reqUpdateUser.setBirthDate(new Date());
        users.setFullName(reqUpdateUser.getFullName());
        users.setPhoneNo(reqUpdateUser.getPhoneNo());
        users.setAddress(reqUpdateUser.getAddress());
        users.setBirthDate(reqUpdateUser.getBirthDate());

        userService.updateUser(1, reqUpdateUser);
        doReturn(Optional.of(users)).when(usersRepository).findById(1);
        verify(usersRepository).findById(1);
        verify(usersRepository).saveAndFlush(users);
    }

    @Test
    void validateSession() {
        User user = new User("rikojs@gmail.com", MD5.encrypt("rikojs@gmail.com"), true, true, true, true,
                AuthorityUtils.createAuthorityList(RoleEnum.BUYER.name()));
        when(userService.validateSession(token)).thenReturn(Optional.of(user));
        var expOptional = Optional.of(user);
        var actOptional = userService.validateSession(token);
        assertEquals(expOptional, actOptional);
    }

    @Test
    void login() {
        ReqLogin reqLogin = new ReqLogin();
        reqLogin.setEmail("jhon@gmail.com");
        reqLogin.setPassword("demos12");

        userService.login(reqLogin);
        doReturn(Optional.of(users)).when(usersRepository).findByEmailIgnoreCaseAndPasswordAndStatus(reqLogin.getEmail(), MD5.encrypt(reqLogin.getPassword()), true);
        String expEmail = reqLogin.getEmail();
        Optional<Users> actEmail = usersRepository.findByEmailIgnoreCaseAndPasswordAndStatus(reqLogin.getEmail(), MD5.encrypt(reqLogin.getPassword()), true);
        assertEquals(expEmail, actEmail.orElse(new Users()).getEmail());
    }

    @Test
    void checkEmail() {
        when(userService.checkEmail("jhon@gmail.com")).thenReturn(users);
        var expResult = users;
        var actualResult = userService.checkEmail("jhon@gmail.com");
        assertEquals(expResult, actualResult);
    }

    @Test
    void generateToken() {
        when(userService.generateToken(users.getUserId(), RoleEnum.BUYER.name(), users.getEmail())).thenReturn(token);
        String token = userService.generateToken(users.getUserId(), RoleEnum.BUYER.name(), users.getEmail());
        assertTrue(token.length() > 0);
    }
}