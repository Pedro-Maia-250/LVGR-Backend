package com.lunarvoid.LVGR.recursos;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lunarvoid.LVGR.entidades.Produto;
import com.lunarvoid.LVGR.servicos.ProdutoServico;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/produtos")
public class ProdutoRecurso {
    
    @Autowired
    ProdutoServico servico;

    @GetMapping
    public ResponseEntity<List<Produto>> findAll(){
        return ResponseEntity.ok().body(servico.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Produto> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(servico.findById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Produto> save(
            @RequestParam("produto") String produtoJson,
            @RequestParam("file") MultipartFile file) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        Produto produto = mapper.readValue(produtoJson, Produto.class);

        produto = servico.save(produto, file);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(produto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(produto);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Produto> update(
            @PathVariable Long id,
            @RequestParam("produto") String produtoJson,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        Produto produto = mapper.readValue(produtoJson, Produto.class);

        Produto atualizado = servico.update(id, produto, file);

        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        servico.delete(id);
        return ResponseEntity.noContent().build();
    }

}
