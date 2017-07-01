/**
 * <p>
 * Ajude Mais - Módulo Web Service
 * </p>
 * 
 * <p>
 * Sistema para potencializar o processo de doação.
 * </p>
 * 
 * <a href="https://github.com/AjudeMais/AjudeMais">Ajude Mais</a>
 * <a href="https://franckaj.github.io">Franck Aragão"></a>
 * 
 * AJUDE MAIS - 2017®
 * 
 */
package br.edu.ifpb.ajudeMais.domain.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * 
 * <p>
 * {@link MensageiroAssociado}
 * </p>
 * 
 * <p>
 * Classe utilizada para representação de associação entre mensageiro e
 * insituição.
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@Entity
@Table(name = "mensageiro_associado")
@NamedQueries({@NamedQuery(name = "MensageiroAssociado.filterMensageirosCloserToBairro", 
query = "SELECT DISTINCT ms.mensageiro,e.id FROM MensageiroAssociado ms JOIN FETCH ms.mensageiro.enderecos e WHERE e.bairro like :bairro and e.localidade like :localidade"
		+ " and e.uf like :uf and ms.instituicaoCaridade.id = :idInstituicao and ms.status is true"),
		
		@NamedQuery(name = "MensageiroAssociado.filterMensageirosCloserToCidade", 
		query = "SELECT DISTINCT ms.mensageiro,e.id FROM MensageiroAssociado ms JOIN FETCH ms.mensageiro.enderecos e WHERE e.localidade like :localidade"
		+ " and e.uf like :uf and ms.instituicaoCaridade.id = :idInstituicao and ms.status is true")}		)
public class MensageiroAssociado {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	/**
	 * 
	 */
	@Temporal(TemporalType.DATE)
	private Date data;

	/**
	 * 
	 */
	@NotNull
	@ManyToOne(cascade = { CascadeType.DETACH })
	private Mensageiro mensageiro;

	/**
	 * 
	 */
	@NotNull
	@ManyToOne(cascade = { CascadeType.ALL })
	private InstituicaoCaridade instituicaoCaridade;

	/**
	 * 
	 */
	private boolean status;

	/**
	 * @return o atributo data
	 */
	public Date getData() {
		return data;
	}

	/**
	 * @param o
	 *            parametro data é setado em data
	 */
	public void setData(Date data) {
		this.data = data;
	}

	/**
	 * @return o atributo mensageiro
	 */
	public Mensageiro getMensageiro() {
		return mensageiro;
	}

	/**
	 * @param o
	 *            parametro mensageiro é setado em mensageiro
	 */
	public void setMensageiro(Mensageiro mensageiro) {
		this.mensageiro = mensageiro;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MensageiroAssociado [id=" + id + ", data=" + data + ", mensageiro=" + mensageiro
				+ ", instituicaoCaridade=" + instituicaoCaridade + ", status=" + status + "]";
	}

}
