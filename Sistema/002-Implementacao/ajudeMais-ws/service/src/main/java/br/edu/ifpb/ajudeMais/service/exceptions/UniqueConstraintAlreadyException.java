
package br.edu.ifpb.ajudeMais.service.exceptions;


/**
 * 
 * <p>
 * <b> UniqueValueAlreadyException.java </b>
 * </p>
 *
 * <p>
 * Exception para valores de atributos unicos
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public class UniqueConstraintAlreadyException extends AjudeMaisException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 
	 */
	public UniqueConstraintAlreadyException(String message) {
		super(message);
	}

}
