package br.edu.ifpb.ajudeMais.service.negocio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.ajudeMais.data.repository.EstadoDoacaoRepository;
import br.edu.ifpb.ajudeMais.domain.entity.EstadoDoacao;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.EstadoDoacaoService;

/**
 * 
 * <p>
 * {@link EstadoDoacaoServiceImpl}
 * </p>
 * 
 * <p>
 * Classe utilizada para implementação de serviços definidos em
 * {@link EstadoDoacaoService}.
 * </p>
 *
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@Service
public class EstadoDoacaoServiceImpl implements EstadoDoacaoService {

	/**
	 * 
	 */
	@Autowired
	private EstadoDoacaoRepository estadoDoacaoRepository;

	/**
	 * 
	 */
	@Transactional
	@Override
	public EstadoDoacao save(EstadoDoacao entity) throws AjudeMaisException {
		return estadoDoacaoRepository.save(entity);
	}

	/**
	 * 
	 */
	@Transactional
	@Override
	public EstadoDoacao update(EstadoDoacao entity) throws AjudeMaisException {
		return estadoDoacaoRepository.save(entity);
	}

	/**
	 * 
	 */
	@Override
	public List<EstadoDoacao> findAll() {
		return estadoDoacaoRepository.findAll();
	}

	/**
	 * 
	 */
	@Override
	public EstadoDoacao findById(Long id) {
		return estadoDoacaoRepository.findOne(id);
	}

	/**
	 * 
	 */
	@Transactional
	@Override
	public void remover(EstadoDoacao entity) throws AjudeMaisException {
		estadoDoacaoRepository.delete(entity);
	}
}
