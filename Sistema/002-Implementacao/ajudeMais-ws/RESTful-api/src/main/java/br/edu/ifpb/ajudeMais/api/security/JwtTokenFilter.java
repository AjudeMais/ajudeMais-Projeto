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
package br.edu.ifpb.ajudeMais.api.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import br.edu.ifpb.ajudeMais.service.security.jwt.JwtTokenUtil;

/**
 * 
 * <p>
 * {@link JwtTokenFilter}
 * </p>
 * 
 * <p>
 * Classe utilizada para implementação de verificação de requisições com
 * cabeçalho de autorização.
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
public class JwtTokenFilter extends OncePerRequestFilter {

	/**
	 * 
	 */
	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * 
	 */
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	/**
	 * 
	 */
	@Value("${jwt.header}")
	private String tokenHeader;

	/**
	 * 
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
		String authToken = request.getHeader(this.tokenHeader);
		String username = jwtTokenUtil.getUsernameFromToken(authToken);

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

			if (jwtTokenUtil.validateToken(authToken, userDetails)) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				response.setHeader(this.tokenHeader, this.refreshToken(authToken));
			}
		}

		chain.doFilter(request, response);

	}

	/**
	 * Pega token atualizado
	 * <p>
	 * </p>
	 * 
	 * @param currentToken
	 * 
	 * @return token atualizado
	 */
	private String refreshToken(String currentToken) {
		return jwtTokenUtil.refreshToken(currentToken);
	}
}
