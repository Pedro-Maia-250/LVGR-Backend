package com.lunarvoid.LVGR.servicos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lunarvoid.LVGR.entidades.Categoria;
import com.lunarvoid.LVGR.entidades.CategoriaProduto;
import com.lunarvoid.LVGR.entidades.Produto;
import com.lunarvoid.LVGR.repositorios.ProdutoRepositorio;

@Service
public class CPServico {
    
    @Autowired
    CategoriaServico cr;

    @Autowired
    ProdutoRepositorio pr;

    public List<CategoriaProduto> find() {
        List<Categoria> categorias = cr.findAll();
        List<Produto> produtos = pr.findAll();

        Map<Categoria, List<Produto>> produtosPorCategoria = produtos.stream().collect(Collectors.groupingBy(Produto::getCategoria));

        List<CategoriaProduto> resultado = new ArrayList<>();

        for (Categoria c : categorias) {
            List<Produto> lista = produtosPorCategoria.getOrDefault(c, new ArrayList<>());
            if(!lista.isEmpty()){
                resultado.add(new CategoriaProduto(lista, c));
            }
        }

        return resultado;
    }
}
