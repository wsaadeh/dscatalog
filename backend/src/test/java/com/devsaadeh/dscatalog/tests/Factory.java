package com.devsaadeh.dscatalog.tests;

import com.devsaadeh.dscatalog.dto.ProductDTO;
import com.devsaadeh.dscatalog.entities.Category;
import com.devsaadeh.dscatalog.entities.Product;

import java.time.Instant;

public class Factory {

    public static Product createProduct(){
        Product product = new Product(1L,"Phone", "Good Phone", 800.0,"https://img.com/img.png", Instant.parse("2020-08-02T03:00:00Z"));
        product.getCategories().add(new Category(2L, "Electronics"));
        return product;
    }

    public static ProductDTO createProductDTO(){
        return new ProductDTO(createProduct(),createProduct().getCategories());
    }
}
