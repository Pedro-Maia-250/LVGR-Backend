package com.lunarvoid.LVGR.repositorios;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import com.lunarvoid.LVGR.entidades.*;

public interface ProdutoRepositorio extends JpaRepository<Produto,Long> {

    List<Produto> findByAtivoTrue(Sort sort);
}
