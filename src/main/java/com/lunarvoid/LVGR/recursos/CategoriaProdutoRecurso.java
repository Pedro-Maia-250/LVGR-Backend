package com.lunarvoid.LVGR.recursos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lunarvoid.LVGR.entidades.CategoriaProduto;
import com.lunarvoid.LVGR.servicos.CPServico;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/cp")
public class CategoriaProdutoRecurso {
    
    @Autowired
    CPServico servico;

    @GetMapping
    public ResponseEntity<List<CategoriaProduto>> find(){
        return ResponseEntity.ok().body(servico.find());
    }
}
