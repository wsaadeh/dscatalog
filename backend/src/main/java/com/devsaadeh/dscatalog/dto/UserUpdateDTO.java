package com.devsaadeh.dscatalog.dto;

import com.devsaadeh.dscatalog.services.validation.UserInsertValid;
import com.devsaadeh.dscatalog.services.validation.UserUpdateValid;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@UserUpdateValid
public class UserUpdateDTO extends UserDTO{
}
