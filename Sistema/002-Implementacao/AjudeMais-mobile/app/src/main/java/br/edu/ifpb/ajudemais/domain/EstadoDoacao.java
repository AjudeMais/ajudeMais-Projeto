package br.edu.ifpb.ajudemais.domain;

import java.io.Serializable;
import java.util.Date;

import br.edu.ifpb.ajudemais.enumarations.Estado;

/**
 * <p>
 * <b>{@link EstadoDoacao}</b>
 * </p>
 * <p>
 * <p>
 * Mapea os estados de doação
 * </p>
 *
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */


public class EstadoDoacao implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = -7652424045014577650L;
    /**
     *
     */
    private Long id;

    /**
     *
     */
    private Date data;

    /**
     *
     */
    private Boolean notificado;

    /**
     *
     */
    private Estado estadoDoacao;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * @return the data
     */
    public Date getData() {
        return data;
    }
    /**
     * @param data the data to set
     */
    public void setData(Date data) {
        this.data = data;
    }
    /**
     * @return the notificado
     */
    public Boolean getNotificado() {
        return notificado;
    }
    /**
     * @param notificado the notificado to set
     */
    public void setNotificado(Boolean notificado) {
        this.notificado = notificado;
    }

    /**
     * @return the estadoDoacao
     */
    public Estado getEstadoDoacao() {
        return estadoDoacao;
    }
    /**
     * @param estadoDoacao the estadoDoacao to set
     */
    public void setEstadoDoacao(Estado estadoDoacao) {
        this.estadoDoacao = estadoDoacao;
    }
}
