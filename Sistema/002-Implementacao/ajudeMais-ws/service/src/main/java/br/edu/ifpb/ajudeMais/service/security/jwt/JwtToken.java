package br.edu.ifpb.ajudeMais.service.security.jwt;

import java.util.Date;

/**
 * 
 * <p>
 * {@link JwtToken}
 * </p>
 * 
 * <p>
 * Classe utilizada para representação de token de autenticação
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
public class JwtToken {

	/**
	 * 
	 * 
	 */
	private String token;

	/**
	 * 
	 * 
	 */
	private Date date = new Date();

	/**
	 * 
	 * 
	 */
	public JwtToken() {

	}

	/**
	 * 
	 * 
	 * 
	 * @param token
	 */
	public JwtToken(String token) {
		this.token = token;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "JwtToken [token=" + token + ", date=" + date + "]";
	}

}
