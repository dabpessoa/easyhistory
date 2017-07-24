package me.dabpessoa.test.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by diego.pessoa on 24/07/2017.
 */
@Entity
@Table(name = "usuario", schema = "easyhistory")
public class Usuario {

    @Id private Long id;
    private String login;
    private String password;

    @Transient
    private Pessoa pessoa;

    public Usuario() {}

    public Usuario(String login, String password, Pessoa pessoa) {
        this.login = login;
        this.password = password;
        this.pessoa = pessoa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;

        Usuario usuario = (Usuario) o;

        return login != null ? login.equals(usuario.login) : usuario.login == null;
    }

    @Override
    public int hashCode() {
        return login != null ? login.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "login='" + login + '\'' +
                ", pessoa=" + pessoa +
                '}';
    }

}
