package br.edu.ifpb.ajudeMais.api.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.ajudeMais.api.dto.DoacoesPeriodoDTO;
import br.edu.ifpb.ajudeMais.api.dto.MensageiroRankingDTO;
import br.edu.ifpb.ajudeMais.data.repository.CampanhaRepository;
import br.edu.ifpb.ajudeMais.data.repository.DoadorRepository;
import br.edu.ifpb.ajudeMais.data.repository.DonativoRepository;
import br.edu.ifpb.ajudeMais.data.repository.InstituicaoCaridadeRepository;
import br.edu.ifpb.ajudeMais.data.repository.MensageiroRepository;
import br.edu.ifpb.ajudeMais.domain.entity.MensageiroAssociado;
import br.edu.ifpb.ajudeMais.domain.enumerations.Estado;
import br.edu.ifpb.ajudeMais.service.negocio.DonativoService;
import br.edu.ifpb.ajudeMais.service.negocio.MensageiroAssociadoService;

/**
 * 
 * <p>
 * {@link DashboardAdminRestService}
 * </p>
 * 
 * <p>
 * Classe utilizada para disponibilização de end points relacionados a consultas
 * das dasboards do administrador.
 * </p>
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@RestController
@RequestMapping("/dashboard/admin")
public class DashboardAdminRestService {

	/**
	 * 
	 */
	@Autowired
	private InstituicaoCaridadeRepository instituicaoCaridadeRepository;

	/**
	 * 
	 */
	@Autowired
	private DonativoRepository donativoRepository;

	/**
	 * 
	 */
	@Autowired
	private CampanhaRepository campanhaRepositoty;

	/**
	 * 
	 */
	@Autowired
	private MensageiroRepository mensageiroRepositoy;

	/**
	 * 
	 */
	@Autowired
	private DonativoService donativoService;

	/**
	 * 
	 */
	@Autowired
	private MensageiroAssociadoService mensageiroAssService;
	
	/**
	 * 
	 */
	@Autowired
	private DoadorRepository doadorRepository;

	/**
	 * 
	 */
	private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM");

	/**
	 * 
	 * <p>
	 * GET /instituicao/count : Método disponibiliza recurso para obtenção de
	 * quantidade de instituição salva. <br>
	 * 
	 * ROLE: ADMIN
	 * </p>
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET, value = "/instituicao/count")
	public ResponseEntity<Long> getCountInstituicaoCaridade() {

		Long count = instituicaoCaridadeRepository.count();
		return new ResponseEntity<Long>(count, HttpStatus.OK);
	}

	/**
	 * 
	 * <p>
	 * GET /donativo/count : Método disponibiliza recurso para obtenção de
	 * quantidade de donativos já recolhidos. <br>
	 * 
	 * ROLE: ADMIN
	 * </p>
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET, value = "/donativo/count")
	public ResponseEntity<Long> getCountDonativos() {

		Long count = donativoRepository.filterCountByEstadoRecolhido();
		return new ResponseEntity<Long>(count, HttpStatus.OK);
	}

	/**
	 * 
	 * <p>
	 * GET /campanha/count : Método disponibiliza recurso para obtenção de
	 * quantidade de campanhas. <br>
	 * 
	 * ROLE: ADMIN
	 * </p>
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET, value = "/doador/count")
	public ResponseEntity<Long> getCountDoadores() {

		Long count = doadorRepository.count();
		return new ResponseEntity<Long>(count, HttpStatus.OK);
	}

	/**
	 * 
	 * <p>
	 * GET /mensageiro/count : Método disponibiliza recurso para obtenção de
	 * quantidade de mensageiros. <br>
	 * 
	 * ROLE: ADMIN
	 * </p>
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET, value = "/mensageiro/count")
	public ResponseEntity<Long> getCountMensageiros() {

		Long count = mensageiroRepositoy.count();
		return new ResponseEntity<Long>(count, HttpStatus.OK);
	}

	/**
	 * 
	 * <p>
	 * GET /donativo/periodo : Busca donativos por periodos ao longo do tempo.
	 * <br>
	 * ROLE: ADMIN
	 * </p>
	 * 
	 * @return Mapa contendo período de tempo com a quantidade de doações
	 *         realizadas para cada data.
	 */

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET, value = "/donativo/periodo")
	public ResponseEntity<List<DoacoesPeriodoDTO>> getDoacoesByPeriodo(@Param("nDays") Integer nDays,
			@Param("estado") String estado) {

		List<DoacoesPeriodoDTO> doacoesPeriodo = getDonativosByPeriodo(nDays, estado, null);

		return new ResponseEntity<List<DoacoesPeriodoDTO>>(doacoesPeriodo, HttpStatus.OK);
	}

	/**
	 * 
	 * <p>
	 * GET /donativo/periodo/instituicao : Busca quantidade de donativos por
	 * periodos ao longo do tempo filtrando por instituição. <br>
	 * ROLE: ADMIN
	 * </p>
	 * 
	 * @return Mapa contendo período de tempo com a quantidade de doações
	 *         realizadas para cada data.
	 */

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET, value = "/donativo/periodo/instituicao/{id}")
	public ResponseEntity<List<DoacoesPeriodoDTO>> getDoacoesByPeriodoInstituicao(@Param("nDays") Integer nDays,
			@Param("estado") String estado, @PathVariable("id") Long idInst) {

		List<DoacoesPeriodoDTO> doacoesPeriodo = getDonativosByPeriodo(nDays, estado, idInst);
		return new ResponseEntity<List<DoacoesPeriodoDTO>>(doacoesPeriodo, HttpStatus.OK);
	}

	/**
	 * 
	 * @param nDays
	 * @param estado
	 * @return
	 */
	private List<DoacoesPeriodoDTO> getDonativosByPeriodo(Integer nDays, String estado, Long idInst) {

		Map<Date, Integer> doacoes = donativoService.getDoacoesByPeriodo(nDays, Estado.getByEstado(estado), idInst);

		List<DoacoesPeriodoDTO> doacoesPeriodo = new ArrayList<>();

		for (Map.Entry<Date, Integer> date : doacoes.entrySet()) {
			DoacoesPeriodoDTO doDto = new DoacoesPeriodoDTO(DATE_FORMAT.format(date.getKey()), doacoes.get(date.getKey()));
			doacoesPeriodo.add(doDto);
		}
		return doacoesPeriodo;
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/mensageiro/ranking")
	public ResponseEntity<List<MensageiroRankingDTO>> getRanking() {

		Map<MensageiroAssociado, Integer> mensageiros = mensageiroAssService.getRanking(null);
		List<MensageiroRankingDTO> rankingMensageiros = new ArrayList<>();

		int limit = 0;
		for (Map.Entry<MensageiroAssociado, Integer> entry : mensageiros.entrySet()) {
			if (limit < 10) {
				MensageiroRankingDTO ranking = new MensageiroRankingDTO(entry.getKey(), entry.getValue());
				rankingMensageiros.add(ranking);
			}
			limit++;
		}
		return new ResponseEntity<List<MensageiroRankingDTO>>(rankingMensageiros, HttpStatus.OK);

	}
}
