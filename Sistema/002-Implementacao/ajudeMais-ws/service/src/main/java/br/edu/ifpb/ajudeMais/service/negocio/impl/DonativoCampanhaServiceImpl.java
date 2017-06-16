
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
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.DonativoCampanhaService;

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
public class DonativoCampanhaServiceImpl implements DonativoCampanhaService{

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
	 * Busca todos os donativos doados para um campanha com base em seu ID.
	 */
	@Override
	public List<DonativoCampanha> findByCampanhaId(Long id) {
		List<DonativoCampanha> donativos = donativoCampanhaRespository.findByCampanhaId(id);
		return donativos;
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
		return donativoSaved;
	}

	
}
