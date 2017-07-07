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
import br.edu.ifpb.ajudeMais.service.job.NotificationBairroJob;

/**
 * 
 * <p>
 * {@link SchedulerJobUtil}
 * </p>
 * 
 * <p>
 * Classe utilitária para definição de operações relacionadas a agendamento de
 * jobs.
 * </p>
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@Component
public class SchedulerJobUtil {

	@Autowired
	private SchedulerFactoryBean schedFactory;

	/**
	 * 
	 * @param jobName
	 * @param triggerName
	 * @param jobClass
	 */
	@SuppressWarnings("rawtypes")
	public void createJob(JobName jobName, TriggerName triggerName, Long dataId, Class jobClass) {
		JobDetailFactoryBean jdfb = QuartzConfig.createJobDetail(NotificationBairroJob.class);
		jdfb.setBeanName(jobName.name());
		jdfb.afterPropertiesSet();

		SimpleTriggerFactoryBean stfb = QuartzConfig.createTrigger(jdfb.getObject(), 5000L, 0);
		stfb.setBeanName(triggerName.name());
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
	 * @param jobName
	 * @param triggerName
	 */
	public void removeJob(JobName jobName, TriggerName triggerName) {
		TriggerKey tkey = new TriggerKey(triggerName.name());
		JobKey jkey = new JobKey(jobName.name());
		try {
			schedFactory.getScheduler().unscheduleJob(tkey);
			schedFactory.getScheduler().deleteJob(jkey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
