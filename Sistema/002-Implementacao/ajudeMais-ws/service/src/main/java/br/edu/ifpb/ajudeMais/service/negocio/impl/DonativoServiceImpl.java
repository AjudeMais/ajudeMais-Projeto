package br.edu.ifpb.ajudeMais.service.negocio.impl;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.ajudeMais.data.repository.DonativoRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Donativo;
import br.edu.ifpb.ajudeMais.domain.entity.Endereco;
import br.edu.ifpb.ajudeMais.domain.entity.EstadoDoacao;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
import br.edu.ifpb.ajudeMais.domain.entity.Mensageiro;
import br.edu.ifpb.ajudeMais.domain.enumerations.Estado;
import br.edu.ifpb.ajudeMais.domain.enumerations.JobName;
import br.edu.ifpb.ajudeMais.domain.enumerations.TriggerName;
import br.edu.ifpb.ajudeMais.service.event.donativo.DonativoEditEvent;
import br.edu.ifpb.ajudeMais.service.event.donativo.notification.newdonativo.DoacaoNotificationEvent;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.job.NotificationJob;
import br.edu.ifpb.ajudeMais.service.maps.dto.LatLng;
import br.edu.ifpb.ajudeMais.service.maps.impl.GoogleMapsServiceImpl;
import br.edu.ifpb.ajudeMais.service.negocio.DonativoService;
import br.edu.ifpb.ajudeMais.service.negocio.EstadoDoacaoService;
import br.edu.ifpb.ajudeMais.service.util.DonativoColetaUtil;
import br.edu.ifpb.ajudeMais.service.util.NotificationUtil;
import br.edu.ifpb.ajudeMais.service.util.SchedulerJobUtil;

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
 * @author <a href="https://github.com/amslv">Ana Silva</a></br>
 *         <a href="https://github.com/FranckAJ">Franck Aragão</a>
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
	private ApplicationEventPublisher publisher;

	/**
	 * 
	 */
	@Autowired
	private GoogleMapsServiceImpl googleMapsResponse;

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
	@Autowired
	private NotificationUtil notificationUtil;

	/**
	 * 
	 */
	@Autowired
	private EstadoDoacaoService estadoDoacaoService;

	/**
	 * 
	 */
	@Transactional
	@Override
	public Donativo save(Donativo entity) throws AjudeMaisException {

		Donativo donativoSaved = donativoRepository.save(entity);
		publisher.publishEvent(new DonativoEditEvent(donativoSaved));

		List<String> notificaveis = coletaUtil.getNotificaveisToBairro(donativoSaved);

		if (notificaveis != null && !notificaveis.isEmpty()) {
			publisher.publishEvent(
					new DoacaoNotificationEvent(notificaveis, donativoSaved, "Novo donativo dispobilizado para coleta"));

		}
		schedulerJobUtil.createJob(JobName.NOTIFICATION, TriggerName.NOTIFICATION, donativoSaved.getId(),
				NotificationJob.class);

		return donativoSaved;
	}

	/**
	 * 
	 */
	@Transactional
	@Override
	public Donativo update(Donativo entity) throws AjudeMaisException {
		Donativo donativoUpdated = donativoRepository.save(entity);

		EstadoDoacao estadoDoacao = notificationUtil.notifyDonativo(entity);
		estadoDoacaoService.update(estadoDoacao);

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
	 * <p>
	 * Busca donativos com estado passado e id da instituicao passada
	 * </p>
	 * 
	 * @return lista de donativos
	 */
	@Override
	public List<Donativo> filterDonativoByEstadoAndInstituicao(Long idInstitucao, Estado estado) {
		return donativoRepository.filterDonativoByEstadoAndInstituicao(idInstitucao, estado);
	}

	/**
	 * <p>
	 * Busca donativos com base na localização
	 * </p>
	 * 
	 * @return lista de donativos
	 */
	@Override
	public List<Donativo> filterByDoadorLocal(LatLng latLng) throws AjudeMaisException {
		Endereco endereco = googleMapsResponse.converteLatitudeAndLongitudeInAddress(latLng.getLatitude(),
				latLng.getLongitude());
		List<Donativo> donativos = donativoRepository.filterDonativoByLocal(endereco.getLocalidade(), endereco.getUf());
		return donativos;
	}

	/**
	 * 
	 */
	@Override
	public List<Donativo> findByMensageiro(Mensageiro mensageiro) {
		List<Donativo> donativos = donativoRepository.findByMensageiroOrderByDataDesc(mensageiro);
		donativos.sort(Comparator.comparing(Donativo::getHorarioAceito));
		return donativos;
	}

	/**
	 * 
	 */
	@Override
	public List<Donativo> filterDonativoByMensageiroAndEstado(Mensageiro mensageiro, Estado estado) {
		List<Donativo> donativos = donativoRepository.filterDonativoByMensageiroAndEstado(mensageiro.getId(), estado);
		donativos.sort(Comparator.comparing(Donativo::getHorarioAceito));
		return donativos;
	}
}
