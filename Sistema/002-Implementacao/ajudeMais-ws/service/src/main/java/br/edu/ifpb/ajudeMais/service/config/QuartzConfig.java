package br.edu.ifpb.ajudeMais.service.config;

import java.io.IOException;
import java.util.Properties;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

/**
 * 
 * <p>
 * {@link QuartzConfig}
 * </p>
 * 
 * <p>
 * Classe utilizada para configurações do Quartz scheduler
 * </p>
 *
 * <pre>
 * 
 * &#64;see https://github.com/pavansolapure/opencodez-samples
 * 
 * </pre>
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@Configuration
public class QuartzConfig {

	/**
	 * 
	 * <p>
	 * Cria bean para que seja possível a injeção nos Jobs do quartz.
	 * </p>
	 * 
	 * @param applicationContext
	 * @return
	 */
	@Bean
	public JobFactory jobFactory(ApplicationContext applicationContext) {
		AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		return jobFactory;
	}

	/**
	 * 
	 * <p>
	 * Cria um bean para Job Factory do quartz.
	 * </p>
	 * 
	 * @param jobFactory
	 * @return
	 * @throws IOException
	 */
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory) throws IOException {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setOverwriteExistingJobs(true);
		factory.setAutoStartup(true);
		factory.setJobFactory(jobFactory);
		factory.setQuartzProperties(quartzProperties());

		return factory;
	}

	/**
	 * 
	 * <p>
	 * Cria bean para configurações adicionadas ao quartz.properties.
	 * </p>
	 * 
	 * @return
	 * @throws IOException
	 */
	@Bean
	public Properties quartzProperties() throws IOException {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}

	/**
	 * 
	 * <p>
	 * Este método é utilizado para criar uma instância de um
	 * {@link SimpleTrigger}
	 * </p>
	 * 
	 * @param jobDetail
	 * @param pollFrequencyMs
	 * @return
	 */
	public static SimpleTriggerFactoryBean createTrigger(JobDetail jobDetail, long pollFrequencyMs, int repeat) {
		SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
		factoryBean.setJobDetail(jobDetail);
		factoryBean.setStartDelay(pollFrequencyMs);
		factoryBean.setRepeatInterval(1l);
		factoryBean.setRepeatCount(repeat);
		factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
		return factoryBean;
	}

	/**
	 * 
	 * <p>
	 * Este método é utilizado para criar uma instancia de um
	 * {@link CronTrigger}
	 * </p>
	 * 
	 * @param jobDetail
	 * @param cronExpression
	 * @return
	 */
	public static CronTriggerFactoryBean createCronTrigger(JobDetail jobDetail, String cronExpression) {
		CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
		factoryBean.setJobDetail(jobDetail);
		factoryBean.setCronExpression(cronExpression);
		factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
		return factoryBean;
	}

	/**
	 * 
	 * <p>
	 * Cria bean para {@link JobDetail}
	 * </p>
	 * 
	 * @param jobClass
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static JobDetailFactoryBean createJobDetail(Class jobClass) {
		JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
		factoryBean.setJobClass(jobClass);
		factoryBean.setDurability(true);
		return factoryBean;
	}
}
