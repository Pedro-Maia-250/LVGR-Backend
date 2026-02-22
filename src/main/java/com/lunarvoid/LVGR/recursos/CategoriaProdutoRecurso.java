package com.lunarvoid.LVGR.recursos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lunarvoid.LVGR.entidades.Categoria;
import com.lunarvoid.LVGR.entidades.CategoriaProduto;
import com.lunarvoid.LVGR.entidades.Produto;
import com.lunarvoid.LVGR.servicos.CategoriaServico;
import com.lunarvoid.LVGR.servicos.ProdutoServico;

@RestController
@RequestMapping(value = "/cp")
public class CategoriaProdutoRecurso {
    
    @Autowired
    private CategoriaServico cs;

    @Autowired
    private ProdutoServico ps;

    @GetMapping
    public ResponseEntity<CategoriaProduto> find(){
        List<Produto> pl = ps.findAll();
        List<Categoria> cl = cs.findAll();
        CategoriaProduto response = new CategoriaProduto(pl, cl);
        return ResponseEntity.ok(response);
    }
}
