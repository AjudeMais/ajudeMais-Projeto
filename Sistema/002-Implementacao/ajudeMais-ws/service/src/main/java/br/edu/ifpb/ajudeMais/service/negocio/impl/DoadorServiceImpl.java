/**
 * <p>
 * Ajude Mais - Módulo Web Service
 * </p>
 * 
 * <p>
 * Sistema para potencializar o processo de doação.
 * </p>
 * 
 * <a href="https://github.com/AjudeMais/AjudeMais">Ajude Mais</a>
 * <a href="https://franckaj.github.io">Franck Aragão"></a>
 * 
 * AJUDE MAIS - 2017®
 * 
 */
package br.edu.ifpb.ajudeMais.service.negocio.impl;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.edu.ifpb.ajudeMais.data.repository.DoadorRepository;
import br.edu.ifpb.ajudeMais.data.repository.ImagemRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.domain.entity.Doador;
import br.edu.ifpb.ajudeMais.service.event.doador.DoadorEditEvent;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.ContaService;
import br.edu.ifpb.ajudeMais.service.negocio.DoadorService;

/**
 * 
 * <p>
 * {@link DoadorServiceImpl}
 * </p>
 * 
 * <p>
 * Classe utilizada para implementação de serviços definidos em
 * {@link DoadorService}
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@Service
public class DoadorServiceImpl implements DoadorService {

	/**
	 * 
	 */
	@Autowired
	private DoadorRepository doadorRepository;

	
	/**
	 * 
	 */
	@Autowired
	private ContaService contaService;

	/**
	 * 
	 */
	@Autowired
	private ApplicationEventPublisher publisher;

	/**
	 * 
	 */
	@Autowired
	private ImagemRepository imagemRepository;

	/**
	 * 
	 * salva um doador no BD
	 * 
	 * @param doador
	 * 
	 * 
	 * @return
	 * 
	 * 
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
	 * 
	 * atualiza um doador previamente cadastrado
	 * 
	 * @throws AjudeMaisException
	 * 
	 * 
	 */
	@Override
	@Transactional
	public Doador update(Doador doador) throws AjudeMaisException {
		Conta conta = contaService.update(doador.getConta());
		doador.setConta(conta);
		String imagemAntiga = null;
		if (doador.getFoto() != null && doador.getFoto().getId() != null) {
			imagemAntiga = imagemRepository.findOne(doador.getFoto().getId()).getNome();
		}
		doador = doadorRepository.save(doador);
		publisher.publishEvent(new DoadorEditEvent(doador, imagemAntiga));
		return doador;

	}

	/**
	 * 
	 * busca e retorna todos os doadores
	 * 
	 */
	@Override
	public List<Doador> findAll() {
		return doadorRepository.findAll();
	}

	/**
	 * 
	 * busca e retorna um doador em especifico com base no id do mesmo
	 * 
	 */
	@Override
	public Doador findById(Long id) {
		return doadorRepository.findOne(id);
	}

	/**
	 * 
	 * remove um doador previamente cadastrado
	 * 
	 */
	@Override
	@Transactional
	public void remover(@NotNull Doador doador) {
		doadorRepository.delete(doador);
	}

	/**
	 * Busca doador por conta, filtrando por username.
	 */
	@Override
	public Doador findByContaUsername(String username) {
		return doadorRepository.findOneByContaUsername(username);
	}
}
