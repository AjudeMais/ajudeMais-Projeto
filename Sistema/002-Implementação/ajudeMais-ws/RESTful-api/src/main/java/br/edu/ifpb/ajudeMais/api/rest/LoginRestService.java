package br.edu.ifpb.ajudeMais.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.ajudeMais.domain.entity.Usuario;
import br.edu.ifpb.ajudeMais.service.negocio.LoginService;

@RestController
public class LoginRestService {
	
	@Autowired
	private LoginService loginService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Object> executarLogin(@RequestBody Usuario usuario) {

		Usuario user = loginService.efetuarLogin(usuario.getUsername(), usuario.getSenha());		
		
		ResponseEntity<Object> responseEntity = null;
		
		if(user == null) {
			responseEntity = new ResponseEntity<Object>("Usuário ou senha inválida", HttpStatus.UNPROCESSABLE_ENTITY);
		} else {			
			responseEntity = new ResponseEntity<Object>(usuario, HttpStatus.OK);
		}

		return responseEntity;
	}
	

}
