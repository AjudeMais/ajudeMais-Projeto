
package br.edu.ifpb.ajudeMais.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
public class DonativoCampanha {
	
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	/**
	 * 
	 */
	@OneToOne
	private Donativo donativo;
	
	/**
	 * 
	 */
	@ManyToOne
	private Campanha campanha;

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
	 * @return the campanha
	 */
	public Campanha getCampanha() {
		return campanha;
	}

	/**
	 * @param campanha the campanha to set
	 */
	public void setCampanha(Campanha campanha) {
		this.campanha = campanha;
	}
	
	

}
