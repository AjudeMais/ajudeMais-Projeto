package br.edu.ifpb.ajudemais.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 */

public class Campanha implements Serializable{

    private Long id;

    /**
     *
     */
    private String nome;

    /**
     *
     */
    private String descricao;

    /**
     *
     */
    private Date dataInicio;

    /**
     *
     */
    private boolean status;

    /**
     *
     */
    private Date dataFim;

    /**
     *
     */
    private InstituicaoCaridade instituicaoCaridade;

    /**
     *
     */
    private List<Categoria> itensDoaveis;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public InstituicaoCaridade getInstituicaoCaridade() {
        return instituicaoCaridade;
    }

    public void setInstituicaoCaridade(InstituicaoCaridade instituicaoCaridade) {
        this.instituicaoCaridade = instituicaoCaridade;
    }

    public List<Categoria> getItensDoaveis() {
        return itensDoaveis;
    }

    public void setItensDoaveis(List<Categoria> itensDoaveis) {
        this.itensDoaveis = itensDoaveis;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Campanha{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", dataInicio=" + dataInicio +
                ", status=" + status +
                ", dataFim=" + dataFim +
                ", instituicaoCaridade=" + instituicaoCaridade +
                ", itensDoaveis=" + itensDoaveis +
                '}';
    }
}
