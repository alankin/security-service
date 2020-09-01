package com.aforo255.securityservice.model.dao;


import com.aforo255.securityservice.model.entity.Usuario;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface UsuarioDao extends PagingAndSortingRepository<Usuario, Long> {

    public Usuario findByUsername(@Param("nombre") String username);
}
