
package br.edu.ifpb.ajudeMais.service.negocio.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import br.edu.ifpb.ajudeMais.data.repository.MensageiroRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.domain.entity.Mensageiro;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.ContaService;
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
	private ContaService contaService;

	/**
	 * 
	 */
	@Override
	@Transactional
	public Mensageiro save(Mensageiro mensageiro) throws AjudeMaisException {

		Conta conta = contaService.save(mensageiro.getConta());
		mensageiro.setConta(conta);
		
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

	/**
	 * 
	 */
	@Override
	public Mensageiro findById(Long id) {
		return mensageiroRepository.findOne(id);
	}

	/**
	 * 
	 */
	@Override
	@Transactional
	public void remover(Mensageiro mensageiro) {
		mensageiroRepository.delete(mensageiro);
	}

}
