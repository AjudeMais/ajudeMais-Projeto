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

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.ifpb.ajudeMais.data.repository.ContaRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.exceptions.InvalidAttributeException;
import br.edu.ifpb.ajudeMais.service.exceptions.UniqueConstraintAlreadyException;
import br.edu.ifpb.ajudeMais.service.negocio.AuthService;
import br.edu.ifpb.ajudeMais.service.negocio.ContaService;

/**
 * 
 * <p>
 * {@link ContaServiceImpl}
 * </p>
 * 
 * <p>
 * Classe utilizada para implementação dos serviços definidos em
 * {@link ContaService}
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@Service
public class ContaServiceImpl implements ContaService {

	/**
	 * 
	 */
	@Autowired
	private ContaRepository contaRepository;

	/**
	 * 
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * 
	 */
	@Autowired
	private AuthService authService;

	/**
	 * salva uma conta no BD
	 * 
	 * @throws UniqueConstraintAlreadyException
	 * 
	 */
	@Override
	public Conta save(Conta entity) throws UniqueConstraintAlreadyException {

		Optional<Conta> contaEmail = contaRepository.findOneByEmail(entity.getEmail());
		Optional<Conta> contaUsername = contaRepository.findOneByUsername(entity.getUsername());

		if (contaEmail.isPresent()) {
			throw new UniqueConstraintAlreadyException("E-mail já está em uso");
		}
		if (contaUsername.isPresent()) {
			throw new UniqueConstraintAlreadyException("Nome de usuário já está em uso");
		}

		String senha = passwordEncoder.encode(entity.getSenha());
		entity.setSenha(senha);
		return contaRepository.save(entity);
	}

	/**
	 * atualiza uma conta existente
	 * 
	 * @throws UniqueConstraintAlreadyException
	 * 
	 */
	@Override
	public Conta update(Conta entity) throws UniqueConstraintAlreadyException {

		Optional<Conta> contaEmail = contaRepository.findOneByEmail(entity.getEmail());
		Optional<Conta> contaUsername = contaRepository.findOneByUsername(entity.getUsername());

		if (contaEmail.isPresent()) {
			if (!entity.getId().equals(contaEmail.get().getId())) {
				throw new UniqueConstraintAlreadyException("E-mail já está em uso");
			}
		}
		if (contaUsername.isPresent()) {
			if (!entity.getId().equals(contaUsername.get().getId())) {
				throw new UniqueConstraintAlreadyException("Nome de usuário já está em uso");
			}
		}

		return contaRepository.save(entity);
	}

	/**
	 * 
	 * 
	 * muda a senha de uma conta
	 * 
	 * 
	 * <p>
	 * </p>
	 * 
	 * @param password
	 */
	public void changePassword(String password, String newPassword) throws AjudeMaisException{
		Optional<Conta> contaOption = contaRepository.findOneByUsername(authService.getCurrentUserLogin());
		
		if (contaOption.isPresent()){
			Conta conta = contaOption.get();
			if(!passwordEncoder.matches(password,conta.getSenha())){
				throw new InvalidAttributeException("A senha atual informada é inválida");
			}
			String encryptedPassword = passwordEncoder.encode(newPassword);
			conta.setSenha(encryptedPassword);
			conta.setResetSenha(new Date());
			contaRepository.save(conta);
		}
	}
	
	@Override
	public void changePasswordInit(String password) {
		contaRepository.findOneByUsername(authService.getCurrentUserLogin()).ifPresent(user -> {
			String encryptedPassword = passwordEncoder.encode(password);
			user.setSenha(encryptedPassword);
			user.setResetSenha(new Date());
			contaRepository.save(user);
		});
	}

	/**
	 * 
	 * busca e retorna todas as contas
	 * 
	 */
	@Override
	public List<Conta> findAll() {
		return contaRepository.findAll();
	}

	/**
	 * 
	 * busca e retorna uma conta especifica com base no id
	 * 
	 */
	@Override
	public Conta findById(Long id) {
		return contaRepository.findOne(id);
	}

	/**
	 * 
	 * retorna uma conta previamente cadastrada
	 * 
	 */
	@Override
	public void remover(Conta entity) {
		contaRepository.delete(entity);
	}
}
