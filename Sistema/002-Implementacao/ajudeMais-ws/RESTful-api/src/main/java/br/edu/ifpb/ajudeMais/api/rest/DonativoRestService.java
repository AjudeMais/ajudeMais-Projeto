package br.edu.ifpb.ajudeMais.api.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.ajudeMais.data.repository.DonativoCampanhaRepository;
import br.edu.ifpb.ajudeMais.data.repository.DonativoRepository;
import br.edu.ifpb.ajudeMais.data.repository.InstituicaoCaridadeRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.domain.entity.Donativo;
import br.edu.ifpb.ajudeMais.domain.entity.DonativoCampanha;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
import br.edu.ifpb.ajudeMais.domain.entity.Mensageiro;
import br.edu.ifpb.ajudeMais.domain.enumerations.Estado;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.AuthService;
import br.edu.ifpb.ajudeMais.service.negocio.DonativoCampanhaService;
import br.edu.ifpb.ajudeMais.service.negocio.DonativoService;
import br.edu.ifpb.ajudeMais.service.negocio.MensageiroService;

/**
 * 
 * 
 * <p>
 * <b> {@link DonativoRestService} </b>
 * </p>
 *
 * <p>
 * Classe define serviços disponibilizados de um Donativo.
 * </p>
 * 
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 * @author <a href="https://github.com/amslv">Ana Silva</a>
 *
 */
@RestController
@RequestMapping(value = "/donativo")
public class DonativoRestService {

	/**
	 * 
	 */
	private Logger LOGGER = LoggerFactory.getLogger(DonativoRestService.class);

	/**
	 * 
	 */
	@Autowired
	private DonativoService donativoService;

	/**
	 * 
	 */
	@Autowired
	private DonativoCampanhaService donativoCampanhaService;

	/**
	 * 
	 */
	@Autowired
	private DonativoCampanhaRepository donativoCampanhaRepository;

	/**
	 * 
	 */
	@Autowired
	private DonativoRepository donativoRepositoory;

	/**
	 * 
	 */
	@Autowired
	private AuthService authService;

	/**
	 * 
	 */
	@Autowired
	private InstituicaoCaridadeRepository instituicaoRepository;

	/**
	 * 
	 */
	@Autowired
	private MensageiroService mensageiroService;

	/**
	 * Salva donativo avulso
	 * 
	 * @param donativo
	 * @return
	 * @throws AjudeMaisException
	 */
	@PreAuthorize("hasRole('DOADOR')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Donativo> save(@RequestBody Donativo donativo) throws AjudeMaisException {
		Donativo donativoSalvo = donativoService.save(donativo);
		return new ResponseEntity<>(donativoSalvo, HttpStatus.CREATED);
	}

	/**
	 * Salva Donativo de uma campanha
	 * 
	 * @param donativo
	 * @return
	 * @throws AjudeMaisException
	 */
	@PreAuthorize("hasRole('DOADOR')")
	@RequestMapping(method = RequestMethod.POST, value = "/save/donativocampanha")
	public ResponseEntity<DonativoCampanha> saveWithCampanha(@RequestBody DonativoCampanha donativo)
			throws AjudeMaisException {
		DonativoCampanha donativoSalvo = donativoCampanhaService.save(donativo);
		return new ResponseEntity<>(donativoSalvo, HttpStatus.CREATED);
	}

	/**
	 * GET /filter/donativocampanha/{id} : Endpoint para buscar DonativoCampanha
	 * pelo id Donativo
	 * 
	 * @param donativo
	 * @return
	 * @throws AjudeMaisException
	 */
	@PreAuthorize("hasRole('DOADOR')")
	@RequestMapping(method = RequestMethod.GET, value = "/filter/donativocampanha/{id}")
	public ResponseEntity<DonativoCampanha> findByDonativoCampanhaId(@PathVariable Long id) throws AjudeMaisException {
		DonativoCampanha donativoCampanha = donativoCampanhaService.findByDonativoId(id);
		return new ResponseEntity<>(donativoCampanha, HttpStatus.OK);
	}

