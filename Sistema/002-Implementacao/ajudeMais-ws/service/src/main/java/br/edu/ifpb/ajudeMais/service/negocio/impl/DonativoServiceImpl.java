package br.edu.ifpb.ajudeMais.service.negocio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.ajudeMais.data.repository.DonativoRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Donativo;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.DonativoService;

/**
 * 
 * 
 * <p>
 * <b> DonativoServiceImpl.java </b>
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
	
	@Override
	public Donativo save(Donativo entity) throws AjudeMaisException {
		return donativoRepository.save(entity);
	}

	@Override
	public Donativo update(Donativo entity) throws AjudeMaisException {
		return null;
	}

	@Override
	public List<Donativo> findAll() {
		return donativoRepository.findAll();
	}

	@Override
	public Donativo findById(Long id) {
		return donativoRepository.findOne(id);
	}

	@Override
	public void remover(Donativo entity) {
		donativoRepository.delete(entity);
	}

	@Override
	public List<Donativo> findByNome(String nome) {
		return donativoRepository.findByNome(nome);
	}

	@Override
	public List<Donativo> findByDoadorNome(String nomeDoador) {
		return donativoRepository.findByDoadorNome(nomeDoador);
	}

	@Override
	public List<Donativo> findByCategoriaInstituicaoCaridade(InstituicaoCaridade instituicao) {
		return donativoRepository.findByCategoriaInstituicaoCaridade(instituicao);
	}

}
