package br.edu.ifpb.ajudeMais.service.event.campanha.notification;

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
 * {@link CampanhaNotificationListener}
 * </p>
 * 
 * <p>
 * Classe utilizada para registro de eventos relacionados a notificação. Sempre
 * que um evento for chamado e o mesmo esteja registrado nesta classe, então é
 * executada a tarefa para este listener.
 * </p>
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@Component
public class CampanhaNotificationListener {

	/**
	 * 
	 */
	@Autowired
	private FcmService fcmService;

	/**
	 * @param event
	 */
	@EventListener(condition = "#event.notificavel")
	public void campanhaSaved(CampanhaNotificationEvent event) {
		Notification notification = new Notification("default", "Nova Campanha", event.getCampanha().getNome());
		Push push = new Push("high", notification, event.getNotificaveis());
		push.setData(new Data(TipoNotificacao.CAMPANHA.toString(), event.getCampanha().getId()));
		fcmService.sendNotification(push);

	}

}
