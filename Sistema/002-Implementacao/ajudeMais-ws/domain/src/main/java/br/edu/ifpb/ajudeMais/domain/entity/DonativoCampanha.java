
package br.edu.ifpb.ajudeMais.domain.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@NamedQueries({
	@NamedQuery(name = "DonativoCampanha.filterDonativoByEstadoAfterAceito", query = "SELECT dc FROM DonativoCampanha dc JOIN FETCH dc.estadosDaDoacao ed "
			+ "WHERE dc.campanha.id = :idcampanha and ed.estadoDoacao != 'DISPONIBILIZADO' and ed.estadoDoacao != 'CANCELADO' "
			+ "and  ed.estadoDoacao != 'ACEITO'  and ed.ativo is true"), })
public class DonativoCampanha extends Donativo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * 
	 */
	@ManyToOne(cascade = CascadeType.MERGE)
	private Campanha campanha;


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
