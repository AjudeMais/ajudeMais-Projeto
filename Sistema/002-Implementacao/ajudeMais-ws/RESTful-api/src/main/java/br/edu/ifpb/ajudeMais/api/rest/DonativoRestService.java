package br.edu.ifpb.ajudeMais.api.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import br.edu.ifpb.ajudeMais.data.repository.InstituicaoCaridadeRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.domain.entity.Donativo;
import br.edu.ifpb.ajudeMais.domain.entity.DonativoCampanha;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.AuthService;
import br.edu.ifpb.ajudeMais.service.negocio.DonativoCampanhaService;
import br.edu.ifpb.ajudeMais.service.negocio.DonativoService;

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
 * @author Franck
 * @author <a href="https://github.com/amslv">Ana Silva</a>
 *
 */
@RestController
@RequestMapping(value = "/donativo")
public class DonativoRestService {

	@Autowired
	private DonativoService donativoService;
	
	@Autowired
	private DonativoCampanhaService donativoCampanhaService;

	@Autowired
	private AuthService authService;

	@Autowired
	private InstituicaoCaridadeRepository instituicaoRepository;

	@PreAuthorize("hasRole('DOADOR')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Donativo> save(@RequestBody Donativo donativo) throws AjudeMaisException {
		Donativo donativoSalvo = donativoService.save(donativo);
		return new ResponseEntity<>(donativoSalvo, HttpStatus.CREATED);
	}

	/**
	 * Salva Donativo de uma campanha
	 * @param donativo
	 * @return
	 * @throws AjudeMaisException
	 */
	@PreAuthorize("hasRole('DOADOR')")
	@RequestMapping(method = RequestMethod.POST, value = "/save/donativocampanha")
	public ResponseEntity<DonativoCampanha> saveWithCampanha(@RequestBody DonativoCampanha donativo) throws AjudeMaisException {
		DonativoCampanha donativoSalvo = donativoCampanhaService.save(donativo);
		return new ResponseEntity<>(donativoSalvo, HttpStatus.CREATED);
	}

	
	@PreAuthorize("hasRole('DOADOR')")
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
	 * GET /donativo/filter/doadorId : Endpoint para buscar os donativos doados
	 * por doador
	 * 
	 * @return donativos
	 */
	@PreAuthorize("hasAnyRole('INSTITUICAO, DOADOR')")
	@RequestMapping(method = RequestMethod.GET, value = "/filter/{id}")
	public ResponseEntity<List<Donativo>> findByDoadorId(@PathVariable Long id) {
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
	 * GET /donativo/filter/instituicao : Busca donativos pela instituição.
	 * 
	 * @return donativos
	 */
	@PreAuthorize("hasAnyRole('INSTITUICAO, DOADOR')")
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
	 * /filter/campanha/estado/{id} : Busca todos os donativos de doados para a campanha com id passado que estão no estado depois de aceito.
	 * 
	 * @return donativos
	 */
	@PreAuthorize("hasRole('INSTITUICAO')")
	@RequestMapping(method = RequestMethod.GET, value = "/filter/campanha/estado/{id}")
	public ResponseEntity<List<DonativoCampanha>> filterDonativoByEstadoAfterAceito(@PathVariable Long id) {
		
		List<DonativoCampanha> donativos = donativoCampanhaService.filterDonativoByEstadoAfterAceito(id);

		return new ResponseEntity<>(donativos, HttpStatus.OK);
	}
}
