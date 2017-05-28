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
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
	private String tokenFCM;

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
	@ManyToMany(mappedBy = "doadores")
	private List<Campanha> campanhas;

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
	 * @return the tokenFCM
	 */
	public String getTokenFCM() {
		return tokenFCM;
	}

	/**
	 * @param tokenFCM
	 *            the tokenFCM to set
	 */
	public void setTokenFCM(String tokenFCM) {
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
	 * @return the campanhas
	 */
	public List<Campanha> getCampanhas() {
		return campanhas;
	}

	/**
	 * @param campanhas the campanhas to set
	 */
	public void setCampanhas(List<Campanha> campanhas) {
		this.campanhas = campanhas;
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
