
/**
 * 
 * <p>
 * <b> DashboardInstituicaoRestService.java </b>
 * </p>
 *
 * <p>
 * Entidade ...
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
package br.edu.ifpb.ajudeMais.api.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.ajudeMais.api.dto.CampanhaMetaDTO;
import br.edu.ifpb.ajudeMais.api.dto.DoacoesPeriodoDTO;
import br.edu.ifpb.ajudeMais.api.dto.MensageiroRankingDTO;
import br.edu.ifpb.ajudeMais.data.repository.CampanhaRepository;
import br.edu.ifpb.ajudeMais.data.repository.CategoriaRepository;
import br.edu.ifpb.ajudeMais.data.repository.DonativoRepository;
import br.edu.ifpb.ajudeMais.data.repository.MensageiroAssociadoRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Campanha;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.domain.entity.Donativo;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
import br.edu.ifpb.ajudeMais.domain.entity.MensageiroAssociado;
import br.edu.ifpb.ajudeMais.domain.entity.Meta;
import br.edu.ifpb.ajudeMais.domain.enumerations.Estado;
import br.edu.ifpb.ajudeMais.service.negocio.AuthService;
import br.edu.ifpb.ajudeMais.service.negocio.CampanhaService;
import br.edu.ifpb.ajudeMais.service.negocio.DonativoService;
import br.edu.ifpb.ajudeMais.service.negocio.InstituicaoCaridadeService;
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
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 *
 */
@RestController
@RequestMapping("/dashboard/instituicao")
public class DashboardInstituicaoRestService {

	/**
	 * 
	 */
	@Autowired
	private DonativoRepository donativoRepository;

	/**
	 * 
	 */
	@Autowired
	private AuthService authService;

	/**
	 * 
	 */
	@Autowired
	private CampanhaRepository campanhaRepositoty;

	/**
	 * 
	 */
	@Autowired
	private CampanhaService campanhaService;

	/**
	 * 
	 */
	@Autowired
	private MensageiroAssociadoRepository mensageiroAssociadoRepository;

	/**
	 * 
	 */
	@Autowired
	private MensageiroAssociadoService mensageiroAssociadoService;

	/**
	 * 
	 */
	@Autowired
	private InstituicaoCaridadeService instituicaoCaridadeService;

	/**
	 * 
	 */
	@Autowired
	private DonativoService donativoService;

	/**
	 * 
	 */
	@Autowired
	private CategoriaRepository categoriaRepository;

	/**
	 * 
	 */
	private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM");

