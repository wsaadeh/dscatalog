package com.devsaadeh.dscatalog.entities;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Category implements Serializable {
    private Long id;
    private String name;
}
