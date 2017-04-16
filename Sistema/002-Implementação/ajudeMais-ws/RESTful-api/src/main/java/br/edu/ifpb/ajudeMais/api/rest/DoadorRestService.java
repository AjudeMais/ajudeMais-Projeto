package br.edu.ifpb.ajudeMais.api.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.ajudeMais.domain.entity.Doador;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.DoadorService;

/**
 * 
 * <p>
 * <b> DoadorRestService </b>
 * </p>
 *
 * <p>
 * Classe define serviços disponibilizados de um doador.
 * </p>
 * 
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
@RestController
@RequestMapping(value = "/doador")
public class DoadorRestService {

	/**
	 * 
	 */
	@Autowired
	private DoadorService doadorService;

	/**
	 * @param doador
	 * @return response 
	 * @throws AjudeMaisException 
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Doador> criar(@Valid @RequestBody Doador doador) throws AjudeMaisException {

		Doador doadorCriado = doadorService.save(doador);

		return new ResponseEntity<Doador>(doadorCriado, HttpStatus.CREATED);
	}

	/**
	 * @param doador
	 * @return
	 */
	@PreAuthorize("hasRole('DOADOR')")
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Doador> alterar(@Valid @RequestBody Doador doador) {

		Doador pacienteAtualizado = doadorService.update(doador);

		return new ResponseEntity<Doador>(pacienteAtualizado, HttpStatus.CREATED);
	}
	
	/**
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('DOADOR')")
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Doador>> buscarTodos() {
		
		List<Doador> doador = doadorService.findAll();
		
		return new ResponseEntity<List<Doador>>(doador,HttpStatus.OK);
	}
}
