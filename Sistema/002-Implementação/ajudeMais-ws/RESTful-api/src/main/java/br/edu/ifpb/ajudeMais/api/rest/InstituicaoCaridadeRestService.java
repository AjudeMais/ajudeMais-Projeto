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

import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.InstituicaoCaridadeService;

/**
 * 
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@RestController
@RequestMapping("/instituicao")
public class InstituicaoCaridadeRestService {
	
	@Autowired
	private InstituicaoCaridadeService instituicaoService;
	
	/**
	 * 
	 * @param instituicaoCaridade
	 * @return
	 * @throws AjudeMaisException
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<InstituicaoCaridade> save(@Valid @RequestBody InstituicaoCaridade instituicaoCaridade) throws AjudeMaisException {
		InstituicaoCaridade instituicaoSalva = instituicaoService.save(instituicaoCaridade);
		return new ResponseEntity<>(instituicaoSalva, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param instituicaoCaridade
	 * @return
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<InstituicaoCaridade> update(@Valid @RequestBody InstituicaoCaridade instituicaoCaridade) {
		InstituicaoCaridade instituicao = instituicaoService.update(instituicaoCaridade);
		
		return new ResponseEntity<InstituicaoCaridade>(instituicao, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<InstituicaoCaridade>> findAll() {
		List<InstituicaoCaridade> instituicoes = instituicaoService.findAll();
		
		return new ResponseEntity<>(instituicoes, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ADMIN, INSTITUICAO')")
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<InstituicaoCaridade> findById(@PathVariable Long id) {
		InstituicaoCaridade instituicao = instituicaoService.findById(id);
		
		return new ResponseEntity<InstituicaoCaridade>(instituicao, HttpStatus.OK);
		
	}
}
