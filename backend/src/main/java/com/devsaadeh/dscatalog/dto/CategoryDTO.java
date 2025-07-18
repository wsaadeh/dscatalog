package com.devsaadeh.dscatalog.dto;

import com.devsaadeh.dscatalog.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO implements Serializable {
    private Long id;
    private String name;

    public CategoryDTO(Category category){
        id = category.getId();
        name = category.getName();
    }
}
