/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.binar.tix.service;

import com.binar.tix.entities.Users;
import com.binar.tix.payload.ReqSigninup;
import com.binar.tix.repository.UsersRepository;
import com.binar.tix.utility.MD5;
import java.util.Optional;
import org.springframework.security.core.userdetails.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

/**
 *
 * @author Riko
 */
@Service
public class UserServiceImpl implements UserService {

    @Value("${secret.key}")
    private String secretKey;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public Boolean registerUser(ReqSigninup req) {
        Optional<Users> cekUser = usersRepository.findByEmailIgnoreCase(req.getEmail());
        if (cekUser.isPresent()) {
            return false;
        }else{
            Users newUsers = new Users();
            newUsers.setEmail(req.getEmail());
            newUsers.setFullName(req.getFullName());
            newUsers.setPassword(MD5.encrypt(req.getPassword()));
            newUsers.setRoleId(1);
            usersRepository.save(newUsers);
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
            User user = new User(claims.get(email).toString(), MD5.encrypt(claims.get(email).toString()), true, true, true, true,
                    AuthorityUtils.createAuthorityList(claims.get("role").toString()));
            return Optional.of(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public String login(ReqSigninup req) {
        Optional<Users> cekUser = usersRepository.findByEmailIgnoreCaseAndPassword(req.getEmail(), MD5.encrypt(req.getPassword()));
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
