
/**
 * 
 * <p>
 * <b>{@link DonativoServiceImpl} </b>
 * </p>
 *
 * <p>
 * Entidade ...
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
package br.edu.ifpb.ajudeMais.service.negocio.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.edu.ifpb.ajudeMais.data.repository.DonativoCampanhaRepository;
import br.edu.ifpb.ajudeMais.domain.entity.DonativoCampanha;
import br.edu.ifpb.ajudeMais.domain.enumerations.JobName;
import br.edu.ifpb.ajudeMais.domain.enumerations.TriggerName;
import br.edu.ifpb.ajudeMais.service.event.donativo.DonativoEditEvent;
import br.edu.ifpb.ajudeMais.service.event.donativo.notification.newdonativo.DoacaoNotificationEvent;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.job.NotificationJob;
import br.edu.ifpb.ajudeMais.service.negocio.DonativoCampanhaService;
import br.edu.ifpb.ajudeMais.service.util.DonativoColetaUtil;
import br.edu.ifpb.ajudeMais.service.util.SchedulerJobUtil;

/**
 * 
 * <p>
 * <b> {@link DonativoCampanhaServiceImpl}</b>
 * </p>
 *
 * <p>
 * Serviços para donativos relacionados a uma campanha.
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a></br>
 *         <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
@Service
public class DonativoCampanhaServiceImpl implements DonativoCampanhaService {

	/**
	 * 
	 */
	@Autowired
	DonativoCampanhaRepository donativoCampanhaRespository;

	/**
	 *           
	 */
	@Autowired
	private ApplicationEventPublisher publisher;

	/**
	 * 
	 */
	@Autowired
	private DonativoColetaUtil coletaUtil;

	/**
	 * 
	 */
	@Autowired
	private SchedulerJobUtil schedulerJobUtil;

	/**
	 * 
	 */
	Logger LOGGER = LoggerFactory.getLogger(DonativoCampanhaServiceImpl.class);

	/**
	 * Busca todos os donativos doados para um campanha com base em seu ID.
	 */
	@Override
	public List<DonativoCampanha> findByCampanhaId(Long id) {
		List<DonativoCampanha> donativos = donativoCampanhaRespository.findByCampanhaId(id);
		return donativos;
	}

	/**
	 * Busca DonativoCampanha com base no id do donativo.
	 */
	@Override
	public DonativoCampanha findByDonativoId(Long id) {
		DonativoCampanha donativo = donativoCampanhaRespository.findOneByDonativoId(id);
		return donativo;
	}

	/**
	 * Busca todos os donativos doados para um campanha com o estado passsado.
	 */
	@Override
	public List<DonativoCampanha> filterDonativoByEstadoAfterAceito(Long idCampanha) {
		List<DonativoCampanha> donativos = donativoCampanhaRespository.filterDonativoByEstadoAfterAceito(idCampanha);
		return donativos;
	}

	/**
	 * Salva um donativoCampanha
	 */
	@Override
	@Transactional
	public DonativoCampanha save(DonativoCampanha entity) throws AjudeMaisException {
		DonativoCampanha donativoSaved = donativoCampanhaRespository.save(entity);
		publisher.publishEvent(new DonativoEditEvent(donativoSaved.getDonativo()));
		List<String> notificaveis = coletaUtil.getNotificaveisToBairro(donativoSaved.getDonativo());
		
		if (notificaveis != null && !notificaveis.isEmpty()) {
			publisher.publishEvent(new DoacaoNotificationEvent(notificaveis, donativoSaved.getDonativo(),
					"Novo donativo dispobilizado para coleta"));
		}
		schedulerJobUtil.createJob(JobName.NOTIFICATION, TriggerName.NOTIFICATION, donativoSaved.getDonativo().getId(),
				NotificationJob.class);
		return donativoSaved;
	}
}
