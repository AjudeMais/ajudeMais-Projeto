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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * <p>
 * {@link Campanha}
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
@NamedQueries({ @NamedQuery(name = "Campanha.filterByInstituicaoLocalOrderByDataCriacaoDesc", query = "SELECT c FROM Campanha c WHERE "
		+ "c.instituicaoCaridade.endereco.localidade like :localidade "
		+ "and c.instituicaoCaridade.endereco.uf like :uf and c.status is true ORDER BY dataCriacao DESC"),

		@NamedQuery(name = "Campanha.filterCountCampanhasMetaCategoriaId", query = "SELECT count(c) FROM Campanha c JOIN c.metas m WHERE "
				+ "m.categoria.id = :idCategoria and c.instituicaoCaridade.id = :idInstituicao") })
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
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInicio;

	/**
	 * 
	 */
	private boolean status;

	/**
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_fim")
	private Date dataFim;

	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criacao")
	private Date dataCriacao = new Date();

	/**
	 * 
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	private InstituicaoCaridade instituicaoCaridade;

	/**
	 * 
	 */
	@NotNull(message = "Ao menos uma meta deve ser adicionado a campanha")
	@OneToMany(cascade = CascadeType.ALL)
	private List<Meta> metas;

	/**
	 * 
	 */
	private boolean notificada;

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
	 * @return o atributo status
	 */
	public boolean isStatus() {
		Date currentDate = new Date();
		if (dataFim != null) {
			if (dataFim.before(currentDate)) {
				return false;
			}
		}
		return status;
	}

	/**
	 * @return o atributo notificada
	 */
	public boolean isNotificada() {
		return notificada;
	}

	/**
	 * @param o
	 *            parametro notificada é setado em notificada
	 */
	public void setNotificada(boolean notificada) {
		this.notificada = notificada;
	}

	/**
	 * @param o
	 *            parametro status é setado em status
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * @return the metas
	 */
	public List<Meta> getMetas() {
		return metas;
	}

	/**
	 * @return o atributo dataCriacao
	 */
	public Date getDataCriacao() {
		return dataCriacao;
	}

	/**
	 * @param o
	 *            parametro dataCriacao é setado em dataCriacao
	 */
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	/**
	 * @param metas
	 *            the metas to set
	 */
	public void setMetas(List<Meta> metas) {
		this.metas = metas;
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Campanha [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", dataInicio=" + dataInicio
				+ ", status=" + status + ", dataFim=" + dataFim + ", instituicaoCaridade=" + instituicaoCaridade
				+ ", metas=" + metas + "]";
	}
}
