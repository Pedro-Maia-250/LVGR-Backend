package com.lunarvoid.LVGR.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lunarvoid.LVGR.entidades.*;

public interface UsuarioRepositorio extends JpaRepository<Usuario,Long> {

    Optional<Usuario> findByNome(String nome);
    List<Usuario> findByAtivoTrue();
}
