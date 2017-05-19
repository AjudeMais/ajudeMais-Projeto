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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.ajudeMais.data.repository.MensageiroAssociadoRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.domain.entity.MensageiroAssociado;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.exceptions.UniqueConstraintAlreadyException;
import br.edu.ifpb.ajudeMais.service.negocio.MensageiroAssociadoService;

/**
 * 
 * <p>
 * {@link MensageiroAssociadoServiceImpl}
 * </p>
 * 
 * <p>
 * Classe utilizada para implementação das operações definidas em
 * {@link MensageiroAssociadoService}.
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@Service
public class MensageiroAssociadoServiceImpl implements MensageiroAssociadoService {

	/**
	 * 
	 */
	@Autowired
	private MensageiroAssociadoRepository mensageiroAssociadoRepository;

	/**
	 * 
	 */
	@Override
	public MensageiroAssociado save(MensageiroAssociado entity) throws AjudeMaisException {
		Optional<MensageiroAssociado> mensageirosAssOp = mensageiroAssociadoRepository
				.findByMensageiroId(entity.getMensageiro().getId());
		if (mensageirosAssOp.isPresent()) {
			throw new UniqueConstraintAlreadyException("Este mensageiro já esta associado a esta insituição.");
		}
		;
		return mensageiroAssociadoRepository.save(entity);
	}

	/**
	 * 
	 */
	@Override
	public MensageiroAssociado update(MensageiroAssociado entity) throws AjudeMaisException {
		return mensageiroAssociadoRepository.save(entity);
	}

	/**
	 * 
	 */
	@Override
	public List<MensageiroAssociado> findAll() {
		return mensageiroAssociadoRepository.findAll();
	}

	/**
	 * 
	 */
	@Override
	public MensageiroAssociado findById(Long id) {
		return mensageiroAssociadoRepository.findOne(id);
	}

	/**
	 * 
	 */
	@Override
	public void remover(MensageiroAssociado entity) {
		mensageiroAssociadoRepository.delete(entity);
	}

	/**
	 * 
	 */
	@Override
	public List<MensageiroAssociado> findByInstituicaoConta(Conta conta) {
		return mensageiroAssociadoRepository.findByInstituicaoCaridadeConta(conta);
	}

}
