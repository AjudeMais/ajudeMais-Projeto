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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.ajudeMais.data.repository.DonativoRepository;
import br.edu.ifpb.ajudeMais.data.repository.MensageiroAssociadoRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.domain.entity.Endereco;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
import br.edu.ifpb.ajudeMais.domain.entity.Mensageiro;
import br.edu.ifpb.ajudeMais.domain.entity.MensageiroAssociado;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.exceptions.UniqueConstraintAlreadyException;
import br.edu.ifpb.ajudeMais.service.maps.GoogleMapsService;
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
	@Autowired
	private GoogleMapsService googleMapsService;

	/**
	 * 
	 */
	@Autowired
	private DonativoRepository donativoRepository;

	/**
	 * Salva um mensageiro considerando restrições de email.
	 */
	@Override
	public MensageiroAssociado save(MensageiroAssociado entity) throws AjudeMaisException {
		Optional<MensageiroAssociado> mensageirosAssOp = mensageiroAssociadoRepository
				.findByMensageiroAndInstituicaoCaridade(entity.getMensageiro(), entity.getInstituicaoCaridade());
		if (mensageirosAssOp.isPresent()) {
			throw new UniqueConstraintAlreadyException("Este mensageiro já esta associado a esta instituição.");
		}
		return mensageiroAssociadoRepository.save(entity);
	}

	/**
	 * Atualiza um mensageiro existente
	 */
	@Override
	public MensageiroAssociado update(MensageiroAssociado entity) throws AjudeMaisException {
		return mensageiroAssociadoRepository.save(entity);
	}

	/**
	 * Busca todos os mensageiros.
	 */
	@Override
	public List<MensageiroAssociado> findAll() {
		return mensageiroAssociadoRepository.findAll();
	}

	/**
	 * Busca um mesageiro, filtrando por ID.
	 */
	@Override
	public MensageiroAssociado findById(Long id) {
		return mensageiroAssociadoRepository.findOne(id);
	}

	/**
	 * Remove um mensageiro associado. Não deve ser disponibilizado como
	 * recurso.
	 */
	@Override
	public void remover(MensageiroAssociado entity) {
		mensageiroAssociadoRepository.delete(entity);
	}

	/**
	 * Busca insituição mensageiro associado por uma conta. Conta da insituição.
	 */
	@Override
	public List<MensageiroAssociado> findByInstituicaoConta(Conta conta) {
		return mensageiroAssociadoRepository.findByInstituicaoCaridadeConta(conta);
	}

	/**
	 * 
	 * <p>
	 * Busca mensageiro Mais proximo, considerando mensageiros do bairro com
	 * base no enderenco passado e no id da instituição insituição.
	 * </p>
	 * 
	 * @param conta
	 * @return List<Mensageiro>
	 * @throws Exception
	 */
	@Override
	public List<Mensageiro> filterMensageirosCloserToBairro(Endereco endereco, Long idInstituicao)
			throws AjudeMaisException {
		List<Object[]> selectedMensageiros = mensageiroAssociadoRepository.filterMensageirosCloserToBairro(
				endereco.getBairro(), endereco.getLocalidade(), endereco.getUf(), idInstituicao);

		List<Mensageiro> mensageiros = googleMapsService
				.validateAddressMensageiros(setEnderecoInList(selectedMensageiros));

		return mensageiros;
	}

	/**
	 * 
	 */
	@Override
	public List<Mensageiro> filterMensageirosCloserToCidade(Endereco endereco, Long idInstituicao)
			throws AjudeMaisException {

		List<Object[]> selectedMensageiros = mensageiroAssociadoRepository
				.filterMensageirosCloserToCidade(endereco.getLocalidade(), endereco.getUf(), idInstituicao);

		List<Mensageiro> mensageiros = googleMapsService
				.validateAddressMensageiros(setEnderecoInList(selectedMensageiros));

		return mensageiros;
	}

	/**
	 * 
	 */
	@Override
	public LinkedHashMap<MensageiroAssociado, Integer> getRanking(InstituicaoCaridade instituicaoCaridade) {

		Map<MensageiroAssociado, Integer> mensageirosDoacoes = new LinkedHashMap<MensageiroAssociado, Integer>();
		List<MensageiroAssociado> mensageiros = new ArrayList<>();

		if (instituicaoCaridade != null) {
			mensageiros = mensageiroAssociadoRepository.findByInstituicaoCaridade(instituicaoCaridade);
		} else {
			mensageiros = mensageiroAssociadoRepository.findAll();
		}

		mensageiros.forEach(m -> {
			Long count = donativoRepository.filterCountByMensageiroAndEstado(m.getMensageiro());
			mensageirosDoacoes.put(m, count.intValue());
		});

		LinkedHashMap<MensageiroAssociado, Integer> mapSorted = mensageirosDoacoes.entrySet().stream()
				.sorted(Map.Entry.<MensageiroAssociado, Integer> comparingByValue().reversed()).limit(10)
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

		return mapSorted;
	}

	/**
	 * <p>
	 * Busca endereço com id recupera na consulta e substitui o id pelo objeto
	 * endereço
	 * </p>
	 * 
	 * @param filters
	 * @return
	 */
	private List<Object[]> setEnderecoInList(List<Object[]> filters) {

		for (int i = 0; i < filters.size(); i++) {

			List<Endereco> enderecos = ((Mensageiro) filters.get(i)[0]).getEnderecos();
			for (Endereco e : enderecos) {
				if (e.getId().equals(filters.get(i)[1])) {
					filters.get(i)[1] = e;
				}
			}
		}

		return filters;
	}

}
