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
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.DonativoService;
import br.edu.ifpb.ajudeMais.service.util.DonativoColetaUtil;
import br.edu.ifpb.ajudeMais.service.util.NotificationUtil;
import br.edu.ifpb.ajudeMais.service.util.SchedulerJobUtil;

/**
 * 
 * <p>
 * {@link NotificationJob}
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
public class NotificationJob implements Job {

	/**
	 * 
	 */
	Logger LOGGER = LoggerFactory.getLogger(NotificationJob.class);

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
	@Autowired
	private NotificationUtil notificationUtil;

	/**
	 * Executa Job.
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LOGGER.info("Executou Job de notificação...");

		Long id = context.getJobDetail().getJobDataMap().getLongValue("id");
		Donativo donativo = this.donativoService.findById(id);
		List<String> notificaveis = new ArrayList<>();

		if (donativo != null && donativo.getMensageiro() == null) {
			try {
				notificaveis = coletaUtil.getNotificaveisToBairro(donativo);
			} catch (AjudeMaisException e) {
				LOGGER.error("Ocorreu um erro ao recuperar notificaveis : " + e.getLocalizedMessage());
			}
			notifyToCidade(donativo, notificaveis);

		} else {
			schedulerJobUtil.removeJob(JobName.NOTIFICATION, TriggerName.NOTIFICATION, donativo.getId());
		}
	}

	/**
	 * 
	 * <p>
	 * Envia notificação para mensageiros considerando a cidade e ignorando os
	 * notificados por bairro.
	 * </p>
	 * 
	 * @param donativo
	 *            donativo a ser notificado.
	 * @param notificaveisBairro
	 *            lista de notificaveis por bairro.
	 */
	private void notifyToCidade(Donativo donativo, List<String> notificaveisBairro) {
		List<String> notificaveis = new ArrayList<>();
		boolean isNotificationValid = notificationUtil.notificationDonativoValid(donativo);
	
		if (isNotificationValid) {
			try {
				notificaveis = coletaUtil.getNotificaveisToCidade(donativo);
				notificaveis = this.getNotificaveisCidade(notificaveisBairro, notificaveis);
				
			} catch (AjudeMaisException e) {
				LOGGER.error(e.getMessage());
			}

			if (notificaveis != null && !notificaveis.isEmpty()) {
				publisher.publishEvent(new DoacaoNotificationEvent(notificaveis, donativo, "Novo donativo dispobilizado para coleta"));
			}
		}
		schedulerJobUtil.createJob(JobName.NOTIFICATION_CIDADE, TriggerName.NOTIFICATION_CIDADE, donativo.getId(),
				NotificationCidadeJob.class);

	}

	/**
	 * 
	 * <p>
	 * Método auxiliar para descartar mensageiros que foram notificados na busca
	 * por bairro. Lista usada para notificar mensageiros na busca por cidade.
	 * </p>
	 * 
	 * @param notificaveisBairro
	 *            lista de notificaveis por bairro
	 * @param notificaveisCidadelista
	 *            de notificaveis por cidade.
	 * @return lista com notificaveis ignorando os já notificados no bairro.
	 */
	private List<String> getNotificaveisCidade(List<String> notificaveisBairro, List<String> notificaveisCidade) {
		List<String> notificaveis = new ArrayList<>();
		notificaveis = notificaveisCidade;
		
		if (!notificaveisBairro.isEmpty()) {
			notificaveis.removeAll(notificaveisBairro);
		}
		
		return notificaveis;

	}
}
