package br.edu.ifpb.ajudeMais.service.negocio.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.edu.ifpb.ajudeMais.data.repository.CampanhaRepository;
import br.edu.ifpb.ajudeMais.data.repository.DoadorRepository;
import br.edu.ifpb.ajudeMais.data.repository.DonativoCampanhaRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Campanha;
import br.edu.ifpb.ajudeMais.domain.entity.Doador;
import br.edu.ifpb.ajudeMais.domain.entity.Endereco;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
import br.edu.ifpb.ajudeMais.domain.entity.Meta;
import br.edu.ifpb.ajudeMais.service.event.campanha.notification.CampanhaNotificationEvent;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.maps.dto.LatLng;
import br.edu.ifpb.ajudeMais.service.maps.impl.GoogleMapsServiceImpl;
import br.edu.ifpb.ajudeMais.service.negocio.CampanhaService;

/**
 * Classe utilizada para serviços de {@link Campanha}
 * 
 * @author elson / Franck
 *
 */
@Service
public class CampanhaServiceImpl implements CampanhaService {

	/**
	 * 
	 */
	@Autowired
	private CampanhaRepository campanhaRepository;

	/**
	 * 
	 */
	@Autowired
	private GoogleMapsServiceImpl googleMapsResponse;

	/**
	 * 
	 */
	@Autowired
	private DoadorRepository doadorRepository;

	/**
	 * 
	 */
	@Autowired
	private DonativoCampanhaRepository donativoCampanhaRepository;

	/**
	 * 
	 */
	@Autowired
	private ApplicationEventPublisher publisher;

	/**
	 * Salva uma campanha no BD
	 * 
	 * @param campanha
	 * @return
	 * @throws AjudeMaisException
	 */
	@Override
	@Transactional
	public Campanha save(Campanha campanha) throws AjudeMaisException {

		Campanha campanhaSaved = campanhaRepository.save(campanha);
		if (campanha.isStatus() && !campanha.isNotificada()) {
			List<String> notificaveis = getNotificaveis(campanha);
			publisher.publishEvent(new CampanhaNotificationEvent(notificaveis, campanhaSaved));
			campanhaSaved.setNotificada(true);
			this.update(campanhaSaved);

		}

		return campanhaSaved;
	}

	/**
	 * Atualiza uma campanha previamente salva no BD
	 * 
	 * @param campanha
	 * @return
	 * @throws AjudeMaisException
	 */
	@Override
	@Transactional
	public Campanha update(Campanha campanha) throws AjudeMaisException {
		Campanha campanhaSaved = campanhaRepository.save(campanha);
		if (campanha.isStatus() && !campanha.isNotificada()) {
			List<String> notificaveis = getNotificaveis(campanha);
			publisher.publishEvent(new CampanhaNotificationEvent(notificaveis, campanhaSaved));
			campanhaSaved.setNotificada(true);
			this.update(campanhaSaved);
		}

		return campanhaSaved;
	}

	/**
	 * Busca e retorna todas as campanhas salvas
	 */
	@Override
	public List<Campanha> findAll() {
		List<Campanha> campanhas = campanhaRepository.findAll();
		for (Campanha campanha : campanhas) {
			setPercetualAtingidoInMeta(campanha);
		}
		return campanhaRepository.findAllByOrderByDataCriacaoDesc();
	}

	/**
	 * Busca e retorna uma campanha específica pelo identificador
	 */
	@Override
	public Campanha findById(Long id) {
		Campanha campanha = campanhaRepository.findOne(id);
		return campanha;
	}

	/**
	 * Remove uma campanha ja cadastrada no BD
	 * 
	 * @param campanha
	 */
	@Override
	@Transactional
	public void remover(Campanha donativo) {
		campanhaRepository.delete(donativo);

	}

	@Override
	public List<Campanha> findByInstituicaoCaridade(InstituicaoCaridade instituicaoCaridade) {
		List<Campanha> campanhas = campanhaRepository
				.findByInstituicaoCaridadeOrderByDataCriacaoDesc(instituicaoCaridade);
		for (Campanha campanha : campanhas) {
			setPercetualAtingidoInMeta(campanha);
		}
		return campanhas;
	}

	/**
	 * Filtra campanhas por localização de instituição
	 */
	@Override
	public List<Campanha> filterByInstituicaoLocal(LatLng latLng) throws AjudeMaisException {

		Endereco endereco = googleMapsResponse.converteLatitudeAndLongitudeInAddress(latLng.getLatitude(),
				latLng.getLongitude());
		List<Campanha> campanhas = campanhaRepository
				.filterByInstituicaoLocalOrderByDataCriacaoDesc(endereco.getLocalidade(), endereco.getUf());

		for (Campanha campanha : campanhas) {
			setPercetualAtingidoInMeta(campanha);
		}
		return getByCurrentStatus(campanhas);
	}

	/**
	 * Busca campanhas por status
	 */
	@Override
	public List<Campanha> findByStatus(boolean status) {

		return this.getByCurrentStatus(campanhaRepository.findByStatus(status));
	}

	/**
	 * 
	 */
	@Override
	public List<Campanha> findByInstituicaoCaridadeIdAndStatus(Long id) {

		List<Campanha> campanhas = campanhaRepository.findByInstituicaoCaridadeIdAndStatusOrderByDataFimAsc(id, true);

		for (Campanha campanha : campanhas) {
			setPercetualAtingidoInMeta(campanha);
		}

		return campanhas;
	}

	/**
	 * 
	 * <p>
	 * Filtra instituições pelo status atual, i.e., status em relação ao
	 * termino.
	 * </p>
	 * 
	 * @param campanhas
	 * @return
	 */
	private List<Campanha> getByCurrentStatus(List<Campanha> campanhas) {
		List<Campanha> camps = new ArrayList<>();
		if (campanhas != null) {
			campanhas.forEach(c -> {
				if (c.isStatus()) {
					setPercetualAtingidoInMeta(c);
					camps.add(c);
				}
			});
		}

		return camps;
	}

	/**
	 * 
	 * <p>
	 * Obtém lista de doadores que serão notificados.
	 * </p>
	 * 
	 * @param campanha
	 * @return
	 */
	private List<String> getNotificaveis(Campanha campanha) {
		List<String> notificaveis = new ArrayList<>();

		String localidade = campanha.getInstituicaoCaridade().getEndereco().getLocalidade();
		String uf = campanha.getInstituicaoCaridade().getEndereco().getUf();

		List<Doador> doadores = doadorRepository.filterByLocal(localidade, uf);
		doadores.forEach(d -> {
			notificaveis.add(d.getTokenFCM().getToken());
		});

		return notificaveis;
	}

	private void setPercetualAtingidoInMeta(Campanha campanha) {

		for (Meta m : campanha.getMetas()) {
			Long qtdDonativos = donativoCampanhaRepository.filterCountByEstadoAndCategoriaAfterAceito(campanha.getId(),
					m.getCategoria().getId());
			if (qtdDonativos > 0) {
				float percetual = (qtdDonativos * 100) / m.getQuantidade().floatValue();
				m.setPercentualAtingido(percetual);

			} else {
				m.setPercentualAtingido(0f);
			}

		}
	}
}
