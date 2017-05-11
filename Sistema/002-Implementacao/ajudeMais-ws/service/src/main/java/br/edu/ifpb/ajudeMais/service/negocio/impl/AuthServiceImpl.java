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
package br.edu.ifpb.ajudeMais.service.negocio.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.domain.enumerations.Grupo;
import br.edu.ifpb.ajudeMais.service.negocio.AuthService;
import br.edu.ifpb.ajudeMais.service.security.UsuarioSistema;
import br.edu.ifpb.ajudeMais.service.security.jwt.JwtToken;
import br.edu.ifpb.ajudeMais.service.security.jwt.JwtTokenUtil;

/**
 * 
 * <p>
 * {@link AuthServiceImpl}
 * </p>
 * 
 * <p>
 * Classe utilizada para implementação de serviços definido em
 * {@link AuthService}
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@Service
public class AuthServiceImpl implements AuthService {

	/**
	 * 
	 */
	@Autowired
	private AuthenticationManager authenticationManager;

	/**
	 * 
	 */
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	/**
	 * 
	 */
	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * 
	 */
	@Override
	public JwtToken criaAutenticao(Conta conta, Device device) throws AuthenticationException {

		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(conta.getUsername(), conta.getSenha()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		final UserDetails userDetails = userDetailsService.loadUserByUsername(conta.getUsername());
		verifyUserByModule(userDetails, device);
		final String token = jwtTokenUtil.generateToken(userDetails, device);

		return new JwtToken(token);
	}

	/**
	 * Get login para usuário .
	 *
	 * @return the login of the current user
	 */
	public String getCurrentUserLogin() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		String userName = null;
		if (authentication != null) {
			if (authentication.getPrincipal() instanceof UserDetails) {
				UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
				userName = springSecurityUser.getUsername();
			} else if (authentication.getPrincipal() instanceof String) {
				userName = (String) authentication.getPrincipal();
			}
		}
		return userName;
	}

	/**
	 * 
	 * busca e retorna a conta do usuario atualmente logado
	 * 
	 */
	@Override
	public Conta getCurrentUser() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		if (authentication != null) {
			UsuarioSistema springUser = (UsuarioSistema) authentication.getPrincipal();
			return springUser.getConta();
		}
		throw new BadCredentialsException("nenhum usuário encontrado");
	}

	/**
	 * 
	 * atualiza o token de autenticacao
	 * 
	 */
	@Override
	public JwtToken atualizaAutenticacao(JwtToken tokenAtual) {
		String tokenAtualizado = jwtTokenUtil.refreshToken(tokenAtual.getToken());
		return tokenAtualizado != null ? new JwtToken(tokenAtualizado) : null;
	}

	/**
	 * 
	 * busca e retorna uma conta de acordo com o token
	 * 
	 */
	@Override
	public Conta getContaPorToken(JwtToken token) {
		if (autenticacaoValida(token)) {
			return this.getConta(token);
		}
		return null;
	}

	/**
	 * 
	 * valida a solicitacao de autenticacao do usuario de acordo com o token passado
	 * 
	 */
	@Override
	public Boolean autenticacaoValida(JwtToken token) {
		String username = jwtTokenUtil.getUsernameFromToken(token.getToken());
		if (username != null) {
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
		return conta;
	}

	/**
	 * 
	 * @param userDetails
	 * @param device
	 */
	private void verifyUserByModule(UserDetails userDetails, Device device) {
		String msg = "Usuário não autorizado";
		List<String> permitions = new ArrayList<>();
		userDetails.getAuthorities().forEach(p -> permitions.add(p.getAuthority()));
		for (String p : permitions) {
			if (device.isNormal()) {
				if (p.contains(Grupo.DOADOR.name()) || p.contains(Grupo.MENSAGEIRO.name())) {
					throw new AccessDeniedException(msg);
				}
			} else {
				if (p.contains(Grupo.ADMIN.name()) || p.contains(Grupo.INSTITUICAO.name())) {
					throw new AccessDeniedException(msg);
				}
			}
		}
	}
}
