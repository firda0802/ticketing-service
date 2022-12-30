package com.binar.tix.google;

import com.binar.tix.entities.Users;
import com.binar.tix.payload.Messages;
import com.binar.tix.payload.ReqRegister;
import com.binar.tix.payload.ReqSigninGoogle;
import com.binar.tix.payload.RespDataGoogle;
import com.binar.tix.service.UserService;
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
public class SigninWithGoogle {

    @Autowired
    UserService userService;

    public  Messages verify(ReqSigninGoogle request) throws GeneralSecurityException, IOException {
        Messages resp = new Messages();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList("568466100708-h2abh5rirojs5llpnpuf1ft07oqhhsbi.apps.googleusercontent.com"))
                .build();
        RespDataGoogle data = new RespDataGoogle();
        data.setFullName(request.getFullName());
        data.setEmail(request.getEmail());

        GoogleIdToken idToken = verifier.verify(request.getToken());
        if (idToken != null) {
            Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            String name = (String) payload.get("name");
            data.setFullName(email);
            data.setEmail(name);
        }
        Users user = userService.checkEmail(data.getEmail());
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
        resp.success();
        resp.setData(data);

        return resp;
    }
}
