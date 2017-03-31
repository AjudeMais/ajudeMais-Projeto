/**
 * 
 */
package br.edu.ifpb.ajudeMais.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * 
 * <p>
 * <b> Doador </b>
 * </p>
 *
 * <p>
 * 	Entiddade de negócio Doador.
 * </p>
 * 
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
@Entity
public class Doador implements Serializable {

	private static final long serialVersionUID = 7784316565471954266L;

	@Id
	@Column(name = "id", unique = true)
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@NotNull (message="Nome deve ser informado")
	private String nome;

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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Paciente [id=" + id + ", nome=" + nome + "]";
	}


	

}
