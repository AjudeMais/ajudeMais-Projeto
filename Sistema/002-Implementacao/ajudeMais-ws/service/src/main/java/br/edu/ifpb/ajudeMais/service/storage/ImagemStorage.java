package br.edu.ifpb.ajudeMais.service.storage;

import org.springframework.web.multipart.MultipartFile;

import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.exceptions.ImageErrorException;

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
	 * @throws AjudeMaisException
	 */
	public byte[] getTmp(String nome) throws AjudeMaisException;

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
	 * @throws ImageErrorException
	 */
	public byte[] get(String img) throws AjudeMaisException;

	/**
	 * 
	 * <p>
	 * Verifica se uma determinada imagem existe no sistema.
	 * </p>
	 * 
	 * @param img
	 * @return
	 */
	boolean exists(String img);

	/**
	 * 
	 * <p>
	 * Remove uma imagem do disco pelo nome.
	 * </p>
	 * 
	 * @param img
	 */
	void remove(String img);

	/**
	 * <p>
	 * Remove uma imagem de TEMP
	 * </p>
	 * 
	 * @param img
	 * @throws ImageErrorException 
	 */
	void removeTmp(String img) throws ImageErrorException;

}
