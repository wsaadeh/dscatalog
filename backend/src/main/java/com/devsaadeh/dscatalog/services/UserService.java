package com.devsaadeh.dscatalog.services;

import com.devsaadeh.dscatalog.dto.UserDTO;
import com.devsaadeh.dscatalog.dto.UserInsertDTO;
import com.devsaadeh.dscatalog.dto.UserUpdateDTO;
import com.devsaadeh.dscatalog.entities.Role;
import com.devsaadeh.dscatalog.entities.User;
import com.devsaadeh.dscatalog.projections.UserDetailsProjection;
import com.devsaadeh.dscatalog.repositories.RoleRepository;
import com.devsaadeh.dscatalog.repositories.UserRepository;
import com.devsaadeh.dscatalog.services.exception.DatabaseException;
import com.devsaadeh.dscatalog.services.exception.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(Pageable pageable) {
        Page<User> userPage = repository.findAll(pageable);
        return userPage.map(x -> new UserDTO(x));
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        Optional<User> obj = repository.findById(id);
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO dto) {
        User entity = new User();
        copyDtoToEntity(dto, entity);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity = repository.save(entity);
        return new UserDTO(entity);

    }

    @Transactional
    public UserDTO update(Long id, UserUpdateDTO dto) {
        try {
            User entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new UserDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }

    }

    @Transactional
    public void delete(Long id) {
        try {
            if (!repository.existsById(id)) {
                throw new ResourceNotFoundException("Id not found " + id);
            }
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation.");
        }
    }

    private void copyDtoToEntity(UserDTO dto, User entity) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        //clear attribute
        entity.getRoles().clear();
        //add data from dto to entity
        dto.getRoles().forEach(x-> {
            Role role = roleRepository.getReferenceById(x.getId());
            entity.getRoles().add(role);
        });
//        for (RoleDTO r: dto.getRoles()){
//            Role role = roleRepository.getReferenceById(r.getId());
//            entity.getRoles().add(role);
//        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> listUserDetails = repository.searchUserAndRoleByEmail(username);
        if (listUserDetails.size()==0){
            throw new UsernameNotFoundException("User not found.");
        }

        User user = new User();
        user.setEmail(username);
        user.setPassword(listUserDetails.get(0).getPassword());
        for (UserDetailsProjection u: listUserDetails){
            user.addRole(new Role(u.getRoleId(), u.getAuthority()));
        }
        return user;
    }
}
