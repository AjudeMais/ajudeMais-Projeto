package br.edu.ifpb.ajudeMais.api.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.service.negocio.AuthService;
import br.edu.ifpb.ajudeMais.service.security.jwt.JwtToken;

/**
 * 
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 *
 */
@RestController
@RequestMapping("/auth")
public class AuthRestService {
	
	@Autowired
	private AuthService authService;
	
	@Value("${jwt.header}")
	private String tokenHeader;

	/**
	 * 
	 * @param conta
	 * @param device
	 * @return
	 * @throws AuthenticationException
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody Conta conta, Device device)
			throws AuthenticationException {

		JwtToken token = authService.criaAutenticao(conta, device);

		return ResponseEntity.ok(token);
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/atualizar", method = RequestMethod.GET)
	public ResponseEntity<?> refreshAuthenticationToken(HttpServletRequest request) {
		String token = request.getHeader(tokenHeader);
		JwtToken tokenAtualizado = authService.atualizaAutenticacao(new JwtToken(token));
		
		if(tokenAtualizado != null){
			return ResponseEntity.ok(tokenAtualizado);
		}else{
			return new ResponseEntity<Object>("token inválido", HttpStatus.UNPROCESSABLE_ENTITY);
		}

	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(HttpServletRequest request) {
		String token = request.getHeader(tokenHeader);
		Conta conta = authService.getContaPorToken(new JwtToken(token));

		return ResponseEntity.ok(conta);
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/valida", method = RequestMethod.GET)
	public ResponseEntity<Boolean> authenticationValid(HttpServletRequest request) {
		String token = request.getHeader(tokenHeader);
		Boolean valid = authService.autenticacaoValida(new JwtToken(token));

		return ResponseEntity.ok(valid);
	}

}
