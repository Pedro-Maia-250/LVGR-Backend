package com.lunarvoid.LVGR.servicos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lunarvoid.LVGR.entidades.Produto;
import com.lunarvoid.LVGR.repositorios.ProdutoRepositorio;
import com.lunarvoid.LVGR.servicos.exceptions.DatabaseException;
import com.lunarvoid.LVGR.servicos.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProdutoServico {
    
    @Value("${app.upload.dir}")
    private String uploadDir;

    @Autowired
    ProdutoRepositorio repositorio;

    public List<Produto> findAll(){
        return repositorio.findAll(Sort.by("categoria").ascending());
    }

    public Produto findById(Long id){
        return repositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }


    public Produto save(Produto produto, MultipartFile file) throws IOException {

        String nomeArquivo = UUID.randomUUID() + "_" + file.getOriginalFilename();

        Path caminho = Paths.get(uploadDir, nomeArquivo);

        Files.copy(file.getInputStream(), caminho);

        String url = "/imagens/" + nomeArquivo;

        produto.setImgUrl(url);

        return repositorio.save(produto);
    }

    public void delete(Long id) {

        try {

            Produto obj = repositorio.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(id));

            // Deletar imagem física se existir
            if (obj.getImgUrl() != null) {
                try {
                    Path caminho = Paths.get(uploadDir,
                            obj.getImgUrl().replace("/imagens/", ""));
                    Files.deleteIfExists(caminho);
                } catch (IOException e) {
                    // Você pode logar isso depois
                    System.out.println("Erro ao deletar imagem física.");
                }
            }

            repositorio.delete(obj);

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Produto update(Long id, Produto newProduto, MultipartFile file) throws IOException {

        try {
            Produto obj = repositorio.getReferenceById(id);

            obj.setDescricao(newProduto.getDescricao());
            obj.setNome(newProduto.getNome());
            obj.setPrice(newProduto.getPrice());

            // Se enviou nova imagem
            if (file != null && !file.isEmpty()) {

                // (Opcional mas recomendado) deletar imagem antiga
                if (obj.getImgUrl() != null) {
                    Path antiga = Paths.get(uploadDir, 
                            obj.getImgUrl().replace("/imagens/", ""));
                    Files.deleteIfExists(antiga);
                }

                String nomeArquivo = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path caminho = Paths.get(uploadDir, nomeArquivo);
                Files.copy(file.getInputStream(), caminho);

                obj.setImgUrl("/imagens/" + nomeArquivo);
            }

            return repositorio.save(obj);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

}
