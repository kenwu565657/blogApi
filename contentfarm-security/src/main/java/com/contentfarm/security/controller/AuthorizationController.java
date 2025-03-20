package com.contentfarm.security.controller;

import com.contentfarm.response.security.GenerateAuthorizationCodeRequest;
import com.contentfarm.security.service.IGenerateAuthorizationCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authorization")
public class AuthorizationController {
    private final IGenerateAuthorizationCodeService generateAuthorizationCodeService;

    @PostMapping(value = "")
    public String generateAuthorizationCode(@RequestBody GenerateAuthorizationCodeRequest generateAuthorizationCodeRequest) {
        return generateAuthorizationCodeService.generateAuthorizationCode(generateAuthorizationCodeRequest);
    }
}
