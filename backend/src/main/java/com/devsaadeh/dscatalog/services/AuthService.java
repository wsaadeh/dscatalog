package com.devsaadeh.dscatalog.services;

import com.devsaadeh.dscatalog.dto.AuthDTO;
import com.devsaadeh.dscatalog.dto.EmailDTO;
import com.devsaadeh.dscatalog.entities.PasswordRecover;
import com.devsaadeh.dscatalog.entities.User;
import com.devsaadeh.dscatalog.repositories.PasswordRecoverRepository;
import com.devsaadeh.dscatalog.repositories.UserRepository;
import com.devsaadeh.dscatalog.services.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class AuthService {

    @Value("${email.password-recover.token.minutes}")
    private Long tokenMinutes;

    @Value("${email.password-recover.uri}")
    private String recoverUri;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordRecoverRepository passwordRecoverRepository;

    @Autowired
    private EmailService emailService;

    @Transactional
    public void createRecoverToken(AuthDTO dto){
        User user = userRepository.findByEmail(dto.getEmail());
        if (user == null){
            throw new ResourceNotFoundException("User doesn't exists.");
        }
        PasswordRecover entity = new PasswordRecover();
        entity.setEmail(dto.getEmail());
        entity.setToken(UUID.randomUUID().toString());
        entity.setExpiration(Instant.now().plus(tokenMinutes, ChronoUnit.MINUTES));
        entity = passwordRecoverRepository.save(entity);

        StringBuilder body = new StringBuilder();
        body.append("Acess o link para definir uma nova senha\n\n");
        body.append(recoverUri + entity.getToken() + "\n\n");
        body.append("Token valido por: " + tokenMinutes + " minutos. \n\n");
        body.append("Atenciosamente,\n\n");
        body.append("Suporte");

        EmailDTO emailDTO = new EmailDTO(dto.getEmail(), "Password Recover",body.toString());
        emailService.sendEmail(emailDTO);
    }
}
