package br.edu.ifpb.ajudeMais.service.negocio.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.ajudeMais.data.repository.DonativoRepository;
import br.edu.ifpb.ajudeMais.data.repository.MensageiroAssociadoRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Donativo;
import br.edu.ifpb.ajudeMais.domain.entity.EstadoDoacao;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
import br.edu.ifpb.ajudeMais.domain.entity.Mensageiro;
import br.edu.ifpb.ajudeMais.domain.entity.MensageiroAssociado;
import br.edu.ifpb.ajudeMais.domain.enumerations.Estado;
import br.edu.ifpb.ajudeMais.domain.enumerations.JobName;
import br.edu.ifpb.ajudeMais.domain.enumerations.TriggerName;
import br.edu.ifpb.ajudeMais.service.event.donativo.DonativoEditEvent;
import br.edu.ifpb.ajudeMais.service.event.donativo.notification.newdonativo.DoacaoNotificationEvent;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.job.NotificationJob;
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
	@Autowired
	private MensageiroAssociadoRepository mensageiroAssociadoRepository;

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
	public List<Donativo> filterByDonativosCloserMensageiroId(Long idMensageiro) throws AjudeMaisException {
		
		List<Donativo> donativoFilted = new ArrayList<>();
		
		List<MensageiroAssociado> mensageiroAssociados = mensageiroAssociadoRepository.findByMensageiroId(idMensageiro);
		
		mensageiroAssociados.forEach(ma->{
			
			ma.getMensageiro().getEnderecos().forEach(e->{
				
				List<Donativo> donativos = donativoRepository.filterDonativoByLocal
						(e.getLocalidade(), e.getUf(), ma.getInstituicaoCaridade().getId(), Estado.DISPONIBILIZADO);

				donativos.removeAll(donativoFilted);
				donativoFilted.addAll(donativos);
				
			});

		});
		
		return donativoFilted;
	}

	/**
	 * Recupera donativos vinculado ao mensageiro passado
	 */
	@Override
	public List<Donativo> findByMensageiro(Mensageiro mensageiro) {
		List<Donativo> donativos = donativoRepository.findByMensageiroOrderByDataDesc(mensageiro);
		donativos.sort(Comparator.comparing(Donativo::getHorarioAceito));
		return donativos;
	}

	/**
	 * Fitra donativos pelo mensageiro e estado passado.
	 */
	@Override
	public List<Donativo> filterDonativoByMensageiroAndEstado(Mensageiro mensageiro, Estado estado) {
		List<Donativo> donativos = donativoRepository.filterDonativoByMensageiroAndEstado(mensageiro.getId(), estado);
		donativos.sort(Comparator.comparing(Donativo::getHorarioAceito));
		return donativos;
	}
	
	/**
	 * Verifica se donativo está válido para ser aceita para coleta.
	 * @return 
	 */
	@Override
	public boolean isValidColeta(Long id) {
		Donativo donativo = donativoRepository.findOne(id);
		
		for(EstadoDoacao e : donativo.getEstadosDaDoacao()){
			if(e.getAtivo() && (e.getEstadoDoacao().equals(Estado.DISPONIBILIZADO) && donativo.getMensageiro() == null)){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Verifica se Donativo está válido para ser recolhido.
	 * @return 
	 */
	@Override
	public boolean isValidRecolhimento(Long id) {
		Donativo donativo = donativoRepository.findOne(id);
		
		for(EstadoDoacao e : donativo.getEstadosDaDoacao()){
			if(e.getAtivo() && (e.getEstadoDoacao().equals(Estado.CANCELADO) || e.getEstadoDoacao().equals(Estado.NAO_ACEITO))){
				return false;
			}
		}
		
		return true;
	}
}
