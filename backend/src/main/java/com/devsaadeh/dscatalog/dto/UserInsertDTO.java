package com.devsaadeh.dscatalog.dto;

import com.devsaadeh.dscatalog.services.validation.UserInsertValid;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@UserInsertValid
public class UserInsertDTO extends UserDTO{
    private String password;
}
