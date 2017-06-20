
/**
 * 
 * <p>
 * <b> InvalidRemoveException.java </b>
 * </p>
 *
 * <p>
 * Entidade ...
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
package br.edu.ifpb.ajudeMais.service.exceptions;


/**
 * 
 * <p>
 * <b> {@link InvalidRemoveException} </b>
 * </p>
 *
 * <p>
 * Exception Para remoções inválidas
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public class InvalidRemoveException extends AjudeMaisException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 */
	public InvalidRemoveException(String message) {
		super(message);
	}

}
