package com.lunarvoid.LVGR.servicos;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lunarvoid.LVGR.entidades.Categoria;
import com.lunarvoid.LVGR.repositorios.CategoriaRepositorio;
import com.lunarvoid.LVGR.servicos.exceptions.DatabaseException;
import com.lunarvoid.LVGR.servicos.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CategoriaServico {
    
    @Autowired
    CategoriaRepositorio repositorio;

    public List<Categoria> findAll(){
        return repositorio.findByAtivoTrue(Sort.by("ordem").ascending());
    }

    public Categoria findById(Long id){
        return repositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Categoria save(Categoria categoria){
        Integer m = repositorio.findMaxOrdem();
        categoria.setOrdem(m + 1);
        return repositorio.save(categoria);
    }

    public void delete(Long id){
        try{
            Categoria obj = repositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
            if(obj.getProdutos().isEmpty()){

                obj.setAtivo(false);
                repositorio.save(obj);
                
            }else{

                throw new IllegalArgumentException();
            }
            
        }catch(EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(id);
        }catch(DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    public Categoria update(Long id, Categoria newCategoria){
        try{
            Categoria categoria = repositorio.getReferenceById(id);
            categoria.setNome(newCategoria.getNome());
            return repositorio.save(categoria);
        }catch(EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }


    @Transactional
    public void mudarOrdem(Long id, Integer novaOrdem){

        List<Categoria> categorias = repositorio.findAllByOrderByOrdemAsc();

        Categoria atual = categorias.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(id));

        categorias.remove(atual);
        categorias.add(novaOrdem - 1, atual);

        int ordem = 1;
        for (Categoria c : categorias) {
            c.setOrdem(ordem++);
        }

        repositorio.saveAll(categorias);
    }

    @Transactional
    public List<Categoria> updateAll(List<Categoria> listaRecebida) {

        Set<Long> ids = new HashSet<>();
        for (Categoria c : listaRecebida) {
            if (!ids.add(c.getId())) {
                throw new IllegalArgumentException("ID duplicado");
            }
        }

        List<Categoria> categoriasBanco = repositorio.findByAtivoTrue(Sort.by("ordem").ascending());

        if (categoriasBanco.size() != listaRecebida.size()) {
            throw new IllegalArgumentException("Quantidade de categorias incorreta");
        }

        Map<Long, Categoria> mapaRecebido = listaRecebida.stream().collect(Collectors.toMap(Categoria::getId, c -> c));

        for (Categoria categoriaBanco : categoriasBanco) {
            Categoria nova = mapaRecebido.get(categoriaBanco.getId());

            if (nova == null) {
                throw new IllegalArgumentException("ID inexistente");
            }

            categoriaBanco.setOrdem(nova.getOrdem());
        }

        return repositorio.saveAll(categoriasBanco);
    }

}
