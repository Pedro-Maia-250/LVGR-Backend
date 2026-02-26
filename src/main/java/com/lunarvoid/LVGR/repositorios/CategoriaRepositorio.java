package com.lunarvoid.LVGR.repositorios;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lunarvoid.LVGR.entidades.*;

public interface CategoriaRepositorio extends JpaRepository<Categoria,Long> {
    @Query("SELECT COALESCE(MAX(c.ordem), 0) FROM Categoria c")
    Integer findMaxOrdem();

    List<Categoria> findByAtivoTrue(Sort sort);
    List<Categoria> findAllByOrderByOrdemAsc();
}
