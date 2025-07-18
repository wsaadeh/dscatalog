package com.devsaadeh.dscatalog.services;

import com.devsaadeh.dscatalog.entities.Category;
import com.devsaadeh.dscatalog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository repository;

    public List<Category> findAll(){
        return repository.findAll();
    }
}
