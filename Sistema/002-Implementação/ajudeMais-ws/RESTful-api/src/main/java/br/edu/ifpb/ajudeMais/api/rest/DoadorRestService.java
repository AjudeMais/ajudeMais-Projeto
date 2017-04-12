package br.edu.ifpb.ajudeMais.api.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.ajudeMais.domain.entity.Doador;
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
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Doador> criar(@Valid @RequestBody Doador doador) {

		Doador doadorCriado = doadorService.criar(doador);

		return new ResponseEntity<Doador>(doadorCriado, HttpStatus.CREATED);
	}

	/**
	 * @param doador
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Doador> alterar(@Valid @RequestBody Doador doador) {

		Doador pacienteAtualizado = doadorService.alterar(doador);

		return new ResponseEntity<Doador>(pacienteAtualizado, HttpStatus.CREATED);
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Doador>> buscarTodos() {
		
		List<Doador> doador = doadorService.buscarTodos();
		
		return new ResponseEntity<List<Doador>>(doador,HttpStatus.OK);
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(method=RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id) {
		
		Doador doador = new Doador();
		doador.setId(id);
		doadorService.remover(doador);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
