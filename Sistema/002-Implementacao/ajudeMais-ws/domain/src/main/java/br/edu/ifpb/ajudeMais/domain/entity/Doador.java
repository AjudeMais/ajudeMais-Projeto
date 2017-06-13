/**
 * Ajude Mais - Módulo Web Service
 * 
 * Sistema para potencializar o processo de doação.
 * 
 * <a href="https://github.com/AjudeMais/AjudeMais">Ajude Mais</a>
 * <a href="https://franckaj.github.io">Franck Aragão"></a>
 * <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 * 
 * AJUDE MAIS - 2017®
 * 
 */
package br.edu.ifpb.ajudeMais.domain.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

/**
 * 
 * <p>
 * <b> Doador </b>
 * </p>
 *
 * <p>
 * Entiddade de negócio Doador.
 * </p>
 * 
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a> And
 *         <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
@NamedQueries({
		@NamedQuery(name = "Doador.filterByLocal", query = "SELECT d FROM Doador d WHERE d.enderecoAtual.localidade like :localidade and d.enderecoAtual.uf like :uf"), })
@Entity
public class Doador implements Serializable {

	private static final long serialVersionUID = 7784316565471954266L;

	@Id
	@Column(name = "id", unique = true)
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@NotNull(message = "Nome deve ser informado")
	private String nome;

	/**
	 * 
	 */
	@NotNull(message = "O telefone deve ser informado")
	private String telefone;

	/**
	 * 
	 */
	private String facebookID;

	/**
	 * 
	 */
	@OneToOne(cascade = CascadeType.ALL)
	private FcmToken tokenFCM;

	/**
	 * 
	 */
	@OneToOne(cascade = CascadeType.ALL)
	private Imagem foto;

	/**
	 * 
	 */
	@OneToOne(cascade = CascadeType.MERGE)
	private Conta conta;

	/**
	 * 
	 */
	@OneToOne(cascade = CascadeType.ALL)
	private Endereco enderecoAtual;

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
	 * @return the facebookID
	 */
	public String getFacebookID() {
		return facebookID;
	}

	/**
	 * @param facebookID
	 *            the facebookID to set
	 */
	public void setFacebookID(String facebookID) {
		this.facebookID = facebookID;
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
	 * @return o atributo enderecoAtual
	 */
	public Endereco getEnderecoAtual() {
		return enderecoAtual;
	}

	/**
	 * @param o
	 *            parametro enderecoAtual é setado em enderecoAtual
	 */
	public void setEnderecoAtual(Endereco enderecoAtual) {
		this.enderecoAtual = enderecoAtual;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Doador [id=" + id + ", nome=" + nome + ", telefone=" + telefone + ", facebookID=" + facebookID
				+ ", TokenFCM=" + tokenFCM + ", foto=" + foto + ", conta=" + conta + "]";
	}

}
