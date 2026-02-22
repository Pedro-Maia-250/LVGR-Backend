package com.lunarvoid.LVGR.recursos;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lunarvoid.LVGR.entidades.Categoria;
import com.lunarvoid.LVGR.servicos.CategoriaServico;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaRecurso {
    
    @Autowired
    CategoriaServico servico;

    @GetMapping
    public ResponseEntity<List<Categoria>> findAll(){
        return ResponseEntity.ok().body(servico.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Categoria> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(servico.findById(id));
    }

    @PostMapping
    public ResponseEntity<Categoria> save(@RequestBody Categoria obj){
        obj = servico.save(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @PutMapping
    public ResponseEntity<List<Categoria>> updateAll(@RequestBody List<Categoria> obj){
        return ResponseEntity.ok().body(servico.updateAll(obj));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Categoria> update(@RequestBody Categoria obj, @PathVariable Long id){
        return ResponseEntity.ok().body(servico.update(id, obj));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        servico.delete(id);
        return ResponseEntity.noContent().build();
    }

}
