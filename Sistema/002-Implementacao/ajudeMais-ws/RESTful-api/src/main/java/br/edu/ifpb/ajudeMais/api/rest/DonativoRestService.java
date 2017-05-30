package br.edu.ifpb.ajudeMais.api.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.ajudeMais.domain.entity.Donativo;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.DonativoService;

/**
 * 
 * 
 * <p>
 * <b> {@link DonativoRestService} </b>
 * </p>
 *
 * <p>
 *	Classe define serviços disponibilizados de um donativo.
 * </p>
 * 
 * @author <a href="https://github.com/amslv">Ana Silva</a>
 *
 */
@RestController
@RequestMapping(value = "/donativo")
public class DonativoRestService {

	
	@Autowired
	private DonativoService donativoService;
	
	/**
	 * 
	 * <p>
	 * POST /donativo/ : Método disponibiliza recurso para salvar um donativo. ROLE:
	 * DOADOR
	 * </p>
	 * 
	 * @param donativo
	 * @return Htpp 201, caso cadastro tenha occorido com sucesso
	 * @throws AjudeMaisException
	 */
	@PreAuthorize("hasRole('DOADOR')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> save(@Valid @RequestBody Donativo donativo) throws AjudeMaisException {
		Donativo donativoCriado = donativoService.save(donativo);
		return new ResponseEntity<>(donativoCriado, HttpStatus.CREATED);
	}
	
	/**
	 * 
	 * <p>
	 * PUT /donativo/ : Método disponibiliza recurso para atualizar um donativo.
	 * ROLE: DOADOR
	 * </p>
	 * 
	 * @param donativo
	 *            donativo a ser atualizado.
	 * @return 201 caso sucesso.
	 * @throws AjudeMaisException
	 */
	@PreAuthorize("hasRole('DOADOR, INSTITUICAO')")
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Donativo> update(@Valid @RequestBody Donativo donativo) throws AjudeMaisException {

		Donativo donativoAtualizado = donativoService.update(donativo);

		return new ResponseEntity<Donativo>(donativoAtualizado, HttpStatus.CREATED);
	}
	
	/**
	 * 
	 * <p>
	 * GET /donativo/id : Busca um donativo pelo deu ID. Caso donativo não exista um
	 * NOT FOUND será lançado para o cliente. <br/>
	 * ROLE: DOADOR
	 * </p>
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasRole('DOADOR, INSTITUICAO')")
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Donativo> findById(@PathVariable Long id) {
		Donativo donativo = donativoService.findById(id);
		if (donativo == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Donativo>(donativo, HttpStatus.OK);
	}
	
	/**
	 * 
	 * <p>
	 * GET /donativo/ : Método disponibiliza recurso obter donativos doados por um determinado doador.
	 * ROLE: DOADOR
	 * </p>
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('DOADOR, INSTITUICAO')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Donativo>> findByDoadorNome(String nomeDoador) {

		List<Donativo> donativos = donativoService.findByDoadorNome(nomeDoador);

		return new ResponseEntity<List<Donativo>>(donativos, HttpStatus.OK);
	}
}
