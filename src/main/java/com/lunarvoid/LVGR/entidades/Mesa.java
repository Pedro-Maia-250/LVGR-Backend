package com.lunarvoid.LVGR.entidades;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "mesa_tb")
public class Mesa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;

    @Column(unique = true, nullable = false, updatable = false)
    private String token;

    
    @JsonIgnore
    @OneToMany(mappedBy = "mesa")
    private List<Pedido> pedidos = new ArrayList<>();
    
    @JsonIgnore
    @OneToMany(mappedBy = "mesa")
    private List<Alarme> alarmes = new ArrayList<>();
    
    protected Mesa(){}
    
    public Mesa(Long id, String numero) {
        this.id = id;
        this.numero = numero;
    }
    
    public String getToken() {
        return token;
    }

    @PrePersist
    public void gerarToken() {
        if(this.token == null){
            this.token = UUID.randomUUID().toString();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public List<Alarme> getAlarmes() {
        return alarmes;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Mesa other = (Mesa) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    
}
