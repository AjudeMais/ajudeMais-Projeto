package br.edu.ifpb.ajudeMais.service.negocio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.ifpb.ajudeMais.data.repository.ContaRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.service.negocio.ContaService;

/**
 * 
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@Service
public class ContaServiceImpl implements ContaService{

	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * 
	 */
	@Override
	public Conta save(Conta entity) {
		String senha = passwordEncoder.encode(entity.getSenha());
		entity.setSenha(senha);
		return contaRepository.save(entity);
	}

	/**
	 * 
	 */
	@Override
	public Conta update(Conta entity) {
		return contaRepository.save(entity);
	}

	/**
	 * 
	 */
	@Override
	public List<Conta> findAll() {
		return contaRepository.findAll();
	}

	/**
	 * 
	 */
	@Override
	public Conta findById(Long id) {
		return contaRepository.findOne(id);
	}

	/**
	 * 
	 */
	@Override
	public void remover(Conta entity) {
		contaRepository.delete(entity);
	}

}
