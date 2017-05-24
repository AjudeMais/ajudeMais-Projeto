
/**
 * 
 * <p>
 * <b> DoadorEditEvent.java </b>
 * </p>
 *
 * <p>
 * Entidade ...
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
package br.edu.ifpb.ajudeMais.service.event.doador;

import org.springframework.util.StringUtils;

import br.edu.ifpb.ajudeMais.domain.entity.Doador;

/**
 * 
 * <p>
 * <b>{@link DoadorEditEvent}</b>
 * </p>
 *
 * <p>
 * Classe utilizada para evento relacionados a criação de um doador. Este evento
 * é chamedo quando o método update doador for acionado.
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public class DoadorEditEvent {

	/**
	 * 
	 */
	private Doador doador;

	/**
	 * 
	 */
	private String imagemAntiga;

	/**
	 * 
	 * <p>
	 * Construtor default
	 * </p>
	 *
	 * @param doador
	 */
	public DoadorEditEvent(Doador doador, String imagemAntiga) {
		this.doador = doador;
		this.imagemAntiga = imagemAntiga;
	}

	/**
	 * @return the doador
	 */
	public Doador getDoador() {
		return doador;
	}

	/**
	 * @param doador
	 *            the doador to set
	 */
	public void setDoador(Doador doador) {
		this.doador = doador;
	}

	/**
	 * @return o atributo imagem
	 */
	public String getImagemAntiga() {
		return imagemAntiga;
	}

	/**
	 * @param o
	 *            parametro imagem é setado em imagem
	 */
	public void setImagemAntiga(String imagemAntiga) {
		this.imagemAntiga = imagemAntiga;
	}

	/**
	 * 
	 * <p>
	 * Verifica se o doador possui uma imagem.
	 * </p>
	 * 
	 * @return
	 */
	public boolean isImage() {
		if (doador.getFoto() == null)
			return false;

		return !StringUtils.isEmpty(doador.getFoto().getNome());
	}
}
