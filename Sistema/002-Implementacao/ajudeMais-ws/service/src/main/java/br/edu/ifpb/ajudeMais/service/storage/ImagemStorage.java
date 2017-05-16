package br.edu.ifpb.ajudeMais.service.storage;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * <p>
 * {@link ImagemStorage}
 * </p>
 * 
 * <p>
 * Interface padrão para definição de operação para starage de imagem.
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
public interface ImagemStorage {

	/**
	 * 
	 * <p>
	 * Salva imagem em diretório temporario
	 * </p>
	 * 
	 * @param files
	 *            multiparte a ser salvo
	 * @return
	 */
	public String saveTmp(MultipartFile file);

	/**
	 * 
	 * <p>
	 * Obtém arquivo de diretório temporário
	 * </p>
	 * 
	 * @param nome
	 * @return
	 */
	public byte[] getTmp(String nome);

	/**
	 * 
	 * <p>
	 * Salva imagem no disco
	 * </p>
	 * 
	 * @param img
	 */
	public void save(String img);

	/**
	 * 
	 * <p>
	 * Recupera imagem do disco
	 * </p>
	 * 
	 * @param img
	 * @return
	 */
	public byte[] get(String img);

}
