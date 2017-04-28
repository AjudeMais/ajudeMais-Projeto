package br.edu.ifpb.ajudemais.domain;

/**
 * Created by Franck Aragão on 4/27/17.
 */

public class InstituicaoCaridade {

    private Long id;

    private String nome;

    private String telefone;

    private String documento;

    private Endereco endereco;

    private Conta conta;

    public InstituicaoCaridade(String nome) {
        this.nome = nome;
    }

    public InstituicaoCaridade() {

    }

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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }
}
