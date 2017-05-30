package br.edu.ifpb.ajudeMais.api.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
 * Classe define serviços disponibilizados de um Donativo.
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

	@PreAuthorize("hasRole('DOADOR')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Donativo> save(@RequestBody Donativo donativo) throws AjudeMaisException {
		Donativo donativoSalvo = donativoService.save(donativo);
		return new ResponseEntity<>(donativoSalvo, HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasRole('DOADOR')")
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Donativo> update(@RequestBody Donativo donativo) throws AjudeMaisException {
		Donativo donativoSalvo = donativoService.save(donativo);
		return new ResponseEntity<>(donativoSalvo, HttpStatus.CREATED);
	}
	
	/**
	 * Endpoint para listar todos os donativos cadastros no sistema.
	 *
	 * @return
	 */
	@PreAuthorize("hasRole('DOADOR, INSTITUICAO')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Donativo>> findAll() {
		List<Donativo> donativos = donativoService.findAll();
		return new ResponseEntity<List<Donativo>>(donativos, HttpStatus.OK);
	}
	
	/**
	 * GET /donativo/filter/doadorNome : Endpoint para buscar os donativos doados por doador
	 * 
	 * @return donativos
	 */
	@PreAuthorize("hasRole('INSTITUICAO, DOADOR')")
	@RequestMapping(method = RequestMethod.GET, value = "/filter/doadorNome")
	public ResponseEntity<List<Donativo>> findByDoadorNome(@RequestParam("doadorNome") String nome) {
		List<Donativo> donativos = donativoService.findByDoadorNome(nome);
		return new ResponseEntity<>(donativos, HttpStatus.OK);
	}
	
	/**
	 * GET /donativo/filter/nome : Endpoint para buscar os donativos pelo nome
	 * 
	 * @return donativos
	 */
	@PreAuthorize("hasRole('INSTITUICAO, DOADOR')")
	@RequestMapping(method = RequestMethod.GET, value = "/filter/nome")
	public ResponseEntity<List<Donativo>> findByNome(@RequestParam("nome") String nome) {
		List<Donativo> donativos = donativoService.findByNome(nome);
		return new ResponseEntity<>(donativos, HttpStatus.OK);
	}
}
