package br.edu.ifpb.ajudeMais.domain.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * <p>
 * </p>
 * 
 * <p>
 * Representação da entidade de negócio Campanha
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@Entity
public class Campanha {
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	/**
	 * 
	 */
	@NotNull
	@NotBlank(message = "O nome deve ser informado")
	private String nome;

	/**
	 * 
	 */
	@NotNull
	@NotBlank(message = "A descrição deve ser informada")
	@Column(columnDefinition = "TEXT")
	private String descricao;

	/**
	 * 
	 */
	@NotNull
	@NotBlank(message = "A data deve ser informada")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInicio;

	/**
	 * 
	 */
	@NotNull
	@NotBlank(message = "A data deve ser informada")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFim;

	/**
	 * 
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	private InstituicaoCaridade instituicaoCaridade;

	/**
	 * 
	 */
	@ManyToMany(cascade = CascadeType.MERGE)
	private List<Categoria> itensDoaveis;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao
	 *            the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the dataInicio
	 */
	public Date getDataInicio() {
		return dataInicio;
	}

	/**
	 * @param dataInicio
	 *            the dataInicio to set
	 */
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	/**
	 * @return the dataFim
	 */
	public Date getDataFim() {
		return dataFim;
	}

	/**
	 * @param dataFim
	 *            the dataFim to set
	 */
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	/**
	 * @return the instituicaoCaridade
	 */
	public InstituicaoCaridade getInstituicaoCaridade() {
		return instituicaoCaridade;
	}

	/**
	 * @param instituicaoCaridade
	 *            the instituicaoCaridade to set
	 */
	public void setInstituicaoCaridade(InstituicaoCaridade instituicaoCaridade) {
		this.instituicaoCaridade = instituicaoCaridade;
	}

	/**
	 * @return o atributo itensDoaveis
	 */
	public List<Categoria> getItensDoaveis() {
		return itensDoaveis;
	}

	/**
	 * @param o
	 *            parametro itensDoaveis é setado em itensDoaveis
	 */
	public void setItensDoaveis(List<Categoria> itensDoaveis) {
		this.itensDoaveis = itensDoaveis;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Campanha [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", dataInicio=" + dataInicio
				+ ", dataFim=" + dataFim + ", instituicaoCaridade=" + instituicaoCaridade + ", itensDoaveis="
				+ itensDoaveis + "]";
	}

}
