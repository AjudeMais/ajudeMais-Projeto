package br.edu.ifpb.ajudeMais.service.event.mensageiro;

import org.springframework.util.StringUtils;

import br.edu.ifpb.ajudeMais.domain.entity.Mensageiro;

/**
 * 
 * <p>
 * {@link MensageiroEditEvent}
 * </p>
 * 
 * <p>
 * Classe utilizada para evento relacionados a criação de um mensageiro. Este
 * evento é chamedo quando o método save doador for acionado.
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
public class MensageiroEditEvent {

	/**
	 * 
	 */
	private Mensageiro mensageiro;

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
	 * @param mensageiro
	 */
	public MensageiroEditEvent(Mensageiro mensageiro, String imagemAntiga) {
		this.mensageiro = mensageiro;
		this.imagemAntiga = imagemAntiga;
	}

	/**
	 * @return o atributo mensageiro
	 */
	public Mensageiro getMensageiro() {
		return mensageiro;
	}

	/**
	 * @param o
	 *            parametro mensageiro é setado em mensageiro
	 */
	public void setMensageiro(Mensageiro mensageiro) {
		this.mensageiro = mensageiro;
	}

	/**
	 * @return o atributo imagemAntiga
	 */
	public String getImagemAntiga() {
		return imagemAntiga;
	}

	/**
	 * @param o
	 *            parametro imagemAntiga é setado em imagemAntiga
	 */
	public void setImagemAntiga(String imagemAntiga) {
		this.imagemAntiga = imagemAntiga;
	}

	/**
	 * 
	 * <p>
	 * Verifica se o mensageiro possui uma imagem.
	 * </p>
	 * 
	 * @return
	 */
	public boolean isImage() {
		if (mensageiro.getFoto() == null) {
			return false;
		}
		return !StringUtils.isEmpty(mensageiro.getFoto().getNome());
	}

}
