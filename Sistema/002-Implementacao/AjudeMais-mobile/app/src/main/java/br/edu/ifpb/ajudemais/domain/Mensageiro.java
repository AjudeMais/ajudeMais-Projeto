package br.edu.ifpb.ajudemais.domain;

import java.io.Serializable;

/**
 * <p>
 * <b>{@link Mensageiro}</b>
 * </p>
 * <p>
 * <p>
 * Entidade Um mensageiro no sistema.
 * </p>
 *
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */

public class Mensageiro implements Serializable {

    private Long id;
    private String nome;
    private String cpf;
    private String telefone;
    private String tokenFCM;
    private Conta conta;
    private Imagem foto;

    /**
     *
     * @param nome
     * @param cpf
     * @param telefone
     * @param conta
     */
    public Mensageiro(String nome, String cpf, String telefone, Conta conta) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.conta = conta;
    }

    /**
     *
     * @return Long
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return String
     */
    public String getNome() {
        return nome;
    }

    /**
     *
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     *
     * @return String
     */
    public String getCpf() {
        return cpf;
    }

    /**
     *
     * @param cpf
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     *
     * @return String
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     *
     * @param telefone
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     *
     * @return String
     */
    public String getTokenFCM() {
        return tokenFCM;
    }

    /**
     *
     * @param tokenFCM
     */
    public void setTokenFCM(String tokenFCM) {
        this.tokenFCM = tokenFCM;
    }

    /**
     *
     * @return Conta
     */
    public Conta getConta() {
        return conta;
    }

    /**
     *
     * @param conta
     */
    public void setConta(Conta conta) {
        this.conta = conta;
    }

    /**
     *
     * @return Imagem
     */
    public Imagem getFoto() {
        return foto;
    }

    /**
     *
     * @param foto
     */
    public void setFoto(Imagem foto) {
        this.foto = foto;
    }
}
