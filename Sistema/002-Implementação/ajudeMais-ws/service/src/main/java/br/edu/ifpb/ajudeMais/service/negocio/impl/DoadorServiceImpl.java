/**
 * 
 */
package br.edu.ifpb.ajudeMais.service.negocio.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.ajudeMais.data.dao.DoadorRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Doador;
import br.edu.ifpb.ajudeMais.service.negocio.DoadorService;

/**
 * 
 * <p>
 * <b> DoadorServiceImpl </b>
 * </p>
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
@Service
public class DoadorServiceImpl implements DoadorService {

	@Autowired
	private DoadorRepository doadorRepository;

	/**
	 * 
	 */
	@Override
	@Transactional
	public Doador criar(Doador paciente) {
		return doadorRepository.save(paciente);
	}

	/**
	 * 
	 */
	@Override
	@Transactional
	public Doador alterar(Doador paciente) {
		return doadorRepository.save(paciente);
	}

	/**
	 * 
	 */
	@Override
	public List<Doador> buscarTodos() {
		return doadorRepository.findAll();
	}

}
