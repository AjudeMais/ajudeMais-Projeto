/**
 * 
 */
package br.edu.ifpb.ajudeMais.service.negocio.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.ajudeMais.data.repository.DoadorRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.domain.entity.Doador;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.ContaService;
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

	@Autowired
	private ContaService contaService;

	/**
	 * 
	 * @param doador
	 * @return
	 * @throws AjudeMaisException
	 */
	@Override
	@Transactional
	public Doador save(Doador doador) throws AjudeMaisException {
		Conta conta = contaService.save(doador.getConta());
		doador.setConta(conta);
		
		return doadorRepository.save(doador);
	}

	/**
	 * 
	 */
	@Override
	@Transactional
	public Doador update(Doador doador) {
		return doadorRepository.save(doador);
	}

	/**
	 * 
	 */
	@Override
	public List<Doador> findAll() {
		return doadorRepository.findAll();
	}

	@Override
	public Doador findById(Long id) {
		return doadorRepository.findOne(id);
	}

	@Override
	@Transactional
	public void remover(Doador doador) {
		doadorRepository.delete(doador);
	}

}
