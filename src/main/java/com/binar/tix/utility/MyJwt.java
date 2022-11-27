package com.binar.tix.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
@Slf4j
public class MyJwt {

    private MyJwt(){}
    public static Integer getUserId(String token){
        try {
            Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode("QiFuYXIgUzNydmljZSAkJCMgRmluYWwgUHJvajNjdEAqKg"),
                    SignatureAlgorithm.HS256.getJcaName());
            Jws<Claims> jwt = Jwts.parserBuilder()
                    .setSigningKey(hmacKey)
                    .build()
                    .parseClaimsJws(token);
            Claims claims = jwt.getBody();
            String userId = claims.get("userId").toString();
            return Integer.valueOf(userId);
        } catch (Exception e) {
            return 0;
        }
    }
}
