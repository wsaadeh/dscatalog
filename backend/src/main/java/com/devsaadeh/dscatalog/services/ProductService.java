package com.devsaadeh.dscatalog.services;

import com.devsaadeh.dscatalog.dto.ProductDTO;
import com.devsaadeh.dscatalog.dto.ProductDTO;
import com.devsaadeh.dscatalog.entities.Category;
import com.devsaadeh.dscatalog.entities.Product;
import com.devsaadeh.dscatalog.entities.Product;
import com.devsaadeh.dscatalog.repositories.CategoryRepository;
import com.devsaadeh.dscatalog.repositories.ProductRepository;
import com.devsaadeh.dscatalog.repositories.ProductRepository;
import com.devsaadeh.dscatalog.services.exception.DatabaseException;
import com.devsaadeh.dscatalog.services.exception.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository repository;

    @Autowired
    CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        return repository.findAll().stream().map(x -> new ProductDTO(x)).toList();
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
        return repository.findAll(pageRequest).map(x -> new ProductDTO(x,x.getCategories()));
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        return repository.findById(id)
                .stream()
                .map(x -> new ProductDTO(x,x.getCategories()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found."));
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product entity = new Product();
        copyDtoToEntity(entity,dto);
        entity = repository.save(entity);
        return new ProductDTO(entity,entity.getCategories());
    }

    private void copyDtoToEntity(Product entity, ProductDTO dto) {
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setDescription(dto.getDescription());
        entity.setImgUrl(dto.getImgUrl());
        entity.setDate(dto.getDate());
        entity.getCategories().clear();//Limpar lista de categorias
        dto.getCategories().forEach(x -> entity.getCategories().add( categoryRepository.getReferenceById(x.getId())));
    }

    @Transactional
    public ProductDTO update(Long id,ProductDTO dto) {
        try {
            Product entity = repository.getReferenceById(id);
            copyDtoToEntity(entity,dto);
            repository.save(entity);
            return new ProductDTO(entity,entity.getCategories());
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

}
