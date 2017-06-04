
/**
 * 
 * <p>
 * <b> DonativoEditEvent.java </b>
 * </p>
 *
 * <p>
 * Entidade ...
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
package br.edu.ifpb.ajudeMais.service.event.donativo;

import br.edu.ifpb.ajudeMais.domain.entity.Donativo;

/**
 * 
 * <p>
 * <b> {@link DonativoEditEvent} </b>
 * </p>
 *
 * <p>
 * Classe utilizada para evento relacionados a criação de um donativo. Este evento
 * é chamado quando o método save de donativo for acionado. * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public class DonativoEditEvent {

	/**
	 * 
	 */
	private Donativo donativo;
	

	/**
	 * 
	 * <p>
	 * Construtor default
	 * </p>
	 *
	 * @param doador
	 */
	public DonativoEditEvent(Donativo donativo) {
		this.donativo = donativo;
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
	 * 
	 * <p>
	 * Verifica se o donativo possui uma imagem.
	 * </p>
	 * 
	 * @return
	 */
	public boolean isImage() {
		if (donativo.getFotosDonativo() == null)
			return false;

		return true;
	}

}
