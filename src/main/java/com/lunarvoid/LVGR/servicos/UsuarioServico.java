package com.lunarvoid.LVGR.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lunarvoid.LVGR.entidades.Usuario;
import com.lunarvoid.LVGR.entidades.enums.NivelAcesso;
import com.lunarvoid.LVGR.repositorios.UsuarioRepositorio;
import com.lunarvoid.LVGR.servicos.exceptions.ResourceNotFoundException;


@Service
public class UsuarioServico {
    
    @Autowired
    UsuarioRepositorio repositorio;

    public NivelAcesso acessar(String senha){
        Usuario user = repositorio.findBySenha(senha).orElseThrow(() -> new ResourceNotFoundException(0));
        return user.getAcesso();
    }

}
