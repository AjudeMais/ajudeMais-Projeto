/**
 * <p>
 * Ajude Mais - Módulo Web Service
 * </p>
 * 
 * <p>
 * Sistema para potencializar o processo de doação.
 * </p>
 * 
 * <a href="https://github.com/AjudeMais/AjudeMais">Ajude Mais</a>
 * <a href="https://franckaj.github.io">Franck Aragão"></a>
 * 
 * AJUDE MAIS - 2017®
 * 
 */
package br.edu.ifpb.ajudeMais.service.event.donativo.notification.statedonativo;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.ajudeMais.domain.entity.Donativo;

/**
 * 
 * <p>
 * <b> {@link DoacaoStateNotificationEvent} </b>
 * </p>
 *
 * <p>
 * Evento para notificar mudança de estado de uma doação.
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public class DoacaoStateNotificationEvent {

	private List<String> notificaveis;

	private Donativo donativo;

	private String mensageChangeState;

	/**
	 * 
	 * @param notificavel
	 * @param donativo
	 * @param mensageChangeState
	 */
	public DoacaoStateNotificationEvent(String notificavel, Donativo donativo, String mensageChangeState) {
		this.notificaveis = new ArrayList<>();
		notificaveis.add(notificavel);
		this.donativo = donativo;
		this.mensageChangeState = mensageChangeState;
	}

	/**
	 * @return the mensageChangeState
	 */
	public String getMensageChangeState() {
		return mensageChangeState;
	}

	/**
	 * @param mensageChangeState
	 *            the mensageChangeState to set
	 */
	public void setMensageChangeState(String mensageChangeState) {
		this.mensageChangeState = mensageChangeState;
	}

	/**
	 * @return the notificavel
	 */
	public List<String> getNotificaveis() {
		return notificaveis;
	}

	/**
	 * @param notificavel
	 *            the notificavel to set
	 */
	public void setNotificaveis(List<String> notificaveis) {
		this.notificaveis = notificaveis;
	}

	/**
	 * @return o atributo donativo
	 */
	public Donativo getDonativo() {
		return donativo;
	}

	/**
	 * @param o parametro donativo é setado em donativo
	 */
	public void setDonativo(Donativo donativo) {
		this.donativo = donativo;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isNotificavel() {
		return !notificaveis.isEmpty();

	}
}
