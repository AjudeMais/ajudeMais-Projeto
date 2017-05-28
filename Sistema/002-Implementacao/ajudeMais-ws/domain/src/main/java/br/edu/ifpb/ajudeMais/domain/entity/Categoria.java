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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * <p>
 * {@link Categoria}
 * </p>
 * 
 * <p>
 * Classe representa a entidade de négocio Categoria.
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@Entity
@Table(name = "categoria")
public class Categoria {

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
	private Boolean ativo;

	@ManyToOne(fetch = FetchType.EAGER)
	private InstituicaoCaridade instituicaoCaridade;

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
	 * @return o atributo ativo
	 */
	public Boolean getAtivo() {
		return ativo;
	}

	/**
	 * @param o
	 *            parametro ativo é setado em ativo
	 */
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
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

}
