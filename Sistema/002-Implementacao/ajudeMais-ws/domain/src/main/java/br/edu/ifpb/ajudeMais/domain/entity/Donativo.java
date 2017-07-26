package br.edu.ifpb.ajudeMais.domain.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * <p>
 * <b> {@link Donativo}</b>
 * </p>
 * <p>
 * Classe que representa um donativo no sistema
 * </p>
 * 
 * @author <a href="https://github.com/amslv">Ana Silva</a>
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 * 
 */
@NamedQueries({
	@NamedQuery(name = "Donativo.filterCountDonativoByEstadoAndDateBetween", query = "SELECT COUNT(d) FROM Donativo d JOIN d.estadosDaDoacao ed "
			+ "WHERE ed.estadoDoacao like :estado and ed.ativo is true and d.data BETWEEN :startDate AND :endDate"),
	
	@NamedQuery(name = "Donativo.filterCountDonativoByEstadoAndDateBetweenAndInst", query = "SELECT COUNT(d) FROM Donativo d JOIN d.estadosDaDoacao ed "
			+ "WHERE ed.estadoDoacao like :estado and ed.ativo is true and d.data BETWEEN :startDate AND :endDate and d.categoria.instituicaoCaridade.id = :idInst"),
	
	@NamedQuery(name = "Donativo.filterCountByEstadoRecolhidoAndDateBetweenAndInst", query = "SELECT COUNT(d) FROM Donativo d JOIN d.estadosDaDoacao ed "
			+ "WHERE ed.estadoDoacao != 'DISPONIBILIZADO' and ed.estadoDoacao != 'CANCELADO' and ed.estadoDoacao != 'RECEBIDO' and ed.ativo is true "
			+ "and ed.estadoDoacao != 'NAO_ACEITO' and ed.estadoDoacao != 'CANCELADO_POR_MENSAGEIRO' and d.data BETWEEN :startDate AND :endDate and d.categoria.instituicaoCaridade.id = :idInst"),
	
	@NamedQuery(name = "Donativo.filterCountByEstadoRecolhidoAndDateBetween", query = "SELECT COUNT(d) FROM Donativo d JOIN d.estadosDaDoacao ed "
			+ "WHERE ed.estadoDoacao != 'DISPONIBILIZADO' and ed.estadoDoacao != 'CANCELADO' and ed.estadoDoacao != 'RECEBIDO' and ed.ativo is true "
			+ "and ed.estadoDoacao != 'NAO_ACEITO' and ed.estadoDoacao != 'CANCELADO_POR_MENSAGEIRO' and d.data BETWEEN :startDate AND :endDate"),
	
	@NamedQuery(name = "Donativo.filterCountByEstadoRecolhido", query = "SELECT COUNT(d) FROM Donativo d JOIN d.estadosDaDoacao ed "
			+ "WHERE ed.estadoDoacao != 'DISPONIBILIZADO' and ed.estadoDoacao != 'CANCELADO' and ed.ativo is true "
			+ "and  ed.estadoDoacao != 'ACEITO' and ed.estadoDoacao != 'NAO_ACEITO' and ed.estadoDoacao != 'CANCELADO_POR_MENSAGEIRO'"),
	
	@NamedQuery(name = "Donativo.filterCountByEstadoRecolhidoAndInstituicaoId", query = "SELECT COUNT(d) FROM Donativo d JOIN d.estadosDaDoacao ed "
			+ "WHERE ed.estadoDoacao != 'DISPONIBILIZADO' and ed.estadoDoacao != 'CANCELADO' and ed.ativo is true "
			+ "and  ed.estadoDoacao != 'ACEITO' and ed.estadoDoacao != 'NAO_ACEITO' and ed.estadoDoacao != 'CANCELADO_POR_MENSAGEIRO' and d.categoria.instituicaoCaridade.id = :idInstituicao"),
	
	@NamedQuery(name = "Donativo.filterCountByMensageiroAndEstado", query = "SELECT COUNT(d) FROM Donativo d JOIN d.estadosDaDoacao ed "
			+ "WHERE ed.estadoDoacao != 'DISPONIBILIZADO' and ed.estadoDoacao != 'CANCELADO' "
			+ "and  ed.estadoDoacao != 'ACEITO' and ed.estadoDoacao != 'NAO_ACEITO' and ed.estadoDoacao != 'CANCELADO_POR_MENSAGEIRO'"
			+ "and ed.ativo is true and d.mensageiro = :mensageiro"),
	
		@NamedQuery(name = "Donativo.filterDonativoByMensageiroAndEstado", query = "SELECT d FROM Donativo d JOIN d.estadosDaDoacao ed "
				+ "WHERE d.mensageiro.id = :idMensageiro and ed.estadoDoacao like :estado and ed.ativo is true"),

		@NamedQuery(name = "Donativo.filterDonativoByEstadoAndInstituicao", query = "SELECT d FROM Donativo d JOIN d.estadosDaDoacao ed "
				+ "WHERE d.categoria.instituicaoCaridade.id = :idInstituicao and ed.estadoDoacao like :estado and ed.ativo is true"),

		@NamedQuery(name = "Donativo.filterDonativoByLocal", query = "SELECT d FROM Donativo d JOIN d.estadosDaDoacao ed "
				+ " WHERE d.endereco.localidade like :localidade and d.endereco.uf like :uf "
				+ "and d.categoria.instituicaoCaridade.id = :idInstituicao and ed.estadoDoacao like :estado and ed.ativo is true") })
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
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
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
	private String descricao;

	/**
	 * 
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@Column(name = "estados_doacao")
	private List<EstadoDoacao> estadosDaDoacao;

	/**
	 * 
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
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
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	private Endereco endereco;

	/**
	 * 
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	private List<Imagem> fotosDonativo;

	/**
	 * 
	 */
	@NotNull
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.DETACH })
	private Categoria categoria;

	/**
	 * 
	 */
	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	private Date data = new Date();

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.DETACH })
	private Mensageiro mensageiro;

	@Transient
	@JsonIgnore
	private Date horarioAceito;

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
	 * @return the quantidade
	 */
	public Integer getQuantidade() {
		return quantidade;
	}

	/**
	 * @param quantidade
	 *            the quantidade to set
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
	 * @param nome
	 *            the nome to set
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
	 * @param descricao
	 *            the descricao to set
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
	 * @param estadosDaDoacao
	 *            the estadosDaDoacao to set
	 */
	public void setEstadosDaDoacao(List<EstadoDoacao> estadosDaDoacao) {
		this.estadosDaDoacao = estadosDaDoacao;
	}

	/**
	 * @return o atributo mensageiro
	 */
	public Mensageiro getMensageiro() {
		return mensageiro;
	}

	/**
	 * @param o
	 *            parametro mensageiro é setado em mensageiro
	 */
	public void setMensageiro(Mensageiro mensageiro) {
		this.mensageiro = mensageiro;
	}

	/**
	 * @return the horariosDisponiveis
	 */
	public List<DisponibilidadeHorario> getHorariosDisponiveis() {
		return horariosDisponiveis;
	}

	/**
	 * @param horariosDisponiveis
	 *            the horariosDisponiveis to set
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
	 * @param doador
	 *            the doador to set
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
	 * @param endereco
	 *            the endereco to set
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
	 * @param fotosDonativo
	 *            the fotosDonativo to set
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
	 * @param categoria
	 *            the categoria to set
	 */
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	/**
	 * @return o atributo data
	 */
	public Date getData() {
		return data;
	}
	
	/**
	 * @return o atributo horarioAceito
	 */
	public Date getHorarioAceito() {
		if(this.getDisponibilidadeAtiva() != null) 
			return this.getDisponibilidadeAtiva().getHoraInicio();
		return new Date();
	}

	/**
	 * @param o parametro horarioAceito é setado em  horarioAceito
	 */
	public void setHorarioAceito(Date horarioAceito) {
		this.horarioAceito = horarioAceito;
	}

	/**
	 * 
	 * @return
	 */
	@JsonIgnore
	@Transient
	public DisponibilidadeHorario getDisponibilidadeAtiva() {
		if (this.horariosDisponiveis != null || !this.horariosDisponiveis.isEmpty()) {
			for (DisponibilidadeHorario disp : this.horariosDisponiveis) {
				if (disp.getAtivo() != null && disp.getAtivo()) {
					return disp;
				}
			}
		}
		return null;
	}

	/**
	 * @param o
	 *            parametro data é setado em data
	 */
	public void setData(Date data) {
		this.data = data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Donativo [id=" + id + ", quantidade=" + quantidade + ", nome=" + nome + ", descricao=" + descricao
				+ ", estadosDaDoacao=" + estadosDaDoacao + ", horariosDisponiveis=" + horariosDisponiveis + ", doador="
				+ doador + ", endereco=" + endereco + ", fotosDonativo=" + fotosDonativo + ", categoria=" + categoria
				+ ", data=" + data + ", mensageiro=" + mensageiro + "]";
	}

}
