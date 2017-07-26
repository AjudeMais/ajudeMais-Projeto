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

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

/**
 * <p>
 * <b> {@link Conta} </b>
 * </p>
 *
 * <p>
 * Entidade que representa a conta de usuário no sistema.
 * </p>
 * 
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a> And
 *         <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 *
 */
@Entity
public class Conta {

	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	/**
	 * 	
	 */
	@NotBlank
	@NotNull
	@Size(min = 4, max = 30)
	@Column(length = 30, nullable = false, unique = true)
	private String username;

	/**
	 * 
	 */
	@NotBlank
	@NotNull
	@Size(min = 4, max = 100)
	@Column(length = 100, nullable = false)
	private String senha;

	/**
	 * 
	 */
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> grupos;

	/**
	 * 
	 */
	private boolean ativo;

	/**
	 * 
	 */
	@NotNull(message = "O e-mail deve ser informado")
	private String email;

	/**
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "reset_senha")
	private Date resetSenha;

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
	 * @return o atributo username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param o
	 *            parametro username é setado em username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return o atributo senha
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * @param o
	 *            parametro senha é setado em senha
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}

	/**
	 * @return o atributo grupos
	 */
	public List<String> getGrupos() {
		return grupos;
	}

	/**
	 * @param o
	 *            parametro grupos é setado em grupos
	 */
	public void setGrupos(List<String> grupos) {
		this.grupos = grupos;
	}

	/**
	 * @return o atributo ativo
	 */
	public boolean isAtivo() {
		return ativo;
	}

	/**
	 * @param o
	 *            parametro ativo é setado em ativo
	 */
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	/**
	 * @return o atributo email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param o
	 *            parametro email é setado em email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return o atributo resetSenha
	 */
	public Date getResetSenha() {
		return resetSenha;
	}

	/**
	 * @param o
	 *            parametro resetSenha é setado em resetSenha
	 */
	public void setResetSenha(Date resetSenha) {
		this.resetSenha = resetSenha;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Conta [id=" + id + ", username=" + username + ", senha=" + senha + ", grupos=" + grupos + ", ativo="
				+ ativo + ", email=" + email + ", resetSenha=" + resetSenha + "]";
	}
	
	

}
