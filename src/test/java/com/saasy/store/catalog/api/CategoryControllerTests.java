package com.saasy.store.catalog.api;

import com.saasy.store.catalog.category.Category;
import com.saasy.store.catalog.category.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
class CategoryControllerTests {

    @Autowired
    private WebApplicationContext applicationContext;

    @MockitoBean
    private CategoryRepository categoryRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUpMockMvc() {
        mockMvc = webAppContextSetup(applicationContext).apply(springSecurity()).build();
    }

    @Test
    void unauthenticatedGetCategoriesReturnsOkAndEmptyList() throws Exception {
        when(categoryRepository.findAllByOrderByNameAsc()).thenReturn(List.of());

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getCategoriesReturnsCategoryDtoWithoutJpaOnlyFields() throws Exception {
        Category category = new Category("Rings", "Handcrafted rings");
        ReflectionTestUtils.setField(category, "id", 1L);
        ReflectionTestUtils.setField(category, "createdAt", Instant.parse("2026-08-20T12:00:00Z"));
        ReflectionTestUtils.setField(category, "updatedAt", Instant.parse("2026-08-20T12:30:00Z"));
        when(categoryRepository.findAllByOrderByNameAsc()).thenReturn(List.of(category));

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Rings"))
                .andExpect(jsonPath("$[0].description").value("Handcrafted rings"))
                .andExpect(jsonPath("$[0].createdAt").doesNotExist())
                .andExpect(jsonPath("$[0].updatedAt").doesNotExist());
    }
}
