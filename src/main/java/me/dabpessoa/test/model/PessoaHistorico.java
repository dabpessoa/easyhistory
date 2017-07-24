package me.dabpessoa.test.model;

import me.dabpessoa.easyHistory.model.AbstractHistory;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by diego.pessoa on 24/07/2017.
 */
@Entity
@Table(name = "pessoa_hist", schema = "easyhistory")
public class PessoaHistorico extends AbstractHistory {

    @Id
    private Long id;
    private String nome;
    private String cpf;

    public PessoaHistorico() {}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PessoaHistorico)) return false;
        if (!super.equals(o)) return false;

        PessoaHistorico that = (PessoaHistorico) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (nome != null ? !nome.equals(that.nome) : that.nome != null) return false;
        return cpf != null ? cpf.equals(that.cpf) : that.cpf == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (cpf != null ? cpf.hashCode() : 0);
        return result;
    }

}
