package com.devsaadeh.dscatalog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AuthDTO {

    @Email(message = "Invalid E-mail.")
    @NotBlank(message = "Required field.")
    private String email;

}
