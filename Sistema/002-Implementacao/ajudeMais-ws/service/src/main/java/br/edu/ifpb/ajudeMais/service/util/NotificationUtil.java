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
import br.edu.ifpb.ajudeMais.domain.enumerations.JobName;
import br.edu.ifpb.ajudeMais.domain.enumerations.TriggerName;
import br.edu.ifpb.ajudeMais.service.event.donativo.notification.newdonativo.DoacaoNotificationEvent;
import br.edu.ifpb.ajudeMais.service.event.donativo.notification.statedonativo.DoacaoStateNotificationEvent;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.job.NotificationJob;

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
	 */
	@Autowired
	private SchedulerJobUtil schedulerJobUtil;

	/**
	 * 
	 * <p>
	 * Notifica estado de um donativo donativo, após sua transição de estado.
	 * </p>
	 * 
	 * @param donativo
	 * @throws AjudeMaisException
	 */
	public EstadoDoacao notifyDonativo(Donativo donativo) throws AjudeMaisException {
		EstadoDoacao estadoDoaco = donativoColetaUtil.getEstadoDoacaoAtivo(donativo);
		if ((estadoDoaco.getNotificado() == null) || (!estadoDoaco.getNotificado())) {
			switch (estadoDoaco.getEstadoDoacao()) {
			case DISPONIBILIZADO:
				List<String> notificaveis = donativoColetaUtil.getNotificaveisToBairro(donativo);
			
				if (notificaveis != null && !notificaveis.isEmpty()) {
					publisher.publishEvent(new DoacaoNotificationEvent(notificaveis, donativo,
							"Novo donativo dispobilizado para coleta"));

				}
				publisher.publishEvent(new DoacaoStateNotificationEvent(
						donativo.getDoador().getTokenFCM().getToken(), donativo, estadoDoaco.getMensagem()));
				estadoDoaco.setNotificado(true);

				schedulerJobUtil.createJob(JobName.NOTIFICATION, TriggerName.NOTIFICATION, donativo.getId(),
						NotificationJob.class);
				
				break;

			case CANCELADO:
				if (donativo.getMensageiro() != null) {
					publisher.publishEvent(new DoacaoStateNotificationEvent(
							donativo.getMensageiro().getTokenFCM().getToken(), donativo, "Doação foi cancelada"));
					estadoDoaco.setNotificado(true);
				}
				break;

			case CANCELADO_POR_MENSAGEIRO:
				publisher.publishEvent(new DoacaoStateNotificationEvent(
						donativo.getDoador().getTokenFCM().getToken(), donativo, estadoDoaco.getMensagem()));
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
