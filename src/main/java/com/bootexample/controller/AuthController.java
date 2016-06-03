package com.bootexample.controller;

import com.bootexample.auth.BootAuthenticationToken;
import com.bootexample.auth.TokenManager;
import com.bootexample.entity.User;
import com.bootexample.exception.UnathorizedException;
import com.bootexample.model.LoginRequest;
import com.bootexample.model.LoginResponse;
import com.bootexample.repository.UserRepository;
import com.bootexample.util.Constants;
import com.bootexample.util.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by antosha4e on 11.05.16.
 */
@RestController
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    protected TokenManager tokenManager;

    /**
     * Makes login request
     * @param credentials User's credentials
     * @return token
     * @throws Exception
     */
    @RequestMapping(value = "/api/login", method = {RequestMethod.POST})
    public LoginResponse login(@RequestBody LoginRequest credentials) throws Exception {
        if(!credentials.isValid()) {
            throw new UnathorizedException("Bad credentials. Access denied.");
        }

        User user = userRepository.find(credentials.getUsername(), PasswordEncryptor.encryptPassword(credentials.getPassword()));

        if(user == null) {
            throw new UnathorizedException("Bad credentials. Access denied.");
        }

        Authentication auth = new BootAuthenticationToken(user);

        SecurityContextHolder.getContext().setAuthentication(auth);

        LoginResponse loginResponse = new LoginResponse();

        loginResponse.setToken(tokenManager.generateToken(auth));

        return loginResponse;
    }

    /**
     * Logout from server (make token invalid)
     * @param request just request
     * @throws Exception
     */
    @RequestMapping(value = "/api/logout", method = {RequestMethod.POST})
    @ResponseStatus(value= HttpStatus.OK, reason = "Logout done")
    public void logout(HttpServletRequest request) throws Exception {
        String xAuth = request.getHeader(Constants.AUTH_HEADER_NAME);

        Authentication auth = tokenManager.getUserByToken(xAuth);

        if(auth == null) {
            throw new UnathorizedException("Bad token in header.");
        }

        tokenManager.removeToken(xAuth);
    }
}