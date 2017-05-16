package br.edu.ifpb.ajudeMais.service.storage;

import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import br.edu.ifpb.ajudeMais.domain.entity.Imagem;

/**
 * 
 * <p>{@link ImagemStorageRunnable} </p>
 * 
 * <p>
 * Classe utilizada para 
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
public class ImagemStorageRunnable implements Runnable{
	
	private MultipartFile file;
	
	private DeferredResult<Imagem> result;
	private ImagemStorage imagemStorage;
	

	/**
	 * 
	 * <p>
	 * </p>
	 *
	 * @param files
	 * @param result
	 * @param imagemStorage
	 */
	public ImagemStorageRunnable(MultipartFile file, DeferredResult<Imagem> result, ImagemStorage imagemStorage) {
		this.file = file;
		this.result = result;
		this.imagemStorage = imagemStorage;
	}

	/**
	 * 
	 */
	@Override
	public void run() {
		String contentType = file.getContentType();
		String fileName = imagemStorage.saveTmp(file);
		
		result.setResult(new Imagem(fileName, contentType));		
	}

}
