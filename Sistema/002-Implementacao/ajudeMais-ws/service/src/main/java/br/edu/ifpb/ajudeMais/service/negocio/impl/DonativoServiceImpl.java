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
import br.edu.ifpb.ajudeMais.service.event.donativo.DonativoEditEvent;
import br.edu.ifpb.ajudeMais.service.event.donativo.notification.DoacaoNotificationEvent;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.DonativoService;

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
 *         <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@Service
public class DonativoServiceImpl implements DonativoService {

	@Autowired
	private DonativoRepository donativoRepository;

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
		publisher.publishEvent(new DonativoEditEvent(donativoSaved));
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
	 * lança envento de notificação para estado de doação.
	 * </p>
	 * 
	 * @param donativo
	 */
	private void notify(Donativo donativo) {

		donativo.getEstadosDaDoacao().forEach(est -> {
			if (est.getAtivo()) {
				switch (est.getEstadoDoacao()) {
				case DISPONIBILIZADO:
					publisher.publishEvent(new DoacaoNotificationEvent(getNotificaveis(donativo), donativo,
							"Novo donativo disponibilizado"));
					break;
				case CANCELADO:
					publisher.publishEvent(
							new DoacaoNotificationEvent(getNotificaveis(donativo), donativo, "Doação foi cancelada"));
					break;
				default:
					break;
				}
			}
		});
	}

	/**
	 * 
	 * <p>
	 * Obtém lista de doadores que serão notificados.
	 * </p>
	 * 
	 * @param donativo
	 * @return
	 */
	private List<String> getNotificaveis(Donativo donativo) {
		List<String> notificaveis = new ArrayList<>();

		// TODO na sprint de coleta

		return notificaveis;
	}
}
