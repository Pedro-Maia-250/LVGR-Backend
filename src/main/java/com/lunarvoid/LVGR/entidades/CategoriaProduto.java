package com.lunarvoid.LVGR.entidades;

import java.util.List;

public class CategoriaProduto {
    private List<Categoria> categorias;
    private List<Produto> produto;

    public CategoriaProduto(List<Produto> produto, List<Categoria> categorias) {
        this.produto = produto;
        this.categorias = categorias;
    }

    public List<Produto> getProdutos() { 
        return produto; 
    }

    public List<Categoria> getCategorias() { 
        return categorias; 
    }
}
