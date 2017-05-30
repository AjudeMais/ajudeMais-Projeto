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
import org.hibernate.validator.constraints.NotEmpty;

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
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInicio;

	/**
	 * 
	 */
	private boolean status;

	/**
	 * 
	 */
	@NotNull
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
	@NotEmpty(message = "Item doável deve ser adicionado a campanha")
	@ManyToMany(cascade = { CascadeType.MERGE })
	private List<Categoria> itensDoaveis;

	/**
	 * @return o atributo id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param o
	 *            parametro id é setado em id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return o atributo nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param o
	 *            parametro nome é setado em nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return o atributo descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param o
	 *            parametro descricao é setado em descricao
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return o atributo dataInicio
	 */
	public Date getDataInicio() {
		return dataInicio;
	}

	/**
	 * @param o
	 *            parametro dataInicio é setado em dataInicio
	 */
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	/**
	 * @return o atributo dataFim
	 */
	public Date getDataFim() {
		return dataFim;
	}

	/**
	 * @param o
	 *            parametro dataFim é setado em dataFim
	 */
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	/**
	 * @return o atributo instituicaoCaridade
	 */
	public InstituicaoCaridade getInstituicaoCaridade() {
		return instituicaoCaridade;
	}

	/**
	 * @param o
	 *            parametro instituicaoCaridade é setado em instituicaoCaridade
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

	/**
	 * @return o atributo status
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * @param o
	 *            parametro status é setado em status
	 */
	public void setStatus(boolean status) {
		this.status = status;
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
