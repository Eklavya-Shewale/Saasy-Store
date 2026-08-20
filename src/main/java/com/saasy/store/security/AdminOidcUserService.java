package com.saasy.store.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class AdminOidcUserService extends OidcUserService {

    private static final String ADMIN_ROLE = "ROLE_ADMIN";

    private final AdminSecurityProperties securityProperties;

    AdminOidcUserService(AdminSecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) {
        OidcUser oidcUser = super.loadUser(userRequest);

        if (!Boolean.TRUE.equals(oidcUser.getClaimAsBoolean("email_verified"))
                || !securityProperties.isConfiguredAdministrator(oidcUser.getEmail())) {
            throw new OAuth2AuthenticationException(new OAuth2Error("access_denied"));
        }

        return new DefaultOidcUser(
                List.of(new SimpleGrantedAuthority(ADMIN_ROLE)),
                oidcUser.getIdToken(),
                oidcUser.getUserInfo(),
                "email"
        );
    }
}
