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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.ajudeMais.api.dto.ChangePasswordDTO;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.AuthService;
import br.edu.ifpb.ajudeMais.service.negocio.ContaService;

/**
 * 
 * <p>
 * {@link ContaRestService}
 * </p>
 * 
 * <p>
 * Classe utilizada para serviços providos referentes a {@link Conta}
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@RestController
@RequestMapping("/conta")
public class ContaRestService {

	/**
	 * 
	 */
	@Autowired
	private ContaService contaService;
	
	@Autowired
	private AuthService authService;


	/**
	 * POST /conta : Salva usuário
	 * 
	 * @param conta
	 * @return response
	 * @throws AjudeMaisException
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Conta> create(@Valid @RequestBody Conta conta) throws AjudeMaisException {

		Conta contaCriada = contaService.save(conta);

		return new ResponseEntity<Conta>(contaCriada, HttpStatus.CREATED);
	}

	/**
	 * POST /conta/changePassword : Altera senha do usuário, considerando a
	 * confirmação de senha (módulo mobile).
	 *
	 * @param nova
	 *            senha
	 * @return ResponseEntity 200 (ok) ou 400 (Bad Request)
	 * @throws AjudeMaisException
	 */
	@PostMapping(path = "/changePassword")
	public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO)
			throws AjudeMaisException {
		contaService.changePassword(changePasswordDTO.getPassword(), changePasswordDTO.getNewPassword());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * POST /conta/changePassword/init : Altera senha do usuário para primeiro
	 * acesso de usuários do módulo web (admin e instituicao).
	 *
	 * @param nova
	 *            senha
	 * @return ResponseEntity 200 (ok) ou 400 (Bad Request)
	 * @throws AjudeMaisException
	 */
	@PreAuthorize("hasAnyRole('ADMIN, INSTITUICAO')")
	@PostMapping(path = "/changePassword/init")
	public ResponseEntity<?> changePassword(@RequestBody String password) throws AjudeMaisException {
		contaService.changePasswordInit(password);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * GET /conta/find/grupo : recupera a lista de Grupo ao qual o usuário pertence.
	 *
	 * @return ResponseEntity 200 (ok) ou 400 (Bad Request)
	 */
	@GetMapping(path = "/find/grupo")
	public ResponseEntity<List<String>> getGrupoCurrentUser() {
		Conta conta = authService.getCurrentUser();
		
		return new ResponseEntity<>(conta.getGrupos(),HttpStatus.OK);
	}
}
