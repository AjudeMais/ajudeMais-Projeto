/**
 * <p>
 * Ajude Mais - Módulo Web Service
 * </p>
 * 
 * <p>
 * Sistema para potencializar o processo de doação.
 * </p>
 * 
 * <a href="https://github.com/AjudeMais/AjudeMais">Ajude Mais</a>
 * <a href="https://franckaj.github.io">Franck Aragão"></a>
 * 
 * AJUDE MAIS - 2017®
 * 
 */
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.edu.ifpb.ajudeMais.domain.entity.Mensageiro;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.MensageiroService;

/**
 * 
 * <p>
 * <b> MensageiroRestService.java </b>
 * </p>
 *
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
@RestController
@RequestMapping(value = "/mensageiro")
public class MensageiroRestService {

	@Autowired
	private MensageiroService mensageiroService;

	/**
	 * Endpoint para cadastro de um mensageiro no sistema.
	 * 
	 * @param mensageiro
	 * @return
	 * @throws AjudeMaisException
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> save(@Valid @RequestBody Mensageiro mensageiro) throws AjudeMaisException {
		Mensageiro mensageiroSaved = mensageiroService.save(mensageiro);
		return new ResponseEntity<>(mensageiroSaved, HttpStatus.CREATED);
	}

	/**
	 * Endpoint para atualizar informações do mensageiro.
	 * 
	 * @param mensageiro
	 * @return Mensageiro
	 * @throws AjudeMaisException
	 */
	@PreAuthorize("hasRole('MENSAGEIRO')")
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Mensageiro> update(@Valid @RequestBody Mensageiro mensageiro) throws AjudeMaisException {
		mensageiro = mensageiroService.update(mensageiro);
		return new ResponseEntity<Mensageiro>(mensageiro, HttpStatus.OK);

	}

	/**
	 * Endpoint para listar todos os mensageiros cadastros no sistema.
	 * 
	 * @return Mensageiro
	 */
	@PreAuthorize("hasRole('INSTITUICAO')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Mensageiro>> findAll() {
		List<Mensageiro> mensageiros = mensageiroService.findAll();
		return new ResponseEntity<List<Mensageiro>>(mensageiros, HttpStatus.OK);
	}

	/**
	 * Endpoint para buscar um mensageiro por seu identificador.
	 * 
	 * @return Mensageiro
	 */
	@PreAuthorize("hasRole('INSTITUICAO')")
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Mensageiro> findById(@PathVariable Long id) {
		Mensageiro mensageiro = mensageiroService.findById(id);
		return new ResponseEntity<Mensageiro>(mensageiro, HttpStatus.OK);
	}

	/**
	 * GET /mensageiro/filter/contaEmail : Endpoint para buscar um mensageiro
	 * por conta, filtrando por e-mail
	 * 
	 * @return Mensageiro
	 */
	@PreAuthorize("hasRole('INSTITUICAO')")
	@RequestMapping(method = RequestMethod.GET, value = "/filter/contaEmail")
	public ResponseEntity<List<Mensageiro>> findByContaEmail(@RequestParam("email") String email) {
		List<Mensageiro> mensageiros = mensageiroService.findByContaEmail(email);
		return new ResponseEntity<>(mensageiros, HttpStatus.OK);
	}
	
	/**
	 * 
	 * <p>
	 * GET /mensageiro/filter/usermane : Busca um Mensageiro pela conta, filtrando pelo nome de
	 * usuário.
	 * <br/>
	 * ROLE: DOADOR
	 * </p>
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasRole('MENSAGEIRO')")
	@RequestMapping(method = RequestMethod.GET, value = "/filter/username")
	public ResponseEntity<Mensageiro> findByContaUsername(@RequestParam String username) {
		Mensageiro mensageiro = mensageiroService.findByContaUsername(username);
		return new ResponseEntity<Mensageiro>(mensageiro, HttpStatus.OK);
	}

}
