package com.saasy.store.catalog;

import com.saasy.store.catalog.category.Category;
import com.saasy.store.catalog.product.Product;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class CatalogDomainValidationTests {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void validCategoryHasNoValidationErrors() {
        assertThat(validator.validate(new Category("Rings", "Handcrafted rings"))).isEmpty();
    }

    @Test
    void productRequiresAValidPriceAndCategory() {
        Product product = new Product("RING-001", "Gold Ring", "A handcrafted gold ring", new BigDecimal("-1.00"), null);

        assertThat(validator.validate(product))
                .extracting(violation -> violation.getPropertyPath().toString())
                .contains("price", "category");
    }
}
