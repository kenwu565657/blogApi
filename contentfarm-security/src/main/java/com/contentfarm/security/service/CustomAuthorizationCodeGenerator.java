package com.contentfarm.security.service;

import jakarta.annotation.Nullable;
import org.springframework.security.crypto.keygen.Base64StringKeyGenerator;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

import java.time.Instant;
import java.util.Base64;

/**
 * source:
 * <p>https://gitee.com/ashegit/spring-authorization-server-demo/tree/1.0.0</p>
 * Copy from <a href="https://gitee.com/ashegit/spring-authorization-server-demo/tree/1.0.0">...</a>
 *
 */

public class CustomAuthorizationCodeGenerator implements OAuth2TokenGenerator<OAuth2AuthorizationCode> {

    private final StringKeyGenerator authorizationCodeGenerator = new Base64StringKeyGenerator(Base64.getUrlEncoder().withoutPadding(), 96);

    public CustomAuthorizationCodeGenerator() {
    }

    @Override
    @Nullable
    public OAuth2AuthorizationCode generate(OAuth2TokenContext context) {
        if (context.getTokenType() != null && "code".equals(context.getTokenType().getValue())) {
            Instant issuedAt = Instant.now();
            Instant expiresAt = issuedAt.plus(context.getRegisteredClient().getTokenSettings().getAuthorizationCodeTimeToLive());
            return new OAuth2AuthorizationCode(this.authorizationCodeGenerator.generateKey(), issuedAt, expiresAt);
        } else {
            return null;
        }
    }
}
