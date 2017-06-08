package br.edu.ifpb.ajudeMais.service.exceptions;

/**
 * 
 * <p>
 * {@link ImageErrorException}
 * </p>
 * 
 * <p>
 * Classe utilizada para exceção de erro em imagem não encontrada nas operações
 * de IO de imagem.
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
public class ImageErrorException extends AjudeMaisException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * <p>
	 * Construtor default. Passa msg de erro para super exception class.
	 * </p>
	 *
	 * @param msg
	 */
	public ImageErrorException(String msg) {
		super(msg);
	}

}
