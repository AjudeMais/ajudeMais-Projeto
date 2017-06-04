package br.edu.ifpb.ajudeMais.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import br.edu.ifpb.ajudeMais.domain.entity.Imagem;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.storage.ImagemStorage;
import br.edu.ifpb.ajudeMais.service.storage.ImagemStorageRunnable;

/**
 * 
 * <p>
 * {@link ImagemStorageRestService}
 * </p>
 * 
 * <p>
 * Classe utilizada para disponibilização de upload e recuperação de imagem,
 * utilziando sistemas de arquivos.
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
	 * POST /upload/imagem : Método responsável por fazer upload de imagem via
	 * {@link MultipartFile}. Por padrão o upload é salvo em diretório
	 * temporário. Para efetivação em local final deve-se mover para fora de
	 * tmp. ROLE: BASIC
	 * </p>
	 * 
	 * @param files parametro referente a imagem, inclui conjunto de bytes, nome
	 *        do arquivo e content type.
	 * @return nome da imagem salva em diretório temporário.
	 */
	@PostMapping
	public DeferredResult<Imagem> upload(@RequestParam(name = "file") MultipartFile file) {

		DeferredResult<Imagem> result = new DeferredResult<>();

		Thread thread = new Thread(new ImagemStorageRunnable(file, result, imagemStorage));
		thread.start();

		return result;
	}

	/**
	 * 
	 * <p>
	 * POST /upload/imagem/tmp/{name} : Recupera imagem salva em diretório
	 * temporário. Este método serve para recuperar imagem pré carregada, não
	 * salva em local final.
	 * </p>
	 * 
	 * @param name da imagem a ser recuperada
	 * @return imagem em bytes
	 * @throws AjudeMaisException 
	 */
	@GetMapping("/tmp/{name:.*}")
	public byte[] getTmpImage(@PathVariable String name) throws AjudeMaisException {
		return imagemStorage.getTmp(name);
	}

	/**
	 * 
	 * <p>
	 * POST /upload/imagem/{name} : recupera imagem em diretório final. Serve
	 * para recuperar imagem final salva.
	 * </p>
	 * 
	 * @param name
	 * @return imagem em bytes
	 * @throws AjudeMaisException 
	 */
	@GetMapping("/{name:.*}")
	public byte[] getImage(@PathVariable String name) throws AjudeMaisException {
		return imagemStorage.get(name);
	}
	
	
	/**
	 * 
	 * <p>
	 * DELETE /upload/imagem/tmp/{name} : remove imagem em TEMP. 
	 * Serve para remover imagens em Temp que não estão sendo utilizadas.
	 * </p>
	 * 
	 * @param name
	 * @return imagem em bytes
	 * @throws AjudeMaisException 
	 */
	@DeleteMapping("/tmp/{name:.*}")
	public void removeTmpImage(@PathVariable String name) throws AjudeMaisException {
		imagemStorage.removeTmp(name);
	}
}
