package com.lunarvoid.LVGR.entidades;

import java.util.List;

public class CategoriaProduto {
    private Categoria categoria;
    private List<Produto> produto;

    public CategoriaProduto(List<Produto> produto, Categoria categoria) {
        this.produto = produto;
        this.categoria = categoria;
    }

    public List<Produto> getProdutos() { 
        return produto; 
    }

    public Categoria getCategoria() { 
        return categoria; 
    }
}
