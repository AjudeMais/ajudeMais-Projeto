package br.edu.ifpb.ajudeMais.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import br.edu.ifpb.ajudeMais.domain.entity.Donativo;
import br.edu.ifpb.ajudeMais.domain.entity.EstadoDoacao;
import br.edu.ifpb.ajudeMais.service.event.donativo.notification.statedonativo.DoacaoStateNotificationEvent;

/**
 * 
 * <p>
 * {@link NotificationUtil}
 * </p>
 * 
 * <p>
 * Classe utilizada para operações comuns de notificação no sistema.
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@Component
public class NotificationUtil {
	
	/**
	 * 
	 */
	Logger LOGGER = LoggerFactory.getLogger(NotificationUtil.class);

	/**
	 *           
	 */
	@Autowired
	private ApplicationEventPublisher publisher;

	/**
	 * 
	 * <p>
	 * Notifica estado de um donativo donativo, após sua transição de estado.
	 * </p>
	 * 
	 * @param donativo
	 */
	public EstadoDoacao notifyDonativo(Donativo donativo) {
		EstadoDoacao estadoDoaco = this.getEstadoDoacaoAtivo(donativo);
		
		if ((estadoDoaco.getNotificado() != null) && (!estadoDoaco.getNotificado())) {
			switch (estadoDoaco.getEstadoDoacao()) {
			case CANCELADO:
				publisher.publishEvent(
						new DoacaoStateNotificationEvent(donativo.getMensageiro().getTokenFCM().getToken(), donativo,
								"Doação foi cancelada pelo doador"));
				estadoDoaco.setNotificado(true);
				break;

			case ACEITO:
				publisher.publishEvent(new DoacaoStateNotificationEvent(donativo.getDoador().getTokenFCM().getToken(),
						donativo, "Doação foi aceita por " + donativo.getMensageiro().getNome()));
				estadoDoaco.setNotificado(true);
				break;

			case RECEBIDO:
				publisher.publishEvent(new DoacaoStateNotificationEvent(donativo.getDoador().getTokenFCM().getToken(),
						donativo, "Foi entregue a " + donativo.getCategoria().getInstituicaoCaridade().getNome()));
				estadoDoaco.setNotificado(true);
				break;

			default:
				break;
			}
		}

		return estadoDoaco;
	}

	/**
	 * 
	 * <p>
	 * Recupera estado da doação com estado ativo de um donativo.
	 * </p>
	 * 
	 * @param donativo
	 * @return
	 */
	public EstadoDoacao getEstadoDoacaoAtivo(Donativo donativo) {
		EstadoDoacao estadoDoacao = null;
		for (EstadoDoacao estado : donativo.getEstadosDaDoacao()) {
			if (estado.getAtivo()) {
				estadoDoacao = estado;
			}
		}
		return estadoDoacao;
	}
}
