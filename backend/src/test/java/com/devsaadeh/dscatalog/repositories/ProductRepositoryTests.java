package com.devsaadeh.dscatalog.repositories;

import com.devsaadeh.dscatalog.entities.Product;
import com.devsaadeh.dscatalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;

    private long existingId;
    private Long countTotalProducts;

    @BeforeEach
    void setUp() throws Exception{
        existingId = 1L;
        countTotalProducts = 25L;
    }

    @Test
    public void saveShouldPersistWithAutoIncrementWhenIdIsNull(){
        Product product = Factory.createProduct();
        product.setId(null);

        product = repository.save(product);

        Assertions.assertNotNull(product.getId());
        Assertions.assertTrue(countTotalProducts < product.getId());
        Assertions.assertEquals(countTotalProducts + 1, product.getId());
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists(){

        repository.deleteById(existingId);

        Optional<Product> result = repository.findById(existingId);
        Assertions.assertFalse(result.isPresent());

    }

    @Test
    public void findByIdShouldReturnOptionalNotEmptyWhenIdExists(){
        Optional<Product> product =  repository.findById(1L);

        Assertions.assertTrue(product.isPresent());

    }

    @Test
    public void findByIdShouldReturnOptionalEmptyWhenIdNotExists(){
        Optional<Product> product = repository.findById(26L);

        Assertions.assertFalse(product.isPresent());
    }
}
