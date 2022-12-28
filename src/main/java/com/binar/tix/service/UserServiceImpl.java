/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.binar.tix.service;

import com.binar.tix.entities.RoleUser;
import com.binar.tix.entities.Users;
import com.binar.tix.enums.RoleEnum;
import com.binar.tix.payload.*;
import com.binar.tix.repository.RoleUserRepository;
import com.binar.tix.repository.UsersRepository;
import com.binar.tix.utility.Constant;
import com.binar.tix.utility.MD5;

import java.util.*;

import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

/**
 * @author Riko
 */
@Service
public class UserServiceImpl implements UserService {

    @Value("${secret.key}")
    String secretKey;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    RoleUserRepository roleRepository;

    @Autowired
    NotificationService notificationService;

    @Override
    public String registerUser(ReqRegister req) {
        Users cekUser = usersRepository.findByEmailIgnoreCaseAndStatusAndRoleId(req.getEmail(), true, 1);
        if (cekUser != null) {
            return "";
        } else {
            Users newUsers = new Users();
            newUsers.setStatus(Boolean.TRUE);
            newUsers.setEmail(req.getEmail());
            newUsers.setFullName(req.getFullName());
            newUsers.setPassword(MD5.encrypt(req.getPassword()));
            newUsers.setRoleId(1);
            usersRepository.saveAndFlush(newUsers);

            ReqCreateNotification notif = new ReqCreateNotification();
            notif.setUserId(newUsers.getUserId());
            notif.setNotificationCategoryId(1);
            notif.setTitle("Hai! Selamat datang di Safety Fly");
            notif.setContent("Nikmati layanan pemesanan tiket pesawat secara online disini");
            notificationService.createNotifUsers(notif);

            return generateToken(newUsers.getUserId(), RoleEnum.BUYER.name(), newUsers.getEmail());
        }
    }

    @Override
    public Users getOneUsers(int userId) {
        Users users = new Users();
        Optional<Users> getUser = usersRepository.findByUserIdAndStatus(userId, true);
        if (getUser.isPresent()) {
            users = getUser.get();
        }
        return users;
    }

    @Override
    public RoleUser addRole(RoleUser role) {
       return roleRepository.saveAndFlush(role);
    }

    @Override
    public List<RoleUser> getAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public void deleteUser(int userId) {
        usersRepository.deleteAkun(userId);
    }

    @Override
    public Boolean updateUser(int userId, ReqUpdateUser req) {
        Optional<Users> getUser = usersRepository.findById(userId);
        if (getUser.isPresent()) {
            Users u = getUser.get();
            u.setFullName(req.getFullName());
            u.setAddress(req.getAddress());
            u.setPhoneNo(req.getPhoneNo());
            u.setBirthDate(req.getBirthDate());
            usersRepository.saveAndFlush(u);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<User> validateSession(String session) {
        try {
            String key = secretKey;
            if(TextUtils.isEmpty(key)){
                //mockito doesn't support @value
                key = Constant.KEY1+Constant.KEY2;
            }
            Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(key),
                    SignatureAlgorithm.HS256.getJcaName());
            Jws<Claims> jwt = Jwts.parserBuilder()
                    .setSigningKey(hmacKey)
                    .build()
                    .parseClaimsJws(session);
            Claims claims = jwt.getBody();
            Optional<Users> cekUser = usersRepository.findByEmailIgnoreCaseAndStatus(claims.get(Constant.EMAIL).toString(), true);
            if (cekUser.isPresent()) {
                User user = new User(claims.get(Constant.EMAIL).toString(), MD5.encrypt(claims.get(Constant.EMAIL).toString()), true, true, true, true,
                        AuthorityUtils.createAuthorityList(claims.get("role").toString()));
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public RespLogin login(ReqLogin req) {
        Optional<Users> cekUser = usersRepository.findByEmailIgnoreCaseAndPasswordAndStatus(req.getEmail(), MD5.encrypt(req.getPassword()), true);
        if (cekUser.isPresent()) {
            Users u = cekUser.get();
            RespLogin login = new RespLogin();
            login.setToken(generateToken(u.getUserId(), u.getRole().getRoleName(), u.getEmail()));
            login.setRole(u.getRole().getRoleName());
            return login;
        } else {
            return null;
        }
    }

    @Override
    public Users checkEmail(String email) {
        return usersRepository.findByEmailIgnoreCaseAndStatusAndRoleId(email, true, 1);
    }

    @Override
    public String generateToken(int userId, String roleName, String email) {
        String key = secretKey;
        if(TextUtils.isEmpty(key)){
            //mockito doesn't support @value
            key = Constant.KEY1+Constant.KEY2;
        }
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(key),
                SignatureAlgorithm.HS256.getJcaName());
        Instant now = Instant.now();
        return Jwts.builder()
                .claim("userId", userId)
                .claim(Constant.EMAIL, email)
                .claim("role", roleName)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(30, ChronoUnit.DAYS)))
                .signWith(hmacKey)
                .compact();
    }
}
