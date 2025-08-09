package com.devsaadeh.dscatalog.services;

import com.devsaadeh.dscatalog.dto.ProductDTO;
import com.devsaadeh.dscatalog.repositories.ProductRepository;
import com.devsaadeh.dscatalog.services.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ProductServiceIT {

    @Autowired
    private ProductService service;

    @Autowired
    private ProductRepository repository;

    private Long existingId;
    private Long nonExistingId;
    private Long countTotalProducts;

    @BeforeEach
    void setUp() throws Exception{
        existingId = 1L;
        nonExistingId = 1000L;
        countTotalProducts = 25L;
    }

    @Test
    public void deleteShouldDeleteResourceWhenIdExists(){
        service.delete(existingId);

        Assertions.assertEquals(countTotalProducts - 1,repository.count());
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdNotExists(){

        Assertions.assertThrows(ResourceNotFoundException.class,() ->{
            service.delete(nonExistingId);
        });
    }

    @Test
    public void findAllPagedShouldReturnPagedProductDTOWhenPage0Size10(){
        PageRequest pageRequest = PageRequest.of(0,10);
        Page<ProductDTO> allPaged = service.findAllPaged(pageRequest);

        Assertions.assertFalse(allPaged.isEmpty());
        Assertions.assertEquals(pageRequest.getPageNumber(),allPaged.getNumber());
        Assertions.assertEquals(pageRequest.getPageSize(),allPaged.getSize());
        Assertions.assertEquals(countTotalProducts,allPaged.getTotalElements());
    }

    @Test
    public void findAllPagedShouldReturnEmptyPageWhenPageDoesNotExist(){
        PageRequest pageRequest = PageRequest.of(50,10);

        Page<ProductDTO> allPaged = service.findAllPaged(pageRequest);

        Assertions.assertTrue(allPaged.isEmpty());
    }

    @Test
    public void findAllPagedShouldReturnSortedPageWhenSortByName(){
        PageRequest pageRequest = PageRequest.of(0,10, Sort.by("name"));

        Page<ProductDTO> allPaged = service.findAllPaged(pageRequest);

        Assertions.assertFalse(allPaged.isEmpty());
        Assertions.assertEquals("Macbook Pro",allPaged.getContent().get(0).getName());
        Assertions.assertEquals("PC Gamer",allPaged.getContent().get(1).getName());
        Assertions.assertEquals("PC Gamer Alfa",allPaged.getContent().get(2).getName());
    }



}
