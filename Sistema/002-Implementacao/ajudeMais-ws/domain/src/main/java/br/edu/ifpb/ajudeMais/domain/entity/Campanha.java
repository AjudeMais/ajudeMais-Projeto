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
@NamedQueries({
	@NamedQuery(name = "Campanha.filterByInstituicaoLocal", query = "SELECT c FROM Campanha c WHERE "
			+ "c.instituicaoCaridade.endereco.localidade like :localidade "
			+ "and c.instituicaoCaridade.endereco.uf like :uf and c.status is true"), })
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
	 * @param metas the metas to set
	 */
	public void setMetas(List<Meta> metas) {
		this.metas = metas;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataFim == null) ? 0 : dataFim.hashCode());
		result = prime * result + ((dataInicio == null) ? 0 : dataInicio.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((instituicaoCaridade == null) ? 0 : instituicaoCaridade.hashCode());
		result = prime * result + ((metas == null) ? 0 : metas.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + (status ? 1231 : 1237);
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Campanha other = (Campanha) obj;
		if (dataFim == null) {
			if (other.dataFim != null)
				return false;
		} else if (!dataFim.equals(other.dataFim))
			return false;
		if (dataInicio == null) {
			if (other.dataInicio != null)
				return false;
		} else if (!dataInicio.equals(other.dataInicio))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (instituicaoCaridade == null) {
			if (other.instituicaoCaridade != null)
				return false;
		} else if (!instituicaoCaridade.equals(other.instituicaoCaridade))
			return false;
		if (metas == null) {
			if (other.metas != null)
				return false;
		} else if (!metas.equals(other.metas))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Campanha [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", dataInicio=" + dataInicio
				+ ", status=" + status + ", dataFim=" + dataFim + ", instituicaoCaridade=" + instituicaoCaridade
				+ ", metas=" + metas + "]";
	}
	
	
	

}
