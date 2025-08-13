package com.devsaadeh.dscatalog.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserInsertDTO extends UserDTO{
    private String password;
}
