package com.lunarvoid.LVGR.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lunarvoid.LVGR.entidades.Alarme;
import com.lunarvoid.LVGR.repositorios.AlarmeRepositorio;
import com.lunarvoid.LVGR.servicos.exceptions.DatabaseException;
import com.lunarvoid.LVGR.servicos.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AlarmeServico {
    
    @Autowired
    AlarmeRepositorio repositorio;

    public List<Alarme> findAll(){
        return repositorio.findAll(Sort.by("momento").ascending());
    }

    public Alarme findById(Long id){
        return repositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Alarme save(Alarme Alarme){
        return repositorio.save(Alarme);
    }

    public void delete(Long id){
        try{
            repositorio.deleteById(id);
        }catch(EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(id);
        }catch(DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    public Alarme update(Long id, Alarme newAlarme){
        try{
            Alarme obj = repositorio.getReferenceById(id);
            obj.setMesa(newAlarme.getMesa());
            obj.setMomento(newAlarme.getMomento());
            return repositorio.save(obj);
        }catch(EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

}
