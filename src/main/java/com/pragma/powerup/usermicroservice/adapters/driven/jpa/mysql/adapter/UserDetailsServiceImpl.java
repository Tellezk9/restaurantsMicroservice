package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.IUserRepository;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.PrincipalUser;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.RoleEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String documentID) throws UsernameNotFoundException {
        UserEntity usuario = userRepository.findByDniNumber(Integer.parseInt(documentID)).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        if (usuario.getRoleEntity() == null) {
            throw new UsernameNotFoundException("User not found with documentID: " + documentID);
        }
        List<RoleEntity> roles = Collections.singletonList(usuario.getRoleEntity());

        return PrincipalUser.build(usuario, roles);
    }
}
