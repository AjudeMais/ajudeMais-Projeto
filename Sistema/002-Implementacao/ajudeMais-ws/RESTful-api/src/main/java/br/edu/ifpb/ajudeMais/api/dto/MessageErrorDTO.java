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
package br.edu.ifpb.ajudeMais.api.dto;

/**
 * 
 * <p>
 * {@link MessageErrorDTO}
 * </p>
 * 
 * <p>
 * Classe utilizada como DTO de messagens de erro
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
public class MessageErrorDTO {

	/**
	 * 
	 */
	private String msg;

	/**
	 * 
	 * <p>
	 * Construtor padrão
	 * </p>
	 *
	 */
	public MessageErrorDTO() {
	}
	
	
	/**
	 * 
	 * <p>
	 * Construtor recebe uma msg
	 * </p>
	 *
	 * @param msg
	 */
	public MessageErrorDTO(String msg) {
		this.msg = msg;
	}



	/**
	 * @return o atributo msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param o
	 *            parametro msg é setado em msg
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
