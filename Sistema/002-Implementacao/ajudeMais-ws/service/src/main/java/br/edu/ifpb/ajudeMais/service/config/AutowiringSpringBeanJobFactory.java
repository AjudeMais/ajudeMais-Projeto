package br.edu.ifpb.ajudeMais.service.config;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * 
 * <p>
 * {@link AutowiringSpringBeanJobFactory}
 * </p>
 * 
 * <p>
 * Classe de configuração para permitir autowiring em job factory do quartz.
 * </p>
 *
 * <pre>
 * 
 * @see https://github.com/pavansolapure/opencodez-samples/blob/master/quartz-
 *      demo/ 
 * </pre>
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
public final class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {

	private transient AutowireCapableBeanFactory beanFactory;

	/**
	 * 
	 */
	@Override
	public void setApplicationContext(final ApplicationContext context) {
		beanFactory = context.getAutowireCapableBeanFactory();
	}

	/**
	 * 
	 */
	@Override
	protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
		final Object job = super.createJobInstance(bundle);
		beanFactory.autowireBean(job);
		return job;
	}
}