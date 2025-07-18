package com.devsaadeh.dscatalog.services;

import com.devsaadeh.dscatalog.dto.CategoryDTO;
import com.devsaadeh.dscatalog.entities.Category;
import com.devsaadeh.dscatalog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll(){
        return repository.findAll().stream().map(x-> new CategoryDTO(x)).toList();
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id){
        return repository.findById(id).stream().map(x-> new CategoryDTO(x)).findFirst().get();
    }
}
