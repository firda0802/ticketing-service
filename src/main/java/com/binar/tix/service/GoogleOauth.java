package com.binar.tix.service;

import com.binar.tix.entities.Users;
import com.binar.tix.payload.Messages;
import com.binar.tix.payload.ReqRegister;
import com.binar.tix.payload.RespDataGoogle;
import com.binar.tix.utility.Constant;
import com.binar.tix.utility.MD5;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Slf4j
@Service
public class GoogleOauth {

    @Autowired
    UserService userService;

    public  Messages verify(String googleTokenId) throws GeneralSecurityException, IOException {
        Messages resp = new Messages();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList("424104245551-94u06m9jdfcqe5130ssdvklga626sp12.apps.googleusercontent.com"))
                .build();

        GoogleIdToken idToken = verifier.verify(googleTokenId);
        if (idToken != null) {
            Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            String name = (String) payload.get("name");
            resp.success();
            RespDataGoogle data = new RespDataGoogle();
            data.setFullName(name);
            data.setEmail(email);
            Users user = userService.checkEmail(email);
            if (user != null) {
                //login
                data.setToken(userService.generateToken(user.getUserId(), user.getRole().getRoleName(), user.getEmail()));
            } else {
                //register
                ReqRegister req = new ReqRegister();
                req.setEmail(data.getEmail());
                req.setPassword(MD5.encrypt(RandomStringUtils.randomAlphanumeric(10)));
                req.setFullName(data.getFullName());
                userService.registerUser(req);
                data.setToken(userService.registerUser(req));
            }
            resp.setData(data);

        } else {
            resp.setResponseCode(Constant.NO_CONTENT);
            resp.setResponseMessage("Invalid Token");
        }

        return resp;
    }
}
