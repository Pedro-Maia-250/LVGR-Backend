package com.lunarvoid.LVGR.servicos;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lunarvoid.LVGR.entidades.ItemPedido;
import com.lunarvoid.LVGR.entidades.Pedido;
import com.lunarvoid.LVGR.entidades.Produto;
import com.lunarvoid.LVGR.entidades.enums.StatusPedido;
import com.lunarvoid.LVGR.repositorios.PedidoRepositorio;
import com.lunarvoid.LVGR.repositorios.ProdutoRepositorio;
import com.lunarvoid.LVGR.servicos.exceptions.DatabaseException;
import com.lunarvoid.LVGR.servicos.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PedidoServico {
    
    @Autowired
    PedidoRepositorio repositorio;

    @Autowired
    ProdutoRepositorio pRepositorio;

    public List<Pedido> findAll(){
        return repositorio.findAll(Sort.by("status").ascending());
    }

    public Pedido findById(Long id){
        return repositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Pedido save(Pedido pedido){

        pedido.setMomento(Instant.now());
        pedido.setStatus(StatusPedido.SUBMETIDO);

        for(ItemPedido item : pedido.getItems()){

            // SETAR O PEDIDO DENTRO DO ITEM (ESSENCIAL)
            item.setPedido(pedido);

            // Buscar produto real no banco
            Produto produto = pRepositorio
                    .findById(item.getProduto().getId())
                    .orElseThrow();

            item.setProduto(produto);

            // Definir o preço no momento do pedido
            item.setPrice(produto.getPrice());
        }

        return repositorio.save(pedido);
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

    public Pedido updateStatus(Long id, Pedido newPedido){
        try{
            Pedido obj = repositorio.getReferenceById(id);
            obj.setStatus(newPedido.getStatus());
            return repositorio.save(obj);
        }catch(EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

}
