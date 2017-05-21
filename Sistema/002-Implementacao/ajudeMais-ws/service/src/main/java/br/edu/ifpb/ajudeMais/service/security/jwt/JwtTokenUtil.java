package br.edu.ifpb.ajudeMais.service.security.jwt;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import br.edu.ifpb.ajudeMais.service.security.UsuarioSistema;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 
 * Classe utilitária para manipulação de JWT
 * 
 * <pre>
 * Baseada nos projetos:
 * &#64;see <a href="https://github.com/brahalla/Cerberus</a>
 * &#64;see <a href="https://www.toptal.com/java/rest-security-with-jwt-spring-security-and-java</a>
 * </pre>
 *
 */
@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -3301605591108950415L;

	static final String CLAIM_KEY_USERNAME = "sub";
	static final String CLAIM_KEY_AUDIENCE = "audience";
	static final String CLAIM_KEY_CREATED = "created";

	private static final String AUDIENCE_UNKNOWN = "unknown";
	private static final String AUDIENCE_WEB = "web";
	private static final String AUDIENCE_MOBILE = "mobile";
	private static final String AUDIENCE_TABLET = "tablet";

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expirationWeb}")
	private Long expirationWeb;
	
	@Value("${jwt.expirationMobile}")
	private Long expirationMobile;

	/**
	 * Pega um token e retorna o username relacionado ao mesmo. Caso exista.
	 * 
	 * @param token
	 * 		Token de usuário
	 * 
	 * @return
	 * 		O username relacionado ao mesmo
	 */
	public String getUsernameFromToken(String token) {
		String username;
		try {
			final Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	/**
	 * Pega e retorna a data de criação de um token
	 * 
	 * @param token
	 * 		Token de usuário
	 * 
	 * @return
	 * 		Data de criação
	 */
	public Date getCreatedDateFromToken(String token) {
		Date created;
		try {
			final Claims claims = getClaimsFromToken(token);
			created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
		} catch (Exception e) {
			created = null;
		}
		return created;
	}

	/**
	 * 
	 * Pega e retorna a data de expiração
	 * 
	 * @param token
	 * 		Token de acesso
	 * 
	 * @return
	 * 		Data de expiração
	 */
	public Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			final Claims claims = getClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}

	/**
	 * Pega e retorna a audience
	 * 
	 * @param token
	 * 		Token de acesso
	 * 
	 * @return
	 * 		retorna a audience
	 */
	public String getAudienceFromToken(String token) {
		String audience;
		try {
			final Claims claims = getClaimsFromToken(token);
			audience = (String) claims.get(CLAIM_KEY_AUDIENCE);
		} catch (Exception e) {
			audience = null;
		}
		return audience;
	}

	/**
	 * Gera um token de acordo com os dados informadoss
	 * 
	 * @param userDetails
	 * 		detalhes do user
	 * 
	 * @param device
	 * 		device usado
	 * 
	 * @return
	 * 		token gerado
	 */
	public String generateToken(UserDetails userDetails, Device device) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
		claims.put(CLAIM_KEY_AUDIENCE, generateAudience(device));
		claims.put(CLAIM_KEY_CREATED, new Date());
		return generateToken(claims);
	}

	
	/**
	 * Atualiza o token previamente gerado e retorna o novo token
	 * 
	 * 
	 * @param token
	 * 		token antigo
	 * 
	 * 
	 * @return
	 * 		novo token
	 */
	public String refreshToken(String token) {
		String refreshedToken;
		try {
			final Claims claims = getClaimsFromToken(token);
			claims.put(CLAIM_KEY_CREATED, new Date());
			refreshedToken = generateToken(claims);
		} catch (Exception e) {
			refreshedToken = null;
		}
		return refreshedToken;
	}

	/**
	 * Valida um token de acordo com as credenciais do usuário
	 * 
	 * @param token
	 * 		token utilizado
	 * 
	 * @param userDetails
	 * 		os detalhes do usuário
	 * 
	 * @return
	 * 		se o token passado é válido ou não
	 */
	public Boolean validateToken(String token, UserDetails userDetails) {
		UsuarioSistema user = (UsuarioSistema) userDetails;
		final String username = getUsernameFromToken(token);
		return (username.equals(user.getUsername()) && !isTokenExpired(token));
	}

	/**
	 * Pega e retorna as informações (Claims) utilizadas pelo JWT
	 * 
	 * 
	 * @param token
	 * 		Token passado
	 * 
	 * 
	 * @return
	 * 		os Claims relacionados aquele token
	 */
	private Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	
	/**
	 * Pega e retorna a data de expriação de um token de acordo
	 * com as Claims relacionadas ao mesmo

	 * 
	 * @param claims
	 * 		As informações (Claims) relacionadas aquele token em questão
	 * 
	 * @return
	 *		Data de expiração do mesmo 
	 *
	 */
	private Date generateExpirationDate(Map<String, Object> claims) {
		if (claims.get(CLAIM_KEY_AUDIENCE).equals(AUDIENCE_WEB)) {
			return new Date(System.currentTimeMillis() + expirationWeb * 60 * 1000);
		}
		return new Date(System.currentTimeMillis() + expirationMobile * 60 * 60 * 1000);
	}

	
	/**
	 * Verifica e retorna se o token passado por parâmetro está expirado
	 * 
	 * @param token
	 * 		Token a ser verificado
	 * 
	 * @return
	 * 		retorna se o token está expirado ou não
	 */
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	/**
	 * Gera a audience relacionada aquele Device
	 * 
	 * 
	 * @param device
	 * 		device relacionado
	 * 
	 * @return
	 * 		a qual audiencia aquele device está relacionado
	 * 
	 */
	private String generateAudience(Device device) {
		String audience = AUDIENCE_UNKNOWN;
		if (device.isNormal()) {
			audience = AUDIENCE_WEB;
		} else if (device.isTablet()) {
			audience = AUDIENCE_TABLET;
		} else if (device.isMobile()) {
			audience = AUDIENCE_MOBILE;
		}
		return audience;
	}

	
	/**
	 * Gera e retorna um token de acordo com as claims requeridas
	 * 
	 * @param claims
	 * 		as informações (Claims) requeridas pelo mesmo
	 * 
	 * @return
	 * 		retorna o token gerados
	 * 
	 */
	private String generateToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate(claims))
				.signWith(SignatureAlgorithm.HS512, secret).compact();

	}
	
}