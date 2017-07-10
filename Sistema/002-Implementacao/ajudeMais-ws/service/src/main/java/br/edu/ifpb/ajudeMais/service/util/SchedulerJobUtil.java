package br.edu.ifpb.ajudeMais.service.util;

import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Component;

import br.edu.ifpb.ajudeMais.domain.enumerations.JobName;
import br.edu.ifpb.ajudeMais.domain.enumerations.TriggerName;
import br.edu.ifpb.ajudeMais.service.config.QuartzConfig;

/**
 * 
 * <p>
 * {@link SchedulerJobUtil}
 * </p>
 * 
 * <p>
 * Classe utilitária para operações relacionadas a agendamento de jobs usando
 * Quartz scheduler.
 * </p>
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@Component
public class SchedulerJobUtil {

	/**
	 * 
	 */
	@Autowired
	private SchedulerFactoryBean schedFactory;

	/**
	 * 
	 * <p>
	 * Cria um agendamento para um Qartz Job específico. Mudar segundo parametro
	 * quando for colocar em produção.
	 * </p>
	 * 
	 * @param jobName
	 *            nome do job a ser criado.
	 * @param triggerName
	 *            nome do trigger responsável por start do job.
	 * @param dataId
	 *            ID de entidade que deve ser passado para o Job
	 * @param jobClass
	 *            implementação do Job.
	 */
	@SuppressWarnings("rawtypes")
	public void createJob(JobName jobName, TriggerName triggerName, Long dataId, Class jobClass) {
		JobDetailFactoryBean jdfb = QuartzConfig.createJobDetail(jobClass);
		jdfb.setBeanName(jobName.name() + "_" + dataId);
		jdfb.afterPropertiesSet();

		SimpleTriggerFactoryBean stfb = QuartzConfig.createTrigger(jdfb.getObject(), 60000L, 0);
		stfb.setBeanName(triggerName.name() + "_" + dataId);
		stfb.afterPropertiesSet();

		jdfb.getJobDataMap().put("id", dataId);

		try {
			schedFactory.getScheduler().scheduleJob(jdfb.getObject(), stfb.getObject());
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * <p>
	 * Remove um Job que esteja em execução passando nome do job e trigger
	 * responsável pela iniciação do mesmo.
	 * </p>
	 * 
	 * @param jobName
	 *            nome do Job a ser removido.
	 * @param triggerName
	 *            nome do trigger responsável pela iniciação do Job.
	 */
	public void removeJob(JobName jobName, TriggerName triggerName, Long dataId) {
		TriggerKey tkey = new TriggerKey(triggerName.name() + "_" + dataId);
		JobKey jkey = new JobKey(jobName.name() + "_" + dataId);
		try {
			schedFactory.getScheduler().unscheduleJob(tkey);
			schedFactory.getScheduler().deleteJob(jkey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
