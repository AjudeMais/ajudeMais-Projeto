package br.edu.ifpb.ajudeMais.service.negocio.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.edu.ifpb.ajudeMais.data.repository.CampanhaRepository;
import br.edu.ifpb.ajudeMais.data.repository.DoadorRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Campanha;
import br.edu.ifpb.ajudeMais.domain.entity.Doador;
import br.edu.ifpb.ajudeMais.domain.entity.Endereco;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
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
		if (campanha.isStatus()) {
			List<String> notificaveis = getNotificaveis(campanha);
			publisher.publishEvent(new CampanhaNotificationEvent(notificaveis, campanhaSaved));
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
		if (campanha.isStatus()) {
			List<String> notificaveis = getNotificaveis(campanha);
			publisher.publishEvent(new CampanhaNotificationEvent(notificaveis, campanhaSaved));
		}

		return campanhaSaved;
	}

	/**
	 * Busca e retorna todas as campanhas salvas
	 */
	@Override
	public List<Campanha> findAll() {
		return campanhaRepository.findAll();
	}

	/**
	 * Busca e retorna uma campanha específica pelo identificador
	 */
	@Override
	public Campanha findById(Long id) {
		return campanhaRepository.findOne(id);
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
		return campanhaRepository.findByInstituicaoCaridade(instituicaoCaridade);
	}

	/**
	 * Filtra campanhas por localização de instituição
	 */
	@Override
	public List<Campanha> filterByInstituicaoLocal(LatLng latLng) throws AjudeMaisException {

		Endereco endereco = googleMapsResponse.converteLatitudeAndLongitudeInAddress(latLng.getLatitude(),
				latLng.getLongitude());
		List<Campanha> campanhas = campanhaRepository.filterByInstituicaoLocal(endereco.getLocalidade(),
				endereco.getUf());

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
}
