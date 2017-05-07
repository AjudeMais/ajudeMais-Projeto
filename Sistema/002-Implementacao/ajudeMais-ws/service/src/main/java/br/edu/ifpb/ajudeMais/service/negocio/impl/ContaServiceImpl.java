package br.edu.ifpb.ajudeMais.service.negocio.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.ifpb.ajudeMais.data.repository.ContaRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.service.exceptions.UniqueConstraintAlreadyException;
import br.edu.ifpb.ajudeMais.service.negocio.AuthService;
import br.edu.ifpb.ajudeMais.service.negocio.ContaService;

/**
 * 
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@Service
public class ContaServiceImpl implements ContaService {

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthService authService;

	/**
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
	 * <p>
	 * </p>
	 * @param password
	 */
	public void changePassword(String password) {
		contaRepository.findOneByUsername(authService.getCurrentUserLogin()).ifPresent(user -> {
			String encryptedPassword = passwordEncoder.encode(password);
			user.setSenha(encryptedPassword);
			user.setResetSenha(new Date());
			contaRepository.save(user);
		});
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
