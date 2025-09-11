package com.devsaadeh.dscatalog.dto;

import com.devsaadeh.dscatalog.services.validation.UserInsertValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@UserInsertValid
public class UserInsertDTO extends UserDTO{

    @NotBlank(message = "Required field")
    @Size(min = 8,message = "Must have at least 8 characters.")
    private String password;
}
