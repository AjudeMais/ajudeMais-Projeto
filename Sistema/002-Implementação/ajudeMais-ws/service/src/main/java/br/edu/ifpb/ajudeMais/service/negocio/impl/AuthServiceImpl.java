package br.edu.ifpb.ajudeMais.service.negocio.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.service.negocio.AuthService;
import br.edu.ifpb.ajudeMais.service.security.UsuarioSistema;
import br.edu.ifpb.ajudeMais.service.security.jwt.JwtToken;
import br.edu.ifpb.ajudeMais.service.security.jwt.JwtTokenUtil;

/**
 * 
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 *
 */

@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * 
	 */
	@Override
	public JwtToken criaAutenticao(Conta conta, Device device) throws AuthenticationException{
		
		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(conta.getUsername(), conta.getSenha()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);

		final UserDetails userDetails = userDetailsService.loadUserByUsername(conta.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails, device);
		
		return new JwtToken(token);
	}

	/**
	 * 
	 */
	@Override
	public JwtToken atualizaAutenticacao(JwtToken tokenAtual) {
		String tokenAtualizado = jwtTokenUtil.refreshToken(tokenAtual.getToken());
		return tokenAtualizado != null? new JwtToken(tokenAtualizado) : null;
	}

	/**
	 * 
	 */
	@Override
	public Conta getContaPorToken(JwtToken token) {
		if(autenticacaoValida(token)){
			return this.getConta(token);
		}
		return null;
	}

	/**
	 * 
	 */
	@Override
	public Boolean autenticacaoValida(JwtToken token) {
		String username = jwtTokenUtil.getUsernameFromToken(token.getToken());
		if(username != null){
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			return jwtTokenUtil.validateToken(token.getToken(), userDetails);
		}
		
		return false;
		
	}
	
	/**
	 * 
	 * @param token
	 * @return
	 */
	private Conta getConta(JwtToken token) {
		String username = jwtTokenUtil.getUsernameFromToken(token.getToken());
		UsuarioSistema userDetails = (UsuarioSistema) userDetailsService.loadUserByUsername(username);
		Conta conta = userDetails.getConta();
		conta.setSenha("[PROTEGIDA]");
		
		return conta;
	}
}
