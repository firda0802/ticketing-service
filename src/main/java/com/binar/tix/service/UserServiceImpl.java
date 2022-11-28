/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.binar.tix.service;

import com.binar.tix.entities.RoleUser;
import com.binar.tix.entities.Users;
import com.binar.tix.payload.ReqCreateNotification;
import com.binar.tix.payload.ReqSigninup;
import com.binar.tix.payload.ReqUpdateUser;
import com.binar.tix.repository.RoleUserRepository;
import com.binar.tix.repository.UsersRepository;
import com.binar.tix.utility.MD5;

import java.util.*;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

/**
 * @author Riko
 */
@Service
public class UserServiceImpl implements UserService {

    @Value("${secret.key}")
    private String secretKey;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RoleUserRepository roleRepository;

    @Autowired
    private NotificationService notificationService;

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
    public void addRole(RoleUser role) {
        roleRepository.saveAndFlush(role);
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
    public Boolean registerUser(ReqSigninup req) {
        Optional<Users> cekUser = usersRepository.findByEmailIgnoreCaseAndStatus(req.getEmail(), true);
        if (cekUser.isPresent()) {
            return false;
        } else {
            Users newUsers = new Users();
            newUsers.setStatus(Boolean.TRUE);
            newUsers.setEmail(req.getEmail());
            newUsers.setFullName(req.getFullName());
            newUsers.setPassword(MD5.encrypt(req.getPassword()));
            newUsers.setRoleId(1);
            usersRepository.save(newUsers);

            ReqCreateNotification notif = new ReqCreateNotification();
            notif.setUserId(newUsers.getUserId());
            notif.setNotificationCategoryId(1);
            notif.setTitle("Hai! Selamat datang di E-Flight");
            notif.setContent("Nikmati layanan pemesanan tiket pesawat secara online disini");
            notificationService.createNotifUsers(notif);
            return true;
        }
    }

    @Override
    public Optional<User> validateSession(String session) {
        try {
            Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secretKey),
                    SignatureAlgorithm.HS256.getJcaName());
            Jws<Claims> jwt = Jwts.parserBuilder()
                    .setSigningKey(hmacKey)
                    .build()
                    .parseClaimsJws(session);
            Claims claims = jwt.getBody();
            String email = "email";
            Optional<Users> cekUser = usersRepository.findByEmailIgnoreCaseAndStatus(claims.get(email).toString(), true);
            if (cekUser.isPresent()) {
                User user = new User(claims.get(email).toString(), MD5.encrypt(claims.get(email).toString()), true, true, true, true,
                        AuthorityUtils.createAuthorityList(claims.get("role").toString()));
                return Optional.of(user);
            } else {
                return Optional.empty();
            }


        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public String login(ReqSigninup req) {
        Optional<Users> cekUser = usersRepository.findByEmailIgnoreCaseAndPasswordAndStatus(req.getEmail(), MD5.encrypt(req.getPassword()), true);
        if (cekUser.isPresent()) {
            Users u = cekUser.get();
            Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secretKey),
                    SignatureAlgorithm.HS256.getJcaName());
            Instant now = Instant.now();
            return Jwts.builder()
                    .claim("userId", u.getUserId())
                    .claim("email", u.getEmail())
                    .claim("role", u.getRole().getRoleName())
                    .setId(UUID.randomUUID().toString())
                    .setIssuedAt(Date.from(now))
                    .setExpiration(Date.from(now.plus(1, ChronoUnit.DAYS)))
                    .signWith(hmacKey)
                    .compact();
        } else {
            return "";
        }
    }
}
