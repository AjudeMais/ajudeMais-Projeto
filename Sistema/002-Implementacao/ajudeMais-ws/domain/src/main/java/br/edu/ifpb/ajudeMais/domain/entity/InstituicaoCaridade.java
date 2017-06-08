/**
 * Ajude Mais - Módulo Web Service
 * 
 * Sistema para potencializar o processo de doação.
 * 
 * <a href="https://github.com/AjudeMais/AjudeMais">Ajude Mais</a>
 * <a href="https://franckaj.github.io">Franck Aragão"></a>
 * 
 * AJUDE MAIS - 2017®
 * 
 */
package br.edu.ifpb.ajudeMais.domain.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * <p>
 * {@link InstituicaoCaridade}
 * </p>
 * 
 * <p>
 * Classe representa a entidade de négocio Instituição de caridade.
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@Entity
@Table(name = "intituicao_caridade")
@NamedQueries({
		@NamedQuery(name = "InstituicaoCaridade.filtersInstituicaoCaridadeClose", query = "SELECT it FROM InstituicaoCaridade it WHERE it.endereco.localidade like :localidade and it.endereco.uf like :uf and it.conta.ativo is true"), })
public class InstituicaoCaridade {

	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	/**
	 * 
	 */
	@Size(min = 2, max = 100)
	@NotNull
	@NotBlank(message = "nome deve ser informado")
	private String nome;

	/**
	 * 
	 */
	@NotNull
	@NotBlank(message = "A descrição deve ser informado")
	@Column(columnDefinition = "TEXT")
	private String descricao;

	/**
	 * 
	 */
	private String telefone;

	/**
	 * 
	 */
	@NotNull
	@NotBlank(message = "CPF/CNPJ deve ser informando")
	@Column(unique = true, nullable = false)
	private String documento;

	@NotNull
	@OneToOne(cascade = CascadeType.ALL)
	private Endereco endereco;

	/**
	 * 
	 */
	@NotNull
	@OneToOne(cascade = CascadeType.MERGE)
	private Conta conta;

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
	 * @return o atributo telefone
	 */
	public String getTelefone() {
		return telefone;
	}

	/**
	 * @param o
	 *            parametro telefone é setado em telefone
	 */
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	/**
	 * @return o atributo documento
	 */
	public String getDocumento() {
		return documento;
	}

	/**
	 * @param o
	 *            parametro documento é setado em documento
	 */
	public void setDocumento(String documento) {
		this.documento = documento;
	}

	/**
	 * @return o atributo endereco
	 */
	public Endereco getEndereco() {
		return endereco;
	}

	/**
	 * @param o
	 *            parametro endereco é setado em endereco
	 */
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	/**
	 * @return o atributo conta
	 */
	public Conta getConta() {
		return conta;
	}

	/**
	 * @param o
	 *            parametro conta é setado em conta
	 */
	public void setConta(Conta conta) {
		this.conta = conta;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "InstituicaoCaridade [id=" + id + ", nome=" + nome + ", telefone=" + telefone + ", documento="
				+ documento + ", endereco=" + endereco + ", conta=" + conta + "]";
	}

}
