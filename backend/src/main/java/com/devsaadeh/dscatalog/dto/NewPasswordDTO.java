package com.devsaadeh.dscatalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewPasswordDTO {
    private String token;

    @NotBlank(message = "Required field.")
    @Size(min = 8, message = "Must have at least 8 characters.")
    private String password;
}
