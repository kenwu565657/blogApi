package com.contentfarm.security.service;

import com.contentfarm.response.security.GenerateAuthorizationCodeRequest;

public interface IGenerateAuthorizationCodeService {
    String generateAuthorizationCode(GenerateAuthorizationCodeRequest generateAuthorizationCodeRequest);
}
