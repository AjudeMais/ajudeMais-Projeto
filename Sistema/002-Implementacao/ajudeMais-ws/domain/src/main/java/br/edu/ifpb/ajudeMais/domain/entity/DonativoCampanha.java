
package br.edu.ifpb.ajudeMais.domain.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 
 * <p>
 * <b>{@link DonativoCampanha} </b>
 * </p>
 *
 * <p>
 * Classse para identificar uma doação feita para um campanha.
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */

@Entity
@Table(name = "donativo_campanha")
@NamedQueries({
		@NamedQuery(name = "DonativoCampanha.filterDonativoByEstadoAfterAceito", query = "SELECT dc FROM DonativoCampanha dc JOIN dc.donativo.estadosDaDoacao ed "
				+ "WHERE dc.campanha.id = :idcampanha and ed.estadoDoacao != 'DISPONIBILIZADO' and ed.estadoDoacao != 'CANCELADO' "
				+ "and  ed.estadoDoacao != 'ACEITO' and ed.estadoDoacao != 'NAO_ACEITO' and ed.estadoDoacao != 'CANCELADO_POR_MENSAGEIRO' and  ed.ativo is true"),

		@NamedQuery(name = "DonativoCampanha.filterCountByEstadoAndCategoriaAfterAceito", query = "SELECT COUNT(dc) FROM DonativoCampanha dc JOIN dc.donativo.estadosDaDoacao ed "
				+ "WHERE dc.campanha.id = :idcampanha and dc.donativo.categoria.id = :idCategoria "
				+ "and ed.estadoDoacao != 'DISPONIBILIZADO' and ed.estadoDoacao != 'CANCELADO' "
				+ "and  ed.estadoDoacao != 'ACEITO' and ed.estadoDoacao != 'NAO_ACEITO' and  ed.estadoDoacao != 'CANCELADO_POR_MENSAGEIRO' and ed.ativo is true") })
public class DonativoCampanha implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	/**
	 * 
	 */
	@NotNull(message = "A campanha referente a doação deve ser informada")
	@ManyToOne(fetch = FetchType.EAGER)
	private Campanha campanha;
	
	@NotNull(message = "O donativo deve ser informada")
	@OneToOne(cascade = CascadeType.ALL)
	private Donativo donativo;

	/**
	 * @return the campanha
	 */
	public Campanha getCampanha() {
		return campanha;
	}

	/**
	 * @param campanha
	 *            the campanha to set
	 */
	public void setCampanha(Campanha campanha) {
		this.campanha = campanha;
	}

	/**
	 * @return the donativo
	 */
	public Donativo getDonativo() {
		return donativo;
	}

	/**
	 * @param donativo the donativo to set
	 */
	public void setDonativo(Donativo donativo) {
		this.donativo = donativo;
	}

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
	
	

}
