
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

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.edu.ifpb.ajudeMais.data.repository.DonativoCampanhaRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Donativo;
import br.edu.ifpb.ajudeMais.domain.entity.DonativoCampanha;
import br.edu.ifpb.ajudeMais.domain.entity.Mensageiro;
import br.edu.ifpb.ajudeMais.service.event.donativo.DonativoEditEvent;
import br.edu.ifpb.ajudeMais.service.event.donativo.notification.DoacaoNotificationEvent;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.DonativoCampanhaService;
import br.edu.ifpb.ajudeMais.service.negocio.MensageiroAssociadoService;

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
	private MensageiroAssociadoService mensageiroAssociadoService;

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
		publisher.publishEvent(new DoacaoNotificationEvent(getNotificaveis(donativoSaved.getDonativo()), donativoSaved.getDonativo(), donativoSaved.getDonativo().getDescricao()));
		return donativoSaved;
	}

	/**
	 * 
	 * <p>
	 * Obtém lista de mensageiros que serão notificados.
	 * </p>
	 * 
	 * @param campanha
	 * @return
	 * @throws AjudeMaisException 
	 */
	private List<String> getNotificaveis(Donativo donativo) throws AjudeMaisException {

		List<Mensageiro> mensageiros = mensageiroAssociadoService.filterMensageirosCloser(donativo.getEndereco(),
				donativo.getCategoria().getInstituicaoCaridade().getId());

		List<String> notificaveis = new ArrayList<>();

		mensageiros.forEach(m -> {
			boolean isValid = true;
			
			for(String n : notificaveis){
				if(n.equals(m.getTokenFCM().getToken()))
					isValid = false;
			}
		
			if (isValid) {
				notificaveis.add(m.getTokenFCM().getToken());
			}
		});

		return notificaveis;
	}

	
}
