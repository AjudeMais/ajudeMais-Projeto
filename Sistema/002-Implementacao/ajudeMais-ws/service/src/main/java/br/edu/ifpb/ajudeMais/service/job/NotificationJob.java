package br.edu.ifpb.ajudeMais.service.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.ajudeMais.service.negocio.DoadorService;

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
	
	Logger LOGGER = LoggerFactory.getLogger(NotificationJob.class);
	
	@Autowired
	private DoadorService doadorService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
	
		LOGGER.info("executando job com sucesso..." + doadorService.findAll().get(0));
		
	}
}
