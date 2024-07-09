package com.api.foroHub.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    @Query("SELECT u FROM Usuario u WHERE u.username LIKE :username")
    Optional<Usuario> findByUsername2(@Param("username") String username);
    UserDetails findByUsername(String username);

    Optional<Usuario> findByEmail(String email);
}
