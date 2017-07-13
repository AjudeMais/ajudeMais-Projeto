package br.edu.ifpb.ajudeMais.service.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import br.edu.ifpb.ajudeMais.domain.entity.Donativo;
import br.edu.ifpb.ajudeMais.domain.entity.EstadoDoacao;
import br.edu.ifpb.ajudeMais.domain.enumerations.Estado;
import br.edu.ifpb.ajudeMais.domain.enumerations.JobName;
import br.edu.ifpb.ajudeMais.domain.enumerations.TriggerName;
import br.edu.ifpb.ajudeMais.service.event.donativo.notification.statedonativo.DoacaoStateNotificationEvent;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.DonativoService;
import br.edu.ifpb.ajudeMais.service.util.DonativoColetaUtil;
import br.edu.ifpb.ajudeMais.service.util.SchedulerJobUtil;

/**
 * 
 * <p>
 * {@link NotificationCidadeJob}
 * </p>
 * 
 * <p>
 * Classe utilizada para execução de job para notificação agendada de cidade.
 * </p>
 *
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */

@Component
public class NotificationCidadeJob implements Job {

	/**
	 * 
	 */
	Logger LOGGER = LoggerFactory.getLogger(NotificationCidadeJob.class);

	/**
	 * 
	 */
	@Autowired
	private DonativoService donativoService;

	/**
	 * 
	 */
	@Autowired
	private DonativoColetaUtil donativoColetaUtil;

	/**
	 *           
	 */
	@Autowired
	private ApplicationEventPublisher publisher;

	/**
	 * 
	 */
	@Autowired
	private SchedulerJobUtil schedulerJobUtil;

	/**
	 * 
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LOGGER.info("Executou Job de notificação para cidade...");

		Long id = context.getJobDetail().getJobDataMap().getLongValue("id");
		Donativo donativo = this.donativoService.findById(id);
		EstadoDoacao estadoAtual = donativoColetaUtil.getEstadoDoacaoAtivo(donativo);

		if (donativo != null && donativo.getMensageiro() == null) {
			if (!estadoAtual.getEstadoDoacao().equals(Estado.CANCELADO)) {
				
				publisher.publishEvent(new DoacaoStateNotificationEvent(donativo.getDoador().getTokenFCM().getToken(),
						donativo, "Nenhum mensageiro disponível para coleta em sua localidade"));

				updateEstadoDonativo(donativo);
			}
		}

		schedulerJobUtil.removeJob(JobName.NOTIFICATION_CIDADE, TriggerName.NOTIFICATION_CIDADE, donativo.getId());
	}

	/**
	 * 
	 * @param donativo
	 * @param estado
	 */
	private void updateEstadoDonativo(Donativo donativo) {
		EstadoDoacao estadoAtual = donativoColetaUtil.getEstadoDoacaoAtivo(donativo);

		if (estadoAtual.getEstadoDoacao().equals(Estado.DISPONIBILIZADO)) {
			donativo = donativoColetaUtil.addEstadoDoacao(donativo, Estado.NAO_ACEITO, true);

			try {
				donativoService.update(donativo);
			} catch (AjudeMaisException e) {
				LOGGER.error(e.getMessage());
			}
		}
	}
}
