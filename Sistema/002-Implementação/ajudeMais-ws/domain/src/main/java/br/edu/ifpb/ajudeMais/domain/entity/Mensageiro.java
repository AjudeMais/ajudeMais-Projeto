
package br.edu.ifpb.ajudeMais.domain.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
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
//@NamedQueries({ @NamedQuery(name = "mensageiro.FiltrarMensageirosPorEndereco",
//query = "SELECT e,m FROM Mensageiro m JOIN m.enderecos listaEnderecos WHERE (SELECT COUNT(e.id) FROM listaEnderecos.endereco e where lower(e.bairro) like :bairro and lower(e.localidade) :localidade and lower(e.uf) like :uf ")})
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
	@NotNull(message = "O telefone deve ser informado")
	private String telefone;

	private String tokenFCM;

	@OneToOne(cascade = CascadeType.ALL)
	private Conta conta;

	@LazyCollection(LazyCollectionOption.TRUE)
	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "mensageiro_id")
	private List<Endereco> enderecos;

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
	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	/**
	 * @param endereco the endereco to set
	 */
	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	
}
