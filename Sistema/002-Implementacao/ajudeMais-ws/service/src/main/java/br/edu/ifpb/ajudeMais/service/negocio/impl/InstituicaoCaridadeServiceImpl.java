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

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.maps.errors.ApiException;

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

	@Autowired
	private InstituicaoCaridadeRepository instituicaoRespository;

	@Autowired
	private ContaService contaService;

	@Autowired
	private GoogleMapsServiceImpl googleMapsResponse;

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
	public List<InstituicaoCaridade> filtersInstituicoesForAddress(Endereco endereco) {
		return instituicaoRespository.filtersInstituicaoCaridadeClose(endereco.getLocalidade(), endereco.getUf());

	}


	/**
	 * 
	 */
	@Override
	public List<InstituicaoCaridade> filtersInstituicaoCloseForLatAndLng(LatLng latLng) {

		Endereco endereco = null;

		try {
			endereco = googleMapsResponse.converteLatitudeAndLongitudeInAddress(latLng.getLatitude(),
					latLng.getLongitude());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ApiException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return instituicaoRespository.filtersInstituicaoCaridadeClose(endereco.getLocalidade(), endereco.getUf());

	}

}
