package com.lunarvoid.LVGR.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.lunarvoid.LVGR.entidades.Usuario;
import com.lunarvoid.LVGR.entidades.enums.NivelAcesso;
import com.lunarvoid.LVGR.repositorios.UsuarioRepositorio;
import com.lunarvoid.LVGR.servicos.exceptions.DatabaseException;
import com.lunarvoid.LVGR.servicos.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;


@Service
public class UsuarioServico {
    
    @Autowired
    UsuarioRepositorio repositorio;

    public List<Usuario> findAll(){
        return repositorio.findByAtivoTrue();
    }

    public Usuario findById(Long id){
        return repositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Usuario save(Usuario Usuario){
        return repositorio.save(Usuario);
    }

    public void delete(Long id){
        try{
            Usuario obj = repositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
            obj.setAtivo(false);
            repositorio.save(obj);
        }catch(EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(id);
        }catch(DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    public Usuario update(Long id, Usuario newUsuario){
        try{
            Usuario obj = repositorio.getReferenceById(id);
            obj.setNome(newUsuario.getNome());
            obj.setSenha(newUsuario.getSenha());
            obj.setAcesso(newUsuario.getAcesso());
            return repositorio.save(obj);
        }catch(EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

    public NivelAcesso acessar(Usuario obj){
        Usuario user = repositorio.findByNome(obj.getNome()).orElseThrow(() -> new ResourceNotFoundException(0));
        if(user.getSenha() == obj.getSenha()){
            return user.getAcesso();
        }else{
            throw new IllegalArgumentException();
        }
    }

}
