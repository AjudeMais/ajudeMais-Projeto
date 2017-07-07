package br.edu.ifpb.ajudeMais.service.job;

import java.util.ArrayList;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import br.edu.ifpb.ajudeMais.domain.entity.Donativo;
import br.edu.ifpb.ajudeMais.domain.enumerations.JobName;
import br.edu.ifpb.ajudeMais.domain.enumerations.TriggerName;
import br.edu.ifpb.ajudeMais.service.event.donativo.notification.newdonativo.DoacaoNotificationEvent;
import br.edu.ifpb.ajudeMais.service.event.donativo.notification.statedonativo.DoacaoStateNotificationEvent;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.DonativoService;
import br.edu.ifpb.ajudeMais.service.util.DonativoColetaUtil;
import br.edu.ifpb.ajudeMais.service.util.SchedulerJobUtil;

/**
 * 
 * <p>
 * {@link NotificationBairroJob}
 * </p>
 * 
 * <p>
 * Classe utilizada para execução de job para notificação agendada.
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */

@Component
public class NotificationBairroJob implements Job {

	/**
	 * 
	 */
	Logger LOGGER = LoggerFactory.getLogger(NotificationBairroJob.class);

	/**
	 * 
	 */
	@Autowired
	private DonativoService donativoService;

	/**
	 * 
	 */
	@Autowired
	private DonativoColetaUtil coletaUtil;

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
		LOGGER.info("Executou Job de notificação por bairro");
		
		Long id = context.getJobDetail().getJobDataMap().getLongValue("id");
		Donativo donativo = this.donativoService.findById(id);
		
		if(donativo!= null && donativo.getMensageiro() != null) {
			schedulerJobUtil.removeJob(JobName.NOTIFICATION, TriggerName.NOTIFICATION);
		} else if(donativo!= null){
			notifyToCidade(donativo);
		}
	}


	/**
	 * 
	 * </p>
	 * @param donativo
	 */
	private void notifyToCidade(Donativo donativo) {
		List<String> notificaveis = new ArrayList<>();

		try {
			notificaveis = coletaUtil.getNotificaveisToCidade(donativo);
		} catch (AjudeMaisException e) {
			LOGGER.error(e.getMessage());
		}

		if (notificaveis != null && !notificaveis.isEmpty()) {
			publisher.publishEvent(new DoacaoNotificationEvent(notificaveis, donativo,
					donativo.getDescricao()));

			schedulerJobUtil.createJob(JobName.NOTIFICATION_CIDADE, TriggerName.NOTIFICATION_CIDADE, donativo.getId(),
					NotificationCidadeJob.class);

		} else {
			publisher.publishEvent(
					new DoacaoStateNotificationEvent(donativo.getDoador().getTokenFCM().getToken(),
							donativo, "Nenhum mensageiro disponível para coleta em sua localidade"));

			donativo = coletaUtil.updateEstadoDoacao(donativo);

			try {
				donativoService.update(donativo);
			} catch (AjudeMaisException e) {
				LOGGER.error(e.getMessage());
			}
		}
	}
}
