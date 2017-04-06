package br.edu.ifpb.ajudeMais.service.negocio.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import br.edu.ifpb.ajudeMais.domain.entity.Usuario;
import br.edu.ifpb.ajudeMais.service.negocio.LoginService;
import br.edu.ifpb.ajudeMais.service.security.UsuarioSistema;

/**
 * 
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 *
 */
@Service
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public Usuario efetuarLogin(String login, String senha) {

		Authentication authentication = this.criarAutenticacao(login, senha);

		if (authentication == null) {
			return null;
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);

		UsuarioSistema usuario = (UsuarioSistema) authentication.getPrincipal();


		return usuario.getUsuario();
	}
	
	/**
	 * @param login
	 * @param senha
	 * @return
	 */
	private Authentication criarAutenticacao(String login, String senha) {

		Authentication authentication = null;

		try {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login, senha);

			authentication = this.authenticationManager.authenticate(token);

		} catch (BadCredentialsException bce) {
			return null;
		}

		return authentication;
	}

}
