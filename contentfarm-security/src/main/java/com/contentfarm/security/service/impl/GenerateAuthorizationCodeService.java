package com.contentfarm.security.service.impl;

import com.contentfarm.response.security.GenerateAuthorizationCodeRequest;
import com.contentfarm.security.service.CustomAuthorizationCodeGenerator;
import com.contentfarm.security.service.IGenerateAuthorizationCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.Principal;

/**
 * source:
 * <p>https://gitee.com/ashegit/spring-authorization-server-demo/tree/1.0.0</p>
 * Copy from <a href="https://gitee.com/ashegit/spring-authorization-server-demo/tree/1.0.0">...</a>
 *
 */

@Service
@RequiredArgsConstructor
public class GenerateAuthorizationCodeService implements IGenerateAuthorizationCodeService {
    private final RegisteredClientRepository registeredClientRepository;
    private final AuthenticationManager authenticationManager;
    private final OAuth2AuthorizationService oAuth2AuthorizationService;
    private final OAuth2TokenGenerator<OAuth2AuthorizationCode> TOKEN_GENERATOR = new CustomAuthorizationCodeGenerator();

    @Override
    public String generateAuthorizationCode(GenerateAuthorizationCodeRequest generateAuthorizationCodeRequest) {
        RegisteredClient registeredClient = registeredClientRepository.findByClientId(generateAuthorizationCodeRequest.getClientId());
        if (registeredClient == null) {
            throw new RuntimeException("");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(generateAuthorizationCodeRequest.getUsername(), generateAuthorizationCodeRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        OAuth2TokenContext oAuth2TokenContext = DefaultOAuth2TokenContext.builder()
                .registeredClient(registeredClient)
                .principal(authentication)
                .tokenType(new OAuth2TokenType(OAuth2ParameterNames.CODE))
                .authorizedScopes(registeredClient.getScopes())
                .build();
        OAuth2AuthorizationCode authorizationCode = TOKEN_GENERATOR.generate(oAuth2TokenContext);
        OAuth2AuthorizationRequest authorizationRequest = OAuth2AuthorizationRequest.authorizationCode()
                .authorizationUri(getRequestAttributes().getRequest().getRequestURL().toString())
                .clientId(registeredClient.getClientId())
                .scopes(registeredClient.getScopes())
                .build();
        OAuth2Authorization oAuth2Authorization = OAuth2Authorization.withRegisteredClient(registeredClient)
                .principalName(authentication.getName())
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .attribute(Principal.class.getName(), authentication)
                .attribute(OAuth2AuthorizationRequest.class.getName(), authorizationRequest)
                .authorizedScopes(registeredClient.getScopes())
                .token(authorizationCode)
                .build();
        oAuth2AuthorizationService.save(oAuth2Authorization);
        return authorizationCode.getTokenValue();
    }

    private ServletRequestAttributes getRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }
}
