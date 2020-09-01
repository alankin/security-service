package com.aforo255.securityservice.service;


import com.aforo255.securityservice.model.dao.UsuarioDao;
import com.aforo255.securityservice.model.entity.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service // se puede poner entre parentesis un tipo alias y en el lugar donde se lo importe, ponerle un qualifier
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioDao client;

    private Logger log = LoggerFactory.getLogger(UsuarioService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = client.findByUsername(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Error Login ");
        }

        List<GrantedAuthority> authorities = usuario.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getNombre()))
                .peek(authority -> log.info("Role: " + authority.getAuthority())).collect(Collectors.toList());

        log.info("Usuario autenticado: " + username);

        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getEnabled(),
                true,
                true,
                true,
                authorities);
    }

}
