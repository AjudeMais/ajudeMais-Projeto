package br.edu.ifpb.ajudeMais.service.event.mensageiro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.edu.ifpb.ajudeMais.service.storage.ImagemStorage;

/**
 * 
 * <p>
 * {@link MensageiroListener}
 * </p>
 * 
 * <p>
 * Classe utilizada para registro de eventos relacionados a mensageiro. Sempre
 * que um evento for chamado e o mesmo esteja registrado nesta classe, então é
 * executada a tarefa para este listener.
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@Component
public class MensageiroListener {

	/**
	 * 
	 */
	@Autowired
	private ImagemStorage imagemStorage;

	/**
	 * 
	 * <p>
	 * Salva a imagem em diretório final sempre que o evento
	 * {@link MensageiroEditEvent} for chamado.
	 * </p>
	 * 
	 * @param event
	 */
	@EventListener(condition = "#event.image")
	public void mensageiroEdited(MensageiroEditEvent event) {
		String foto = event.getMensageiro().getFoto().getNome();
		if (!imagemStorage.exists(foto)) {
			imagemStorage.save(foto);
			if (event.getImagemAntiga() != null) {
				imagemStorage.remove(event.getImagemAntiga());
			}
		}

	}
}
