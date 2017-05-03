package br.edu.ifpb.ajudeMais.domain.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@Entity
@Table(name = "intituicao_caridade")
@NamedQueries({
@NamedQuery(name = "InstituicaoCaridade.filtersInstituicaoCaridadeClose", query = "SELECT it FROM InstituicaoCaridade it WHERE it.endereco.localidade like :localidade and it.endereco.uf like :uf")})

public class InstituicaoCaridade {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Size(min = 2, max = 100)
	@NotNull
	@NotBlank(message = "nome deve ser informado")
	private String nome;
	
	@NotNull
	@NotBlank(message = "A descrição deve ser informado")
	@Column(columnDefinition="TEXT")
	private String descricao;

	private String telefone;

	@NotNull
	@NotBlank(message = "CPF/CNPJ deve ser informando")
	private String documento;

	@NotNull
	@OneToOne(cascade = CascadeType.ALL)
	private Endereco endereco;

	@NotNull
	@OneToOne(cascade = CascadeType.MERGE)
	private Conta conta;
	

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@Column(name = "itens_doaveis")
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
	 * @return the telefone
	 */
	public String getTelefone() {
		return telefone;
	}

	/**
	 * @param telefone
	 *            the telefone to set
	 */
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	/**
	 * @return the documento
	 */
	public String getDocumento() {
		return documento;
	}

	/**
	 * @param documento
	 *            the documento to set
	 */
	public void setDocumento(String documento) {
		this.documento = documento;
	}

	/**
	 * @return the endereco
	 */
	public Endereco getEndereco() {
		return endereco;
	}

	/**
	 * @param endereco
	 *            the endereco to set
	 */
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	/**
	 * @return the conta
	 */
	public Conta getConta() {
		return conta;
	}

	/**
	 * @param conta
	 *            the conta to set
	 */
	public void setConta(Conta conta) {
		this.conta = conta;
	}
	
	

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
