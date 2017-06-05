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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * <p>
 * <b> Foto</b>
 * </p>
 *
 * <p>
 * Entidade que representa um foto.
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
@Entity
public class Imagem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	private String nome;

	/**
	 * 
	 */
	private String contentType;

	/**
	 * 
	 * <p>
	 * </p>
	 *
	 */
	public Imagem() {
	}

	public Imagem(String nome, String contentType) {
		super();
		this.nome = nome;
		this.contentType = contentType;
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
	 * @return o atributo contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param o
	 *            parametro contentType é setado em contentType
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Imagem [id=" + id + ", nome=" + nome + ", contentType=" + contentType + "]";
	}

}
