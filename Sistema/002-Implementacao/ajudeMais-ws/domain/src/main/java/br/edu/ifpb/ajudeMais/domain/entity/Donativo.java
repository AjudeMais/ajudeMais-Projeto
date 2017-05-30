package br.edu.ifpb.ajudeMais.domain.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
	private String nome;
	
	/**
	 * 
	 */
	private String descricao;

	/**
	 * 
	 */
	@OneToMany
	private List<EstadoDoacao> estadosDaDoacao;
	
	/**
	 * 
	 */
	@OneToMany
	private List<DisponibilidadeHorario> horariosDisponiveis;
	
	/**
	 * 
	 */
	@ManyToOne
	private Doador doador;
	
	/**
	 * 
	 */
	@ManyToOne
	private Endereco endereco;
	
	/**
	 * 
	 */
	@OneToMany
	private List<Imagem> fotosDonativo;
	
	/**
	 * 
	 */
	@ManyToOne
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


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((doador == null) ? 0 : doador.hashCode());
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + ((estadosDaDoacao == null) ? 0 : estadosDaDoacao.hashCode());
		result = prime * result + ((fotosDonativo == null) ? 0 : fotosDonativo.hashCode());
		result = prime * result + ((horariosDisponiveis == null) ? 0 : horariosDisponiveis.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((quantidade == null) ? 0 : quantidade.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Donativo other = (Donativo) obj;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (doador == null) {
			if (other.doador != null)
				return false;
		} else if (!doador.equals(other.doador))
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (estadosDaDoacao == null) {
			if (other.estadosDaDoacao != null)
				return false;
		} else if (!estadosDaDoacao.equals(other.estadosDaDoacao))
			return false;
		if (fotosDonativo == null) {
			if (other.fotosDonativo != null)
				return false;
		} else if (!fotosDonativo.equals(other.fotosDonativo))
			return false;
		if (horariosDisponiveis == null) {
			if (other.horariosDisponiveis != null)
				return false;
		} else if (!horariosDisponiveis.equals(other.horariosDisponiveis))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (quantidade == null) {
			if (other.quantidade != null)
				return false;
		} else if (!quantidade.equals(other.quantidade))
			return false;
		return true;
	}
}
