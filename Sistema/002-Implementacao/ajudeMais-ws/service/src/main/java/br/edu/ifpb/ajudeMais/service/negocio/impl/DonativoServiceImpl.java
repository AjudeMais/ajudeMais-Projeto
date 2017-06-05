package br.edu.ifpb.ajudeMais.service.negocio.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.edu.ifpb.ajudeMais.data.repository.DonativoRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Donativo;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
import br.edu.ifpb.ajudeMais.service.event.donativo.DonativoEditEvent;
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
		return donativoRepository.findAll();
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
		return donativoRepository.findByDoadorId(idDoador);
	}

	/**
	 * 
	 */
	@Override
	public List<Donativo> findByCategoriaInstituicaoCaridade(InstituicaoCaridade instituicao) {
		return donativoRepository.findByCategoriaInstituicaoCaridade(instituicao);
	}
}
