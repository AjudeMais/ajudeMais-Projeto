
/**
 * 
 * <p>
 * <b> DoadorListener.java </b>
 * </p>
 *
 * <p>
 * Entidade ...
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
package br.edu.ifpb.ajudeMais.service.event.doador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.edu.ifpb.ajudeMais.service.storage.ImagemStorage;

/**
 * 
 * <p>
 * <b> {@link DoadorListener} </b>
 * </p>
 *
 * <p>
 * Classe utilizada para registro de eventos relacionados a doador. Sempre que
 * um evento for chamado e o mesmo esteja registrado nesta classe, então é
 * executada a tarefa para este listener.
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
@Component
public class DoadorListener {

	/**
	 * 
	 */
	@Autowired
	private ImagemStorage imagemStorage;

	/**
	 * 
	 * <p>
	 * Salva a imagem em diretório final sempre que o evento
	 * {@link DoadorEditEvent} for chamado.
	 * </p>
	 * 
	 * @param event
	 */
	@EventListener(condition = "#event.image")
	public void doadorEdited(DoadorEditEvent event) {
		String foto = event.getDoador().getFoto().getNome();
		if (!imagemStorage.exists(foto)) {
			imagemStorage.save(foto);
			if (event.getImagemAntiga() != null) {
				imagemStorage.remove(event.getImagemAntiga());
			}
		}
	}
}
