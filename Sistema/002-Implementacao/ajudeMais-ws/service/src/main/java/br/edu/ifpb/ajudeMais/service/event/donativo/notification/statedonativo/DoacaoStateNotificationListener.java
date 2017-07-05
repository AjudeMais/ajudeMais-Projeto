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


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.edu.ifpb.ajudeMais.domain.enumerations.TipoNotificacao;
import br.edu.ifpb.ajudeMais.service.fcm.FcmService;
import br.edu.ifpb.ajudeMais.service.fcm.dto.Data;
import br.edu.ifpb.ajudeMais.service.fcm.dto.Notification;
import br.edu.ifpb.ajudeMais.service.fcm.dto.Push;

/**
 * 
 * <p>
 * <b> {@link DoacaoStateNotificationListener}</b>
 * </p>
 *
 * <p>
 * Ouvinte para evento de notificar algumas mudança de estado de doação
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
@Component
public class DoacaoStateNotificationListener {

	/**
	 * 
	 */
	@Autowired
	private FcmService fcmService;

	/**
	 * @param event
	 */
	@EventListener(condition = "#event.notificavel")
	public void campanhaSaved(DoacaoStateNotificationEvent event) {
		Notification notification = new Notification("default", event.getDonativo().getNome(),
				event.getMensageChangeState());
		Push push = new Push("high", notification, event.getNotificaveis());
		push.setData(new Data(TipoNotificacao.DOACAO.toString(), event.getDonativo().getId()));
		fcmService.sendNotification(push);

	}
}
