package com.binar.tix.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.Optional;
import org.springframework.stereotype.Component;
import com.binar.tix.service.UserService;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Riko
 */
@Component
@Slf4j
public class SecurityAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    UserService userService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException{
        //no operation
    }

    @Override
    protected UserDetails retrieveUser(String header, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        log.info("PROVIDER : "+header);
        Object token= usernamePasswordAuthenticationToken.getCredentials();
        log.info("TOKEN : "+ token);
        return Optional
                .ofNullable(token)
                .map(String::valueOf)
                .flatMap(userService::validateSession)
                .orElseThrow(() -> new UsernameNotFoundException("Token invalid : " + token));
    }
}
