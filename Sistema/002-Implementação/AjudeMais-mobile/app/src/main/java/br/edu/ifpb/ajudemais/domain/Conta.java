package br.edu.ifpb.ajudemais.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rafaelfeitosa on 10/04/17.
 * Classe que representa um Doador no sistema.
 */

public class Conta implements Serializable{

    private Long id;
    private String username;
    private String senha;
    private boolean ativo;
    private String email;
    private List<String> grupos;

    public Conta(){}

    public Conta(String email) {
        this.email = email;
    }

    public Conta(String username, String senha) {
        this.username = username;
        this.senha = senha;
    }

    public Conta(String email, List<String> grupos) {
        this.email = email;
        this.grupos = grupos;
    }

    public Conta(String username, String senha, boolean ativo, String email, List<String> grupos) {
        this.username = username;
        this.senha = senha;
        this.ativo = ativo;
        this.email = email;
        this.grupos = grupos;
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
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return String
     */
    public String getSenha() {
        return senha;
    }

    /**
     *
     * @param senha
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }


    /**
     *
     * @return List
     */
    public List<String> getGrupos() {
        return grupos;
    }

    /**
     *
     * @param grupos
     */
    public void setGrupos(List<String> grupos) {
        this.grupos = grupos;
    }

    /**
     *
     * @return boolean
     */
    public boolean isAtivo() {
        return ativo;
    }

    /**
     *
     * @param ativo
     */
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Conta{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", senha='" + senha + '\'' +
                ", ativo=" + ativo +
                ", email='" + email + '\'' +
                ", grupos=" + grupos +
                '}';
    }
}