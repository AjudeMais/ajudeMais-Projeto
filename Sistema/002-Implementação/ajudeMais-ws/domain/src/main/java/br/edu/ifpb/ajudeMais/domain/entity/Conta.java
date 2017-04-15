package br.edu.ifpb.ajudeMais.domain.entity;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

/**
 * <p>
 * <b> Doador </b>
 * </p>
 *
 * <p>
 * Entidade que representa a conta de usuário no sistema.
 * </p>
 * 
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 * And <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 *
 */
@Entity
public class Conta {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	/**
	 * 	
	 */
	@NotBlank 
	@NotNull
	@Size(min=4, max=100)
	@Column(length=100, nullable=false)
	private String username;

	/**
	 * 
	 */
	@NotBlank 
	@NotNull 
	@Size(min=4, max=100)
	@Basic(fetch = FetchType.LAZY)
    @Column(length=100, nullable=false)
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
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * @param senha the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}

	/**
	 * @return the grupos
	 */
	public List<String> getGrupos() {
		return grupos;
	}

	/**
	 * @param grupos the grupos to set
	 */
	public void setGrupos(List<String> grupos) {
		this.grupos = grupos;
	}

	/**
	 * @return the ativo
	 */
	public boolean isAtivo() {
		return ativo;
	}

	/**
	 * @param ativo the ativo to set
	 */
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}


	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Conta [id=" + id + ", username=" + username + ", senha=" + senha + ", grupos=" + grupos + ", ativo="
				+ ativo + ", email=" + email + "]";
	}
	
	
	
}
