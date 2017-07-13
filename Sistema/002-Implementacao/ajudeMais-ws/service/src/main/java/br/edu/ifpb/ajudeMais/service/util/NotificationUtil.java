package br.edu.ifpb.ajudeMais.service.util;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import br.edu.ifpb.ajudeMais.domain.entity.DisponibilidadeHorario;
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
	 */
	@Autowired
	private DonativoColetaUtil donativoColetaUtil;

	/**
	 * 
	 * <p>
	 * Notifica estado de um donativo donativo, após sua transição de estado.
	 * </p>
	 * 
	 * @param donativo
	 */
	public EstadoDoacao notifyDonativo(Donativo donativo) {
		EstadoDoacao estadoDoaco = donativoColetaUtil.getEstadoDoacaoAtivo(donativo);
		if ((estadoDoaco.getNotificado() == null) || (!estadoDoaco.getNotificado())) {
			switch (estadoDoaco.getEstadoDoacao()) {
			case CANCELADO:
				if (donativo.getMensageiro() != null) {
					publisher.publishEvent(
							new DoacaoStateNotificationEvent(donativo.getMensageiro().getTokenFCM().getToken(),
									donativo, "Doação foi cancelada"));
					estadoDoaco.setNotificado(true);
				}
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
	 * Verifica se horários de doação estão validos para notificação.
	 * </p>
	 * 
	 * @param donativo
	 * @return
	 */
	public boolean notificationDonativoValid(Donativo donativo) {

		List<DisponibilidadeHorario> horarios = donativo.getHorariosDisponiveis();

		for (DisponibilidadeHorario dispHorario : horarios) {

			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, 1);
			if (c.getTime().before(dispHorario.getHoraFim())) {
				return true;
			}
		}

		return false;
	}
}
