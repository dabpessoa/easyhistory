package me.dabpessoa.test.model;

import me.dabpessoa.easyHistory.model.enums.HistoryClass;
import me.dabpessoa.easyHistory.model.enums.HistoryField;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by diego.pessoa on 24/07/2017.
 */
@Entity
@Table(name = "pessoa", schema = "easyhistory")
@HistoryClass(PessoaHistorico.class)
public class Pessoa {

    @Id private Long id;
    @HistoryField(name = "nome")
    private String nome;
    @HistoryField(name = "cpf")
    private String cpf;
    private int idade;

    @Transient
    private Endereco endereco;

    public Pessoa() {}

    public Pessoa(String nome, String cpf, int idade, Endereco endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
        this.endereco = endereco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pessoa)) return false;

        Pessoa pessoa = (Pessoa) o;

        if (idade != pessoa.idade) return false;
        if (nome != null ? !nome.equals(pessoa.nome) : pessoa.nome != null) return false;
        return cpf != null ? cpf.equals(pessoa.cpf) : pessoa.cpf == null;
    }

    @Override
    public int hashCode() {
        int result = nome != null ? nome.hashCode() : 0;
        result = 31 * result + (cpf != null ? cpf.hashCode() : 0);
        result = 31 * result + idade;
        return result;
    }

}
