package com.saasy.store.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app.security")
public record AdminSecurityProperties(
        @NotBlank @Email String adminEmail
) {

    public boolean isConfiguredAdministrator(String email) {
        return email != null && adminEmail.equalsIgnoreCase(email);
    }
}
