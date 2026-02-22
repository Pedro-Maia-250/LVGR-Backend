package com.lunarvoid.LVGR.recursos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lunarvoid.LVGR.entidades.enums.NivelAcesso;
import com.lunarvoid.LVGR.servicos.UsuarioServico;


@RestController
@RequestMapping("/usuarios")
public class UsuarioRecurso {
    
    @Autowired
    UsuarioServico servico;

    @GetMapping(value = "/{senha}")
    public ResponseEntity<NivelAcesso> acessar(@PathVariable String senha){
        return ResponseEntity.ok().body(servico.acessar(senha));
    }
}
