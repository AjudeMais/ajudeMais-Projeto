package br.edu.ifpb.ajudeMais.service.negocio.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.edu.ifpb.ajudeMais.data.repository.DonativoRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Donativo;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
import br.edu.ifpb.ajudeMais.domain.entity.Mensageiro;
import br.edu.ifpb.ajudeMais.service.event.donativo.DonativoEditEvent;
import br.edu.ifpb.ajudeMais.service.event.donativo.notification.DoacaoNotificationEvent;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.DonativoService;
import br.edu.ifpb.ajudeMais.service.negocio.MensageiroAssociadoService;

/**
 * 
 * 
 * <p>
 * <b> {@link DonativoServiceImpl} </b>
 * </p>
 *
 * <p>
 *
 * </p>
 * 
 * @author <a href="https://github.com/amslv">Ana Silva</a>
 *
 */
@Service
public class DonativoServiceImpl implements DonativoService {

	/**
	 * 
	 */
	@Autowired
	private DonativoRepository donativoRepository;

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
	 * 
	 */
	@Transactional
	@Override
	public Donativo save(Donativo entity) throws AjudeMaisException {

		Donativo donativoSaved = donativoRepository.save(entity);

		//publisher.publishEvent(new DonativoEditEvent(donativoSaved));
		
		List<String> notificaveis = getNotificaveis(donativoSaved);

		publisher.publishEvent(new DoacaoNotificationEvent(notificaveis, donativoSaved, donativoSaved.getDescricao()));

		return donativoSaved;
	}

	/**
	 * 
	 */
	@Transactional
	@Override
	public Donativo update(Donativo entity) throws AjudeMaisException {
		Donativo donativoUpdated = donativoRepository.save(entity);
		return donativoUpdated;

	}

	/**
	 * 
	 */
	@Override
	public List<Donativo> findAll() {
		return donativoRepository.findAllByOrderByDataDesc();
	}

	/**
	 * 
	 */
	@Override
	public Donativo findById(Long id) {
		return donativoRepository.findOne(id);
	}

	/**
	 * 
	 */
	@Transactional
	@Override
	public void remover(Donativo entity) {
		donativoRepository.delete(entity);
	}

	/**
	 * 
	 */
	@Override
	public List<Donativo> findByNome(String nome) {
		return donativoRepository.findByNome(nome);
	}

	/**
	 * 
	 */
	@Override
	public List<Donativo> findByDoadorNome(String nomeDoador) {
		return donativoRepository.findByDoadorNome(nomeDoador);
	}

	/**
	 * Busca donativos de acordo com id do doador.
	 */
	@Override
	public List<Donativo> findByDoadorId(Long idDoador) {
		return donativoRepository.findByDoadorIdOrderByDataDesc(idDoador);
	}

	/**
	 * 
	 */
	@Override
	public List<Donativo> findByCategoriaInstituicaoCaridade(InstituicaoCaridade instituicao) {
		return donativoRepository.findByCategoriaInstituicaoCaridadeOrderByDataDesc(instituicao);
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
