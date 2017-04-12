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
 * 
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<String> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<String> grupos) {
		this.grupos = grupos;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Conta [id=" + id + ", username=" + username + ", senha=" +senha + ", grupos=" + grupos + ", ativo="
				+ ativo + "]";
	}

}
