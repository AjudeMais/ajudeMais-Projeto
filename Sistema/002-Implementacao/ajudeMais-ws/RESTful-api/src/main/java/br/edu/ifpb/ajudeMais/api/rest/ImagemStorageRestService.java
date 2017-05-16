package br.edu.ifpb.ajudeMais.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import br.edu.ifpb.ajudeMais.domain.entity.Imagem;
import br.edu.ifpb.ajudeMais.service.storage.ImagemStorage;
import br.edu.ifpb.ajudeMais.service.storage.ImagemStorageRunnable;

/**
 * 
 * <p>{@link ImagemStorageRestService} </p>
 * 
 * <p>
 * Classe utilizada para disponibilização de upload de imagem.
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@RestController
@RequestMapping("/upload/imagem")
public class ImagemStorageRestService {
	
	@Autowired
	private ImagemStorage imagemStorage;
	
	/**
	 * 
	 * <p>
	 * </p>
	 * @param files
	 * @return
	 */
	@PostMapping
	public DeferredResult<Imagem> upload(@RequestParam(name="file") MultipartFile file) {
		
		DeferredResult<Imagem> result = new DeferredResult<>();
		
		Thread thread = new Thread(new ImagemStorageRunnable(file, result, imagemStorage));
		thread.start();
		
		return result;
	}

}
