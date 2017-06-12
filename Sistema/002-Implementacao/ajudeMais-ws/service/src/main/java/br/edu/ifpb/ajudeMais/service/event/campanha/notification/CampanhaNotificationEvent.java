package br.edu.ifpb.ajudeMais.service.event.campanha.notification;

import java.util.List;

import br.edu.ifpb.ajudeMais.domain.entity.Campanha;

/**
 * 
 * <p>
 * {@link CampanhaNotificationEvent}
 * </p>
 * 
 * <p>
 * Classe representa enventos relacionados a envio de notificação de uma
 * camapnha.
 * </p>
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
public class CampanhaNotificationEvent {

	private List<String> notificaveis;

	private Campanha campanha;

	/**
	 * 
	 * @param notificaveis
	 * @param campanha
	 */
	public CampanhaNotificationEvent(List<String> notificaveis, Campanha campanha) {
		this.notificaveis = notificaveis;
		this.campanha = campanha;
	}

	/**
	 * @return o atributo notificaveis
	 */
	public List<String> getNotificaveis() {
		return notificaveis;
	}

	/**
	 * @return o atributo campanha
	 */
	public Campanha getCampanha() {
		return campanha;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isNotificavel() {
		return !notificaveis.isEmpty();
	}

}
