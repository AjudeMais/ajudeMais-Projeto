package br.edu.ifpb.ajudeMais.api.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.ContaService;

/**
 * 
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@RestController
@RequestMapping("/conta")
public class ContaRestService {

	@Autowired
	private ContaService contaService;

	/**
	 * @param conta
	 * @return response
	 * @throws AjudeMaisException 
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Conta> create(@Valid @RequestBody Conta conta) throws AjudeMaisException {

		Conta contaCriada = contaService.save(conta);

		return new ResponseEntity<Conta>(contaCriada, HttpStatus.CREATED);
	}

}
