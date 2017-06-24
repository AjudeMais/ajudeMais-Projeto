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
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.edu.ifpb.ajudeMais.data.repository.ImagemRepository;
import br.edu.ifpb.ajudeMais.data.repository.MensageiroRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.domain.entity.Mensageiro;
import br.edu.ifpb.ajudeMais.service.event.mensageiro.MensageiroEditEvent;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.exceptions.UniqueConstraintAlreadyException;
import br.edu.ifpb.ajudeMais.service.negocio.ContaService;
import br.edu.ifpb.ajudeMais.service.negocio.MensageiroService;

/**
 * 
 * <p>
 * <b> MesageiroServiceImpl</b>
 * </p>
 * 
 * Classe utilizada para implementação de serviços definidos em
 * {@link MensageiroService}
 *
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
@Service
public class MesageiroServiceImpl implements MensageiroService {

	/**
	 * 
	 */
	@Autowired
	private MensageiroRepository mensageiroRepository;

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
	 * salva um mensageiro no BD
	 * 
	 * @param mensageiro
	 *            a ser salvo
	 * 
	 * 
	 * @return retorna o mensageiro salvo
	 */
	@Override
	@Transactional
	public Mensageiro save(Mensageiro mensageiro) throws AjudeMaisException {
		
		Optional<Mensageiro> mensageiroOp = mensageiroRepository
				.findOneByCpf(mensageiro.getCpf());
		
		if (mensageiroOp.isPresent()) {
			throw new UniqueConstraintAlreadyException("CPF já esta sendo usado");
		}
		
		Conta conta = contaService.save(mensageiro.getConta());
		mensageiro.setConta(conta);

		return mensageiroRepository.save(mensageiro);
	}

	/**
	 * 
	 * atualiza um mensageiro previamente cadastrado
	 * 
	 * @param mensageiro
	 *            mensageiro a ser atualizado no bd
	 * 
	 * @return mensageiro atualizado
	 * @throws AjudeMaisException 
	 * 
	 */
	@Override
	@Transactional
	public Mensageiro update(Mensageiro mensageiro) throws AjudeMaisException {
		Conta conta = contaService.update(mensageiro.getConta());
		mensageiro.setConta(conta);
		
		String imagemAntiga = null;
		if (mensageiro.getFoto() != null && mensageiro.getFoto().getId() != null) {
			imagemAntiga = imagemRepository.findOne(mensageiro.getFoto().getId()).getNome();
		}
		Mensageiro mensageiroUpdated = mensageiroRepository.save(mensageiro);
		publisher.publishEvent(new MensageiroEditEvent(mensageiroUpdated, imagemAntiga));

		return mensageiroUpdated;

	}

	/**
	 * 
	 * busca e retorna todos os mensageiros cadastrados
	 * 
	 * 
	 * @return lista com todos os mensageiros
	 * 
	 */
	@Override
	public List<Mensageiro> findAll() {
		return mensageiroRepository.findAll();
	}

	/**
	 * busca e retorna um mensageiro específico
	 * 
	 * @param id
	 *            id do mensageiro
	 * 
	 * @return o mensageiro, caso tenha sido encontrado no BD
	 */
	@Override
	public Mensageiro findById(Long id) {
		return mensageiroRepository.findOne(id);
	}

	/**
	 * busca e retorna os mensageiros mais próximos daquele local
	 * 
	 * @param logradouro
	 *            nome do logradouro
	 * 
	 * @param bairro
	 *            nome do bairro
	 * 
	 * @param localidade
	 *            nome da localidade
	 * 
	 * @param uf
	 *            sigla do estado
	 * 
	 * @return lista com os mensageiros mais próximos do local
	 * 
	 */
	public List<Object[]> filtersMensageiroCloser(String logradouro, String bairro, String localidade, String uf) {
		return null;

	}

	/**
	 * remove um mensageiro previamente cadastrado
	 * 
	 * @param mensageiro
	 *            mensageiro a ser removido
	 * 
	 */
	@Override
	@Transactional
	public void remover(Mensageiro mensageiro) {
		mensageiroRepository.delete(mensageiro);
	}

	/**
	 * Busca um mensageiro por conta, filtrando pelo e-mail.
	 */
	@Override
	public List<Mensageiro> findByContaEmail(String email) {
		return mensageiroRepository.findByContaEmailIgnoreCaseContaining(email);
	}

	/**
	 * Busca um mensageiro pelo username da conta.
	 */
	@Override
	public Mensageiro findByContaUsername(String username) {
		return mensageiroRepository.findOneByContaUsername(username);
	}

}
