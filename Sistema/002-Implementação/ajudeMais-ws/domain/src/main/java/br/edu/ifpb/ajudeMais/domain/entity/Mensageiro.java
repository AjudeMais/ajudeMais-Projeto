
package br.edu.ifpb.ajudeMais.domain.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	private String cpf;

	/**
	 * 
	 */
	@NotNull(message = "O e-mail deve ser informado")
	private String email;

	/**
	 * 
	 */
	@NotNull(message = "O telefone deve ser informado")
	private String telefone;

	private String tokenFCM;

	@OneToOne(cascade = CascadeType.ALL)
	private Conta conta;

	@OneToMany(cascade = CascadeType.ALL, targetEntity = Endereco.class, mappedBy = "mensageiro")
	private List<Endereco> endereco;

	/**
	 * 
	 */
	@OneToOne(cascade = CascadeType.ALL)
	private Foto foto;

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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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
	public Foto getFoto() {
		return foto;
	}

	/**
	 * @param foto
	 *            the foto to set
	 */
	public void setFoto(Foto foto) {
		this.foto = foto;
	}

	/**
	 * @return the endereco
	 */
	public List<Endereco> getEndereco() {
		return endereco;
	}

	/**
	 * @param endereco the endereco to set
	 */
	public void setEndereco(List<Endereco> endereco) {
		this.endereco = endereco;
	}

	
}
