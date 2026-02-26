package com.lunarvoid.LVGR.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import com.lunarvoid.LVGR.entidades.*;

public interface MesaRepositorio extends JpaRepository<Mesa,Long> {
    
    Optional<Mesa> findByToken(String token);
    List<Mesa> findByAtivoTrue(Sort sort);
    
}
