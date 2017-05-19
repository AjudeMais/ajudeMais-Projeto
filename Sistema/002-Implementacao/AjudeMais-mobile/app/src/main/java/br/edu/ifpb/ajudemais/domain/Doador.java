package br.edu.ifpb.ajudemais.domain;

import java.io.Serializable;

/**
 * <p>
 * <b>{@link Doador}</b>
 * </p>
 * </p>
 *
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public class Doador implements Serializable {

    private Long id;
    private String nome;
    private String telefone;
    private String facebookID;
    private String tokenFCM;
    private Conta conta;
    private Imagem foto;

    /**
     *
     */
    public Doador(){}

    /**
     *
     * @param nome
     * @param telefone
     * @param facebookID
     * @param tokenFCM
     * @param conta
     */
    public Doador(String nome, String telefone, String facebookID, String tokenFCM, Conta conta) {
        this.nome = nome;
        this.telefone = telefone;
        this.facebookID = facebookID;
        this.tokenFCM = tokenFCM;
        this.conta = conta;
    }

    /**
     *
     * @param nome
     * @param telefone
     * @param facebookID
     * @param conta
     */
    public Doador(String nome, String telefone, String facebookID, Conta conta) {
        this.nome = nome;
        this.telefone = telefone;
        this.facebookID = facebookID;
        this.conta = conta;
    }

    /**
     *
     * @param nome
     * @param telefone
     * @param conta
     */
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
    public String getFacebookID() {
        return facebookID;
    }

    /**
     *
     * @param facebookID
     */
    public void setFacebookID(String facebookID) {
        this.facebookID = facebookID;
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

    @Override
    public String toString() {
        return "Doador{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", facebookID='" + facebookID + '\'' +
                ", tokenFCM='" + tokenFCM + '\'' +
                ", conta=" + conta +
                ", foto=" + foto +
                '}';
    }
}
