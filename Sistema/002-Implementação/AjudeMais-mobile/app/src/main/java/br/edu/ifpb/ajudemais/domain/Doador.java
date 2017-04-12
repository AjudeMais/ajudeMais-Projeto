package br.edu.ifpb.ajudemais.domain;

/**
 * Created by rafaelfeitosa on 10/04/17.
 * Classe que representa um doador no Sistema.
 */

public class Doador {

    private Long id;
    private String nomeUsuario;
    private String senha;
    private boolean ativo;
    private Conta conta;

    public Doador(String nomeUsuario, String senha, boolean ativo, Conta conta) {
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.ativo = ativo;
        this.conta = conta;
    }

    /**
     *
     * @return String
     */
    public String getNomeUsuario() {
        return nomeUsuario;
    }

    /**
     *
     * @param nomeUsuario
     */
    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
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
     * @return String
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

    @Override
    public String toString() {
        return "Doador{" +
                "id=" + id +
                ", nomeUsuario='" + nomeUsuario + '\'' +
                ", senha='" + senha + '\'' +
                ", ativo=" + ativo +
                '}';
    }
}
