package com.devsaadeh.dscatalog.dto;

import com.devsaadeh.dscatalog.entities.Category;
import com.devsaadeh.dscatalog.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class ProductDTO implements Serializable {
    private Long id;

    @Size(min = 5, max = 60, message = "Name must have between 5 and 60 characters.")
    @NotBlank(message = "Required field")
    private String name;

    @NotBlank(message = "Required field")
    private String description;

    @Positive(message = "Price must be positive.")
    private Double price;
    private String imgUrl;

    @PastOrPresent(message = "Product date can't be in the future")
    private Instant date;

    @Setter(AccessLevel.NONE)
    private List<CategoryDTO> categories = new ArrayList<>();

    public ProductDTO(Long id, String name, String description, Double price, String imgUrl, Instant date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.date = date;
    }

    public ProductDTO(Product entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        price = entity.getPrice();
        imgUrl = entity.getImgUrl();
        date = entity.getDate();
    }

    public ProductDTO(Product entity, Set<Category> categories){
        this(entity);
//        categories.stream().map(x -> this.categories.add(new CategoryDTO(x)));
        categories.forEach(x -> this.categories.add(new CategoryDTO(x)));
    }

}
