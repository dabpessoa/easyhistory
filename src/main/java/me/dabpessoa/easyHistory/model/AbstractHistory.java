package me.dabpessoa.easyHistory.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * Created by diego.pessoa on 06/07/2017.
 */
@MappedSuperclass
public abstract class AbstractHistory {

    @Column(name = "data_registro", nullable = false)
    private Date dataRegistro;

    @Column(name = "cd_usuario")
    private Long codigoUsuario;

    public AbstractHistory() {}

    public AbstractHistory(Date dataRegistro, Long codigoUsuario) {
        this.dataRegistro = dataRegistro;
        this.codigoUsuario = codigoUsuario;
    }

    public Date getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(Date dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Long getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(Long codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractHistory)) return false;
        if (!super.equals(o)) return false;

        AbstractHistory that = (AbstractHistory) o;

        if (dataRegistro != null ? !dataRegistro.equals(that.dataRegistro) : that.dataRegistro != null) return false;
        return codigoUsuario != null ? codigoUsuario.equals(that.codigoUsuario) : that.codigoUsuario == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (dataRegistro != null ? dataRegistro.hashCode() : 0);
        result = 31 * result + (codigoUsuario != null ? codigoUsuario.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AbstractHistory{" +
                "dataRegistro=" + dataRegistro +
                ", codigoUsuario=" + codigoUsuario +
                '}';
    }

}
