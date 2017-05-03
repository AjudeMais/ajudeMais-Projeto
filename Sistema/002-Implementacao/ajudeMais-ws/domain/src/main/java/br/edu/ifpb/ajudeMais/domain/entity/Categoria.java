package br.edu.ifpb.ajudeMais.domain.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "categoria")
public class Categoria {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@NotNull
	@NotBlank(message = "O nome deve ser informado")
	private String nome;
	
	@NotNull
	@NotBlank(message = "A descrição deve ser informade")
	private String descricao;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Foto foto;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "categoria_id")
	private List<Categoria> categorias;
	
	
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
	 * @return the foto
	 */
	public Foto getFoto() {
		return foto;
	}



	/**
	 * @param foto the foto to set
	 */
	public void setFoto(Foto foto) {
		this.foto = foto;
	}
	
	/**
	 * @return the categorias
	 */
	public List<Categoria> getCategorias() {
		return categorias;
	}



	/**
	 * @param categorias the categorias to set
	 */
	
	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Categoria [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", foto=" + foto + "]";
	}
	
	
	
	

}
