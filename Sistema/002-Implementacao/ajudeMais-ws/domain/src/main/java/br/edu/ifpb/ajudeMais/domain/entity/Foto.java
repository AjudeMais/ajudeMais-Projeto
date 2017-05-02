package br.edu.ifpb.ajudeMais.domain.entity;

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
 * <p> Entidade que representa um foto. </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
@Entity
public class Foto {

	@Id
	@Column(name = "id", unique = true)
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	/**
	 * 
	 */
	private String nome;
	
	private String pathFoto;
	
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	
	/**
	 * @param id the id to set
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
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/**
	 * @return the pathFoto
	 */
	public String getPathFoto() {
		return pathFoto;
	}
	
	/**
	 * @param pathFoto the pathFoto to set
	 */
	public void setPathFoto(String pathFoto) {
		this.pathFoto = pathFoto;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Foto [id=" + id + ", nome=" + nome + ", pathFoto=" + pathFoto + "]";
	}
	
}
