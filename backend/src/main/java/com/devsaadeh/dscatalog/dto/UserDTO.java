package com.devsaadeh.dscatalog.dto;

import com.devsaadeh.dscatalog.entities.User;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    @Setter(AccessLevel.PRIVATE)
    Set<RoleDTO> roles = new HashSet<>();


    public UserDTO(User entity){
        id = entity.getId();
        firstName = entity.getFirstName();
        lastName = entity.getLastName();
        email = entity.getEmail();
        entity.getRoles().forEach(role -> roles.add(new RoleDTO(role)));
    }
}
