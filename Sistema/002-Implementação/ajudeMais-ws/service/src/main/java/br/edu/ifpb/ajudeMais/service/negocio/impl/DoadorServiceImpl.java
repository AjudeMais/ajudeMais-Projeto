/**
 * 
 */
package br.edu.ifpb.ajudeMais.service.negocio.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	/**
	 * 
	 */
	@Override
	@Transactional
	public Doador criar(Doador doador) {
		String senha = passwordEncoder.encode(doador.getConta().getSenha());
		doador.getConta().setSenha(senha);
		return doadorRepository.save(doador);
	}

	/**
	 * 
	 */
	@Override
	@Transactional
	public Doador alterar(Doador doador) {
		return doadorRepository.save(doador);
	}

	/**
	 * 
	 */
	@Override
	public List<Doador> buscarTodos() {
		return doadorRepository.findAll();
	}

	@Override
	@Transactional
	public void remover(Doador doador) {
		doadorRepository.delete(doador);
	}

}
