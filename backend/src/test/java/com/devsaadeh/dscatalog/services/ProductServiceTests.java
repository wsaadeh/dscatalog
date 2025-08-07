package com.devsaadeh.dscatalog.services;

import com.devsaadeh.dscatalog.dto.ProductDTO;
import com.devsaadeh.dscatalog.entities.Category;
import com.devsaadeh.dscatalog.entities.Product;
import com.devsaadeh.dscatalog.repositories.CategoryRepository;
import com.devsaadeh.dscatalog.repositories.ProductRepository;
import com.devsaadeh.dscatalog.services.exception.DatabaseException;
import com.devsaadeh.dscatalog.services.exception.ResourceNotFoundException;
import com.devsaadeh.dscatalog.tests.Factory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;


//When you've used a extension of your component like we have done with ProductService. We must mock components like
// ProductRepository using @Mock instead of @MockBean.
//@ExtendWith is used to make a unit test, in other words you are using only one component a part from others.
@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    @Mock
    private CategoryRepository categoryRepository;

    private Long existingId;
    private Long nonExistingId;
    private Long dependentId;
    private PageImpl<Product> page;
    private Product product;
    private Category category;
    private ProductDTO dto;


    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 2L;
        dependentId = 3L;
        product = Factory.createProduct();
        category = Factory.createCategory();
        dto = Factory.createProductDTO();
        page = new PageImpl<>(List.of(product));

        Mockito.when(repository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);

        Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
        Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        Mockito.when(repository.getReferenceById(existingId)).thenReturn(product);
//        Mockito.when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);

        Mockito.when(categoryRepository.getReferenceById(product.getCategories().stream().findFirst().get().getId())).thenReturn(category);
//        Mockito.when(categoryRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);

//        Mockito.doNothing().when(repository).deleteById(existingId);
        Mockito.doThrow(EntityNotFoundException.class).when(repository).getReferenceById(nonExistingId);
        Mockito.doThrow(ResourceNotFoundException.class).when(repository).deleteById(nonExistingId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);

        Mockito.when(repository.existsById(existingId)).thenReturn(true);
        Mockito.when(repository.existsById(nonExistingId)).thenReturn(false);
        Mockito.when(repository.existsById(dependentId)).thenReturn(true);

    }

    @Test
    public void findByIdShouldReturnProductDTOWhenIdExists() {
        ProductDTO dto = service.findById(existingId);
        Assertions.assertNotNull(dto);

        Mockito.verify(repository).findById(existingId);
    }

    @Test
    public void findByIdShouldThrowExceptionWhenIdNotExists() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(nonExistingId);
        });
    }

    @Test
    public void updateShouldReturnProductDTOWhenIdExists() {

        dto = service.update(existingId, dto);

        Assertions.assertNotNull(dto);
    }

    @Test
    public void updateShouldThrowExceptionResourceNotFoundWhenIdNotExists() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            //dto.setId(nonExistingId);
            service.update(nonExistingId, dto);
        });
    }

    @Test
    public void findAllPagedShouldReturnPage() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<ProductDTO> result = service.findAllPaged(pageable);

        Assertions.assertNotNull(result);
        Mockito.verify(repository, Mockito.times(1)).findAll(pageable);

    }

    @Test
    public void deleteShouldThrowDatabaseExceptionWhenDependentId() {
        Assertions.assertThrows(DatabaseException.class, () -> {
            service.delete(dependentId);
        });
    }

    @Test
    public void deleteShouldThrowExceptionWhenIdNotExists() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });

    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> {
            service.delete(existingId);
        });

        Mockito.verify(repository, times(1)).deleteById(existingId);
    }
}
