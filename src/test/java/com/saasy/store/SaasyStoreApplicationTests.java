package com.saasy.store;

import com.saasy.store.catalog.category.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(properties = {
        "spring.flyway.enabled=false",
        "spring.autoconfigure.exclude=org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration,org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration",
        "GOOGLE_CLIENT_ID=test-client-id",
        "GOOGLE_CLIENT_SECRET=test-client-secret",
        "ADMIN_EMAIL=admin@example.com"
})
class SaasyStoreApplicationTests {

    @MockitoBean
    private CategoryRepository categoryRepository;

    @Test
    void contextLoads() {
        // Verifies that the Phase 1 application context starts successfully.
    }
}
