package com.contentfarm.api.controller;

import com.contentfarm.api.domain.response.ResponseResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/login")
public class LoginController {

    public ResponseResult<Boolean> login() {
        return ResponseResult.<Boolean>builder().build();
    }
}