	/**
	 * GET /filter/donativo/{id} : Endpoint para buscar Donativo pelo id
	 * Donativo
	 * 
	 * @param donativo
	 * @return
	 * @throws AjudeMaisException
	 */
	@PreAuthorize("hasAnyRole('MENSAGEIRO, DOADOR')")
	@RequestMapping(method = RequestMethod.GET, value = "/filter/donativo/{id}")
	public ResponseEntity<Donativo> findByDonativoId(@PathVariable Long id) throws AjudeMaisException {
		Donativo donativo = donativoService.findById(id);
		return new ResponseEntity<>(donativo, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('MENSAGEIRO, DOADOR')")
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Donativo> update(@RequestBody Donativo donativo) throws AjudeMaisException {
		Donativo donativoUpdated = donativoService.update(donativo);
		return new ResponseEntity<>(donativoUpdated, HttpStatus.OK);
	}

	/**
	 * Endpoint para listar todos os donativos cadastros no sistema.
	 *
	 * @return
	 */
	@PreAuthorize("hasRole('INSTITUICAO')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Donativo>> findAll() {
		List<Donativo> donativos = donativoService.findAll();
		return new ResponseEntity<List<Donativo>>(donativos, HttpStatus.OK);
	}

	/**
	 * GET /donativo/filter/doadorNome : Endpoint para buscar os donativos
	 * doados por doador
	 * 
	 * @return donativos
	 */
	@PreAuthorize("hasAnyRole('INSTITUICAO, DOADOR')")
	@RequestMapping(method = RequestMethod.GET, value = "/filter/doadorNome")
	public ResponseEntity<List<Donativo>> findByDoadorNome(@RequestParam("doadorNome") String nome) {
		List<Donativo> donativos = donativoService.findByDoadorNome(nome);
		return new ResponseEntity<>(donativos, HttpStatus.OK);
	}

	/**
	 * GET /donativo/filter/doadorId : Endpoint para buscar os donativos de um
	 * doador com id passado
	 * 
	 * @return donativos
	 */
	@PreAuthorize("hasAnyRole('INSTITUICAO, DOADOR')")
	@RequestMapping(method = RequestMethod.GET, value = "/filter/{id}")
	public ResponseEntity<List<Donativo>> findByDonativoToDoadorId(@PathVariable Long id) {
		List<Donativo> donativos = donativoService.findByDoadorId(id);
		return new ResponseEntity<>(donativos, HttpStatus.OK);
	}

	/**
	 * GET /donativo/filter/nome : Endpoint para buscar os donativos pelo nome
	 * 
	 * @return donativos
	 */
	@PreAuthorize("hasAnyRole('INSTITUICAO, DOADOR')")
	@RequestMapping(method = RequestMethod.GET, value = "/filter/nome")
	public ResponseEntity<List<Donativo>> findByNome(@RequestParam("nome") String nome) {
		List<Donativo> donativos = donativoService.findByNome(nome);
		return new ResponseEntity<>(donativos, HttpStatus.OK);
	}

	/**
	 * 
	 * <p>
	 * GET /donativo/filter/mensageiro : Busca mensageiros filtrando por
	 * mensageiro.
	 * </p>
	 * 
	 * @param username
	 *            do mensageiro
	 * @return lista encontrada
	 */
	@PreAuthorize("hasRole('MENSAGEIRO')")
	@RequestMapping(method = RequestMethod.GET, value = "/filter/mensageiro")
	public ResponseEntity<List<Donativo>> findByMensageiro(@RequestParam("username") String username) {

		Mensageiro mensageiro = mensageiroService.findByContaUsername(username);
		List<Donativo> donativos = donativoService.findByMensageiro(mensageiro);
		return new ResponseEntity<List<Donativo>>(donativos, HttpStatus.OK);
	}

	/**
	 * 
	 * <p>
	 * GET /donativo/filter/mensageiroEstado : Busca mensageiros filtrando por
	 * mensageiro e estado.
	 * </p>
	 * 
	 * @param username
	 *            do mensageiro
	 * @param estado
	 *            estado do donativo.
	 * @return lista encontrada
	 */
	@PreAuthorize("hasRole('MENSAGEIRO')")
	@RequestMapping(method = RequestMethod.GET, value = "/filter/mensageiroEstado")
	public ResponseEntity<List<Donativo>> findByMensageiroAndEstado(@RequestParam("username") String username,
			@RequestParam("estado") String estado) {

		Mensageiro mensageiro = mensageiroService.findByContaUsername(username);
		List<Donativo> donativos = donativoService.filterDonativoByMensageiroAndEstado(mensageiro,
				Estado.getByEstado(estado));

		return new ResponseEntity<List<Donativo>>(donativos, HttpStatus.OK);
	}

	/**
	 * GET /donativo/filter/instituicao : Busca donativos pela instituição.
	 * 
	 * @return donativos
	 */
	@PreAuthorize("hasRole('INSTITUICAO')")
	@RequestMapping(method = RequestMethod.GET, value = "/filter/instituicao")
	public ResponseEntity<List<Donativo>> findByInstituicao() {
		Conta conta = authService.getCurrentUser();
		Optional<InstituicaoCaridade> instituicaoOp = instituicaoRepository.findOneByConta(conta);

		List<Donativo> donativos = new ArrayList<>();
		if (instituicaoOp.isPresent()) {
			donativos = donativoService.findByCategoriaInstituicaoCaridade(instituicaoOp.get());
		}
		return new ResponseEntity<>(donativos, HttpStatus.OK);
	}

	/**
	 * /filter/campanha/estado/{id} : Busca todos os donativos de doados para a
	 * campanha com id passado que estão no estado depois de aceito.
	 * 
	 * @return donativos
	 */
	@PreAuthorize("hasRole('INSTITUICAO')")
	@RequestMapping(method = RequestMethod.GET, value = "/filter/campanha/estado/{id}")
	public ResponseEntity<List<DonativoCampanha>> filterDonativoByEstadoAfterAceito(@PathVariable Long id) {

		List<DonativoCampanha> donativos = donativoCampanhaService.filterDonativoByEstadoAfterAceito(id);

		return new ResponseEntity<>(donativos, HttpStatus.OK);
	}

	/**
	 * /filter/estado/ : Busca todos os donativos com estado passado vinculados
	 * a instituição com id passado.
	 * 
	 * @return donativos
	 */
	@PreAuthorize("hasRole('INSTITUICAO')")
	@RequestMapping(method = RequestMethod.GET, value = "/filter/estado")
	public ResponseEntity<List<Donativo>> filterDonativoByEstadoAndInstituicao(@RequestParam("estado") String estado) {

		Conta conta = authService.getCurrentUser();
		Optional<InstituicaoCaridade> instituicaoOp = instituicaoRepository.findOneByConta(conta);

		if (instituicaoOp.isPresent()) {
			List<Donativo> donativos = donativoService.filterDonativoByEstadoAndInstituicao(instituicaoOp.get().getId(),
					Estado.getByEstado(estado));
			return new ResponseEntity<>(donativos, HttpStatus.OK);

		} else {
			return new ResponseEntity<List<Donativo>>(HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * "/filter/closer/mensageiro/{id} : Busca todos os donativos com base na
	 * localização
	 * 
	 * @return solicitacoesColeta
	 */
	@PreAuthorize("hasRole('MENSAGEIRO')")
	@RequestMapping(method = RequestMethod.GET, value = "/filter/closer/mensageiro/{id}")
	public ResponseEntity<List<Donativo>> filterByDonativosCloserMensageiroId(@PathVariable Long id)
			throws AjudeMaisException {
		List<Donativo> solicitacoesColeta = donativoService.filterByDonativosCloserMensageiroId(id);
		return new ResponseEntity<List<Donativo>>(solicitacoesColeta, HttpStatus.OK);

	}

	/**
	 * "/validatecoleta/{id} : Verifica se donativo com id passado está
	 * disponível para coleta.
	 * 
	 * @return boolean
	 */
	@PreAuthorize("hasRole('MENSAGEIRO')")
	@RequestMapping(method = RequestMethod.GET, value = "/validatecoleta/{id}")
	public ResponseEntity<Boolean> isValidColetaByDonativo(@PathVariable Long id) {
		return new ResponseEntity<Boolean>(new Boolean(donativoService.isValidColeta(id)), HttpStatus.OK);
	}

	/**
	 * "/validaterecolhimento/{id} : Verifica se donativo com id passado está
	 * disponível para coleta.
	 * 
	 * @return boolean
	 */
	@PreAuthorize("hasRole('MENSAGEIRO')")
	@RequestMapping(method = RequestMethod.GET, value = "/validaterecolhimento/{id}")
	public ResponseEntity<Boolean> isValidRecolhimentoByDonativo(@PathVariable Long id) {
		return new ResponseEntity<Boolean>(new Boolean(donativoService.isValidRecolhimento(id)), HttpStatus.OK);
	}

	/**
	 * "/validatecancelamento/{id} : Verifica se donativo com id passado está
	 * disponível para coleta.
	 * 
	 * @return boolean
	 */
	@PreAuthorize("hasAnyRole('MENSAGEIRO, DOADOR')")
	@RequestMapping(method = RequestMethod.GET, value = "/validatecancelamento/{id}")
	public ResponseEntity<Boolean> isValidCancelamentoByDonativo(@PathVariable Long id) {
		return new ResponseEntity<Boolean>(new Boolean(donativoService.isValidRecolhimento(id)), HttpStatus.OK);
	}

	/**
	 * 
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ADMIN, INSTITUICAO')")
	@RequestMapping(method = RequestMethod.GET, value = "/campanha/count/{id}")
	public ResponseEntity<Long> countDoacoesCampanhasByInstituicao(@PathVariable("id") Long idInstituicao) {
		Long count = donativoCampanhaRepository.countByDonativoCategoriaInstituicaoCaridadeId(idInstituicao);
		return new ResponseEntity<Long>(count, HttpStatus.OK);
	}

	/**
	 * 
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ADMIN, INSTITUICAO')")
	@RequestMapping(method = RequestMethod.GET, value = "/count/{id}")
	public ResponseEntity<Long> countDoacoes(@PathVariable("id") Long idInstituicao) {
		Long count = donativoRepositoory.countByCategoriaInstituicaoCaridadeId(idInstituicao);
		return new ResponseEntity<Long>(count, HttpStatus.OK);
	}
}
