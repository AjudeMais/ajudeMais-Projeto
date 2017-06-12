package br.edu.ifpb.ajudeMais.domain.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * <p>
 * {@link FcmToken}
 * </p>
 * 
 * <p>
 * Representa token de registro do FCM.
 * </p>
 *
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class FcmToken {

	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	/**
	 * 
	 */
	private String token;

	/**
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date date = new Date();

	/**
	 * 
	 *
	 * @param token
	 * @param date
	 */
	public FcmToken(String token, Date date) {
		this.token = token;
		this.date = date;
	}

	public FcmToken() {
	}

	/**
	 * @return o atributo id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param o
	 *            parametro id é setado em id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return o atributo token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param o
	 *            parametro token é setado em token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return o atributo date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param o
	 *            parametro date é setado em date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

}
