package com.devsaadeh.dscatalog.dto;

import com.devsaadeh.dscatalog.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleDTO implements Serializable {

    private Long id;
    private String authority;

    public RoleDTO(Role entity){
        id = entity.getId();
        authority = entity.getAuthority();
    }

}
