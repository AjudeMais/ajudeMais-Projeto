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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * <p>
 * {@link Endereco}
 * </p>
 * 
 * <p>
 * Classe representa entidade de négocio Endereço
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@Entity
public class Endereco {

	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	/**
	 * 
	 */
	private String logradouro;

	/**
	 * 
	 */
	private String numero;

	/**
	 * 
	 */
	private String bairro;

	/**
	 * 
	 */
	@NotNull
	@NotBlank
	private String cep;

	/**
	 * 
	 */
	@NotNull
	@NotBlank
	private String localidade;

	/**
	 * 
	 */
	@NotNull
	@NotBlank
	private String uf;

	/**
	 * 
	 */
	private String unidade;

	/**
	 * 
	 */
	private String complemento;

	/**
	 * 
	 */
	private String ibge;

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
	 * @return the logradouro
	 */
	public String getLogradouro() {
		return logradouro;
	}

	/**
	 * @param logradouro
	 *            the logradouro to set
	 */
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	/**
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * @param numero
	 *            the numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * @return the bairro
	 */
	public String getBairro() {
		return bairro;
	}

	/**
	 * @param bairro
	 *            the bairro to set
	 */
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	/**
	 * @return the cep
	 */
	public String getCep() {
		return cep;
	}

	/**
	 * @param cep
	 *            the cep to set
	 */
	public void setCep(String cep) {
		this.cep = cep;
	}

	/**
	 * @return the localidade
	 */
	public String getLocalidade() {
		return localidade;
	}

	/**
	 * @param localidade
	 *            the localidade to set
	 */
	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	/**
	 * @return the uf
	 */
	public String getUf() {
		return uf;
	}

	/**
	 * @param uf
	 *            the uf to set
	 */
	public void setUf(String uf) {
		this.uf = uf;
	}

	/**
	 * @return the unidade
	 */
	public String getUnidade() {
		return unidade;
	}

	/**
	 * @param unidade
	 *            the unidade to set
	 */
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	/**
	 * @return the complemento
	 */
	public String getComplemento() {
		return complemento;
	}

	/**
	 * @param complemeto
	 *            the complemeto to set
	 */
	public void setComplemento(String complemeto) {
		this.complemento = complemeto;
	}

	/**
	 * @return the ibge
	 */
	public String getIbge() {
		return ibge;
	}

	/**
	 * @param ibge
	 *            the ibge to set
	 */
	public void setIbge(String ibge) {
		this.ibge = ibge;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Endereco [id=" + id + ", logradouro=" + logradouro + ", numero=" + numero + ", bairro=" + bairro
				+ ", cep=" + cep + ", localidade=" + localidade + ", uf=" + uf + ", unidade=" + unidade
				+ ", complemento=" + complemento + ", ibge=" + ibge + "]";
	}

}