	/**
	 * 
	 * <p>
	 * GET /donativo/count : Método disponibiliza recurso para obtenção de
	 * quantidade de donativos já recolhidos pela instituicao. <br>
	 * 
	 * ROLE: INSTITUICAO
	 * </p>
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('INSTITUICAO')")
	@RequestMapping(method = RequestMethod.GET, value = "/donativo/count")
	public ResponseEntity<Long> getCountDonativos() {
		Conta conta = authService.getCurrentUser();
		Optional<InstituicaoCaridade> instituicaoOp = instituicaoCaridadeService.findOneByConta(conta);

		if (instituicaoOp.isPresent()) {
			Long count = donativoRepository.filterCountByEstadoRecolhidoAndInstituicaoId(instituicaoOp.get().getId());
			return new ResponseEntity<Long>(count, HttpStatus.OK);
		} else {
			return new ResponseEntity<Long>(HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * 
	 * <p>
	 * GET /campanha/count : Método disponibiliza recurso para obtenção de
	 * quantidade de campanhas estão ativas pela instituicao. <br>
	 * 
	 * ROLE: INSTITUICAO
	 * </p>
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('INSTITUICAO')")
	@RequestMapping(method = RequestMethod.GET, value = "/campanha/count")
	public ResponseEntity<Long> getCountCampanhas() {
		Conta conta = authService.getCurrentUser();
		Optional<InstituicaoCaridade> instituicaoOp = instituicaoCaridadeService.findOneByConta(conta);

		if (instituicaoOp.isPresent()) {
			Long count = campanhaRepositoty.countByInstituicaoCaridadeIdAndStatus(instituicaoOp.get().getId(), true);
			return new ResponseEntity<Long>(count, HttpStatus.OK);
		} else {
			return new ResponseEntity<Long>(HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('INSTITUICAO')")
	@RequestMapping(method = RequestMethod.GET, value = "/itens/count")
	public ResponseEntity<Long> getCountItens() {
		Conta conta = authService.getCurrentUser();
		Optional<InstituicaoCaridade> instituicaoOp = instituicaoCaridadeService.findOneByConta(conta);

		if (instituicaoOp.isPresent()) {
			Long count = categoriaRepository.countByInstituicaoCaridadeAndAtivo(instituicaoOp.get(), true);
			return new ResponseEntity<Long>(count, HttpStatus.OK);
		} else {
			return new ResponseEntity<Long>(HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * 
	 * <p>
	 * GET /campanha/count : Método disponibiliza recurso para obtenção de
	 * quantidade de campanhas estão ativas pela instituicao. <br>
	 * 
	 * ROLE: INSTITUICAO
	 * </p>
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('INSTITUICAO')")
	@RequestMapping(method = RequestMethod.GET, value = "/mensageiro/count")
	public ResponseEntity<Long> getCountMensageirosAssociados() {
		Conta conta = authService.getCurrentUser();

		Optional<InstituicaoCaridade> instituicaoOp = instituicaoCaridadeService.findOneByConta(conta);
		
		if (instituicaoOp.isPresent()) {
			Long count = mensageiroAssociadoRepository.countByStatusAndInstituicaoCaridadeId(true,
					instituicaoOp.get().getId());
			return new ResponseEntity<Long>(count, HttpStatus.OK);
		} else {
			return new ResponseEntity<Long>(HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('INSTITUICAO')")
	@RequestMapping(method = RequestMethod.GET, value = "/mensageiro/ranking")
	public ResponseEntity<List<MensageiroRankingDTO>> getRanking() {
		Conta conta = authService.getCurrentUser();
		Optional<InstituicaoCaridade> instituicaoOp = instituicaoCaridadeService.findOneByConta(conta);

		Map<MensageiroAssociado, Integer> mensageiros = mensageiroAssociadoService.getRanking(instituicaoOp.get());
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

	@PreAuthorize("hasRole('INSTITUICAO')")
	@RequestMapping(method = RequestMethod.GET, value = "/donativo/periodo/instituicao")
	public ResponseEntity<List<DoacoesPeriodoDTO>> getDoacoesByPeriodoInstituicao(@Param("nDays") Integer nDays,
			@Param("estado") String estado) {

		List<DoacoesPeriodoDTO> doacoesPeriodo = getDonativosByPeriodo(nDays, estado);
		return new ResponseEntity<List<DoacoesPeriodoDTO>>(doacoesPeriodo, HttpStatus.OK);
	}

	/**
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('INSTITUICAO')")
	@RequestMapping(method = RequestMethod.GET, value = "/campanha/metas")
	public ResponseEntity<List<CampanhaMetaDTO>> getCampanhasMetas() {

		Conta conta = authService.getCurrentUser();
		Optional<InstituicaoCaridade> instituicaoOp = instituicaoCaridadeService.findOneByConta(conta);

		List<Campanha> campanhasAtivas = campanhaService
				.findByInstituicaoCaridadeIdAndStatus(instituicaoOp.get().getId());
		List<CampanhaMetaDTO> campanhasMetas = new ArrayList<>();

		campanhasAtivas.forEach(c -> {
			Float percentual = this.getPercentualMetas(c);
			CampanhaMetaDTO cm = new CampanhaMetaDTO(c, percentual);
			campanhasMetas.add(cm);
		});

		return new ResponseEntity<List<CampanhaMetaDTO>>(campanhasMetas, HttpStatus.OK);

	}

	/**
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('INSTITUICAO')")
	@RequestMapping(method = RequestMethod.GET, value = "/donativo/timeline")
	public ResponseEntity<List<Donativo>> getLastDonativos() {

		Conta conta = authService.getCurrentUser();
		Optional<InstituicaoCaridade> instituicaoOp = instituicaoCaridadeService.findOneByConta(conta);

		List<Donativo> donativos = donativoRepository
				.findFirst10ByCategoriaInstituicaoCaridadeOrderByDataDesc(instituicaoOp.get());

		return new ResponseEntity<>(donativos, HttpStatus.OK);

	}

	/**
	 * @param campanha
	 * @return
	 */
	private Float getPercentualMetas(Campanha campanha) {
		Float percentual = 0f;
		for (Meta meta : campanha.getMetas()) {
			percentual += meta.getPercentualAtingido();
		}
		return percentual;

	}

	/**
	 * 
	 * @param nDays
	 * @param estado
	 * @return
	 */
	private List<DoacoesPeriodoDTO> getDonativosByPeriodo(Integer nDays, String estado) {

		Conta conta = authService.getCurrentUser();
		Optional<InstituicaoCaridade> instituicaoOp = instituicaoCaridadeService.findOneByConta(conta);

		Map<Date, Integer> doacoes = donativoService.getDoacoesByPeriodo(nDays, Estado.getByEstado(estado),
				instituicaoOp.get().getId());

		List<DoacoesPeriodoDTO> doacoesPeriodo = new ArrayList<>();

		for (Date date : doacoes.keySet()) {
			DoacoesPeriodoDTO doDto = new DoacoesPeriodoDTO(DATE_FORMAT.format(date), doacoes.get(date));
			doacoesPeriodo.add(doDto);
		}
		return doacoesPeriodo;
	}

}
