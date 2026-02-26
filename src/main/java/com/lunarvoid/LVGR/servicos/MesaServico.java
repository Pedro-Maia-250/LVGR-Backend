package com.lunarvoid.LVGR.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lunarvoid.LVGR.entidades.Mesa;
import com.lunarvoid.LVGR.repositorios.MesaRepositorio;
import com.lunarvoid.LVGR.servicos.exceptions.DatabaseException;
import com.lunarvoid.LVGR.servicos.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MesaServico {
    
    @Autowired
    MesaRepositorio repositorio;

    public List<Mesa> findAll(){
        return repositorio.findByAtivoTrue(Sort.by("numero").ascending());
    }

    public Mesa findById(Long id){
        return repositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Mesa findByToken(String token){
        return repositorio.findByToken(token).orElseThrow(() -> new ResourceNotFoundException(token));
    }

    public Mesa save(Mesa Mesa){
        return repositorio.save(Mesa);
    }

    public void delete(Long id){
        try{
            Mesa obj = repositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
            obj.setAtivo(false);
            repositorio.save(obj);
        }catch(EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(id);
        }catch(DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    public Mesa updateNumero(Long id, Mesa newMesa){
        try{
            Mesa obj = repositorio.getReferenceById(id);
            obj.setNumero(newMesa.getNumero());
            return repositorio.save(obj);
        }catch(EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

}
