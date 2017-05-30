package br.edu.ifpb.ajudeMais.service.negocio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.edu.ifpb.ajudeMais.data.repository.DonativoRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Donativo;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.DoadorService;
import br.edu.ifpb.ajudeMais.service.negocio.DonativoService;

/**
 * 
 * 
 * <p>
 * <b> {@link DonativoServiceImpl} </b>
 * </p>
 *
 * <p>
 *	Classe que implementa os serviços definidos em {@link DoadorService}
 * </p>
 * 
 * @author <a href="https://github.com/amslv">Ana Silva</a>
 *
 */
public class DonativoServiceImpl implements DonativoService {

	@Autowired
	private DonativoRepository donativoRepository;
	
	@Override
	public Donativo save(Donativo donativo) throws AjudeMaisException {
		return donativoRepository.save(donativo);
	}

	@Override
	public Donativo update(Donativo donativo) throws AjudeMaisException {
		return donativoRepository.save(donativo);
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

}
