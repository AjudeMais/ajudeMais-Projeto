
/**
 * 
 * <p>
 * <b> DonativoCampanhaServiceImpl.java </b>
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.edu.ifpb.ajudeMais.data.repository.DonativoCampanhaRepository;
import br.edu.ifpb.ajudeMais.domain.entity.DonativoCampanha;
import br.edu.ifpb.ajudeMais.service.event.donativo.DonativoEditEvent;
import br.edu.ifpb.ajudeMais.service.event.donativo.notification.newdonativo.DoacaoNotificationEvent;
import br.edu.ifpb.ajudeMais.service.event.donativo.notification.statedonativo.DoacaoStateNotificationEvent;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.DonativoCampanhaService;
import br.edu.ifpb.ajudeMais.service.negocio.DonativoService;
import br.edu.ifpb.ajudeMais.service.util.DonativoColetaUtil;

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
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
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
	private DonativoService donativoService;
	
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
		
		List<String> notificaveis = coletaUtil.getNotificaveis(donativoSaved.getDonativo());

		if (notificaveis != null && !notificaveis.isEmpty()) {
			publisher.publishEvent(
					new DoacaoNotificationEvent(notificaveis, donativoSaved.getDonativo(), donativoSaved.getDonativo().getDescricao()));
		} else {
			publisher.publishEvent(new DoacaoStateNotificationEvent
					(donativoSaved.getDonativo().getDoador().getTokenFCM().getToken(),
					donativoSaved.getDonativo(), "Nenhum mensageiro disponível para coleta em sua localidade"));

			donativoSaved.setDonativo(
					coletaUtil.updateEstadoDoacao(donativoSaved.getDonativo()));
			
			
			donativoService.update(donativoSaved.getDonativo());
		}
		return donativoSaved;
	}

}
