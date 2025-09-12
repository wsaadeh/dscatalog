package com.devsaadeh.dscatalog.resources;

import com.devsaadeh.dscatalog.dto.AuthDTO;
import com.devsaadeh.dscatalog.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    @Autowired
    AuthService authService;

    @PostMapping("/recover-token")
    public ResponseEntity<Void> createRecoverToken(@Valid @RequestBody AuthDTO body){
        authService.createRecoverToken(body);
        return ResponseEntity.noContent().build();
    }

}
