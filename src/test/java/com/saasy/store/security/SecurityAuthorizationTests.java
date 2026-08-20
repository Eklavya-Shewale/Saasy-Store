package com.saasy.store.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest(properties = {
        "spring.flyway.enabled=false",
        "spring.autoconfigure.exclude=org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration,org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration",
        "GOOGLE_CLIENT_ID=test-client-id",
        "GOOGLE_CLIENT_SECRET=test-client-secret",
        "ADMIN_EMAIL=owner@example.com"
})
class SecurityAuthorizationTests {

    @Autowired
    private WebApplicationContext applicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUpMockMvc() {
        mockMvc = webAppContextSetup(applicationContext).apply(springSecurity()).build();
    }

    @Test
    void publicCatalogueRoutesDoNotRequireAuthentication() throws Exception {
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isNotFound());
    }

    @Test
    void unauthenticatedAdminRequestIsRejected() throws Exception {
        mockMvc.perform(get("/api/admin/session"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void nonAdminAuthenticatedUserIsDenied() throws Exception {
        mockMvc.perform(get("/api/admin/session").with(user("customer@example.com").roles("USER")))
                .andExpect(status().isForbidden());
    }

    @Test
    void configuredAdministratorRoleCanAccessAdminEndpoint() throws Exception {
        mockMvc.perform(get("/api/admin/session").with(user("owner@example.com").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("owner@example.com"));
    }

    @Test
    void administratorEmailMatchingIsCaseInsensitiveAndRejectsOthers() {
        AdminSecurityProperties properties = new AdminSecurityProperties("owner@example.com");

        org.assertj.core.api.Assertions.assertThat(properties.isConfiguredAdministrator("OWNER@example.com")).isTrue();
        org.assertj.core.api.Assertions.assertThat(properties.isConfiguredAdministrator("customer@example.com")).isFalse();
    }
}
