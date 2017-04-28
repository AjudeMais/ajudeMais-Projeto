package br.edu.ifpb.ajudeMais.service.negocio.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.ajudeMais.data.repository.InstituicaoCaridadeRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.exceptions.UniqueConstraintAlreadyException;
import br.edu.ifpb.ajudeMais.service.negocio.ContaService;
import br.edu.ifpb.ajudeMais.service.negocio.InstituicaoCaridadeService;

/**
 * 
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@Service
public class InstituicaoCaridadeServiceImpl implements InstituicaoCaridadeService {

	@Autowired
	private InstituicaoCaridadeRepository instituicaoRespository;

	@Autowired
	private ContaService contaService;

	/**
	 * 
	 */
	@Transactional
	@Override
	public InstituicaoCaridade save(InstituicaoCaridade entity) throws AjudeMaisException {

		Optional<InstituicaoCaridade> instituicaoOptional = instituicaoRespository
				.findOneByDocumento(entity.getDocumento());

		if (instituicaoOptional.isPresent()) {
			throw new UniqueConstraintAlreadyException("CPF/CNPJ já esta sendo usado");
		}

		Conta conta = contaService.save(entity.getConta());
		entity.setConta(conta);

		return instituicaoRespository.save(entity);
	}

	/**
	 * @throws AjudeMaisException 
	 * 
	 */
	@Transactional
	@Override
	public InstituicaoCaridade update(InstituicaoCaridade entity) throws AjudeMaisException {
		Conta conta = contaService.update(entity.getConta());
		entity.setConta(conta);
		return instituicaoRespository.save(entity);
	}

	/**
	 * 
	 */
	@Override
	public List<InstituicaoCaridade> findAll() {
		return instituicaoRespository.findAll();
	}

	/**
	 * 
	 */
	@Override
	public InstituicaoCaridade findById(Long id) {
		return instituicaoRespository.findOne(id);
	}

	/**
	 * 
	 */
	@Transactional
	@Override
	public void remover(InstituicaoCaridade entity) {
		instituicaoRespository.delete(entity);
	}

	/**
	 * 
	 */
	@Override
	public List<InstituicaoCaridade> filtersInstituicaoCaridadeClose(String localidade, String uf) {
		return instituicaoRespository.filtersInstituicaoCaridadeClose(localidade, uf);
	}

}
