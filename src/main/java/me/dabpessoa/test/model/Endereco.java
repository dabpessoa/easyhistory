package me.dabpessoa.test.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by diego.pessoa on 24/07/2017.
 */
@Entity
@Table(name = "endereco", schema = "easyhistory")
public class Endereco {

    @Id private Long id;
    private String logradouro;
    private int numero;
    private String bairro;
    private String cidade;
    private String pais;

    public Endereco() {}

    public Endereco(String logradouro, int numero, String bairro, String cidade, String pais) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.pais = pais;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Endereco)) return false;

        Endereco endereco = (Endereco) o;

        if (numero != endereco.numero) return false;
        if (logradouro != null ? !logradouro.equals(endereco.logradouro) : endereco.logradouro != null) return false;
        if (bairro != null ? !bairro.equals(endereco.bairro) : endereco.bairro != null) return false;
        if (cidade != null ? !cidade.equals(endereco.cidade) : endereco.cidade != null) return false;
        return pais != null ? pais.equals(endereco.pais) : endereco.pais == null;
    }

    @Override
    public int hashCode() {
        int result = logradouro != null ? logradouro.hashCode() : 0;
        result = 31 * result + numero;
        result = 31 * result + (bairro != null ? bairro.hashCode() : 0);
        result = 31 * result + (cidade != null ? cidade.hashCode() : 0);
        result = 31 * result + (pais != null ? pais.hashCode() : 0);
        return result;
    }

}
