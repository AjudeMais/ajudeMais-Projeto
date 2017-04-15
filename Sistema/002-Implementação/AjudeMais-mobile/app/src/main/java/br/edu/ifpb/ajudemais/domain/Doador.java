package br.edu.ifpb.ajudemais.domain;

import java.io.Serializable;

/**
 * Created by rafaelfeitosa on 10/04/17.
 * Classe que representa um doador no Sistema.
 */

public class Doador implements Serializable{

    private Long id;
    private String nome;
    private String telefone;
    private String facebookId;
    private String tokenFCM;
    private Conta conta;

    public Doador(){}

    public Doador(String nome, String telefone, String facebookId, String tokenFCM, Conta conta) {
        this.nome = nome;
        this.telefone = telefone;
        this.facebookId = facebookId;
        this.tokenFCM = tokenFCM;
        this.conta = conta;
    }

    public Doador(String nome, String telefone, String facebookId, Conta conta) {
        this.nome = nome;
        this.telefone = telefone;
        this.facebookId = facebookId;
        this.conta = conta;
    }

    public Doador(String nome, String telefone, Conta conta) {
        this.nome = nome;
        this.telefone = telefone;
        this.conta = conta;
    }

    /**
     * @return String
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome
     */
    public void setNomeUsuario(String nome) {
        this.nome = nome;
    }

    /**
     * @return Long
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return Conta
     */
    public Conta getConta() {
        return conta;
    }

    /**
     * @param conta
     */
    public void setConta(Conta conta) {
        this.conta = conta;
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
    public String getFacebookId() {
        return facebookId;
    }

    /**
     *
     * @param facebookId
     */
    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
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

    @Override
    public String toString() {
        return "Doador{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", facebookId='" + facebookId + '\'' +
                ", tokenFCM='" + tokenFCM + '\'' +
                ", conta=" + conta +
                '}';
    }
}
