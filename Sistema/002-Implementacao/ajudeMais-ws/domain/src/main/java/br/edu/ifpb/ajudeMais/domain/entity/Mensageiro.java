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

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

/**
 * 
 * <p>
 * <b> {@link Mensageiro} </b>
 * </p>
 *
 * <p>
 * Entidade que representa Mensageiro no sistema
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */

@Entity
public class Mensageiro {

	/**
	 * 
	 */
	@Id
	@Column(name = "id", unique = true)
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	/**
	 * 
	 */
	@NotNull(message = "O nome deve ser informado")
	private String nome;

	/**
	 * 
	 */
	@CPF
	@NotNull(message = "O CPF deve ser informado")
	@Column(unique = true)
	private String cpf;

	/**
	 * 
	 */
	@NotNull(message = "O telefone deve ser informado")
	private String telefone;

	/**
	 * 
	 */
	@OneToOne(cascade = CascadeType.ALL)
	private FcmToken tokenFCM;

	/**
	 * 
	 */
	@OneToOne(cascade = CascadeType.ALL)
	private Conta conta;

	/**
	 * 
	 */
	@OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "mensageiro_id")
	private List<Endereco> enderecos;

	/**
	 * 
	 */
	@OneToOne(cascade = CascadeType.ALL)
	private Imagem foto;

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
	 * @return the cpf
	 */
	public String getCpf() {
		return cpf;
	}

	/**
	 * @param cpf
	 *            the cpf to set
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
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
	 * @return o atributo tokenFCM
	 */
	public FcmToken getTokenFCM() {
		return tokenFCM;
	}

	/**
	 * @param o
	 *            parametro tokenFCM é setado em tokenFCM
	 */
	public void setTokenFCM(FcmToken tokenFCM) {
		this.tokenFCM = tokenFCM;
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
	 * @return the foto
	 */
	public Imagem getFoto() {
		return foto;
	}

	/**
	 * @param foto
	 *            the foto to set
	 */
	public void setFoto(Imagem foto) {
		this.foto = foto;
	}

	/**
	 * @return the endereco
	 */
	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	/**
	 * @param endereco
	 *            the endereco to set
	 */
	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Mensageiro [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", telefone=" + telefone + ", tokenFCM="
				+ tokenFCM + ", conta=" + conta + ", enderecos=" + enderecos + ", foto=" + foto + "]";
	}
}
