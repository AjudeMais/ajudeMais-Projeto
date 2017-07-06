package br.edu.ifpb.ajudeMais.service.util;

import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Component;

import br.edu.ifpb.ajudeMais.service.config.QuartzConfig;
import br.edu.ifpb.ajudeMais.service.job.NotificationJob;

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
	public void createJob(String jobName, String triggerName, Class jobClass) {
		JobDetailFactoryBean jdfb = QuartzConfig.createJobDetail(NotificationJob.class);
		jdfb.setBeanName(jobName);
		jdfb.afterPropertiesSet();

		SimpleTriggerFactoryBean stfb = QuartzConfig.createTrigger(jdfb.getObject(), 5000L, 2);
		stfb.setBeanName(triggerName);
		stfb.afterPropertiesSet();

		jdfb.getJobDataMap().put("id", 10);
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
	public void removeJob(String jobName, String triggerName) {
		TriggerKey tkey = new TriggerKey(triggerName);
		JobKey jkey = new JobKey(jobName);
		try {
			schedFactory.getScheduler().unscheduleJob(tkey);
			schedFactory.getScheduler().deleteJob(jkey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
