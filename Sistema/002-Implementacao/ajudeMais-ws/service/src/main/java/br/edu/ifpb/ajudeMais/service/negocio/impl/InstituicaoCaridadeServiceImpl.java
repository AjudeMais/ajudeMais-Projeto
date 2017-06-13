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
import org.springframework.stereotype.Service;

import br.edu.ifpb.ajudeMais.data.repository.InstituicaoCaridadeRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.domain.entity.Endereco;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.exceptions.UniqueConstraintAlreadyException;
import br.edu.ifpb.ajudeMais.service.maps.dto.LatLng;
import br.edu.ifpb.ajudeMais.service.maps.impl.GoogleMapsServiceImpl;
import br.edu.ifpb.ajudeMais.service.negocio.ContaService;
import br.edu.ifpb.ajudeMais.service.negocio.InstituicaoCaridadeService;

/**
 * 
 * <p>
 * {@link InstituicaoCaridadeServiceImpl}
 * </p>
 * 
 * <p>
 * Classe utilizada para implementação de serviços definidos em
 * {@link InstituicaoCaridadeService}
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@Service
public class InstituicaoCaridadeServiceImpl implements InstituicaoCaridadeService {

	/**
	 * 
	 */
	@Autowired
	private InstituicaoCaridadeRepository instituicaoRespository;

	/**
	 * 
	 */
	@Autowired
	private ContaService contaService;

	/**
	 * 
	 */
	@Autowired
	private GoogleMapsServiceImpl googleMapsResponse;

	/**
	 * 
	 * salva uma instituição de caridade no BD
	 * 
	 * @param entity
	 *            entidade a ser salva
	 * 
	 * @return instituição salva
	 * 
	 * 
	 * @throws AjudeMaisException
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
	 * 
	 * atualiza uma instituição de caridade previamente cadastrada no BD
	 * 
	 * @param entity
	 *            entidade a ser atualizada
	 * 
	 * @return instituição atualizada
	 * 
	 * 
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
	 * lista todas as instituições de caridade salvas no BD
	 * 
	 * @return lista de instituições
	 * 
	 */
	@Override
	public List<InstituicaoCaridade> findAll() {
		return instituicaoRespository.findAll();
	}

	/**
	 * busca e retorna uma instituição com base no id
	 * 
	 * @param id
	 *            id a ser buscada no BD
	 * 
	 * @return instituição de caridade, caso exista
	 * 
	 */
	@Override
	public InstituicaoCaridade findById(Long id) {
		return instituicaoRespository.findOne(id);
	}

	/**
	 * 
	 * remove uma instituição de caridade previamente cadastrada
	 * 
	 * @param entity
	 *            instituição a ser removida
	 * 
	 */
	@Transactional
	@Override
	public void remover(InstituicaoCaridade entity) {
		instituicaoRespository.delete(entity);
	}

	/**
	 * busca e retorna as instituições de caridade situadas aquele endereco
	 * 
	 * @param endereco
	 *            endereco pesquisado
	 * 
	 * @return lista de instituicoes situadas
	 */
	@Override
	public List<InstituicaoCaridade> filtersInstituicoesForAddress(Endereco endereco) {
		return instituicaoRespository.filtersInstituicaoCaridadeClose(endereco.getLocalidade(), endereco.getUf());

	}

	/**
	 * 
	 * busca e retorna instituicoes que estao situadas naquele ponto especifico
	 * 
	 * @param latLng
	 *            latitude e longitude daquele ponto especifico no mapa
	 * 
	 * @return lista de instituicoes
	 * @throws AjudeMaisException
	 * 
	 */
	@Override
	public List<InstituicaoCaridade> filtersInstituicaoCloseForLatAndLng(LatLng latLng) throws AjudeMaisException {

		Endereco endereco;

		endereco = googleMapsResponse.converteLatitudeAndLongitudeInAddress(latLng.getLatitude(),
				latLng.getLongitude());

		return instituicaoRespository.filtersInstituicaoCaridadeClose(endereco.getLocalidade(), endereco.getUf());

	}

	/**
	 * Lista instituições por ativas ou não.
	 */
	@Override
	public List<InstituicaoCaridade> findByContaAtivo(boolean ativo) {

		return instituicaoRespository.findByContaAtivo(ativo);
	}

	/**
	 * 
	 */
	@Override
	public Optional<InstituicaoCaridade> findOneByConta(Conta conta) {
		return instituicaoRespository.findOneByConta(conta);
	}

}
