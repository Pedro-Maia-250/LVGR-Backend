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

import com.lunarvoid.LVGR.entidades.Mesa;
import com.lunarvoid.LVGR.servicos.MesaServico;


@RestController
@RequestMapping(value = "/mesas")
public class MesaRecurso {
    
    @Autowired
    MesaServico servico;

    @GetMapping
    public ResponseEntity<List<Mesa>> findAll(){
        return ResponseEntity.ok().body(servico.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Mesa> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(servico.findById(id));
    }

    @PostMapping
    public ResponseEntity<Mesa> save(@RequestBody Mesa obj){
        obj = servico.save(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Mesa> update(@RequestBody Mesa obj, @PathVariable Long id){
        return ResponseEntity.ok().body(servico.updateNumero(id, obj));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        servico.delete(id);
        return ResponseEntity.noContent().build();
    }

}
