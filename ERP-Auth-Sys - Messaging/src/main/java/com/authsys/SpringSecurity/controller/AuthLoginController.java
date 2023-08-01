package com.authsys.SpringSecurity.controller;

import com.authsys.SpringSecurity.common.constant.SecurityConstants;
import com.authsys.SpringSecurity.model.LoginRequest;
import com.authsys.SpringSecurity.model.LoginResponse;
import com.authsys.SpringSecurity.service.AuthService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@Log4j2

public class AuthLoginController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @ApiOperation("登录")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.createToken(loginRequest);
        log.info("token:" + token);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(SecurityConstants.TOKEN_HEADER, token);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setHttpHeader(token);
        log.info("httpHeaders:" + httpHeaders);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        authService.removeToken();
        log.info("Successfully Logout!");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
