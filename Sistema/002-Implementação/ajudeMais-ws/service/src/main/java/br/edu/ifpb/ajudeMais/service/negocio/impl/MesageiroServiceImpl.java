
package br.edu.ifpb.ajudeMais.service.negocio.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.edu.ifpb.ajudeMais.data.repository.MensageiroRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.domain.entity.Doador;
import br.edu.ifpb.ajudeMais.domain.entity.Mensageiro;
import br.edu.ifpb.ajudeMais.service.exceptions.UniqueConstraintAlreadyException;
import br.edu.ifpb.ajudeMais.service.negocio.MensageiroService;

/**
 * 
 * <p>
 * <b> MesageiroServiceImpl</b>
 * </p>
 *
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public class MesageiroServiceImpl implements MensageiroService{
	
	@Autowired
	private MensageiroRepository mensageiroRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional
	public Mensageiro save(Mensageiro mensageiro) throws UniqueConstraintAlreadyException {

		Optional<Conta> contaEmail = mensageiroRepository.findOneByEmail(mensageiro.getConta().getEmail());
		Optional<Conta> contaUsername = mensageiroRepository.findOneByUsername(mensageiro.getConta().getUsername());

		if (contaEmail.isPresent()) {
			throw new UniqueConstraintAlreadyException("E-mail já está em uso");
		}
		if (contaUsername.isPresent()) {
			throw new UniqueConstraintAlreadyException("Nome de usuário já está em uso");
		}
		String senha = passwordEncoder.encode(mensageiro.getConta().getSenha());
		mensageiro.getConta().setSenha(senha);
		return mensageiroRepository.save(mensageiro);
	}

	/**
	 * 
	 */
	@Override
	@Transactional
	public Mensageiro update(Mensageiro mensageiro) {
		return mensageiroRepository.save(mensageiro);
	}

	/**
	 * 
	 */
	@Override
	public List<Mensageiro> findAll() {
		return mensageiroRepository.findAll();
	}

	@Override
	public Mensageiro findById(Long id) {
		return mensageiroRepository.findOne(id);
	}

	@Override
	@Transactional
	public void remover(Mensageiro mensageiro) {
		mensageiroRepository.delete(mensageiro);
	}

}
