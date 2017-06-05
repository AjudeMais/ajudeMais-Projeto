package br.edu.ifpb.ajudeMais.domain.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 * 
 * 
 * <p>
 * <b> Donativo </b>
 * </p>
 *
 * <p>
 *	Classe que representa um donativo no sistema
 * </p>
 * 
 * @author <a href="https://github.com/amslv">Ana Silva</a>
 *
 */
@Entity
public class Donativo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3822250537408431358L;
	
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	/**
	 * 
	 */
	private Integer quantidade;
	
	/**
	 * 
	 */
	@NotNull
	private String nome;
	
	/**
	 * 
	 */
	@NotNull
	private String descricao;

	/**
	 * 
	 */
	@OneToMany(cascade = CascadeType.ALL)
	@Column(name = "estados_doacao")
	private List<EstadoDoacao> estadosDaDoacao;
	
	/**
	 * 
	 */
	@OneToMany(cascade = CascadeType.ALL)
	private List<DisponibilidadeHorario> horariosDisponiveis;
	
	/**
	 * 
	 */
	@NotNull
	@ManyToOne
	private Doador doador;
	
	/**
	 * 
	 */
	@NotNull
	@ManyToOne(cascade ={CascadeType.MERGE, CascadeType.PERSIST})
	private Endereco endereco;
	
	/**
	 * 
	 */
	@OneToMany(cascade = CascadeType.ALL)
	private List<Imagem> fotosDonativo;
	
	/**
	 * 
	 */
	@NotNull
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH})
	private Categoria categoria;
	
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
	 * @return the quantidade
	 */
	public Integer getQuantidade() {
		return quantidade;
	}

	/**
	 * @param quantidade the quantidade to set
	 */
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
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
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the estadosDaDoacao
	 */
	public List<EstadoDoacao> getEstadosDaDoacao() {
		return estadosDaDoacao;
	}

	/**
	 * @param estadosDaDoacao the estadosDaDoacao to set
	 */
	public void setEstadosDaDoacao(List<EstadoDoacao> estadosDaDoacao) {
		this.estadosDaDoacao = estadosDaDoacao;
	}

	/**
	 * @return the horariosDisponiveis
	 */
	public List<DisponibilidadeHorario> getHorariosDisponiveis() {
		return horariosDisponiveis;
	}

	/**
	 * @param horariosDisponiveis the horariosDisponiveis to set
	 */
	public void setHorariosDisponiveis(List<DisponibilidadeHorario> horariosDisponiveis) {
		this.horariosDisponiveis = horariosDisponiveis;
	}

	/**
	 * @return the doador
	 */
	public Doador getDoador() {
		return doador;
	}

	/**
	 * @param doador the doador to set
	 */
	public void setDoador(Doador doador) {
		this.doador = doador;
	}

	/**
	 * @return the endereco
	 */
	public Endereco getEndereco() {
		return endereco;
	}

	/**
	 * @param endereco the endereco to set
	 */
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	/**
	 * @return the fotosDonativo
	 */
	public List<Imagem> getFotosDonativo() {
		return fotosDonativo;
	}

	/**
	 * @param fotosDonativo the fotosDonativo to set
	 */
	public void setFotosDonativo(List<Imagem> fotosDonativo) {
		this.fotosDonativo = fotosDonativo;
	}

	/**
	 * @return the categoria
	 */
	public Categoria getCategoria() {
		return categoria;
	}

	/**
	 * @param categoria the categoria to set
	 */
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
}
