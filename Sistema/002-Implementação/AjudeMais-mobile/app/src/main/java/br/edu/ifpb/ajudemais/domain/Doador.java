package br.edu.ifpb.ajudemais.domain;

import java.io.Serializable;

/**
 * Created by rafaelfeitosa on 10/04/17.
 * Classe que representa um doador no Sistema.
 */

public class Doador implements Serializable{

    private Long id;
    private String nome;
    private Conta conta;

    public Doador(){}

    public Doador(String nome,Conta conta) {
        this.nome = nome;
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


    @Override
    public String toString() {
        return "Doador{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", conta=" + conta +
                '}';
    }
}
